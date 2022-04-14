package com.banvien.fc.promotion.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(
        name = "users"
)
@Cache(
        usage = CacheConcurrencyStrategy.TRANSACTIONAL
)
@Inheritance(
        strategy = InheritanceType.SINGLE_TABLE
)
@Entity
public class UserEntity implements Serializable {
    private Long userId;
    private String userName;
    private String code;
    private String password;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private UserGroupEntity userGroup;
    private String postCode;
    private String avatar;
    private String locale;
    private String timezone;

    public UserEntity() {
    }

    public UserEntity(Long userId) {
        this.userId = userId;
    }

    @Column(name = "userid")
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(
            name = "username"
    )
    @Basic
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
            name = "password"
    )
    @Basic
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(
            name = "fullname"
    )
    @Basic
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(
            name = "firstname"
    )
    @Basic
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(
            name = "lastname"
    )
    @Basic
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(
            name = "email"
    )
    @Basic
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(
            name = "phonenumber"
    )
    @Basic
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(
            name = "status"
    )
    @Basic
    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @ManyToOne
    @JoinColumn(
            name = "usergroupid",
            referencedColumnName = "UserGroupId",
            nullable = false
    )
    public UserGroupEntity getUserGroup() {
        return this.userGroup;
    }

    public void setUserGroup(UserGroupEntity userGroup) {
        this.userGroup = userGroup;
    }

    @Column(
            name = "postcode"
    )
    @Basic
    public String getPostCode() {
        return this.postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Column(
            name = "avatar"
    )
    @Basic
    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Column(
            name = "locale"
    )
    @Basic
    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Column(
            name = "timezone"
    )
    @Basic
    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
