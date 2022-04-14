package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.PromotionCustomerTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

import java.util.List;

@Repository
public interface PromotionCustomerTargetRepository extends JpaRepository<PromotionCustomerTargetEntity, Long> {
    @Query(value = "select p.customerGroupId from PromotionCustomerTargetEntity p where p.outletPromotionId = :outletPromotionId")
    Set<Long> getListCustomerGroupIdByOutletId(@Param("outletPromotionId") Long outletPromotionId);

    List<PromotionCustomerTargetEntity> findByOutletPromotionIdAndCustomerGroupId(Long outletId,Long customerGroupId);

    @Query(value = "select C.groupname As groupname from customergroup C " +
            "join promotioncustomertarget P on P.customergroupid = C.customergroupid " +
            "where P.outletpromotionid = :outletPromotionId ",nativeQuery = true)
    List<String> findGroupName (@Param("outletPromotionId") Long outletPromotionId);

    List<PromotionCustomerTargetEntity> findByOutletPromotionId(Long outletPromotionId);
}
