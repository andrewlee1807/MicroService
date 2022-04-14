package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "outletpromotioncustomer")
public class OutletPromotionCustomerEntity {
    private Long outletPromotionCustomerId;
    private Long outletPromotionId;
    private Long customerId;
    private Integer quantity;
    private Timestamp createdDate;

    public OutletPromotionCustomerEntity(){

    }

    public OutletPromotionCustomerEntity(Long customerId, Long outletPromotionId, Integer quantity, Timestamp createdDate) {
        this.customerId = customerId;
        this.outletPromotionId = outletPromotionId;
        this.quantity = quantity;
        this.createdDate = createdDate;
    }

    @Id
    @Column(name = "outletpromotioncustomerid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOutletPromotionCustomerId() {
        return outletPromotionCustomerId;
    }

    public void setOutletPromotionCustomerId(Long outletPromotionCustomerId) {
        this.outletPromotionCustomerId = outletPromotionCustomerId;
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
    @Column(name = "customerid")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "createddate")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
