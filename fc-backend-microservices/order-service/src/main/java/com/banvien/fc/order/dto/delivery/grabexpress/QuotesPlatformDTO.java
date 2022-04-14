package com.banvien.fc.order.dto.delivery.grabexpress;

import java.util.List;

public class QuotesPlatformDTO {
    private Long customerId;
    private String device;
    private Long outletId;
    private Double latitude;
    private Double longitude;
    private String address;
    private List<PackagesDTO> packages;
    private Double srcLatitude;
    private Double srcLongitude;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PackagesDTO> getPackages() {
        return packages;
    }

    public void setPackages(List<PackagesDTO> packages) {
        this.packages = packages;
    }

    public Double getSrcLatitude() {
        return srcLatitude;
    }

    public void setSrcLatitude(Double srcLatitude) {
        this.srcLatitude = srcLatitude;
    }

    public Double getSrcLongitude() {
        return srcLongitude;
    }

    public void setSrcLongitude(Double srcLongitude) {
        this.srcLongitude = srcLongitude;
    }
}
