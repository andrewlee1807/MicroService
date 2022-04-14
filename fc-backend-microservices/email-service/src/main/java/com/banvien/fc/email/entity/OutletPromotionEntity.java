package com.banvien.fc.email.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "outletpromotion")
public class OutletPromotionEntity implements Serializable {
    private Long outletPromotionId;
    private Timestamp notifySendDate;
    private Timestamp notifySentDate;
    private String title;
    private String notifyContent;
    private Long outletId;
    private Boolean status;
    private String newPromotionJson;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }

    public String getNewPromotionJson() {
        return newPromotionJson;
    }

    public void setNewPromotionJson(String newPromotionJson) {
        this.newPromotionJson = newPromotionJson;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getNotifySendDate() {
        return notifySendDate;
    }

    public void setNotifySendDate(Timestamp notifySendDate) {
        this.notifySendDate = notifySendDate;
    }

    public Timestamp getNotifySentDate() {
        return notifySentDate;
    }

    public void setNotifySentDate(Timestamp notifySentDate) {
        this.notifySentDate = notifySentDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
