package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.CustomerRewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface CustomerRewardRepository extends JpaRepository<CustomerRewardEntity, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE customerreward " +
            "SET redeem = true, redeemdate = :current_timestamp, incart = false " +
            "WHERE incart = true AND customerrewardid IN (:customerRewardIds)", nativeQuery = true)
    void redeemRewards(@Param("customerRewardIds")List<Long> customerRewardIds, @Param("current_timestamp")Timestamp current_timestamp);

    @Query(value = " SELECT * FROM customerreward c " +
            "WHERE c.redeem = false AND c.incart = true AND c.customerid =:customerId " +
            "and case when :outletId <> -1 then c.outletid = :outletId else 1 = 1 end",nativeQuery = true)
    List<CustomerRewardEntity> findAllInCart (@Param("customerId")Long customerId,@Param("outletId")Long outletId);

    @Query(value = "SELECT COUNT(*) FROM customerreward cr " +
            " LEFT JOIN gift g ON cr.giftid = g.giftid WHERE " +
            "cr.redeem = false AND cr.customerid = :customerId AND (cr.incart = false or cr.incart is null) " +
            "AND (cr.giftid IS NULL OR g.expireddate >= now()) " +
            "AND cr.giftid is not null ",nativeQuery = true)
    Integer countAvailableGift(@Param("customerId")Long customerId);

    @Query(value = "from CustomerRewardEntity c " +
            "where  c.redeemCode =:redeemCode and c.orderOutletId IS NULL")
    CustomerRewardEntity getCustomerRewardByCode(@Param("redeemCode") String redeemCode);
}
