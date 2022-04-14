package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.RawCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RawCategoryRepository extends JpaRepository<RawCategoryEntity, Long>, JpaSpecificationExecutor<RawCategoryEntity> {
}
