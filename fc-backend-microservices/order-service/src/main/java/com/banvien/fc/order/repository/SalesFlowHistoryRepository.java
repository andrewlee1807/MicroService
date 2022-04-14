package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.SalesFlowHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesFlowHistoryRepository extends JpaRepository<SalesFlowHistoryEntity, Long> {
}
