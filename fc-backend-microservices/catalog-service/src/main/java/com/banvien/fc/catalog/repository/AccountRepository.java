package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Repository
@FeignClient(name = "account-service")
@RequestMapping("/account")
public interface AccountRepository {
    @PostMapping("/salesflow/{countryId}/user")
    ResponseEntity<UserDTO> addOrUpdateUser(@RequestBody UserDTO userDTO, @PathVariable("countryId") Long countryId);

    @PostMapping("/salesflow/{countryId}/customer")
    void updateSalesFlowCustomers(@RequestBody Map<String, String> bodyContent, @PathVariable("countryId") Long countryId);
}
