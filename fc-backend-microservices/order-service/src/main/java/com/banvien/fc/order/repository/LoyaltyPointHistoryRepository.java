package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.LoyaltyPointHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoyaltyPointHistoryRepository extends JpaRepository<LoyaltyPointHistoryEntity,Long> {
    Integer countByCustomerIdAndLoyaltyOutletEventId(long customerId,Long loyaltyId);
}
