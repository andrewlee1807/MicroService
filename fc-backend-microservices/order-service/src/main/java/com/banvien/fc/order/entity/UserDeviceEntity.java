package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Hieu
 *
 */
@Table(name = "userdevice")
@Entity
public class UserDeviceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userDeviceId;

	@Basic
	@Column(name = "token")
	private String token;

	private String type;

	private Timestamp createdDate;

	private Timestamp modifiedDate;

	private String lang;

	@Basic
	@Column(name = "lang")
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Basic
	@Column(name = "userid")
	private Long userId;

	public Long getUserDeviceId() {
		return userDeviceId;
	}

	public void setUserDeviceId(Long userDeviceId) {
		this.userDeviceId = userDeviceId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
