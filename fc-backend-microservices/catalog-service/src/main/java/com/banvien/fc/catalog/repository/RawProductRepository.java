package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.RawProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RawProductRepository extends JpaRepository<RawProductEntity, Long>, JpaSpecificationExecutor<RawProductEntity> {
}
