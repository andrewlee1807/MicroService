package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.RawBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RawBrandRepository extends JpaRepository<RawBrandEntity, Long>, JpaSpecificationExecutor<RawBrandEntity> {
}
