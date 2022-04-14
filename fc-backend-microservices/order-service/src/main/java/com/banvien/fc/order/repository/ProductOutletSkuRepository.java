package com.banvien.fc.order.repository;

import com.banvien.fc.order.dto.ProductOutletSkuDTO;
import com.banvien.fc.order.entity.ProductOutletSkuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOutletSkuRepository extends JpaRepository<ProductOutletSkuEntity,Long> {

    ProductOutletSkuEntity findByProductOutletSkuId(Long id);
    List<ProductOutletSkuEntity> findByProductOutletSkuIdIn(List<Long> ids);
}
