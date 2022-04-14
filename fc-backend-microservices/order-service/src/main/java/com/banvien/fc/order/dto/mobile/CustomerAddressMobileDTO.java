package com.banvien.fc.order.dto.mobile;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerAddressMobileDTO implements Serializable {
    private Long customerAddressId;
    private String address;
    private String addressName;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String type;
    private Boolean isDefault;

    public CustomerAddressMobileDTO(){}

    public CustomerAddressMobileDTO(Long customerAddressId, String address, String addressName, BigDecimal longitude,
                                    BigDecimal latitude, String type, Boolean isDefault) {
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
