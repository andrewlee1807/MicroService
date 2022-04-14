package com.banvien.fc.order.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;


@Table(name = "customerdebthistory")
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class CustomerDebtHistoryEntity {
    @Id
    @Column(name = "customerdebthistoryid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerDebtHistoryId;
    @ManyToOne()
    @JoinColumn(name = "orderoutletid", referencedColumnName = "orderoutletid")
    private OrderOutletEntity orderOutlet;
    @ManyToOne()
    @JoinColumn(name = "loyaltymemberid", referencedColumnName = "loyaltymemberid")
    private LoyaltyMemberEntity loyaltyMember;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "amount")
    private Double amount;
    @Basic
    @Column(name = "note")
    private String note;
    @ManyToOne()
    @JoinColumn(name = "createdBy", referencedColumnName = "userId")
    private UserEntity createdBy;
    @Basic
    @Column(name = "createddate")
    private Timestamp createdDate;
    @Basic
    @Column(name = "creditbalance")
    private Double creditBalance;
    @Basic
    @Column(name = "paymentdueamount")
    private Double paymentDueAmount;
    @Basic
    @Column(name = "paymentduedate")
    private Timestamp paymentDueDate;
    @Basic
    @Column(name = "status")
    private Integer status;
    @Basic
    @Column(name = "collectiontype")
    private String collectionType;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(Double creditBalance) {
        this.creditBalance = creditBalance;
    }

    public Timestamp getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Timestamp paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Double getPaymentDueAmount() {
        return paymentDueAmount;
    }

    public void setPaymentDueAmount(Double paymentDueAmount) {
        this.paymentDueAmount = paymentDueAmount;
    }

    public Long getCustomerDebtHistoryId() {
        return customerDebtHistoryId;
    }

    public void setCustomerDebtHistoryId(Long customerDebtHistoryId) {
        this.customerDebtHistoryId = customerDebtHistoryId;
    }

    public OrderOutletEntity getOrderOutlet() {
        return orderOutlet;
    }

    public void setOrderOutlet(OrderOutletEntity orderOutlet) {
        this.orderOutlet = orderOutlet;
    }

    public LoyaltyMemberEntity getLoyaltyMember() {
        return loyaltyMember;
    }

    public void setLoyaltyMember(LoyaltyMemberEntity loyaltyMember) {
        this.loyaltyMember = loyaltyMember;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

}
