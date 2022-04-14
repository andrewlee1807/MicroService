package com.banvien.fc.promotion.dto;

import java.io.Serializable;

public class CustomerGroupDTO implements Serializable {
    private Long customerGroupId;
    private OutletDTO outlet;
    private String groupName;
    private int totalCustomer;
    private PricingDTO pricing;

    public OutletDTO getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletDTO outlet) {
        this.outlet = outlet;
    }

    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getTotalCustomer() {
        return totalCustomer;
    }

    public void setTotalCustomer(int totalCustomer) {
        this.totalCustomer = totalCustomer;
    }

    public PricingDTO getPricing() {
        return pricing;
    }

    public void setPricing(PricingDTO pricing) {
        this.pricing = pricing;
    }
}
