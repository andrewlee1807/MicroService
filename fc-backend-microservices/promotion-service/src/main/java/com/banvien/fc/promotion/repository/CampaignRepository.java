package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface CampaignRepository extends JpaRepository<CampaignEntity,Long> {
    List<CampaignEntity> findByOutletOutletIdAndStatus(Long outletId, int status);
}
