package com.banvien.fc.account.repository;

import com.banvien.fc.account.entity.LoyaltyPointHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by son.nguyen on 7/28/2020.
 */
@Repository
public interface LoyaltyPointHistoryRepository extends JpaRepository<LoyaltyPointHistoryEntity, Long> {
    long countByCustomerIdAndLoyaltyOutletEventId(long customerId, Long loyaltyOutletEvenId);
}
