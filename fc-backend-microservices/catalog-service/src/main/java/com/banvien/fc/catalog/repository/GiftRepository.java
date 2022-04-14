package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.GiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface GiftRepository extends JpaRepository<GiftEntity,Long> {

    List<GiftEntity> findByOutletIdAndTypePromotionIsTrueAndStatus(Long outletId,int status);

    @Query(value = "from GiftEntity g where " +
            "g.outletId = :outletId and g.status = 1 and g.expiredDate >= :currentDate and g.typePromotion = true and (g.quantityPromotion is not null and g.quantityPromotion > 0 ) ")
    List<GiftEntity> getAllGiftForCreatePromotion(Long outletId, Timestamp currentDate);

    GiftEntity findByGiftId(Long giftId);

    @Query(value = "from GiftEntity g where " +
            "g.giftId = :id and g.status = 1 and g.expiredDate >= :currentDate and g.typePromotion is not null and g.typePromotion = true and (g.quantityPromotion is not null and g.quantityPromotion > 0 )")
    GiftEntity getGiftForPromotionApplied(Long id, Timestamp currentDate);
}
