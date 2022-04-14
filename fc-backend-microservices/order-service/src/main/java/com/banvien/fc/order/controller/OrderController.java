package com.banvien.fc.order.controller;

import com.banvien.fc.order.dto.EasyOrderSubmitDTO;
import com.banvien.fc.order.dto.order.CommonOrderSubmitDTO;
import com.banvien.fc.order.entity.CustomerEntity;
import com.banvien.fc.order.entity.OrderOutletEntity;
import com.banvien.fc.order.repository.CustomerRepository;
import com.banvien.fc.order.repository.OrderOutletRepository;
import com.banvien.fc.order.repository.ProductOutletSkuRepository;
import com.banvien.fc.order.service.EasyOrderService;
import com.banvien.fc.order.service.OrderService;
import com.banvien.fc.order.service.SalesFlowService;
import com.banvien.fc.order.status.StatusResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController extends ApplicationObjectSupport {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EasyOrderService easyOrderService;
    @Autowired
    private ProductOutletSkuRepository productOutletSkuRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderOutletRepository orderOutletRepository;
    @Autowired
    private SalesFlowService salesFlowService;

    @RequestMapping("/sync")
    public ResponseEntity syncOrder(@RequestBody String body) {
        Gson gson = new Gson();
        List<CommonOrderSubmitDTO> commonDTOs = gson.fromJson(body, new TypeToken<ArrayList<CommonOrderSubmitDTO>>() {
        }.getType());
        List<EasyOrderSubmitDTO> easyOrders = commonDTOs.stream().map(dto -> {
            EasyOrderSubmitDTO easyOrderDTO = new EasyOrderSubmitDTO();
            easyOrderDTO.setOutletId(dto.getOutletId());
            easyOrderDTO.setSalesManUserId(dto.getSalesManUserId());
            easyOrderDTO.setNote(dto.getNote());
            easyOrderDTO.setSource(dto.getSource());
            easyOrderDTO.setCredit(dto.getCredit() != null ? dto.getCredit() : false);
            easyOrderDTO.setDebt(dto.getDebt());
            easyOrderDTO.setOrderCode(dto.getCode());
            // todo: change submit customerId to userId from front-end
            easyOrderDTO.setUserId(dto.getCustomerInformation().getUserId());
            if (dto.getCustomerInformation().getUserId() == null && dto.getCustomerInformation().getCustomerId() != null) {
                CustomerEntity customerEntity = customerRepository.findByCustomerId(dto.getCustomerInformation().getCustomerId());
                if (customerEntity != null) {
                    // todo: change property userId -> user in class CustomerEntity
                    easyOrderDTO.setUserId(customerEntity.getUserId().getUserId());
                    easyOrderDTO.setCustomerId(dto.getCustomerInformation().getCustomerId());
                }
            }
            easyOrderDTO.setPrefixPhoneNumber(dto.getInformationMobile().getPrefixCountry());
            easyOrderDTO.setName(dto.getInformationMobile().getReceiverName());
            easyOrderDTO.setPhone(dto.getInformationMobile().getReceiverPhone());
            easyOrderDTO.setPaymentMethod(dto.getInformationMobile().getPaymentMethod());
            easyOrderDTO.setDeliveryMethod(dto.getInformationMobile().getDeliveryMethod());
            easyOrderDTO.setAddress(dto.getInformationMobile().getReceiverAddress());
            easyOrderDTO.setEmail(dto.getCustomerInformation().getEmail());
            easyOrderDTO.setProducts(dto.getProductsSummary());
//            easyOrderDTO.setTotalStoreDiscountPrice(dto.getTotalStoreDiscount());               //base on Mr.Khang said
            easyOrderDTO.setTotalStoreDiscountPrice(0D); // fix // pending
            easyOrderDTO.setWarehouseId(dto.getWarehouseId());
            easyOrderDTO.setPromotionCode(dto.getPromotionCode());
            return easyOrderDTO;
        }).collect(Collectors.toList());
        List<StatusResponse> statusResponses = easyOrderService.syncOrders(easyOrders);
        System.out.println("======= Sync submit order successful! =======");
        return ResponseEntity.ok(statusResponses);
    }

    @RequestMapping("/submitted/edit")
    public void editOrderAfterSubmitted(@RequestParam("orderId") Long orderId) throws ObjectNotFoundException {
        System.out.println("======Edit Order After Submitted=======");
        orderService.editOrderAfterSubmitted(orderId);
    }

    @RequestMapping("/submitted/cancel")
    public void cancelOrderAfterSubmitted(@RequestParam("orderId") Long orderId) {
        System.out.println("======Cancel Order After Submitted=======");
        orderService.cancelPromotionInOrder(orderId);
    }

    @RequestMapping("/quantitySkuAvail")
    public Integer getProductSkuAvailability(@RequestParam(value = "productSkuId") Long id) {
        return easyOrderService.getQuantityProductSkuAvailability(id);
    }

    @RequestMapping("/countOrder")
    public Integer countByOutletOutletIdAndCustomerId(@RequestParam("outletId") Long outletId, @RequestParam("customerId") Long customerId) {
        return orderOutletRepository.countByOutletOutletIdAndCustomerId(outletId, customerId);
    }

    @RequestMapping("/temporary")
    public Map<String, Object> getAmountOrderTemporary(@RequestParam("orderTemporaryId") Long orderTemporaryId) {
        Map<String, Object> rs = new HashMap<>();
        try {
            rs = orderService.getAmountOrderTemporary(orderTemporaryId);
        } catch (Exception e) {
            return null;
        }
        return rs;
    }

    @RequestMapping("/get")
    public Map<String, Object> getOrderOutletInfo(@RequestParam("code") String code) {
        Map<String, Object> rs = new HashMap<>();
        try {
            OrderOutletEntity orderOutletEntity = orderOutletRepository.findByCode(code);
            if (orderOutletEntity != null) {
                rs.put("outletId", orderOutletEntity.getOutlet().getOutletId());
            }
        } catch (Exception e) {
            return null;
        }
        return rs;
    }

    @RequestMapping("/saleFlow/cancel")
    public boolean isCancel(@RequestParam("orderCode") String code) {
        try {
            return salesFlowService.cancelOrder(code);
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
