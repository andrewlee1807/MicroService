package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.CustomerDebtHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDebHistoryRepository extends JpaRepository<CustomerDebtHistoryEntity,Long> {
}
