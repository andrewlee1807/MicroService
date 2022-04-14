package com.banvien.fc.order.service;

import com.banvien.fc.common.util.CommonUtils;
import com.banvien.fc.order.dto.ProductDTO;
import com.banvien.fc.order.entity.OrderOutletEntity;
import com.banvien.fc.order.entity.ProductOutletSkuEntity;
import com.banvien.fc.order.entity.RawOrderStatusEntity;
import com.banvien.fc.order.entity.SalesFlowHistoryEntity;
import com.banvien.fc.order.repository.OrderOutletRepository;
import com.banvien.fc.order.repository.ProductOutletSkuRepository;
import com.banvien.fc.order.repository.RawOrderStatusRepository;
import com.banvien.fc.order.repository.SalesFlowHistoryRepository;
import com.banvien.fc.order.utils.CoreConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SalesFlowService {

    @Autowired
    private Environment env;
    @Autowired
    private SalesFlowHistoryRepository salesFlowHistoryRepository;
    @Autowired
    private OrderOutletRepository orderOutletRepository;
    @Autowired
    private RawOrderStatusRepository rawOrderStatusRepository;
    @Autowired
    private ProductOutletSkuRepository productOutletSkuRepository;

    public String sendRequest(String uri, Map<String, Object> params) {
        try {
            String host = env.getProperty("salesflow.host", "http://localhost:9090");
            Map<String, String> header = new HashMap<>();
            header.put("Content-Type", "application/x-www-form-urlencoded");
            header.put("AccessKey", env.getProperty("salesflow.AccessKey", "MTYwNTgxNjgzN19SWkR5T05pNmJwaXlqRmtvWDJraGVRPT0="));
            header.put("SecretKey", env.getProperty("salesflow.SecretKey", "UlpEeU9OaTZicGl5akZrb1gya2hlUT09"));
            header.put("X-API-KEY", env.getProperty("salesflow.X-API-KEY", "vOHr2X7EDe6QM3HYj6nj42zwhw1jTpFE1Tc9aGKr"));
            header.put("CompanyCode", env.getProperty("salesflow.CompanyCode", "efoods4"));
            HttpResponse<String> response = CommonUtils.sendPostRequest(host, uri, header, params);
            if (response != null) {
                SalesFlowHistoryEntity historyEntity = new SalesFlowHistoryEntity();
                historyEntity.setUri(uri);
                historyEntity.setStatus(response.getStatus());
                historyEntity.setResponse(response.getBody());
                historyEntity.setSendTime(new Timestamp(System.currentTimeMillis()));
                ObjectMapper mapper = new ObjectMapper();
                historyEntity.setRequestBody(mapper.writeValueAsString(params));
                salesFlowHistoryRepository.save(historyEntity);
            }
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //orderId
    public String createOrder(String orderId, String outletCode, String buyerId, List<ProductDTO> products) throws JsonProcessingException, JSONException {
        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, List<ProductOutletSkuEntity>> data = findSameProductOutlet(productOutletSkuRepository.findByProductOutletSkuIdIn(products.stream().map(ProductDTO::getProductOutletSkuId).collect(Collectors.toList())));
        data.forEach((key, value) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("SalesfloSKUCode", key);
            item.put("SKUQTYVirtualPack", 0);
            value.forEach(entity -> {
                Integer quantity = products.stream().filter(x -> x.getProductOutletSkuId().equals(entity.getProductOutletSkuId())).collect(Collectors.toList()).get(0).getQuantity();
                if (entity.getTitle().startsWith("UNIT")) {
                    item.put("SKUQTYUnits", quantity);
                }
                if (entity.getTitle().startsWith("CARTON")) {
                    item.put("SKUQTYCartons", quantity);
                }
            });
            items.add(item);
        });
        Map<String, Object> orderData = new HashMap<>();
        orderData.put("CompanyOrderNumber", orderId);
        orderData.put("SalesfloDistributorCode", outletCode);
        orderData.put("SalesfloStoreCode", buyerId);
        orderData.put("OrderDetails", items);

        Map<String, Object> params = new HashMap<>();
        params.put("type", "CreateOrder");
        ObjectMapper mapper = new ObjectMapper();
        params.put("OrderData", mapper.writeValueAsString(orderData));
        String rp = this.sendRequest("/orders", params);
        JSONObject rs = new JSONObject(rp);
        if ("SUCCESS".equals(rs.get("response"))) {
            return rs.get("OrderNumber").toString();
        } else {
            return null;
        }
    }

    private Map<String, List<ProductOutletSkuEntity>> findSameProductOutlet(List<ProductOutletSkuEntity> entities) {
        Map<String, List<ProductOutletSkuEntity>> rs = new HashMap<>();
        for (ProductOutletSkuEntity entity : entities) {
            if (!rs.containsKey(entity.getProductOutlet().getCode())) {
                rs.put(entity.getProductOutlet().getCode(), new ArrayList<>());
            }
            rs.get(entity.getProductOutlet().getCode()).add(entity);
        }
        return rs;
    }

    public boolean cancelOrder(String orderCode) throws JsonProcessingException {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "CancelOrder");
        params.put("OrderNumber", orderCode);
        String rp = this.sendRequest("/orders", params);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> rs = mapper.readValue(rp, Map.class);
        return "SUCCESS".equals(rs.get("response"));
    }

    public void bulkOrderStatus() throws JsonProcessingException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String today = now.toLocalDateTime().getDayOfMonth() + "/" + now.toLocalDateTime().getMonthValue() + "/" + now.toLocalDateTime().getYear();
        Map<String, Object> params = new HashMap<>();
        params.put("type", "BulkOrderStatus");
        params.put("StartDate", today);
        params.put("EndDate", today);
        String rp = this.sendRequest("/orders", params);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> rs = mapper.readValue(rp, Map.class);
        if ("SUCCESS".equals(rs.get("response"))) {
            List orderStatus = (List) rs.get("OrderStatus");
            orderStatus.forEach(item -> {
                String code = ((Map) item).get("OrderNumber").toString();
                String status = ((Map) item).get("Status").toString();
                //save tmp table
                RawOrderStatusEntity orderStatusEntity = new RawOrderStatusEntity();
                orderStatusEntity.setOrderCode(code);
                orderStatusEntity.setOrderStatus(status);
                orderStatusEntity.setCreatedDate(now);
                orderStatusEntity.setProcessStatus(CoreConstants.PROCESS_STATUS.WAITTING_FOR_UPDATE.getValue());
                rawOrderStatusRepository.save(orderStatusEntity);
                System.out.println(code + " : " + status);
            });
        }
        updateOrderStatus();
    }

    private void updateOrderStatus() {
        List<RawOrderStatusEntity> rawOrderStatusEntities = rawOrderStatusRepository.findByProcessStatus();
        rawOrderStatusEntities.forEach(entity -> {
            OrderOutletEntity orderOutletEntity = orderOutletRepository.findByCode(entity.getOrderCode());
            if (orderOutletEntity == null) {
                System.out.println("Order not found! - code: " + entity.getOrderCode());
                entity.setProcessStatus(CoreConstants.PROCESS_STATUS.FAIL.getValue());
                rawOrderStatusRepository.save(entity);
                return;
            }
            switch (entity.getOrderStatus()) {
                case "Open":
                    orderOutletEntity.setStatus(CoreConstants.WAITING_FOR_CONFIRMATION);
                    break;
                case "Serviced":
                    orderOutletEntity.setStatus(CoreConstants.STATUS_SUCCESS);
                    break;
                case "Canelled":
                    orderOutletEntity.setStatus(CoreConstants.STATUS_CANCELED);
                    break;
            }
            orderOutletRepository.save(orderOutletEntity);
            entity.setProcessStatus(CoreConstants.PROCESS_STATUS.UPDATED.getValue());
            rawOrderStatusRepository.save(entity);
        });
    }
}
