package com.banvien.fc.order.entity;

import javax.persistence.*;

@Entity
@Table(
        name = "loyaltyorderhistory"
)
public class LoyaltyOrderHistoryEntity {
    private Long loyaltyOrderHistoryId;
    private String orderOutletCode;
    private Long loyaltyOutletEventId;
    private Integer point;
    private Long customerId;

    public LoyaltyOrderHistoryEntity() {
    }

    @Id
    @Column(
            name = "loyaltyorderhistoryid"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    public Long getLoyaltyOrderHistoryId() {
        return this.loyaltyOrderHistoryId;
    }

    public void setLoyaltyOrderHistoryId(Long loyaltyOrderHistoryId) {
        this.loyaltyOrderHistoryId = loyaltyOrderHistoryId;
    }

    @Basic
    @Column(
            name = "orderoutletcode"
    )
    public String getOrderOutletCode() {
        return this.orderOutletCode;
    }

    public void setOrderOutletCode(String orderOutletCode) {
        this.orderOutletCode = orderOutletCode;
    }

    @Basic
    @Column(
            name = "loyaltyoutleteventid"
    )
    public Long getLoyaltyOutletEventId() {
        return loyaltyOutletEventId;
    }

    public void setLoyaltyOutletEventId(Long loyaltyOutletEventId) {
        this.loyaltyOutletEventId = loyaltyOutletEventId;
    }

    @Basic
    @Column(
            name = "point"
    )
    public Integer getPoint() {
        return this.point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    @Basic
    @Column(
            name = "customerid"
    )
    public Long getCustomerId() {
        return customerId;
    }


    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
