package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "appsetting")
@Entity
public class AppSettingEntity implements Serializable {

    private Long appSettingId;
    private String key;
    private String value;
    private boolean status;
    private Long outletId;

    @Id
    @Column(name = "appSettingId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getAppSettingId() {
        return appSettingId;
    }

    public void setAppSettingId(Long appSettingId) {
        this.appSettingId = appSettingId;
    }

    @Column(name = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "status")
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
