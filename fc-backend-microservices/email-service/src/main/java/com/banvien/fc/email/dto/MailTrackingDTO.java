package com.banvien.fc.email.dto;

import java.io.Serializable;
import java.time.Instant;

public class MailTrackingDTO implements Serializable {
    private Long mailTrackingId;
    private String recipient;
    private String cc;
    private String bcc;
    private String subject;
    private String body;
    private Integer status;
    private String note;
    private Instant createdDate;
    private Instant sentDate;
    private Instant planDate;
    private String planDateDim;
    private String fileAttachments;

    public Long getMailTrackingId() {
        return mailTrackingId;
    }

    public void setMailTrackingId(Long mailTrackingId) {
        this.mailTrackingId = mailTrackingId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getSentDate() {
        return sentDate;
    }

    public void setSentDate(Instant sentDate) {
        this.sentDate = sentDate;
    }

    public Instant getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Instant planDate) {
        this.planDate = planDate;
    }

    public String getPlanDateDim() {
        return planDateDim;
    }

    public void setPlanDateDim(String planDateDim) {
        this.planDateDim = planDateDim;
    }

    public String getFileAttachments() {
        return fileAttachments;
    }

    public void setFileAttachments(String fileAttachments) {
        this.fileAttachments = fileAttachments;
    }
}
