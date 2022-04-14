package com.banvien.fc.order;

import com.banvien.fc.common.service.NumberUtils;
import com.banvien.fc.order.dto.CartInfoMobileDTO;
import com.banvien.fc.order.dto.ProductDTO;
import com.banvien.fc.order.dto.ReductionDTO;
import com.banvien.fc.order.dto.rules.Enum.DiscountType;
import com.banvien.fc.order.entity.OrderOutletEntity;
import com.banvien.fc.order.entity.RawOrderStatusEntity;
import com.banvien.fc.order.repository.OrderOutletRepository;
import com.banvien.fc.order.repository.RawOrderStatusRepository;
import com.banvien.fc.order.service.SalesFlowService;
import com.banvien.fc.order.utils.CoreConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.tools.rmi.ObjectNotFoundException;
import org.codehaus.jettison.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.*;

@SpringBootTest
class OrderServiceApplicationTests {

    @Test
    void contextLoads1() {
        System.out.println("SO" + System.currentTimeMillis());
        System.out.println(UUID.randomUUID().toString());
    }

    @Test
    void testabc() {
        Timestamp startdate = new Timestamp(System.currentTimeMillis());
        System.out.println(startdate.toLocalDateTime().getDayOfMonth() + "/" + startdate.toLocalDateTime().getMonthValue() + "/" + startdate.toLocalDateTime().getYear());
        int k = 10;
    }

    @Test
    void contextLoads() {
//        EasyOrderSubmitDTO easyOrderSubmitDTO = new EasyOrderSubmitDTO();
//
//        easyOrderSubmitDTO.setCode("22062020329092");
//        easyOrderSubmitDTO.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//        easyOrderSubmitDTO.setTotalStoreDiscountPrice(0.0);
//        easyOrderSubmitDTO.setTotalPromotionDiscountPrice(0.0);
//        easyOrderSubmitDTO.setDeliveryPrice(0.0);
//        easyOrderSubmitDTO.setAmount(20.6);
//        easyOrderSubmitDTO.setReceiverName("Toan Truong");
//        easyOrderSubmitDTO.setPhoneNumber("+60909298172");
//        easyOrderSubmitDTO.setReceiverAddress("12 Ton Dan");
//        easyOrderSubmitDTO.setPaymentMethod("CHEQUE");
//        easyOrderSubmitDTO.setDeliveryMethod("Get it from the store");
//        easyOrderSubmitDTO.setShipDate(new Timestamp(System.currentTimeMillis()));
//
//        List<ListProductDTO> lst = new ArrayList<>();
//
//        ListProductDTO listProductDTO = new ListProductDTO();
//        listProductDTO.setProductOutletSkuId(10967L);
//        listProductDTO.setQuantity(2);
//
//        listProductDTO.setProductOutletSkuId(10947L);
//        listProductDTO.setQuantity(5);
//        lst.add(listProductDTO);
//
//        easyOrderSubmitDTO.setListProductDTOS(lst);
//        EasyOrderService easyOrderService = new EasyOrderService();
//        easyOrderService.submitOrders(easyOrderSubmitDTO);

    }

    class Sortbyroll implements Comparator<ReductionDTO> {
        // Used for sorting in descending order of
        // roll number
        public int compare(ReductionDTO a, ReductionDTO b) {
            return b.getPromotionType() - a.getPromotionType();
        }
    }

    @Test
    void sortListPromotion() {

        List<ReductionDTO> lstPromotionPass = new ArrayList<>();
        ReductionDTO reductionDTO = new ReductionDTO();
        reductionDTO.setPromotionType(1);
        lstPromotionPass.add(reductionDTO);
        ReductionDTO reductionDTO1 = new ReductionDTO();
        reductionDTO1.setPromotionType(2);
        lstPromotionPass.add(reductionDTO1);
        ReductionDTO reductionDTO2 = new ReductionDTO();
        reductionDTO2.setPromotionType(0);
        lstPromotionPass.add(reductionDTO2);
        ReductionDTO reductionDTO3 = new ReductionDTO();
        reductionDTO3.setPromotionType(3);
        lstPromotionPass.add(reductionDTO3);

        Collections.sort(lstPromotionPass, new Sortbyroll());
        System.out.println("Naturally Sorted List::" + lstPromotionPass);
        for (int i = 0; i < lstPromotionPass.size(); i++)
            System.out.println(lstPromotionPass.get(i).getPromotionType());
    }

