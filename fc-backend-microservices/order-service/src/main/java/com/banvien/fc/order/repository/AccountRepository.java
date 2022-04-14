package com.banvien.fc.order.repository;

import com.banvien.fc.order.dto.CustomerDTO;
import com.banvien.fc.order.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

/**
 * Created by son.nguyen on 7/13/2020.
 */
@Repository
@FeignClient(name = "account-service")
@RequestMapping("/account")
public interface AccountRepository {
    @PostMapping("/customer/getOrCreate")
    ResponseEntity<CustomerDTO> getOrCreateCustomer(@RequestBody CustomerDTO customerDTO);

    @PostMapping("/welcomeGift")
    void welcomeGift(@RequestBody Long loyaltyMemberId);

    @GetMapping("/getDefaultUser")
    ResponseEntity<UserDTO> getDefaultUser(@RequestParam("countryId") long countryId);

    @PostMapping("/isUserExist")
    ResponseEntity<Boolean> isUserExist(@RequestParam("phoneNumber") String fullPhoneNumber, @RequestParam("country") String prefixCountry, @RequestParam("countryId") long countryId);

}
