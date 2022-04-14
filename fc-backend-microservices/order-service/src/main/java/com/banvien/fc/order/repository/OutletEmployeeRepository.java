package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.OutletEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutletEmployeeRepository extends JpaRepository<OutletEmployeeEntity, Long> {
    OutletEmployeeEntity findByUserUserId(Long userId);
}
