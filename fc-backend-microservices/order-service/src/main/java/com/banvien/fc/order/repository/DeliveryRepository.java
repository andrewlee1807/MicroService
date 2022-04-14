package com.banvien.fc.order.repository;

import com.banvien.fc.order.dto.ResponseDTO;
import com.banvien.fc.order.dto.delivery.grabexpress.QuotesPlatformDTO;
import com.banvien.fc.order.dto.delivery.grabexpress.QuotesResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
@FeignClient(name = "delivery-service")
@RequestMapping("/delivery/grabexpress")
public interface DeliveryRepository {

    @RequestMapping("/quotes")
    ResponseDTO<QuotesResponseDTO> getQuotes(@RequestBody QuotesPlatformDTO quotesPlatformDTO);
}
