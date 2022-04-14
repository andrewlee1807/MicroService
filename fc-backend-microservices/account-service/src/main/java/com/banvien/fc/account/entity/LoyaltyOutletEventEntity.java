package com.banvien.fc.account.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "loyaltyoutletevent")
public class LoyaltyOutletEventEntity {
    private long loyaltyOutletEventId;
    private long outletId;
    private String status;
    private Integer point;
    private String name;
    private Integer maxPlay;
    private String targetType;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp notificationDate;
    private String notificationContent;
    private String thumbnail;
    private Integer maxPlayInOrder;
    private Timestamp createdDate;
    private String description;

    @Id
    @Column(name = "loyaltyOutletEventId", nullable = false)
    public long getLoyaltyOutletEventId() {
        return loyaltyOutletEventId;
    }

    public void setLoyaltyOutletEventId(long loyaltyOutletEventId) {
        this.loyaltyOutletEventId = loyaltyOutletEventId;
    }

    @Basic
    @Column(name = "outletId", nullable = false)
    public long getOutletId() {
        return outletId;
    }

    public void setOutletId(long outletId) {
        this.outletId = outletId;
    }

    @Basic
    @Column(name = "status", nullable = false, length = -1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "point", nullable = true)
    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    @Basic
    @Column(name = "name", nullable = true, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "maxPlay", nullable = true)
    public Integer getMaxPlay() {
        return maxPlay;
    }

    public void setMaxPlay(Integer maxPlay) {
        this.maxPlay = maxPlay;
    }

    @Basic
    @Column(name = "targetType", nullable = true, length = 250)
    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    @Basic
    @Column(name = "startDate", nullable = true)
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "endDate", nullable = true)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "notificationDate", nullable = true)
    public Timestamp getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Timestamp notificationDate) {
        this.notificationDate = notificationDate;
    }

    @Basic
    @Column(name = "notificationContent", nullable = true, length = -1)
    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    @Basic
    @Column(name = "thumbnail", nullable = true, length = 255)
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Basic
    @Column(name = "maxPlayInOrder", nullable = true)
    public Integer getMaxPlayInOrder() {
        return maxPlayInOrder;
    }

    public void setMaxPlayInOrder(Integer maxPlayInOrder) {
        this.maxPlayInOrder = maxPlayInOrder;
    }

    @Basic
    @Column(name = "createdDate", nullable = false)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
