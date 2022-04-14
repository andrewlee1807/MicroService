package com.banvien.fc.promotion.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "customerreward")
@Entity
public class CustomerRewardEntity {
    @Column(name = "customerrewardid", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Integer quantity;
    @Column(name = "windate")
    private Timestamp winDate;      // Day get Code
    private Boolean redeem;
    @Column(name = "redeemdate")
    private Timestamp redeemDate;   // Day use Code
    @Column(name = "redeemcode")
    private String redeemCode;
    @Column(name = "outletid")
    private Long outletId;
    @Column(name = "productoutletskuid")
    private Long productOutletSkuId;
    @Column(name = "typediscount")
    private Integer discountType;   // -- 0 : percentOff; 1 : amountOff
    @Column(name = "valuediscount")
    private Double discountValue;
    @Column(name = "expireddate")
    private Timestamp expiredDate;
    @Column(name = "outletpromotionid")
    private Long outletPromotionId;
    @Column(name = "orderoutletid")
    private Long orderOutletId;

    public CustomerRewardEntity() {
    }

    public CustomerRewardEntity(Long customerId, Integer quantity, Timestamp winDate, Boolean redeem, Timestamp redeemDate, String redeemCode,
                                Long outletId, Long productOutletSkuId, Integer discountType, Double discountValue, Timestamp expiredDate, Long outletPromotionId) {
        this.customerId = customerId;
        this.quantity = quantity;
        this.winDate = winDate;
        this.redeem = redeem;
        this.redeemDate = redeemDate;
        this.redeemCode = redeemCode;
        this.outletId = outletId;
        this.productOutletSkuId = productOutletSkuId;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.expiredDate = expiredDate;
        this.outletPromotionId = outletPromotionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(String redeemCode) {
        this.redeemCode = redeemCode;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer typeDiscount) {
        this.discountType = typeDiscount;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double valueDiscount) {
        this.discountValue = valueDiscount;
    }

    public Timestamp getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Timestamp expiredDate) {
        this.expiredDate = expiredDate;
    }


}
