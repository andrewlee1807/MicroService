package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.DataInActiveTrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DataInActiveTrackingRepository extends JpaRepository<DataInActiveTrackingEntity, Long> {
    @Query(value=" from DataInActiveTrackingEntity d where " +
            "d.inactiveId = :inactiveId and d.outletId = :outletId")
    DataInActiveTrackingEntity getByOutletPromotionIdAndOutletId(@Param("inactiveId") Long outletPromotionId, @Param("outletId") Long outletId);
}
