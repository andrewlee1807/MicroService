package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.OutletPromotionCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutletPromotionCustomerRepository extends JpaRepository<OutletPromotionCustomerEntity,Long> {
    OutletPromotionCustomerEntity findByOutletPromotionIdAndCustomerId(Long outletPromotionId,Long customerId);

    OutletPromotionCustomerEntity findByCustomerIdAndOutletPromotionId(Long customerId, Long outletPromotionId);
}
