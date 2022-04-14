package com.banvien.fc.account.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "loyaltymember")
public class LoyaltyMemberEntity {

    @Id
    @Column(name = "loyaltymemberid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loyaltyMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outletid", referencedColumnName = "outletid")
    private OutletEntity outlet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    private CustomerEntity customer;

    @Basic
    @Column(name = "joindate")
    private Timestamp joinDate;

    @Basic
    @Column(name = "point")
    private Integer point;

    @Basic
    @Column(name = "PointExpiredDate")
        private Timestamp pointExpiredDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referedby", referencedColumnName = "userId")
    private UserEntity referedby;

    @Basic
    @Column(name="status")
    private String status;

    @Basic
    @Column(name = "customercode")
    private String customerCode;

    @Basic
    @Column(name = "creditlimit")
    private Double creditLimit; // Maximum debt can be own

    @Basic
    @Column(name = "creditwarning")
    private Double creditWarning;

    @Basic
    @Column(name = "totalDebt")
    private Double totalDebt; // payment due amount
    @Basic
    @Column(name = "updateddate")
    private Timestamp updatedDate;

    @Basic
    @Column(name = "terms")
    private Boolean terms; // Allow credit debt or not

    @Basic
    @Column(name = "creditterms")
    private Integer creditTerms; // how long they have to pay back debt

    @Basic
    @Column(name = "paymentduedate")
    private Timestamp paymentDueDate;

    @Basic
    @Column(name = "active")
    private int active;

    @Basic
    @Column(name = "customerGroupId")
    private Long customerGroupId;

    @Basic
    @Column(name = "quitstorestatus")
    private String quitStoreStatus;

    @Basic
    @Column(name = "termtype")
    private String termType;


    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public Long getLoyaltyMemberId() {
        return loyaltyMemberId;
    }

    public void setLoyaltyMemberId(Long loyaltyMemberId) {
        this.loyaltyMemberId = loyaltyMemberId;
    }

    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public UserEntity getReferedby() {
        return referedby;
    }

    public void setReferedby(UserEntity referedby) {
        this.referedby = referedby;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(Double totalDebt) {
        this.totalDebt = totalDebt;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getCreditTerms() {
        return creditTerms;
    }

    public void setCreditTerms(Integer creditTerms) {
        this.creditTerms = creditTerms;
    }

    public Timestamp getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Timestamp paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }

    public Double getCreditWarning() {
        return creditWarning;
    }

    public void setCreditWarning(Double creditWarning) {
        this.creditWarning = creditWarning;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Timestamp getPointExpiredDate() {
        return pointExpiredDate;
    }

    public void setPointExpiredDate(Timestamp pointExpiredDate) {
        this.pointExpiredDate = pointExpiredDate;
    }

    public String getQuitStoreStatus() {
        return quitStoreStatus;
    }

    public void setQuitStoreStatus(String quitStoreStatus) {
        this.quitStoreStatus = quitStoreStatus;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }
}
