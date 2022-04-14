package com.banvien.fc.email.entity;

import javax.persistence.*;
import java.time.Instant;

/**
 * Created by son.nguyen on 7/7/2020.
 */
@Table(name = "mailtracking")
@Entity
public class MailTracking {
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
    @Id
    @Column(name = "mailtrackingid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getMailTrackingId() {
        return mailTrackingId;
    }

    public void setMailTrackingId(Long mailTrackingId) {
        this.mailTrackingId = mailTrackingId;
    }

    @Basic
    @Column(name = "recipient")
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Basic
    @Column(name = "cc")
    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    @Basic
    @Column(name = "bcc")
    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    @Basic
    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "createddate")
    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "sentdate")
    public Instant getSentDate() {
        return sentDate;
    }

    public void setSentDate(Instant sentDate) {
        this.sentDate = sentDate;
    }
}
