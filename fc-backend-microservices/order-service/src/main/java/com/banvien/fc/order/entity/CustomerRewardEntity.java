package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "customerreward")
public class CustomerRewardEntity {
    @Id
    @Column(name = "customerrewardid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerRewardId;

    @ManyToOne
    @JoinColumn(name = "customerId" ,referencedColumnName = "customerId")
    private CustomerEntity customer;

    @Basic
    @Column(name = "quantity")
    private Integer quantity;

    @Basic
    @Column(name = "windate")
    private Timestamp winDate;

    @Basic
    @Column(name = "redeem")
    private Boolean redeem;

    @Basic
    @Column(name = "redeemdate")
    private Timestamp redeemDate;

    @Basic
    @Column(name = "redeemcode")
    private String redeemCode;

    @ManyToOne
    @JoinColumn(name = "giftid", referencedColumnName = "giftid")
    private GiftEntity gift;

    @Basic
    @Column(name = "incart")
    private Boolean inCart; // reward gift in shopping cart

    @ManyToOne
    @JoinColumn(name = "outletid", referencedColumnName = "outletid")
    private OutletEntity outlet;

    @Column(name = "orderoutletid")
    private Long orderOutletId;

    public String getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(String redeemCode) {
        this.redeemCode = redeemCode;
    }

    public CustomerRewardEntity() {
    }

    public CustomerRewardEntity(Long customerRewardId) {
        this.customerRewardId = customerRewardId;
    }

    public Long getCustomerRewardId() {
        return customerRewardId;
    }

    public void setCustomerRewardId(Long customerRewardId) {
        this.customerRewardId = customerRewardId;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Timestamp getWinDate() {
        return winDate;
    }

    public void setWinDate(Timestamp winDate) {
        this.winDate = winDate;
    }

    public Boolean getRedeem() {
        return redeem;
    }

    public void setRedeem(Boolean redeem) {
        this.redeem = redeem;
    }

    public Timestamp getRedeemDate() {
        return redeemDate;
    }

    public void setRedeemDate(Timestamp redeemDate) {
        this.redeemDate = redeemDate;
    }

    public GiftEntity getGift() {
        return gift;
    }

    public void setGift(GiftEntity gift) {
        this.gift = gift;
    }

    public Boolean getInCart() {
        return inCart;
    }

    public void setInCart(Boolean inCart) {
        this.inCart = inCart;
    }

    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    public Long getOrderOutletId() {
        return orderOutletId;
    }

    public void setOrderOutletId(Long orderOutletId) {
        this.orderOutletId = orderOutletId;
    }
}
