package com.banvien.fc.catalog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftDTO {
    private long giftId;
    private long outletId;
    private String name;
    private String image;
    private Boolean typePromotion;
    private int status;

    public GiftDTO() {
    }

    public GiftDTO(long giftId, String name) {
        this.giftId = giftId;
        this.name = name;
    }

    public long getGiftId() {
        return giftId;
    }

    public void setGiftId(long giftId) {
        this.giftId = giftId;
    }

    public long getOutletId() {
        return outletId;
    }

    public void setOutletId(long outletId) {
        this.outletId = outletId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getTypePromotion() {
        return typePromotion;
    }

    public void setTypePromotion(Boolean typePromotion) {
        this.typePromotion = typePromotion;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
