package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.AppSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppSettingRepository extends JpaRepository<AppSettingEntity, Long> {
    AppSettingEntity findByKeyAndOutletIdAndStatus(String key, Long outletId, boolean status);
}
