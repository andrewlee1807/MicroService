package com.banvien.fc.promotion.entity;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "syncversiontracking")
public class SyncVersionTrackingEntity {
    private Long syncVersionTrackingId;
    private Long outletId;
    private Long userId;
    private String typeSync;
    private Timestamp syncDate;
    private String token;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getSyncVersionTrackingId() {
        return syncVersionTrackingId;
    }

    public void setSyncVersionTrackingId(Long syncVersionTrackingId) {
        this.syncVersionTrackingId = syncVersionTrackingId;
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

    public String getTypeSync() {
        return typeSync;
    }

    public void setTypeSync(String typeSync) {
        this.typeSync = typeSync;
    }

    public Timestamp getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Timestamp syncDate) {
        this.syncDate = syncDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
