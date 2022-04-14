package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.ProductOutletSaleChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOutletSaleChannelRepository extends JpaRepository<ProductOutletSaleChannelEntity, Long> {
    @Query(value = "select distinct c FROM ProductOutletSaleChannelEntity c WHERE c.productOutlet.productOutletId = :productOutletId")
    List<ProductOutletSaleChannelEntity> findByProductOutlet(@Param("productOutletId") Long productOutletId);
}
