package com.banvien.fc.order.entity;

import javax.persistence.*;

@Entity
@Table(name = "customergroup")
public class CustomerGroupEntity {
    private Long customerGroupId;
    private Long outletId;
    private String groupName;
    private Long pricingId;

    @Basic
    @Column(name = "outletid")
    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    @Id
    @Column(name = "customergroupid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    @Basic
    @Column(name = "groupname")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "pricingid")
    public Long getPricingId() {
        return pricingId;
    }

    public void setPricingId(Long pricingId) {
        this.pricingId = pricingId;
    }
}
