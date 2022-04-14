package com.banvien.fc.order.repository;

import com.banvien.fc.order.dto.payment.AuthenDTO;
import com.banvien.fc.order.dto.payment.CertificatesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@FeignClient(name = "payment-service")
@RequestMapping("/partner/grabpay")
public interface PaymentRepository {
    @PostMapping(value = "/easy/init")
    AuthenDTO generateAuthen(@RequestParam("orderTemporaryId") Long orderTemporaryId, @RequestParam("currency") String currency);

    @GetMapping(value = "/certificates")
    CertificatesDTO getCertificates(@RequestParam("outletId") Long outletId);

}
