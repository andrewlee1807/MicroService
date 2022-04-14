package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.ProductPromotionBlockedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPromotionBlockedRepository extends JpaRepository<ProductPromotionBlockedEntity, Long> {
    List<ProductPromotionBlockedEntity> findByOutletId(Long id);

    List<ProductPromotionBlockedEntity> findByOutletIdIsNull();

    void deleteByProductOutletSkuIdAndOutletId(Long skuId, Long outletId);

    void deleteByProductId(Long productId);

    List<ProductPromotionBlockedEntity> findByOutletIdOrOutletIdIsNull(Long id);
}