    @Test
    void listItemInfoInCart() throws ObjectNotFoundException, JSONException {
        CartInfoMobileDTO cartInfoMobileDTO = new CartInfoMobileDTO();
        cartInfoMobileDTO.setDevice("df86d3bf4277af17");
        cartInfoMobileDTO.setOutletId(5235L);
        cartInfoMobileDTO.setDeliveryMethod("SHIP_TO_HOME");
        cartInfoMobileDTO.setTotalLoyaltyPoint(0);
        cartInfoMobileDTO.setRemainingTotalPrice(0D);
        cartInfoMobileDTO.setShipToAddress("123 Nguyễn Hữu Tiến, Tay Thanh, Tân Phú, Ho Chi Minh City, Vietnam");
//        cartInfoMobileDTO.setLatitude(BigDecimal.valueOf(10.8080204));
//        cartInfoMobileDTO.setLongitude(BigDecimal.valueOf(106.6259208));
//        OrderService orderService = new OrderService();
//        orderService.listCartItemsInfor(cartInfoMobileDTO);
    }

    @Test
    void formatDiscountPromotion2Json() throws Exception {
        ReductionDTO dto = new ReductionDTO();
        dto.setValueDiscount(12D);
        dto.setDiscountByProducts(new ArrayList<>());
        dto.setDiscountType(DiscountType.AMOUNT_OFF);
        dto.setDiscountForTarget(1L);
        dto.setPromotionType(1);
        dto.setMinValue(23D);


        ObjectMapper oMapper = new ObjectMapper();
        String tmp = oMapper.writeValueAsString(dto); // obj 2 json

        int k = 0;
    }

    @Test
    void testNumberUtils() {
        double doubleNumber = 77.091215;
        doubleNumber = NumberUtils.calculate(doubleNumber);
        doubleNumber = NumberUtils.calculate(doubleNumber);
        doubleNumber = NumberUtils.calculate(doubleNumber);
        doubleNumber = NumberUtils.calculate(doubleNumber);
        doubleNumber = NumberUtils.calculate(doubleNumber);
        System.out.println("number: " + NumberUtils.calculate(doubleNumber));

        System.out.println("string: " + NumberUtils.toString(NumberUtils.calculate(doubleNumber)));
    }

    @Autowired
    private SalesFlowService salesFlowService;
    @Autowired
    private OrderOutletRepository orderOutletRepository;
    @Autowired
    private RawOrderStatusRepository rawOrderStatusRepository;

