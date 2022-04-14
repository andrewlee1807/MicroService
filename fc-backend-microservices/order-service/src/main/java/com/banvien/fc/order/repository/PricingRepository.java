package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.PricingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PricingRepository extends JpaRepository<PricingEntity,Long> {

    @Query(value = "select p.pricingid from pricing p where p.pricingid in (select c.pricingid from customergroup c where c.customergroupid = :customergroupid) and p.status = 1" , nativeQuery = true)
    List<Long> findPricingId(@Param("customergroupid") long customergroupid);
}
