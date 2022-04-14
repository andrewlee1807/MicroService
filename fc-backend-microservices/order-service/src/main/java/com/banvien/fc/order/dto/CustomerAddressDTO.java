package com.banvien.fc.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class CustomerAddressDTO implements Serializable {
    private Long customerAddressId;
    private CustomerDTO customer;
    private String address;
    private String addressName;
    private String type;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Boolean isDefault;

    public CustomerAddressDTO() {

    }

    public CustomerAddressDTO(Long customerAddressId, String address, String addressName, BigDecimal longitude, BigDecimal latitude, String type, Boolean isDefault) {
        this.customerAddressId = customerAddressId;
        this.address = address;
        this.addressName = addressName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
        this.isDefault = isDefault;
    }

    public Long getCustomerAddressId() {
        return customerAddressId;
    }

    public void setCustomerAddressId(Long customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
}
