package com.banvien.fc.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by son.nguyen on 7/26/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    private Long customerId;
    private Long outletId;
    private UserDTO user;
    private String address;
    private String prefixCountry;
    private Boolean loyaltyMemberCheck;
    private Double longitude;
    private Double latitude;
    private String customerCode;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

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

    public String getPrefixCountry() {
        return prefixCountry;
    }

    public void setPrefixCountry(String prefixCountry) {
        this.prefixCountry = prefixCountry;
    }

    public Boolean getLoyaltyMemberCheck() {
        return loyaltyMemberCheck;
    }

    public void setLoyaltyMemberCheck(Boolean loyaltyMemberCheck) {
        this.loyaltyMemberCheck = loyaltyMemberCheck;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
