package com.banvien.fc.promotion.entity;

import javax.persistence.*;

@Entity
@Table(name = "promotioncustomertarget")
public class PromotionCustomerTargetEntity {

    private Long promotionCustomerTargetId;
    private Long outletPromotionId;
    private Long customerGroupId;

    public PromotionCustomerTargetEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotioncustomertargetid")
    public Long getPromotionCustomerTargetId() {
        return promotionCustomerTargetId;
    }

    public void setPromotionCustomerTargetId(Long promotionCustomerTargetId) {
        this.promotionCustomerTargetId = promotionCustomerTargetId;
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
    @Column(name = "customergroupid")
    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }
}