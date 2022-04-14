package com.banvien.fc.delivery.service;

import com.banvien.fc.delivery.dto.grabexpress.GEQuotesDTO;
import com.banvien.fc.delivery.dto.grabexpress.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name = "order-service")
@RequestMapping("/order/mobile/shoppingCart")
public interface OrderServiceProxy {
    @RequestMapping("/cartItems")
    public ResponseEntity<ResponseDTO<GEQuotesDTO>> getShoppingCartItems(@RequestParam(value = "customerId") Long customerId,
                                                                         @RequestParam(value = "device", required = false) String device,
                                                                         @RequestParam(value = "outletId") Long outletId);
}
