package com.banvien.fc.email.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Copyright by (c) Ban Vien Company Limited. All rights reserved.
 * User: Vincent
 * Date: 6/09/16
 * Time: 4:28 PM
 * Email: vien.nguyen@banvien.com
 */
@Table(name = "usergroup")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class UserGroupEntity implements Serializable {
    private Long userGroupId;

    private String code;

    private String name;

    private String description;

    private Timestamp createdDate;

    private Timestamp modifiedDate;

    public UserGroupEntity() {
    }

    public UserGroupEntity(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    @Column(name = "usergroupid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    @Column(name = "code")
    @Basic
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "createddate")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "modifieddate")
    @Basic
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
