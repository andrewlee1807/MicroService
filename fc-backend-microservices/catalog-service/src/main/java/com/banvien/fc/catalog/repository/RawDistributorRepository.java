package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.RawDistributorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RawDistributorRepository extends JpaRepository<RawDistributorEntity, Long>, JpaSpecificationExecutor<RawDistributorEntity> {
}
