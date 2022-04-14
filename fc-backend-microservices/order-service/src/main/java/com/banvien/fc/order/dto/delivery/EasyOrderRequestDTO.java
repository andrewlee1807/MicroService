package com.banvien.fc.order.dto.delivery;

import com.banvien.fc.order.dto.delivery.grabexpress.PackagesDTO;

import java.math.BigDecimal;
import java.util.List;

public class EasyOrderRequestDTO {
    private String paymentType;
    private String address;
    private Long outletId;
    private List<PackagesDTO> packages;
    private BigDecimal srcLatitude;
    private BigDecimal srcLongitude;
    private BigDecimal desLatitude;
    private BigDecimal desLongitude;


    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public List<PackagesDTO> getPackages() {
        return packages;
    }

    public void setPackages(List<PackagesDTO> packages) {
        this.packages = packages;
    }

    public BigDecimal getSrcLatitude() {
        return srcLatitude;
    }

    public void setSrcLatitude(BigDecimal srcLatitude) {
        this.srcLatitude = srcLatitude;
    }

    public BigDecimal getSrcLongitude() {
        return srcLongitude;
    }

    public void setSrcLongitude(BigDecimal srcLongitude) {
        this.srcLongitude = srcLongitude;
    }

    public BigDecimal getDesLatitude() {
        return desLatitude;
    }

    public void setDesLatitude(BigDecimal desLatitude) {
        this.desLatitude = desLatitude;
    }

    public BigDecimal getDesLongitude() {
        return desLongitude;
    }

    public void setDesLongitude(BigDecimal desLongitude) {
        this.desLongitude = desLongitude;
    }
}
