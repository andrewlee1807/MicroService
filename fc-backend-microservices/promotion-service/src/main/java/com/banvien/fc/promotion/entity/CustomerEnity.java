package com.banvien.fc.promotion.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "customer")
@Entity
public class CustomerEnity {
    @Column(name = "customerid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(name = "userid")
    private Long userId;
    @Column(name = "lastlogin")
    private Timestamp lastLogin;
    @Column(name = "beforelastlogin")
    private Timestamp beforeLastLogin;

    public CustomerEnity() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastlogin) {
        this.lastLogin = lastlogin;
    }

    public Timestamp getBeforeLastLogin() {
        return beforeLastLogin;
    }

    public void setBeforeLastLogin(Timestamp beforeLastLogin) {
        this.beforeLastLogin = beforeLastLogin;
    }
}
