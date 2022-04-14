package com.banvien.fc.account.entity;

import javax.persistence.*;
import java.sql.Timestamp;

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
    @Column(name = "loyaltyPointHistoryId", nullable = false)
    public long getLoyaltyPointHistoryId() {
        return loyaltyPointHistoryId;
    }

    public void setLoyaltyPointHistoryId(long loyaltyPointHistoryId) {
        this.loyaltyPointHistoryId = loyaltyPointHistoryId;
    }

    @Basic
    @Column(name = "outletId", nullable = false)
    public long getOutletId() {
        return outletId;
    }

    public void setOutletId(long outletId) {
        this.outletId = outletId;
    }

    @Basic
    @Column(name = "customerId", nullable = false)
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "loyaltyOutletEventId", nullable = true)
    public Long getLoyaltyOutletEventId() {
        return loyaltyOutletEventId;
    }

    public void setLoyaltyOutletEventId(Long loyaltyOutletEventId) {
        this.loyaltyOutletEventId = loyaltyOutletEventId;
    }

    @Basic
    @Column(name = "completionDate", nullable = false)
    public Timestamp getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Timestamp completionDate) {
        this.completionDate = completionDate;
    }

    @Basic
    @Column(name = "redeemDate", nullable = true)
    public Timestamp getRedeemDate() {
        return redeemDate;
    }

    public void setRedeemDate(Timestamp redeemDate) {
        this.redeemDate = redeemDate;
    }

    @Basic
    @Column(name = "redeemCode", nullable = true, length = -1)
    public String getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(String redeemCode) {
        this.redeemCode = redeemCode;
    }

    @Basic
    @Column(name = "point", nullable = false)
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Basic
    @Column(name = "giftId", nullable = true)
    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

}
