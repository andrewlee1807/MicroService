package com.banvien.fc.order.dto.delivery.grabexpress;

import java.io.Serializable;
import java.util.List;

public class QuotesRequestDTO implements Serializable {
    private Long customerId;
    private String device;
    private Long outletId;
    private String serviceType;
    private List<PackagesDTO> packages;
    private OriginDTO origin;
    private DestinationDTO destination;
    private String merchantOrderID;

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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<PackagesDTO> getPackages() {
        return packages;
    }

    public void setPackages(List<PackagesDTO> packages) {
        this.packages = packages;
    }

    public OriginDTO getOrigin() {
        return origin;
    }

    public void setOrigin(OriginDTO origin) {
        this.origin = origin;
    }

    public DestinationDTO getDestination() {
        return destination;
    }

    public void setDestination(DestinationDTO destination) {
        this.destination = destination;
    }

    public String getMerchantOrderID() {
        return merchantOrderID;
    }

    public void setMerchantOrderID(String merchantOrderID) {
        this.merchantOrderID = merchantOrderID;
    }
}
