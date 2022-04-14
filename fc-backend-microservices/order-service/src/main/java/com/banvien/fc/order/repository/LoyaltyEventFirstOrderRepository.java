package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.LoyaltyEventFirstOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface LoyaltyEventFirstOrderRepository extends JpaRepository<LoyaltyEventFirstOrderEntity,Long> {
    @Query("SELECT l FROM LoyaltyEventFirstOrderEntity l where l.loyaltyOutletEvent.status = 'ACTIVE' and l.loyaltyOutletEvent.outletId = :outletId and l.loyaltyOutletEvent.startDate < :current_timestamp and l.loyaltyOutletEvent.endDate > :current_timestamp")
    List<LoyaltyEventFirstOrderEntity> findByLoyaltyOutletEventOutletIdAndLoyaltyOutletEventStatus(@Param("outletId")Long outletId, @Param("current_timestamp")Timestamp timestamp);
}
