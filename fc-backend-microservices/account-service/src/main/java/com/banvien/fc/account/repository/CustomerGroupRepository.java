package com.banvien.fc.account.repository;

import com.banvien.fc.account.entity.CustomerGroupEntity;
import com.banvien.fc.account.entity.LoyaltyEventWelcomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by son.nguyen on 7/28/2020.
 */
@Repository
public interface CustomerGroupRepository extends JpaRepository<CustomerGroupEntity, Long> {
    @Query("from CustomerGroupEntity c where c.outletId = :outletId")
    List<CustomerGroupEntity> findIdByOutletOutletId(@Param("outletId") Long outletId);
}
