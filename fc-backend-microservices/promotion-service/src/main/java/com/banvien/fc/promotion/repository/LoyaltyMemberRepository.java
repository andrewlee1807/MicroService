package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.LoyaltyMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface LoyaltyMemberRepository extends JpaRepository<LoyaltyMemberEntity,Long> {

    LoyaltyMemberEntity findByCustomerIdAndOutletId(Long customerId, Long outletId);
}
