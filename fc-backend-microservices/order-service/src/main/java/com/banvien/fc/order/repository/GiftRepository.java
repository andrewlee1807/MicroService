package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.GiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface GiftRepository extends JpaRepository<GiftEntity,Long> {

    List<GiftEntity> findByOutletId(Long outletId);
    List<GiftEntity> findByGiftIdIn(List<Long> ids);

    @Query(value = "from GiftEntity g where " +
            "g.giftId = :id and g.status = 1 and g.expiredDate >= :currentDate and g.typePromotion is not null and g.typePromotion = true and (g.quantityPromotion is not null and g.quantityPromotion > 0)")
    GiftEntity getGiftForPromotionApplied(@Param("id") Long id,@Param("currentDate") Timestamp currentDate);
}
