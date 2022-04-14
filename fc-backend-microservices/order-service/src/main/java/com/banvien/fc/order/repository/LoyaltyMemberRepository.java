package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.LoyaltyMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface LoyaltyMemberRepository extends JpaRepository<LoyaltyMemberEntity, Long> {

    LoyaltyMemberEntity findByCustomerIdAndOutletOutletId(Long id, Long outletId);

    @Transactional
    @Modifying
    @Query("update LoyaltyMemberEntity l set l.point = :point, l.updatedDate = :updateDate where l.loyaltyMemberId = :id")
    void updateLoyaltyMember(@Param("point") int point, @Param("updateDate") Timestamp updateDate, @Param("id") Long id);

    List<LoyaltyMemberEntity> findByCustomerIdAndOutletOutletIdAndActive(Long customerId, Long outletId, int active);

    @Query(value = "SELECT l.customerCode FROM loyaltymember l " +
            "inner join outlet o on o.outletid = l.outletid " +
            "WHERE l.customerCode ~ E'^CUS(\\\\\\\\d)*$' " +
            "AND o.outletId = :outletId " +
            "ORDER BY cast(NULLIF(regexp_replace(l.customerCode, 'CUS', '', 'g'), '') as NUMERIC) DESC NULLS LAST", nativeQuery = true)
    String findLastCustomerCode(@Param("outletId") Long outletId);
}
