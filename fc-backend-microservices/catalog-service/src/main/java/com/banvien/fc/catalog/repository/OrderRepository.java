package com.banvien.fc.catalog.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@FeignClient(name = "order-service")
@RequestMapping("/order")
public interface OrderRepository {
    @RequestMapping("/quantitySkuAvail")
    Integer getProductSkuAvailability(@RequestParam(value = "productSkuId") Long id);
}
