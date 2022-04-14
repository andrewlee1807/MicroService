package com.banvien.fc.email.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by son.nguyen on 7/7/2020.
 */
@Entity
@Table(name = "mailtemplate")
public class MailTemplateEntity {
    private Long mailTemplateId;
    private String templateName;
    private String subject;
    private String body;
    private Integer status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    @Id
    @Column(name = "mailtemplateid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getMailTemplateId() {
        return mailTemplateId;
    }

    public void setMailTemplateId(Long mailTemplateId) {
        this.mailTemplateId = mailTemplateId;
    }

    @Basic
    @NotNull
    @Column(name = "templatename")
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Basic
    @NotNull
    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @NotNull
    @Column(name = "body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Basic
    @NotNull
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @NotNull
    @Column(name = "createddate", updatable = false)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "modifieddate")
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
