package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.WareHouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<WareHouseEntity, Long> {
    WareHouseEntity findByOutletOutletIdAndIsDefaultIsTrue(Long outletId);
}
