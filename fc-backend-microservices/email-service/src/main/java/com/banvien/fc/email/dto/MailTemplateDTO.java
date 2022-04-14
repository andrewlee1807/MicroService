package com.banvien.fc.email.dto;

import java.io.Serializable;

public class MailTemplateDTO implements Serializable {
    private Long mailTemplateId;
    private String templateName;
    private String subject;
    private String body;
    private Integer status;

    public Long getMailTemplateId() {
        return mailTemplateId;
    }

    public void setMailTemplateId(Long mailTemplateId) {
        this.mailTemplateId = mailTemplateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
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
}
