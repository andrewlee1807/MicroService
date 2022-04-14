package com.banvien.fc.account.repository;

import com.banvien.fc.account.entity.LoyaltyMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by son.nguyen on 7/28/2020.
 */
public interface LoyaltyMemberRepository extends JpaRepository<LoyaltyMemberEntity, Long> {
    @Query(value = "SELECT l.customerCode FROM {h-schema}loyaltymember l " +
            "inner join {h-schema}outlet o on o.outletid = l.outletid  " +
            "WHERE l.customerCode ~ E'^CUS(\\\\d)*$'  " +
            "AND o.outletId = :outletId " +
            "ORDER BY cast(NULLIF(regexp_replace(l.customerCode, 'CUS', '', 'g'), '') as NUMERIC) DESC NULLS LAST ",
            nativeQuery = true)
    List<String> findLastCustomerCode(@Param("outletId") Long outletId);

    @Query(value = "from LoyaltyMemberEntity l " +
            "where l.customer.customerId = :customerId and l.outlet.outletId = :outletId ")
    LoyaltyMemberEntity findByOutletAndCustomer(@Param("outletId") Long outletId, @Param("customerId") Long customerId);
}
