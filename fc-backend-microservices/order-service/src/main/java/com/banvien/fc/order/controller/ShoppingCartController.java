package com.banvien.fc.order.controller;

import com.banvien.fc.common.rest.errors.BadRequestException;
import com.banvien.fc.common.util.RestUtil;
import com.banvien.fc.order.dto.*;
import com.banvien.fc.order.dto.delivery.GEQuotesDTO;
import com.banvien.fc.order.dto.mobile.*;
import com.banvien.fc.order.dto.rules.Enum.ApiStatusCode;
import com.banvien.fc.order.repository.OutletRepository;
import com.banvien.fc.order.service.DeliveryService;
import com.banvien.fc.order.service.OrderService;
import com.banvien.fc.order.utils.JsonIgnoreUtil;
import javassist.tools.rmi.ObjectNotFoundException;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/order/mobile/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DeliveryService deliveryService;

    @RequestMapping(value = {"/listCartItem"})
    public Object listCartItems(@RequestBody SubmitMobileDTO dto) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ShoppingCartSummaryMobileDTO> shoppingCartDTOs = null;
            Integer totalCartItems = 0;
            shoppingCartDTOs = orderService.cartSummaryItems(dto.getUserId(), null, dto.getWarehouseId() == null ? null : dto.getWarehouseId(), dto.getDevice(),dto.getSalesManUserId());
            //totalCartItems = shoppingCartManagementLocalBean.countCartItems(customerId, device);
            for (ShoppingCartSummaryMobileDTO shoppingCartSummaryMobileDTO : shoppingCartDTOs) {
                List<ShoppingCartItemMobileDTO> lsProductOrderItemDTOS = shoppingCartSummaryMobileDTO.getProductItems();
                List<CustomerReward4MobileDTO> customerRewardGift = shoppingCartSummaryMobileDTO.getCustomerRewards();
                Integer totalItemReward = (customerRewardGift != null ? customerRewardGift.size() : 0);
                // Handle Customer Order in different Outlet
                Integer totalItemsEachCart = 0;
                if (lsProductOrderItemDTOS != null && lsProductOrderItemDTOS.size() > 0)
                    for (ShoppingCartItemMobileDTO shoppingCartItemMobileDTO : lsProductOrderItemDTOS) {
                        if (shoppingCartItemMobileDTO.getShoppingCartId() != null)
                            totalItemsEachCart++;
                    }
                shoppingCartSummaryMobileDTO.setTotalItem(totalItemsEachCart + totalItemReward);
                totalCartItems += (totalItemsEachCart + totalItemReward);
            }

            //find default location of user
            Map<String, Object> rs = orderService.findAddressDefault4Mobile(dto.getUserId());
            map.put("status", ApiStatusCode.SUCCESS.getValue());
            map.put("message", ApiStatusCode.SUCCESS.getKey());
            map.put("result", shoppingCartDTOs);
            map.put("totalCartItems", totalCartItems);
            map.put("defaultAddress", rs.get("address"));
            map.put("hasGift", rs.get("hasGift"));
        } catch (Exception e) {
            map.put("status", ApiStatusCode.NOT_FOUND.getValue());
            map.put("message", e.getMessage());
        }
        return map;
    }


    @PostMapping("/submit")
    public Object submitOrder4Mobile(@RequestBody OrderInformationMobileDTO dto, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            //Get userId from token
            Long userId = RestUtil.getUserIdFromToken(request);

            //just for test
            System.out.println("UserID :" + userId);
            SubmitOrderResponseDTO responseDTO = orderService.submitOrder4Mobile(dto, userId);
            map.put("response", responseDTO);
            map.put("status", ApiStatusCode.SUCCESS.getValue());
            map.put("message", ApiStatusCode.SUCCESS.getKey());
        } catch (Exception e) {
            map.put("status", ApiStatusCode.NOT_FOUND.getValue());
            map.put("message",e.getMessage());
            e.printStackTrace();
        }
        return JsonIgnoreUtil.convertData(map);
    }

    @PostMapping("/cartItemsInfor")
    public Object listCartItemsInfor(@RequestBody CartInfoMobileDTO dto, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            Long userId;
            //get userId from token or dto
            if (Objects.isNull(dto.getUserId())) {
                userId = RestUtil.getUserIdFromToken(request);
            } else {
                userId = dto.getUserId();
            }
            dto.setUserId(userId);
            Object[] objects = orderService.listCartItemsInfor(dto);
            Integer totalCartItems = (Integer) objects[0];
            List<ShoppingCartSummaryMobileDTO> shoppingCartDTOs = (List<ShoppingCartSummaryMobileDTO>) objects[1];
            DeliveryValue4MobileDTO deliveryValue4MobileDTO = (DeliveryValue4MobileDTO) objects[2];

            map.put("status", ApiStatusCode.SUCCESS.getValue());
            map.put("message", ApiStatusCode.SUCCESS.getKey());
            map.put("totalCartItems", totalCartItems);
            map.put("result", shoppingCartDTOs);
            map.put("deliveryValue", deliveryValue4MobileDTO);
        } catch (Exception e) {
            map.put("status", ApiStatusCode.NOT_FOUND.getValue());
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("/cartItems")
    public ResponseEntity<ResponseDTO<GEQuotesDTO>> getShoppingCartItems(@RequestParam(value = "customerId") Long customerId,
                                                                         @RequestParam(value = "device", required = false) String device,
                                                                         @RequestParam(value = "outletId") Long outletId) {
        ResponseDTO responseDTO = new ResponseDTO();
        GEQuotesDTO response = new GEQuotesDTO();
        try {
            List<ShoppingCartDTO> shoppingCartDTOS = deliveryService.findCartItemsByCustomer(customerId, device, outletId);
            OutletDTO origin = deliveryService.findOutletById(outletId);
            response.setPackages(shoppingCartDTOS);
            response.setOrigin(origin);
            responseDTO.setStatus(200);
            responseDTO.setMessage("");
            responseDTO.setBody(response);
            return new ResponseEntity<ResponseDTO<GEQuotesDTO>>(responseDTO, null, HttpStatus.OK);
        } catch (Exception e) {
            responseDTO.setStatus(400);
            responseDTO.setMessage(e.getMessage());
            return new ResponseEntity<ResponseDTO<GEQuotesDTO>>(null, null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/grab/checkout")
    public Map<String, Object> checkoutShoppingCart(@RequestBody OrderInformationMobileDTO dto) throws JSONException, ObjectNotFoundException {
        return orderService.calculateTempPrice4GrabPay(dto);
    }

    @PostMapping("/grab/submit")
    public Object submitOrderFromGrabPay(@RequestParam String state,
                                         @RequestParam String txId,
                                         @RequestParam String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            orderService.submitOrder4GrabPay(state, txId, token);
            map.put("status", ApiStatusCode.SUCCESS.getValue());
            map.put("message", ApiStatusCode.SUCCESS.getKey());
        } catch (Exception e) {
            map.put("status", ApiStatusCode.NOT_FOUND.getValue());
            map.put("message", ApiStatusCode.NOT_FOUND.getKey());
            e.printStackTrace();
        }
        return map;
    }
}
