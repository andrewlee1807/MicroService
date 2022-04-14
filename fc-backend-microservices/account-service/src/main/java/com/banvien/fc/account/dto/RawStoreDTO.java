package com.banvien.fc.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RawStoreDTO implements Serializable {
    public String StoreCode;
    public String StoreName;
    public String CompanyStoreCode;
    public String Birthday;
    public String CountryCode;
    public String Email;
    public String Address;
    public String CustomerGroup;
    public String SalesmanCode;
    public Double Latitude;
    public Double Longtitude;
    public String DistributorCode;
    public String PhoneNumber;
    public Integer Status;

    public String getStoreCode() {
        return StoreCode;
    }

    public void setStoreCode(String storeCode) {
        StoreCode = storeCode;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getCompanyStoreCode() {
        return CompanyStoreCode;
    }

    public void setCompanyStoreCode(String companyStoreCode) {
        CompanyStoreCode = companyStoreCode;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCustomerGroup() {
        return CustomerGroup;
    }

    public void setCustomerGroup(String customerGroup) {
        CustomerGroup = customerGroup;
    }

    public String getSalesmanCode() {
        return SalesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        SalesmanCode = salesmanCode;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongtitude() {
        return Longtitude;
    }

    public void setLongtitude(Double longtitude) {
        Longtitude = longtitude;
    }

    public String getDistributorCode() {
        return DistributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        DistributorCode = distributorCode;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
