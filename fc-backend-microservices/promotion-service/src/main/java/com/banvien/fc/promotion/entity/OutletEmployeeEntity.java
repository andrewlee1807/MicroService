package com.banvien.fc.promotion.entity;

import javax.persistence.*;

@Entity
@Table(name = "outletemployee")
public class OutletEmployeeEntity {
    private Long outletEmployeeId;
    private Long outletId;
    private Long userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOutletEmployeeId() {
        return outletEmployeeId;
    }

    public void setOutletEmployeeId(Long outletEmployeeId) {
        this.outletEmployeeId = outletEmployeeId;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
