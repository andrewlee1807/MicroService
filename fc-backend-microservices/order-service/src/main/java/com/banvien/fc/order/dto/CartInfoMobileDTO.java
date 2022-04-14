package com.banvien.fc.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartInfoMobileDTO implements Serializable {
    private Long customerId;
    private Long outletId;
    private Long userId;
    private Long warehouseId;
    private Long salesManUserId;
    private String deliveryMethod;
    private Integer totalLoyaltyPoint;
    private Double remainingTotalPrice;
    private String device;
    private String shipToAddress;
    private Double longitude;
    private Double latitude;
    private String promotionCode;
    private Double pointRate;


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Integer getTotalLoyaltyPoint() {
        return totalLoyaltyPoint;
    }

    public void setTotalLoyaltyPoint(Integer totalLoyaltyPoint) {
        this.totalLoyaltyPoint = totalLoyaltyPoint;
    }

    public Double getRemainingTotalPrice() {
        return remainingTotalPrice;
    }

    public void setRemainingTotalPrice(Double remainingTotalPrice) {
        this.remainingTotalPrice = remainingTotalPrice;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public Double getPointRate() {
        return pointRate;
    }

    public void setPointRate(Double pointRate) {
        this.pointRate = pointRate;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getSalesManUserId() {
        return salesManUserId;
    }

    public void setSalesManUserId(Long salesManUserId) {
        this.salesManUserId = salesManUserId;
    }
}
