package com.banvien.fc.order.dto.mobile;

import java.io.Serializable;

public class OutletPromotionMobileDTO implements Serializable {
    private Long outletPromotionId;
    private String promotionType;
    private String masterPromotionTitle;
    private Integer limitPlayer;
    private String description;
    private String image;
    private Integer maxQuantityCanBuy;
    private Integer timesApplied;
    private Integer totalGift;
    private Integer remainGift;

    public String getMasterPromotionTitle() {
        return masterPromotionTitle;
    }

    public void setMasterPromotionTitle(String masterPromotionTitle) {
        this.masterPromotionTitle = masterPromotionTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }

    public Integer getLimitPlayer() {
        return limitPlayer;
    }

    public void setLimitPlayer(Integer limitPlayer) {
        this.limitPlayer = limitPlayer;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getMaxQuantityCanBuy() {
        return maxQuantityCanBuy;
    }

    public void setMaxQuantityCanBuy(Integer maxQuantityCanBuy) {
        this.maxQuantityCanBuy = maxQuantityCanBuy;
    }

    public Integer getTotalGift() {
        return totalGift;
    }

    public void setTotalGift(Integer totalGift) {
        this.totalGift = totalGift;
    }

    public Integer getRemainGift() {
        return remainGift;
    }

    public void setRemainGift(Integer remainGift) {
        this.remainGift = remainGift;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public Integer getTimesApplied() {
        return timesApplied;
    }

    public void setTimesApplied(Integer timesApplied) {
        this.timesApplied = timesApplied;
    }
}
