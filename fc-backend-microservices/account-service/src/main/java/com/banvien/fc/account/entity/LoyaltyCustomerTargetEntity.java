package com.banvien.fc.account.entity;

import javax.persistence.*;

@Entity
@Table(name = "loyaltycustomertarget")
public class LoyaltyCustomerTargetEntity {
    private long loyaltyCustomerTargetId;
    private long loyaltyOutletEventId;
    private Long customerGroupId;

    @Id
    @Column(name = "loyaltyCustomerTargetId", nullable = false)
    public long getLoyaltyCustomerTargetId() {
        return loyaltyCustomerTargetId;
    }

    public void setLoyaltyCustomerTargetId(long loyaltyCustomerTargetId) {
        this.loyaltyCustomerTargetId = loyaltyCustomerTargetId;
    }

    @Basic
    @Column(name = "loyaltyOutletEventId", nullable = false)
    public long getLoyaltyOutletEventId() {
        return loyaltyOutletEventId;
    }

    public void setLoyaltyOutletEventId(long loyaltyOutletEventId) {
        this.loyaltyOutletEventId = loyaltyOutletEventId;
    }

    @Basic
    @Column(name = "customerGroupId", nullable = true)
    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

}
