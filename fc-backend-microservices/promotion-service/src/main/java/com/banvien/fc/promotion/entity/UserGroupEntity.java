package com.banvien.fc.promotion.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Table(
        name = "usergroup"
)
@Cache(
        usage = CacheConcurrencyStrategy.TRANSACTIONAL
)
@Entity
public class UserGroupEntity implements Serializable {
    private Long userGroupId;
    private String code;
    private String name;
    private String description;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private List<RoleEntity> roles;

    public UserGroupEntity() {
    }

    public UserGroupEntity(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    @Column(
            name = "usergroupid"
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    public Long getUserGroupId() {
        return this.userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
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

    @Column(
            name = "createddate"
    )
    @Basic
    public Timestamp getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(
            name = "modifieddate"
    )
    @Basic
    public Timestamp getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "UserGroupRoleACL",
            joinColumns = {@JoinColumn(
                    name = "UserGroupId",
                    referencedColumnName = "UserGroupId"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "RoleId",
                    referencedColumnName = "RoleId"
            )}
    )
    public List<RoleEntity> getRoles() {
        return this.roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
}
