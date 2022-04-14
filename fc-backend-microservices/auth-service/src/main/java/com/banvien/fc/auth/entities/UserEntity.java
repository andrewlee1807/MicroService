package com.banvien.fc.auth.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserEntity {
    private long userid;
    private String username;
    private String code;
    private String password;
    private String fullname;
    private String firstname;
    private String lastname;
    private String email;
    private String phonenumber;
    private int status;
    private UserGroupEntity role;
    private String fromsrc;
    private String profilepic;
    private String avatar;
    private String postcode;
    private String locale;
    private String timezone;
    private Long countryId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", nullable = false)
    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "fullname", nullable = true, length = 255)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Basic
    @Column(name = "firstname", nullable = true, length = 255)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname", nullable = true, length = 255)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phonenumber", nullable = true, length = 20)
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "fromsrc", nullable = true, length = 255)
    public String getFromsrc() {
        return fromsrc;
    }

    public void setFromsrc(String fromsrc) {
        this.fromsrc = fromsrc;
    }

    @Basic
    @Column(name = "profilepic", nullable = true, length = 512)
    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    @Basic
    @Column(name = "avatar", nullable = true, length = 125)
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Basic
    @Column(name = "postcode", nullable = true, length = 20)
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Basic
    @Column(name = "locale", nullable = true, length = 50)
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Basic
    @Column(name = "timezone", nullable = true, length = 255)
    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userid == that.userid &&
                status == that.status &&
                Objects.equals(username, that.username) &&
                Objects.equals(code, that.code) &&
                Objects.equals(password, that.password) &&
                Objects.equals(fullname, that.fullname) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phonenumber, that.phonenumber) &&
                Objects.equals(fromsrc, that.fromsrc) &&
                Objects.equals(profilepic, that.profilepic) &&
                Objects.equals(avatar, that.avatar) &&
                Objects.equals(postcode, that.postcode) &&
                Objects.equals(locale, that.locale) &&
                Objects.equals(timezone, that.timezone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, username, code, password, fullname, firstname, lastname, email, phonenumber, status, fromsrc, profilepic, avatar, postcode, locale, timezone);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usergroupid", referencedColumnName = "usergroupid")
    public UserGroupEntity getRole() {
        return role;
    }

    public void setRole(UserGroupEntity role) {
        this.role = role;
    }

    @Basic
    @Column(name = "countryid")
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
