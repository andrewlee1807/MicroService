package com.banvien.fc.account.repository;

import com.banvien.fc.account.entity.LoyaltyEventWelcomeEntity;
import com.banvien.fc.account.entity.LoyaltyOutletEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by son.nguyen on 7/28/2020.
 */
@Repository
public interface LoyaltyEvenWelcomeRepository extends JpaRepository<LoyaltyEventWelcomeEntity, Long> {
    List<LoyaltyEventWelcomeEntity> findByLoyaltyOutletEventId(long loyaltyOutletEvenId);
}
