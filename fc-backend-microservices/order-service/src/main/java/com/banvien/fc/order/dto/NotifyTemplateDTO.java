package com.banvien.fc.order.dto;

import java.io.Serializable;

public class NotifyTemplateDTO implements Serializable {
	private Long notifyTemplateId;
	private String code;
	private String title;
	private Integer status;
	private String contentEn;
	private String contentVi;
	private String contentMa;
	private String contentId;
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

	public String getContentMa() {
		return contentMa;
	}

	public void setContentMa(String contentMa) {
		this.contentMa = contentMa;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getContentCn() {
		return contentCn;
	}

	public void setContentCn(String contentCn) {
		this.contentCn = contentCn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public NotifyTemplateDTO(Long notifyTemplateId,
                             String code,
                             String title,
                             Integer status,
                             String contentEn,
                             String contentVi,
                             String contentMa,
                             String contentId,
                             String contentCn,
                             String note) {
		this.notifyTemplateId = notifyTemplateId;
		this.code = code;
		this.title = title;
		this.status = status;
		this.contentEn = contentEn;
		this.contentVi = contentVi;
		this.contentMa = contentMa;
		this.contentId = contentId;
		this.contentCn = contentCn;
		this.note = note;
	}

	public String getContentWithLanguage(String language) {
		switch (language) {
			case "vi":
				return this.contentVi;
			case "cn":
			case "zh":
				return this.contentCn;
			case "ma":
				return this.contentMa;
			case "id":
				return this.contentId;
			default:
				return this.contentEn;
		}
	}
}
