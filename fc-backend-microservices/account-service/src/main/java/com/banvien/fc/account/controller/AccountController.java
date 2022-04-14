package com.banvien.fc.account.controller;

import com.banvien.fc.account.dto.CustomerDTO;
import com.banvien.fc.account.dto.CustomerGroupDTO;
import com.banvien.fc.account.dto.UserDTO;
import com.banvien.fc.account.service.CustomerService;
import com.banvien.fc.common.util.Constants;
import com.banvien.fc.common.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by son.nguyen on 7/26/2020.
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @PostMapping("/customer/getOrCreate")
    public ResponseEntity<CustomerDTO> getOrCreateUser(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.getOrCreate(customerDTO));
    }

    @PostMapping("/salesflow/{countryId}/user")
    public ResponseEntity<UserDTO> addOrUpdateUser(@RequestBody UserDTO userDTO, @PathVariable("countryId") Long countryId) {
        return ResponseEntity.ok(customerService.addOrUpdateUser(userDTO, Constants.USER_GROUP_OUTLET, new Timestamp(System.currentTimeMillis()), null, false));
    }

    @PostMapping("/salesflow/{countryId}/customer")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateSalesFlowCustomers(@RequestBody Map<String, String> bodyMap, @PathVariable("countryId") Long countryId) {
        customerService.updateSalesFlowCustomers(bodyMap.get(Constants.DATA), countryId);
    }

    @GetMapping("/getDefaultUser")
    public ResponseEntity getDefaultUser(@RequestParam("countryId")long countryId) {
        return ResponseEntity.ok(customerService.getDefaultUser(countryId));
    }

    @PostMapping("/isUserExist")
    public ResponseEntity isUserExist(@RequestParam("phoneNumber") String fullPhoneNumber, @RequestParam("country") String prefixCountry, @RequestParam("countryId") long countryId) {
        return ResponseEntity.ok(customerService.isExist(fullPhoneNumber, prefixCountry, countryId));
    }

    @GetMapping("/customer/groups")
    public ResponseEntity<List<CustomerGroupDTO>> getCustomerGroups(@RequestParam(value = "outletId", required = false) Long outletId,
                                                                    HttpServletRequest request) {
        if (outletId == null) {
            outletId = RestUtil.getOutletIdFromToken(request);
        }
        return ResponseEntity.ok(customerService.getCustomerGroups(outletId));
    }

    @PostMapping("/welcomeGift")
    public void welcomeGift(@RequestBody Long loyaltyMemberId) {
        customerService.welcomingGift(loyaltyMemberId);
    }

    @GetMapping("/customer/findCustomerOutlet")
    public ResponseEntity findCustomerByOutlet(@RequestParam(value = "outletId", required = false) Long outletId,
                                               @RequestParam("key") String key,
                                               Pageable pageable, HttpServletRequest request) {
        if (outletId == null) {
            outletId = RestUtil.getOutletIdFromToken(request);
        }
        if (outletId == null) {
            return ResponseEntity.badRequest().body("Can not found outlet");
        }
        if (key == null || key.length() == 0 || key.contains("^") || key.contains("|") || key.contains("&") || key.contains("#") || key.contains("%")) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(customerService.findUserByOutlet(outletId, key.toLowerCase(), pageable));
    }

    @PostMapping("/phone/invalid")
    public ResponseEntity checkInvalidPhoneNumber(@RequestParam("prefix") String prefix,@RequestParam("phone") String phoneNumber){
        return ResponseEntity.ok(customerService.invalidPhoneNumber(prefix,phoneNumber));
    }

    @Autowired
    private CustomerService customerService;
}
