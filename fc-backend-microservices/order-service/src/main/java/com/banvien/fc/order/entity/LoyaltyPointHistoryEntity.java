package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "loyaltypointhistory")
public class LoyaltyPointHistoryEntity {
    private long loyaltyPointHistoryId;
    private long outletId;
    private long customerId;
    private Long loyaltyOutletEventId;
    private Timestamp completionDate;
    private Timestamp redeemDate;
    private String redeemCode;
    private int point;
    private Long giftId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loyaltypointhistoryid")
    public long getLoyaltyPointHistoryId() {
        return loyaltyPointHistoryId;
    }

    public void setLoyaltyPointHistoryId(long loyaltyPointHistoryId) {
        this.loyaltyPointHistoryId = loyaltyPointHistoryId;
    }

    @Basic
    @Column(name = "outletid")
    public long getOutletid() {
        return outletId;
    }

    public void setOutletid(long outletid) {
        this.outletId = outletid;
    }

    @Basic
    @Column(name = "customerid")
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "loyaltyoutleteventid")
    public Long getLoyaltyOutletEventId() {
        return loyaltyOutletEventId;
    }

    public void setLoyaltyOutletEventId(Long loyaltyOutletEventId) {
        this.loyaltyOutletEventId = loyaltyOutletEventId;
    }




    @Basic
    @Column(name = "completiondate")
    public Timestamp getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Timestamp completionDate) {
        this.completionDate = completionDate;
    }

    @Basic
    @Column(name = "redeemdate")
    public Timestamp getRedeemDate() {
        return redeemDate;
    }

    public void setRedeemDate(Timestamp redeemDate) {
        this.redeemDate = redeemDate;
    }

    @Basic
    @Column(name = "redeemcode")
    public String getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(String redeemCode) {
        this.redeemCode = redeemCode;
    }

    @Basic
    @Column(name = "point")
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Basic
    @Column(name = "giftid")
    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }
}
