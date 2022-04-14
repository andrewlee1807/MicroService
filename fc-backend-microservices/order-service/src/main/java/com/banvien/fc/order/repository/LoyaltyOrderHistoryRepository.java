package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.LoyaltyOrderHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoyaltyOrderHistoryRepository extends JpaRepository<LoyaltyOrderHistoryEntity,Long> {
    Integer countByCustomerIdAndLoyaltyOutletEventId(Long customerId,Long loyaltyOutletEventId);

    List<LoyaltyOrderHistoryEntity> findByOrOrderOutletCode(String orderCode);
}
