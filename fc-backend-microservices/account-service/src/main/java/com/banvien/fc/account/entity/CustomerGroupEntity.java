package com.banvien.fc.account.entity;

import javax.persistence.*;

@Entity
@Table(name = "customergroup")
public class CustomerGroupEntity {
    private Long customerGroupId;
    private Long outletId;
    private String groupName;
    private Long pricingId;

    @Id
    @Column(name = "customerGroupId", nullable = false)
    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    @Basic
    @Column(name = "outletId", nullable = false)
    public long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    @Basic
    @Column(name = "groupName", nullable = false, length = -1)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "pricingId", nullable = true)
    public Long getPricingId() {
        return pricingId;
    }

    public void setPricingId(Long pricingId) {
        this.pricingId = pricingId;
    }

}
