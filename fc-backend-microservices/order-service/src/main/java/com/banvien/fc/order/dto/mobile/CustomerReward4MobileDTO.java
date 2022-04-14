package com.banvien.fc.order.dto.mobile;

import java.io.Serializable;

public class CustomerReward4MobileDTO implements Serializable {
    private Long customerRewardId;
    private String giftName;
    private String giftImage;
    private String expiryDate;
    private String availableLeft;
    private Long outletId;
    private String outletName;
    private String outletAddress;

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getOutletAddress() {
        return outletAddress;
    }

    public void setOutletAddress(String outletAddress) {
        this.outletAddress = outletAddress;
    }

    public Long getCustomerRewardId() {
        return customerRewardId;
    }

    public void setCustomerRewardId(Long customerRewardId) {
        this.customerRewardId = customerRewardId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getAvailableLeft() {
        return availableLeft;
    }

    public void setAvailableLeft(String availableLeft) {
        this.availableLeft = availableLeft;
    }

    public String getGiftImage() {
        return giftImage;
    }

    public void setGiftImage(String giftImage) {
        this.giftImage = giftImage;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }
}
