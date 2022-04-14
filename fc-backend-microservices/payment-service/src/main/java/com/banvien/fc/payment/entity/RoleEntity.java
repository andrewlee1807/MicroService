package com.banvien.fc.payment.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Copyright by (c) Ban Vien Company Limited. All rights reserved.
 * User: Vincent
 * Date: 6/09/16
 * Time: 4:29 PM
 * Email: vien.nguyen@banvien.com
 */
@Table(name = "Role")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class RoleEntity implements Serializable {
    private Long roleId;

    private String code;

    private String name;

    private String description;

    public RoleEntity() {
    }

    public RoleEntity(Long roleId) {
        this.roleId = roleId;
    }

    @Column(name = "RoleId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @javax.persistence.Column(name = "Code")
    @Basic
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @javax.persistence.Column(name = "Name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @javax.persistence.Column(name = "Description")
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

