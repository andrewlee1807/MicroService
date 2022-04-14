package com.banvien.fc.order.entity;

import javax.persistence.*;

@Table(name="loyaltyeventfirstorder")
@Entity
public class LoyaltyEventFirstOrderEntity {
    private Long loyaltyEventFirstOrderId;
    private LoyaltyOutletEventEntity loyaltyOutletEvent;
    private Integer orderNumber;
    private GiftEntity gift;

    public LoyaltyEventFirstOrderEntity() {
    }

    @Id
    @Column(name="loyaltyeventfirstorderid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getLoyaltyEventFirstOrderId() {
        return loyaltyEventFirstOrderId;
    }

    public void setLoyaltyEventFirstOrderId(Long loyaltyEventFirstOrderId) {
        this.loyaltyEventFirstOrderId = loyaltyEventFirstOrderId;
    }

    @OneToOne()
    @JoinColumn(name="loyaltyoutleteventid", referencedColumnName = "loyaltyoutleteventid")
    public LoyaltyOutletEventEntity getLoyaltyOutletEvent() {
        return loyaltyOutletEvent;
    }

    public void setLoyaltyOutletEvent(LoyaltyOutletEventEntity loyaltyOutletEvent) {
        this.loyaltyOutletEvent = loyaltyOutletEvent;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @OneToOne()
    @JoinColumn(name="giftid", referencedColumnName = "giftid")
    public GiftEntity getGift() {
        return gift;
    }

    public void setGift(GiftEntity gift) {
        this.gift = gift;
    }
}
