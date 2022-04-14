package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "OrderOutletPromotion")
public class OrderOutletPromotionEntity implements Serializable {

    private Long orderOutletPromotionId;
    private Long orderoutletId;
    private Long outletPromotionId;
    private String promotionDiscount;

    @Id
    @Column(name = "orderoutletpromotionid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOrderOutletPromotionId() {
        return orderOutletPromotionId;
    }

    public void setOrderOutletPromotionId(Long orderOutletPromotionId) {
        this.orderOutletPromotionId = orderOutletPromotionId;
    }

    @Basic
    @Column(name = "orderoutletid")
    public Long getOrderoutletId() {
        return orderoutletId;
    }

    public void setOrderoutletId(Long orderoutletId) {
        this.orderoutletId = orderoutletId;
    }

    @Basic
    @Column(name = "outletpromotionid")
    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }

    @Basic
    @Column(name = "promotiondiscount")
    public String getPromotionDiscount() {
        return promotionDiscount;
    }

    public void setPromotionDiscount(String promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }
}
