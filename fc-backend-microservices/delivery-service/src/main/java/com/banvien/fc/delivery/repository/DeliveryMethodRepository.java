package com.banvien.fc.delivery.repository;

import com.banvien.fc.delivery.entity.DeliveryServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeliveryMethodRepository extends JpaRepository<DeliveryServiceEntity, Long> {

    @Query(value = "SELECT * FROM deliveryservice d  " +
            "where d.outletid = :outletId AND d.code = :code", nativeQuery = true)
    DeliveryServiceEntity findDeliveryMethodByCode(@Param("code") String code, @Param("outletId") Long outletId);
}
