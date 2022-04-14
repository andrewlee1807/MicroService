package com.banvien.fc.account.repository;

import com.banvien.fc.account.entity.LoyaltyCustomerTargetEntity;
import com.banvien.fc.account.entity.LoyaltyEventWelcomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by son.nguyen on 7/28/2020.
 */
@Repository
public interface LoyaltyCustomerTargetRepository extends JpaRepository<LoyaltyCustomerTargetEntity, Long> {
    List<LoyaltyCustomerTargetEntity> findByLoyaltyOutletEventId(long loyaltyOutletEventId);
}
