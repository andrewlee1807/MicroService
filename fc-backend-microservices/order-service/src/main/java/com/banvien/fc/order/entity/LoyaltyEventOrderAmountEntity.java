package com.banvien.fc.order.entity;


import javax.persistence.*;
import java.sql.Timestamp;

@Table(name="loyaltyeventorderamount")
@Entity
public class LoyaltyEventOrderAmountEntity {
    private Long loyaltyEventOrderAmountId;
    private LoyaltyOutletEventEntity loyaltyOutletEvent;
    private Timestamp startDate;
    private Timestamp endDate;
    private Double orderAmount;
    private Boolean hasDuration;

    public LoyaltyEventOrderAmountEntity() {
    }


    @Id
    @Column(name="loyaltyeventorderamountid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getLoyaltyEventOrderAmountId() {
        return loyaltyEventOrderAmountId;
    }

    public void setLoyaltyEventOrderAmountId(Long loyaltyEventOrderAmountId) {
        this.loyaltyEventOrderAmountId = loyaltyEventOrderAmountId;
    }

    @OneToOne()
    @JoinColumn(name="loyaltyoutleteventid", referencedColumnName = "loyaltyoutleteventid")
    public LoyaltyOutletEventEntity getLoyaltyOutletEvent() {
        return loyaltyOutletEvent;
    }

    public void setLoyaltyOutletEvent(LoyaltyOutletEventEntity loyaltyOutletEvent) {
        this.loyaltyOutletEvent = loyaltyOutletEvent;
    }

    @Basic
    @Column(name="startdate")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name="enddate")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name="orderamount")
    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Basic
    @Column(name="hasduration")
    public Boolean getHasDuration() {
        return hasDuration;
    }

    public void setHasDuration(Boolean hasDuration) {
        this.hasDuration = hasDuration;
    }
}
