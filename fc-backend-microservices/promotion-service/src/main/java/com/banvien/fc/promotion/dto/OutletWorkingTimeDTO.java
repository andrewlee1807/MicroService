package com.banvien.fc.promotion.dto;

import java.io.Serializable;
import java.sql.Time;

public class OutletWorkingTimeDTO implements Serializable {
    private Long outletWorkingTimeId;
    private Time openTime;
    private Time closeTime;
    private boolean openMonday;
    private boolean openTuesday;
    private boolean openWednesday;
    private boolean openThursday;
    private boolean openFriday;
    private boolean openSaturday;
    private boolean openSunday;

    public OutletWorkingTimeDTO() {
    }

    public Long getOutletWorkingTimeId() {
        return outletWorkingTimeId;
    }

    public void setOutletWorkingTimeId(Long outletWorkingTimeId) {
        this.outletWorkingTimeId = outletWorkingTimeId;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    public boolean isOpenMonday() {
        return openMonday;
    }

    public void setOpenMonday(boolean openMonday) {
        this.openMonday = openMonday;
    }

    public boolean isOpenTuesday() {
        return openTuesday;
    }

    public void setOpenTuesday(boolean openTuesday) {
        this.openTuesday = openTuesday;
    }

    public boolean isOpenWednesday() {
        return openWednesday;
    }

    public void setOpenWednesday(boolean openWednesday) {
        this.openWednesday = openWednesday;
    }

    public boolean isOpenThursday() {
        return openThursday;
    }

    public void setOpenThursday(boolean openThursday) {
        this.openThursday = openThursday;
    }

    public boolean isOpenFriday() {
        return openFriday;
    }

    public void setOpenFriday(boolean openFriday) {
        this.openFriday = openFriday;
    }

    public boolean isOpenSaturday() {
        return openSaturday;
    }

    public void setOpenSaturday(boolean openSaturday) {
        this.openSaturday = openSaturday;
    }

    public boolean isOpenSunday() {
        return openSunday;
    }

    public void setOpenSunday(boolean openSunday) {
        this.openSunday = openSunday;
    }

}
