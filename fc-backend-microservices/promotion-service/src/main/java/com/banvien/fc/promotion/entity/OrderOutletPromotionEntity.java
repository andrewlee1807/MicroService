package com.banvien.fc.promotion.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "OrderOutletPromotion")
public class OrderOutletPromotionEntity implements Serializable {
    @Id
    @Column(name = "orderoutletpromotionid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "orderoutletid")
    private Long orderOutletId;
    @Basic
    @Column(name = "outletpromotionid")
    private Long outletPromotionId;

    public OrderOutletPromotionEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderOutletId() {
        return orderOutletId;
    }

    public void setOrderOutletId(Long orderOutletId) {
        this.orderOutletId = orderOutletId;
    }

    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }
}
