package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.OutletPromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface OutletPromotionRepository extends JpaRepository<OutletPromotionEntity,Long> {
    @Query(value = "from OutletPromotionEntity o " +
            "where o.status = true and o.outletPromotionId = :outletPromotionId and " +
            "o.remainGift > 0 and o.priority IS NOT NULL and " +
            "o.startDate <= :timestamp and o.endDate >= :timestamp ")
    OutletPromotionEntity getOutLetPromotionById(@Param("outletPromotionId") Long outletPromotionId, @Param("timestamp") Timestamp timestamp);
}
