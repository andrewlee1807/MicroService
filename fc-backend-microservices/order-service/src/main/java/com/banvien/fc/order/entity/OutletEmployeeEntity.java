package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "outletemployee")
public class OutletEmployeeEntity implements Serializable {
    private Long outletEmployeeId;
    private String code;
    private OutletEntity outlet;
    private UserEntity user;
    private Boolean canSeeAllCustomer;

    public OutletEmployeeEntity() {
    }

    public OutletEmployeeEntity(Long outletEmployeeId) {
        this.outletEmployeeId = outletEmployeeId;
    }

    @Id
    @Column(name = "outletemployeeid")
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    public Long getOutletEmployeeId() {
        return outletEmployeeId;
    }

    public void setOutletEmployeeId(Long outletEmployeeId) {
        this.outletEmployeeId = outletEmployeeId;
    }

    @ManyToOne
    @JoinColumn(name = "outletid", referencedColumnName = "outletid")
    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Column(name = "code")
    @Basic
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "CanSeeAllCustomer")
    @Basic
    public Boolean getCanSeeAllCustomer() {
        return canSeeAllCustomer;
    }

    public void setCanSeeAllCustomer(Boolean canSeeAllCustomer) {
        this.canSeeAllCustomer = canSeeAllCustomer;
    }
}
