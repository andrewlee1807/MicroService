package com.banvien.fc.delivery.dto.grabexpress;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by son.nguyen on 7/26/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    private Long customerId;
    private Long outletId;
    //private UserDTO user;
    private String address;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

//    public UserDTO getUser() {
//        return user;
//    }
//
//    public void setUser(UserDTO user) {
//        this.user = user;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }
}
