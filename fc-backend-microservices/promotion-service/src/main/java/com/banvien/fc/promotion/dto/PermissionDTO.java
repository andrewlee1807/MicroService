package com.banvien.fc.promotion.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class PermissionDTO implements Serializable {
    private Long permissionId;
    private String code;
    private String name;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public PermissionDTO() {
    }

    public PermissionDTO(Long permissionId, String code, String name, Timestamp createdDate, Timestamp modifiedDate) {
        this.permissionId = permissionId;
        this.code = code;
        this.name = name;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
