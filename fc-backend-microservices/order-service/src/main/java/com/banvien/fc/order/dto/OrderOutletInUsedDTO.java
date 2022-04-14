package com.banvien.fc.order.dto;

import java.io.Serializable;

public class OrderOutletInUsedDTO implements Serializable {
    private Long orderOutletId;
    private String orderCode;

    public Long getOrderOutletId() {
        return orderOutletId;
    }

    public void setOrderOutletId(Long orderOutletId) {
        this.orderOutletId = orderOutletId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
