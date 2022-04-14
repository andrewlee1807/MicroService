package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.OutletBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutletBrandRepository extends JpaRepository<OutletBrandEntity,Long> {

    List<OutletBrandEntity> findByOutletid(Long outletId);
}
