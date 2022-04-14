package com.banvien.fc.order.dto;

import java.sql.Timestamp;

public class OrderTemporaryDTO {
    private Long ordertemporaryid;
    private Long customerid;
    private Double amount;
    private String orderoutletcode;
    private String status;
    private Long createdby;
    private Timestamp createdate;

    public Long getOrdertemporaryid() {
        return ordertemporaryid;
    }

    public void setOrdertemporaryid(Long ordertemporaryid) {
        this.ordertemporaryid = ordertemporaryid;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrderoutletcode() {
        return orderoutletcode;
    }

    public void setOrderoutletcode(String orderoutletcode) {
        this.orderoutletcode = orderoutletcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Long createdby) {
        this.createdby = createdby;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }
}
