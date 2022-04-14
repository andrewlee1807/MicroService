package com.banvien.fc.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RawDistributorDTO implements Serializable {
    public String DistributorCode;
    public String DistributorName;
    public String DistrbutorPhone;
    public String DistributorAddress;
    public Double Longtitude;
    public Double Latitude;
    public Integer Status;

    public String getDistributorCode() {
        return DistributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        DistributorCode = distributorCode;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public void setDistributorName(String distributorName) {
        DistributorName = distributorName;
    }

    public String getDistrbutorPhone() {
        return DistrbutorPhone;
    }

    public void setDistrbutorPhone(String distrbutorPhone) {
        DistrbutorPhone = distrbutorPhone;
    }

    public String getDistributorAddress() {
        return DistributorAddress;
    }

    public void setDistributorAddress(String distributorAddress) {
        DistributorAddress = distributorAddress;
    }

    public Double getLongtitude() {
        return Longtitude;
    }

    public void setLongtitude(Double longtitude) {
        Longtitude = longtitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }
}
