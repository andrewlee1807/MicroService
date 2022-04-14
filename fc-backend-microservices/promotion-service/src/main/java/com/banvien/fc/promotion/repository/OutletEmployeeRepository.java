package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.AdminPromotionEntity;
import com.banvien.fc.promotion.entity.OutletEmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OutletEmployeeRepository extends JpaRepository<OutletEmployeeEntity, Long> {
    OutletEmployeeEntity findByUserId(Long userId);
}
