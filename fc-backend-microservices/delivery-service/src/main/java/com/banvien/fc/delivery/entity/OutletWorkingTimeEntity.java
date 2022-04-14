package com.banvien.fc.delivery.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Time;

@Table(name = "outletworkingtime")
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class OutletWorkingTimeEntity {

    private Long outletWorkingTimeId;

    @Column(name = "outletworkingtimeid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOutletWorkingTimeId() {
        return outletWorkingTimeId;
    }

    public void setOutletWorkingTimeId(Long outletWorkingTimeId) {
        this.outletWorkingTimeId = outletWorkingTimeId;
    }

    private Time openTime;

    @Column(name="openTime")
    @Basic
    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    private Time closeTime;

    @Column(name="closeTime")
    @Basic
    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    private boolean openMonday;

    @Column(name = "openMonday")
    @Basic
    public boolean isOpenMonday() {
        return openMonday;
    }

    public void setOpenMonday(boolean openMonday) {
        this.openMonday = openMonday;
    }

    private boolean openTuesday;

    @Column(name = "openTuesday")
    @Basic
    public boolean isOpenTuesday() {
        return openTuesday;
    }

    public void setOpenTuesday(boolean openTuesday) {
        this.openTuesday = openTuesday;
    }

    private boolean openWednesday;

    @Column(name = "openWednesday")
    @Basic
    public boolean isOpenWednesday() {
        return openWednesday;
    }

    public void setOpenWednesday(boolean openWednesday) {
        this.openWednesday = openWednesday;
    }

    private boolean openThursday;

    @Column(name = "openThursday")
    @Basic
    public boolean isOpenThursday() {
        return openThursday;
    }

    public void setOpenThursday(boolean openThursday) {
        this.openThursday = openThursday;
    }

    private boolean openFriday;

    @Column(name = "openFriday")
    @Basic
    public boolean isOpenFriday() {
        return openFriday;
    }

    public void setOpenFriday(boolean openFriday) {
        this.openFriday = openFriday;
    }

    private boolean openSaturday;

    @Column(name = "openSaturday")
    @Basic
    public boolean isOpenSaturday() {
        return openSaturday;
    }

    public void setOpenSaturday(boolean openSaturday) {
        this.openSaturday = openSaturday;
    }

    private boolean openSunday;

    @Column(name = "openSunday")
    @Basic
    public boolean isOpenSunday() {
        return openSunday;
    }

    public void setOpenSunday(boolean openSunday) {
        this.openSunday = openSunday;
    }

    public OutletWorkingTimeEntity() {
    }
}
