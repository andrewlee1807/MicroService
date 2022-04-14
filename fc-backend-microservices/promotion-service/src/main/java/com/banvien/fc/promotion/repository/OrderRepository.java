package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.dto.OrderOutletDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@FeignClient(name = "order-service")
@RequestMapping("/order")
public interface OrderRepository {
    @GetMapping("/easy/totalQuantity/{customerId}/{outletId}/{numberOfDay}")
    public Integer getTotalQuantityByUserAndOutlet(@PathVariable("customerId") Long customerId, @PathVariable("outletId") Long outletId, @PathVariable("numberOfDay") int numberOfDay);

    @GetMapping("/easy/totalAmount/{customerId}/{outletId}/{numberOfDay}")
    public Double getTotalAmountByUserAndOutlet(@PathVariable("customerId") Long customerId, @PathVariable("outletId") Long outletId, @PathVariable("numberOfDay") int numberOfDay);

    @RequestMapping("/easy/firstOrder/{customerId}/{outletId}")
    public Boolean userFirstTimeOrderInOutlet(@PathVariable("customerId") Long customerId, @PathVariable("outletId") Long outletId);

    @GetMapping("/easy/orders/{customerId}/{outletId}")
    public List<OrderOutletDTO> getOrderOutletByCustomerAndOutlet(@PathVariable("customerId") Long customerId, @PathVariable("outletId") Long outletId);

    @RequestMapping("/countOrder")
    public Integer countByOutletOutletIdAndCustomerId(@RequestParam("outletId") Long outletId, @RequestParam("customerId") Long customerId);
}
