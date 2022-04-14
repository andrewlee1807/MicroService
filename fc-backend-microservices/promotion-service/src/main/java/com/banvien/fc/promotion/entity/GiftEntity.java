package com.banvien.fc.promotion.entity;

import javax.persistence.*;

@Entity
@Table(name = "gift")
public class GiftEntity {
    private long giftId;
    private long outletId;
    private String name;
    private String image;
    private Integer totalReceivedPromotion;

    public GiftEntity() {
    }

    @Id
    @Column(name = "giftid")
    @Basic
    public long getGiftId() {
        return giftId;
    }

    public void setGiftId(long giftId) {
        this.giftId = giftId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name="outletId")
    public long getOutletId() {
        return outletId;
    }

    public void setOutletId(long outletId) {
        this.outletId = outletId;
    }

    @Basic
    @Column(name="totalreceivepromotion")
    public Integer getTotalReceivedPromotion() {
        return totalReceivedPromotion;
    }

    public void setTotalReceivedPromotion(Integer totalReceived) {
        this.totalReceivedPromotion = totalReceived;
    }
}
