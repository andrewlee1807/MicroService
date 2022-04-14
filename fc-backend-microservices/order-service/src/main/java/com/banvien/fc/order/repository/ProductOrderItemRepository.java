package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.ProductOrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderItemRepository extends JpaRepository<ProductOrderItemEntity,Long> {
    List<ProductOrderItemEntity> findByOrderOutletId (Long orderId);
}
