package com.banvien.fc.promotion.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "datainactivetracking")
public class DataInActiveTrackingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="datainactivetrackingid")
    private Long id;
    @Column(name ="inactiveid")
    private Long inactiveId;    // outletPromotion, product, category, brand,...
    private String type;
    @Column(name ="updatedate")
    private Timestamp updateDate;
    @Column(name ="outletid")
    private Long outletId;

    public DataInActiveTrackingEntity() {
    }

    public DataInActiveTrackingEntity(Long inactiveId, String type, Timestamp updateDate, Long outletId) {
        this.inactiveId = inactiveId;
        this.type = type;
        this.updateDate = updateDate;
        this.outletId = outletId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInactiveId() {
        return inactiveId;
    }

    public void setInactiveId(Long inactiveId) {
        this.inactiveId = inactiveId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }
}
