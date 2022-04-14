package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.CustomerRewardOrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerRewardOrderItemRepository extends JpaRepository<CustomerRewardOrderItemEntity, Long> {
    @Transactional
    @Modifying
    @Query(value = " INSERT INTO customerrewardorderitem(customerrewardid, orderoutletid) " +
            " SELECT cr.customerrewardid, :orderOutletId FROM customerreward cr " +
            "WHERE cr.customerrewardid IN (:customerRewardIds)", nativeQuery = true)
    void addToOrder(@Param("customerRewardIds")List<Long> customerRewardIds,@Param("orderOutletId") Long orderOutletId);

    List<CustomerRewardOrderItemEntity> findByOrderOutletOrderOutletID (Long orderOutletId);
}
