package com.banvien.fc.sms.vivas.client.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "RQST")
@XmlType(propOrder={"username","password"})
public class UserReq {
    private String username;
    private String password;
    public UserReq() {
    }
    public UserReq(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @XmlElement(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @XmlElement(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
