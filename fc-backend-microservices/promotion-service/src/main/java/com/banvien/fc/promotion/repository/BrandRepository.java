package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    BrandEntity findByBrandId(Long id);
}
