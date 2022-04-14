package com.banvien.fc.email.repository;

import com.banvien.fc.email.entity.OutletEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public abstract interface OutletRepository extends JpaRepository<OutletEntity, Long> {
    @Query(value = "select shopman from outlet where outletid = :outletId", nativeQuery = true)
    List<Long> findByOutletId(@Param("outletId") Long outletId);
}
