package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.PricingSkuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PricingSkuRepository extends JpaRepository<PricingSkuEntity,Long> {
    List<PricingSkuEntity> findByPricingIdAndProductOutletSkuId(Long pricingId, Long productOutletSkuId);
}
