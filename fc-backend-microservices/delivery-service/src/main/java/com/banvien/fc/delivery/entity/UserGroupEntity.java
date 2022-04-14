package com.banvien.fc.delivery.entity;

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
@Table(name = "UserGroup")
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

    @Column(name = "UserGroupId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    @Column(name = "Code")
    @Basic
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "Name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Description")
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "CreatedDate")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "ModifiedDate")
    @Basic
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    private List<RoleEntity> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="UserGroupRoleACL",
            joinColumns = @JoinColumn(name="UserGroupId", referencedColumnName = "UserGroupId"),
            inverseJoinColumns = @JoinColumn(name="RoleId", referencedColumnName = "RoleId"))
    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
}
