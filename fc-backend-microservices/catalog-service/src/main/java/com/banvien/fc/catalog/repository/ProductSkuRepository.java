package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.ProductSkuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSkuRepository extends JpaRepository<ProductSkuEntity, Long> {
}
