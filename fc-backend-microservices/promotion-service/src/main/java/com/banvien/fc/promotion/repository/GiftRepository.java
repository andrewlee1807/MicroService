package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.GiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface GiftRepository extends JpaRepository<GiftEntity, Long> {
    List<GiftEntity> findByGiftIdIn(Collection<Long> ids);
}
