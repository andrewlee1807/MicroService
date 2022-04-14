package com.banvien.fc.order.repository;


import com.banvien.fc.order.dto.EasyOrderSubmitDTO;
import com.banvien.fc.order.dto.OutletPromotionDTO;
import com.banvien.fc.order.dto.rules.DiscountDTO;
import com.banvien.fc.order.entity.PromotionPerOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
@FeignClient(name = "promotion-service")
@RequestMapping("/promotion")
public interface PromotionRepository {
    @PostMapping("/apply")
    PromotionPerOrderDTO getPromotionDiscountPriceByEasyOrder(@RequestBody EasyOrderSubmitDTO easyOrderSubmitDTO);

    @PostMapping("/easyOrder/presuppose")
    List<OutletPromotionDTO> getPromotionByEasyOrder(@RequestBody EasyOrderSubmitDTO easyOrderSubmitDTO);

    @PostMapping("/order/unapply")
    void restoreRemain2OutletPromotion(@RequestParam("orderOutletId") Long orderOutletId, @RequestParam("customerId") Long customerId);

    @GetMapping("/easyOrder/getByEasyOrderCode")
    DiscountDTO getPromotionByEasyOrderCode(@RequestParam("easyOrderCode") String easyOrderCode);

    @RequestMapping("/checkProductPromotion")
    ResponseEntity<boolean[]> getPromotionProduct(@RequestBody String body);
}
