package com.banvien.fc.promotion.dto;

import java.io.Serializable;

public class SocialChatDTO implements Serializable {

    private static final long serialVersionUID = 4495557095053072642L;

    private Long socialChatId;
    private OutletDTO outlet;
    private String appId;
    private String token;
    private String type;
    private String verifyToken;
    private String fanpageId;
    private String appSecret;

    public Long getSocialChatId() {
        return socialChatId;
    }

    public void setSocialChatId(Long socialChatId) {
        this.socialChatId = socialChatId;
    }

    public OutletDTO getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletDTO outlet) {
        this.outlet = outlet;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public String getFanpageId() {
        return fanpageId;
    }

    public void setFanpageId(String fanpageId) {
        this.fanpageId = fanpageId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}