    @Test
    void test() throws Exception {
//        {
//            Map<String, Object> params = new HashMap<>();
//            params.put("type", "Categories");
//            String rp = salesFlowService.sendRequest("/master-data", params);
//            int k = 10;
//        }
//        {
//            Map<String, Object> params = new HashMap<>();
//            params.put("type", "CancelOrder");
//            params.put("OrderNumber", "D0001ORD1");
//            String rp = salesFlowService.sendRequest("/orders", params);
//            int k = 10;
//        }
        {
            Map<String, Object> params = new HashMap<>();
            params.put("type", "BulkOrderStatus");
            params.put("StartDate", "29/12/2020");
            params.put("EndDate", "31/12/2020");
            String rp = salesFlowService.sendRequest("/orders", params);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> rs = mapper.readValue(rp, Map.class);
            if ("SUCCESS".equals(rs.get("response"))) {
                List orderStatus = (List) rs.get("OrderStatus");
                Timestamp now = new Timestamp(System.currentTimeMillis());
                orderStatus.forEach(item -> {
                    String code = ((Map) item).get("OrderNumber").toString();
                    String status = ((Map) item).get("OrderStatus").toString();
                    //save tmp table
                    RawOrderStatusEntity orderStatusEntity = new RawOrderStatusEntity();
                    orderStatusEntity.setOrderCode(code);
                    orderStatusEntity.setOrderStatus(status);
                    orderStatusEntity.setCreatedDate(now);
                    orderStatusEntity.setProcessStatus(CoreConstants.PROCESS_STATUS.WAITTING_FOR_UPDATE.getValue());
                    rawOrderStatusRepository.save(orderStatusEntity);
                    System.out.println(code + " : " + status);
                });

                List<RawOrderStatusEntity> rawOrderStatusEntities = rawOrderStatusRepository.findByProcessStatus();
                rawOrderStatusEntities.forEach(entity -> {
                    OrderOutletEntity orderOutletEntity = orderOutletRepository.findByCode(entity.getOrderCode());
                    if (orderOutletEntity == null) {
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
                        case "Cancelled":
                            orderOutletEntity.setStatus(CoreConstants.STATUS_CANCELED);
                            break;
                    }
                    orderOutletRepository.save(orderOutletEntity);
                    entity.setProcessStatus(CoreConstants.PROCESS_STATUS.UPDATED.getValue());
                    rawOrderStatusRepository.save(entity);
                });
            }
            int k = 10;
        }
//        {
//            Map<String, Object> params = new HashMap<>();
//            params.put("type", "OrderStatus");
//            params.put("OrderNumber", "D0001ORD1");
//            String rp = salesFlowService.sendRequest("/orders", params);
//            int k = 10;
//        }
//        {
//            Map<String, Object> params = new HashMap<>();
//            params.put("type", "CreateOrder");
//            params.put("OrderData", "{ \n" +
//                    "\"CompanyOrderNumber\": \"ORD1\", \n" +
//                    "\"SalesfloDistributorCode\": \"D0001\", \n" +
//                    "\"SalesfloStoreCode\": \"N0000001\", \n" +
//                    "\"OrderDetails\": \n" +
//                    "[ \n" +
//                    "{ \n" +
//                    "\"SalesfloSKUCode\": \"SKU000001\", \n" +
//                    "\"SKUQTYUnits\": 2, \n" +
//                    "\"SKUQTYCartons\": 1, \n" +
//                    "\"SKUQTYVirtualPack\": 0 \n" +
//                    "}, \n" +
//                    "{ \n" +
//                    "\"SalesfloSKUCode\": \"SKU000002\", \n" +
//                    "\"SKUQTYUnits\": 5, \n" +
//                    "\"SKUQTYCartons\": 2, \n" +
//                    "\"SKUQTYVirtualPack\": 0 \n" +
//                    "} \n" +
//                    "] \n" +
//                    "}\n");
//            String rp = salesFlowService.sendRequest("/orders", params);
//            int k = 10;
//        }
    }

    @Test
    void test2() throws Exception {
        List<ProductDTO> products = new ArrayList<>();
        {
            ProductDTO dto = new ProductDTO();
            dto.setProductOutletSkuId(44348l);
            dto.setQuantity(2);
            products.add(dto);
        }
        {
            ProductDTO dto = new ProductDTO();
            dto.setProductOutletSkuId(44349l);
            dto.setQuantity(1);
            products.add(dto);
        }
        {
            ProductDTO dto = new ProductDTO();
            dto.setProductOutletSkuId(44485l);
            dto.setQuantity(3);
            products.add(dto);
        }

        System.out.println(salesFlowService.createOrder(String.valueOf(System.currentTimeMillis()), "D70001646", "N00000011148", products));
    }

    @Test
    void test3() throws Exception{
        salesFlowService.bulkOrderStatus();
    }
}
