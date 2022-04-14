package com.banvien.fc.order.dto.delivery;

import java.io.Serializable;

public class AppSettingDTO implements Serializable {


    private Long appSettingId;
    private String key;
    private String value;
    private boolean status;
    private Long outletId;

    public AppSettingDTO() {
    }

    public Long getAppSettingId() {
        return appSettingId;
    }

    public void setAppSettingId(Long appSettingId) {
        this.appSettingId = appSettingId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }
}
