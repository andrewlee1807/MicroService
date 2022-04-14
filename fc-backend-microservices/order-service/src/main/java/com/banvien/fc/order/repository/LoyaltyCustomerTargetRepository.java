package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.LoyaltyCustomerTargetEntity;
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
