package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.DeliveryServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryServiceRepository extends JpaRepository<DeliveryServiceEntity, Long> {
    @Query(value = "select c FROM DeliveryServiceEntity c WHERE c.outlet.outletId = :outletId")
    List<DeliveryServiceEntity> findByOutlet(@Param("outletId") Long outletId);
}
