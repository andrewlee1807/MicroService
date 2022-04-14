package com.banvien.fc.email.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "notifytemplate")
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)

public class NotifyTemplateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notifyTemplateId;
    private String code;
    private String title;
    private Integer status;
    private String contentEn;
    private String contentVi;
    private String contentId;
    private String contentMa;
    private String contentCn;
    private String note;

    public Long getNotifyTemplateId() {
        return notifyTemplateId;
    }

    public void setNotifyTemplateId(Long notifyTemplateId) {
        this.notifyTemplateId = notifyTemplateId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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


    public NotifyTemplateEntity(Long notifyTemplateId, String code, String title, Integer status, String contentEn, String note) {
        this.notifyTemplateId = notifyTemplateId;
        this.code = code;
        this.title = title;
        this.status = status;
        this.contentEn = contentEn;
        this.note = note;
    }



    public NotifyTemplateEntity(Long notifyTemplateId, String code, String title, Integer status, String contentEn, String contentVi, String contentId, String contentMa, String contentCn, String note) {
        this.notifyTemplateId = notifyTemplateId;
        this.code = code;
        this.title = title;
        this.status = status;
        this.contentEn = contentEn;
        this.contentVi = contentVi;
        this.contentId = contentId;
        this.contentMa = contentMa;
        this.contentCn = contentCn;
        this.note = note;
    }

    public NotifyTemplateEntity() {
    }

    public String getContentEn() {
        return contentEn;
    }

    public void setContentEn(String contentEn) {
        this.contentEn = contentEn;
    }

    public String getContentVi() {
        return contentVi;
    }

    public void setContentVi(String contentVi) {
        this.contentVi = contentVi;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentMa() {
        return contentMa;
    }

    public void setContentMa(String contentMa) {
        this.contentMa = contentMa;
    }

    public String getContentCn() {
        return contentCn;
    }

    public void setContentCn(String contentCn) {
        this.contentCn = contentCn;
    }
}
