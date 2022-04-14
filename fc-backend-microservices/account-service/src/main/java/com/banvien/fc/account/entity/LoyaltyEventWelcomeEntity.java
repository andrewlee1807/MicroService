package com.banvien.fc.account.entity;

import javax.persistence.*;

@Entity
@Table(name = "loyaltyeventwelcome")
public class LoyaltyEventWelcomeEntity {
    private long loyaltyEventWelcomeId;
    private long loyaltyOutletEventId;

    @Id
    @Column(name = "loyaltyEventWelcomeId", nullable = false)
    public long getLoyaltyEventWelcomeId() {
        return loyaltyEventWelcomeId;
    }

    public void setLoyaltyEventWelcomeId(long loyaltyEventWelcomeId) {
        this.loyaltyEventWelcomeId = loyaltyEventWelcomeId;
    }

    @Basic
    @Column(name = "loyaltyOutletEventId", nullable = false)
    public long getLoyaltyOutletEventId() {
        return loyaltyOutletEventId;
    }

    public void setLoyaltyOutletEventId(long loyaltyOutletEventId) {
        this.loyaltyOutletEventId = loyaltyOutletEventId;
    }

}
