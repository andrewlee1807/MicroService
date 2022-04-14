package com.banvien.fc.promotion.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
@FeignClient(name = "email-service")
@RequestMapping("/email")
public interface EmailRepository {

    @PostMapping("/promotion-end-notify")
    String send(Long outletPromotionId);
}
