package com.banvien.fc.promotion.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "loyaltymember")
public class LoyaltyMemberEntity {

    @Id
    @Column(name = "loyaltymemberid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loyaltyMemberId;

    @Column(name = "customerid")
    private Long customerId;

    @Column(name = "outletid")
    private Long outletId;


    @Column(name = "customergroupid")
    private Long customerGroupId;

    public LoyaltyMemberEntity() {
    }

    public Long getLoyaltyMemberId() {
        return loyaltyMemberId;
    }

    public void setLoyaltyMemberId(Long loyaltyMemberId) {
        this.loyaltyMemberId = loyaltyMemberId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }


    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }
}
