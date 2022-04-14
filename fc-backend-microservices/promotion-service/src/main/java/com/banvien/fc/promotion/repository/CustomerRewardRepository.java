package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.CustomerRewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface CustomerRewardRepository extends JpaRepository<CustomerRewardEntity, Long> {
    @Query(value = "from CustomerRewardEntity c " +
            "where c.customerId = :customerId and c.redeem = false and c.redeemCode =:redeemCode and c.expiredDate >= :timestamp")
    CustomerRewardEntity getCustomerRewardByCode(@Param("redeemCode") String redeemCode, @Param("timestamp") Timestamp timestamp, @Param("customerId") long customerId);

    CustomerRewardEntity findByOrderOutletId(Long orderOutletId);
}
