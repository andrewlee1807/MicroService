package com.banvien.fc.account.repository;

import com.banvien.fc.account.entity.LoyaltyOutletEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by son.nguyen on 7/28/2020.
 */
@Repository
public interface LoyaltyOutletEvenRepository extends JpaRepository<LoyaltyOutletEventEntity, Long> {
    List<LoyaltyOutletEventEntity> findByOutletIdAndStatusAndStartDateAfterAndEndDateBefore(long outletId, String status, Timestamp startDate, Timestamp endDate);
}
