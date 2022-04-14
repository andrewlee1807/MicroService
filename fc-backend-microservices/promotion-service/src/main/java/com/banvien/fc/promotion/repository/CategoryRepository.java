package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.CatGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository extends JpaRepository<CatGroupEntity,Long> {
    CatGroupEntity findByCatGroupId(Long categoryId);
    List<CatGroupEntity> findByCatGroupIdIn(Collection ids);
}
