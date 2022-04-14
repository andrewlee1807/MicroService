package com.banvien.fc.promotion.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "outletpromotioncustomer")
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class OutletPromotionCustomerEntity {
    @Column(name = "outletpromotioncustomerid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customerid")
    private Long customerId;
    @Column(name = "outletPromotionid")
    private Long outletPromotionId;
    private Integer quantity;
    @Column(name = "createddate")
    private Timestamp createdDate;

    public OutletPromotionCustomerEntity() {
    }

    public OutletPromotionCustomerEntity(Long customerId, Long outletPromotionId, Integer quantity, Timestamp createdDate) {
        this.customerId = customerId;
        this.outletPromotionId = outletPromotionId;
        this.quantity = quantity;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
