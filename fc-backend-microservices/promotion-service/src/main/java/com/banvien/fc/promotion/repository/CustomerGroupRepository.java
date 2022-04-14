package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.CustomerGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomerGroupRepository extends JpaRepository<CustomerGroupEntity, Long> {
    @Query(value = "select c from CustomerGroupEntity c where c.outletId = :outletId and c.customerGroupId = :customerGrId")
    CustomerGroupEntity findByOutletIdAndCustomerGroupId(@Param("outletId") Long outletId, @Param("customerGrId") Long customerGrId);

    @Query("select c.customerGroupId from CustomerGroupEntity c where c.outletId = :outletId and c.groupName = :groupName")
    List<Long> findIdByOutletOutletIdAndGroupName(@Param("outletId") Long outletId, @Param("groupName") String groupName);

    @Query("from CustomerGroupEntity c where c.outletId = :outletId")
    List<CustomerGroupEntity> findIdByOutletOutletId(@Param("outletId") Long outletId);
}
