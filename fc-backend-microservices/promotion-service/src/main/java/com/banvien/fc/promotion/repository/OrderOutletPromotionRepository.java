package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.OrderOutletPromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderOutletPromotionRepository extends JpaRepository<OrderOutletPromotionEntity,Long> {
    @Query(value = "from OrderOutletPromotionEntity o " +
            "where o.orderOutletId = :orderOutletId ")
    List<OrderOutletPromotionEntity> getOrderOutletPromotionByOrderOutletId(@Param("orderOutletId") Long orderOutletId);
}
