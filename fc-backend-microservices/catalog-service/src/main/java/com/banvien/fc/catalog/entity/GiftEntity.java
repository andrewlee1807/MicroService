package com.banvien.fc.catalog.entity;

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
    private int status;     // 0 inactive , 1 : active
    private Integer totalReceivedPromotion;
//    private Integer totalReceivedPromotion;
    private Long quantityPromotion;
    private Timestamp expiredDate;

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
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name="totalreceivePromotion")
    public Integer getTotalReceivedPromotion() {
        return totalReceivedPromotion;
    }

    public void setTotalReceivedPromotion(Integer totalReceivedPromotion) {
        this.totalReceivedPromotion = totalReceivedPromotion;
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
}

