package com.banvien.fc.order.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "notification")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class NotificationEntity implements Serializable {

    @Id
    @Column(name = "notificationid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "type")
    private String type;

    @Column(name = "sentdate")
    private Timestamp sentDate;

    @Column(name = "createddate")
    private Timestamp createdDate;

    @Column(name = "title")
    private String title;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "notifytemplateid")
    private Long notifyTemplateId;

    @Column(name = "ScheduledDate")
    private Timestamp scheduledDate;

    @Column(name = "note")
    private String note;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "outletid")
    private Long outlet;

    @Column(name = "outletpromotionid")
    private Long outletPromotionId;

    @Column(name = "loyaltyoutleteventid")
    private Long loyaltyOutletEventId;

    @Column(name = "targettype")
    private String targetType;

    @Column(name = "targetid")
    private Long targetId;

    @Column(name = "sendbymanual")
    private Boolean sendByManual;

    @Basic
    @Column(name = "lang")
    private String lang;

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getSentDate() {
        return sentDate;
    }

    public void setSentDate(Timestamp sentDate) {
        this.sentDate = sentDate;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNotifyTemplateId() {
        return notifyTemplateId;
    }

    public void setNotifyTemplateId(Long notifyTemplateId) {
        this.notifyTemplateId = notifyTemplateId;
    }

    public Timestamp getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Timestamp scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getOutlet() {
        return outlet;
    }

    public void setOutlet(Long outlet) {
        this.outlet = outlet;
    }

    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }

    public Long getLoyaltyOutletEventId() {
        return loyaltyOutletEventId;
    }

    public void setLoyaltyOutletEventId(Long loyaltyOutletEventId) {
        this.loyaltyOutletEventId = loyaltyOutletEventId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Boolean getSendByManual() {
        return sendByManual;
    }

    public void setSendByManual(Boolean sendByManual) {
        this.sendByManual = sendByManual;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}