package com.banvien.fc.promotion.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
//import com.banvien.fc.smartshopper.core.logic.*;

@Table(
        name = "role"
)
@Cache(
        usage = CacheConcurrencyStrategy.TRANSACTIONAL
)
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

    @Column(
            name = "roleid"
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Column(
            name = "code"
    )
    @Basic
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(
            name = "name"
    )
    @Basic
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(
            name = "description"
    )
    @Basic
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
