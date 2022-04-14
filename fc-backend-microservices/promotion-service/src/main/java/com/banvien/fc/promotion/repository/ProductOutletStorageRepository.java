package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.ProductOutletStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOutletStorageRepository extends JpaRepository<ProductOutletStorageEntity, Long> {
    List<ProductOutletStorageEntity> findByWareHouseWareHouseId(Long wareHouserId);
}
