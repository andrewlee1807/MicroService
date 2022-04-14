package com.banvien.fc.email.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import javax.persistence.*;
@Entity
@Table(name = "gift")

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class GiftEntity {
    private long giftId;
    private long outletId;
    private String name;
    private String image;
    private Boolean typePromotion;
    private int status;
    private Integer totalReceived;
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
    @Column(name="totalreceive")
    public Integer getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(Integer totalReceived) {
        this.totalReceived = totalReceived;
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
