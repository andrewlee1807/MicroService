package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.StockOutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockOutRepository extends JpaRepository<StockOutEntity, Long> {

}
