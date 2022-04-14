package com.banvien.fc.promotion.entity;

import javax.persistence.*;

@Table(name = "socialchat")
@Entity
public class SocialChatEntity {

    private Long socialChatId;
    private OutletEntity outlet;
    private String appId;
    private String token;
    private String type;
    private String verifyToken;
    private String fanpageId;
    private String appSecret;


    @Id
    @Column(name = "socialchatid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getSocialChatId() {
        return socialChatId;
    }

    public void setSocialChatId(Long socialChatId) {
        this.socialChatId = socialChatId;
    }

    @Basic
    @Column(name = "appid")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name="outletid", referencedColumnName = "outletid")
    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    @Basic
    @Column(name = "verifytoken")
    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    @Basic
    @Column(name = "fanpageid")
    public String getFanpageId() {
        return fanpageId;
    }

    public void setFanpageId(String fanpageId) {
        this.fanpageId = fanpageId;
    }

    @Basic
    @Column(name = "appsecret")
    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
