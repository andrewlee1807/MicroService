package com.banvien.fc.order.repository;


import com.banvien.fc.order.dto.SendMailDTO;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
@FeignClient(name = "email-service")
@RequestMapping("/email")
public interface EmailRepository {
    @PostMapping(path = "/send")
    String sendMail(SendMailDTO dto);
}
