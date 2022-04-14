package com.banvien.fc.promotion.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class PricingDTO implements Serializable {

    private Long pricingId;
    private String name;
    private OutletDTO outlet;
    private Timestamp startedDate;
    private Timestamp endDate;
    private Integer status;

    public Long getPricingId() {
        return pricingId;
    }

    public void setPricingId(Long pricingId) {
        this.pricingId = pricingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Timestamp startedDate) {
        this.startedDate = startedDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public OutletDTO getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletDTO outlet) {
        this.outlet = outlet;
    }
}
