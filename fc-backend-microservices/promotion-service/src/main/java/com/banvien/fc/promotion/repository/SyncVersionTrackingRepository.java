package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.SyncVersionTrackingEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncVersionTrackingRepository extends JpaRepository<SyncVersionTrackingEntity, Long> {
    SyncVersionTrackingEntity findByUserIdAndTypeSyncAndToken(Long userId, String typeSync, String token);
}
