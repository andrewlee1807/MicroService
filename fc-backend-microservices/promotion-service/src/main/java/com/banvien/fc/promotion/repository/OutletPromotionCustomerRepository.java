package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.OutletPromotionCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutletPromotionCustomerRepository extends JpaRepository<OutletPromotionCustomerEntity, Long> {
    OutletPromotionCustomerEntity findByCustomerIdAndOutletPromotionId(Long customerId, Long outletPromotionId);
}
