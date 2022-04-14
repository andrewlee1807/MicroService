package com.banvien.fc.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SendMailDTO implements Serializable {
    private String mailTemplate;
    private String[] recipients;
    private String[] cc;
    private String[] bcc;
    private Map<String, Object> sender;
    private Boolean sendNow;
    private Instant planDate;
    private Integer triggerType;
    private Long notificationId;
    private Long ownerId;

    public String[] getRecipients() {
        return recipients;
    }

    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public String getMailTemplate() {
        return mailTemplate;
    }

    public void setMailTemplate(String mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

    public Boolean getSendNow() {
        return sendNow;
    }

    public void setSendNow(Boolean sendNow) {
        this.sendNow = sendNow;
    }

    public Map<String, Object> getSender() {
        return sender;
    }

    public void setSender(Map<String, Object> sender) {
        this.sender = sender;
    }

    public Instant getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Instant planDate) {
        this.planDate = planDate;
    }

    public Integer getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Integer triggerType) {
        this.triggerType = triggerType;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
