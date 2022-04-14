package com.banvien.fc.account.repository;

import com.banvien.fc.account.entity.RawStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RawStoreRepository extends JpaRepository<RawStoreEntity, Long>, JpaSpecificationExecutor<RawStoreEntity> {
}
