package com.banvien.fc.account.dto;

import java.io.Serializable;

public class CustomerGroupDTO implements Serializable {
    private Long customerGroupId;
    private String groupName;

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
}
