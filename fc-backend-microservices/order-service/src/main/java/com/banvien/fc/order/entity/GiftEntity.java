package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "gift")
public class GiftEntity {
    private long giftId;
    private long outletId;
    private String name;
    private String image;
    private Boolean typePromotion;
    private Long quantityPromotion;
    private Timestamp expiredDate;
    private Integer status; // GIFT_OUT_STOCK = 2, ACTIVE_STATUS = 1
    private Integer totalReceivedPromotion;

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
    @Column(name="typepromotion")
    public Boolean getTypePromotion() {
        return typePromotion;
    }

    public void setTypePromotion(Boolean typePromotion) {
        this.typePromotion = typePromotion;
    }

    @Basic
    @Column(name="quantitypromotion")
    public Long getQuantityPromotion() {
        return quantityPromotion;
    }

    public void setQuantityPromotion(Long quantity) {
        this.quantityPromotion = quantity;
    }

    @Basic
    @Column(name="expireddate")
    public Timestamp getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Timestamp expireDate) {
        this.expiredDate = expireDate;
    }

    @Basic
    @Column(name="status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
