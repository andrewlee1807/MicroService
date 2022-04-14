package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.LoyaltyMasterEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoyaltyMasterEventRepository extends JpaRepository<LoyaltyMasterEventEntity,Long> {
    List<LoyaltyMasterEventEntity> findByEventTypeAndTitle(String eventType,String title);
}
