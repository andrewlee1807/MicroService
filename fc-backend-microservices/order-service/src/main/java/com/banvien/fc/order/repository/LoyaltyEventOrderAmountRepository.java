package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.LoyaltyEventOrderAmountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface LoyaltyEventOrderAmountRepository extends JpaRepository<LoyaltyEventOrderAmountEntity,Long> {

    @Query(value = " select * FROM loyaltyeventorderamount  e " +
            " join loyaltyoutletevent l on l.loyaltyoutleteventid = e.loyaltyoutleteventid " +
            " where l.outletid = :outletId" +
            " and e.orderamount <= :price" +
            " and (l.startdate < :current_timestamp and l.enddate > :current_timestamp) " +
            " and l.status = 'ACTIVE'" , nativeQuery = true)
    List<LoyaltyEventOrderAmountEntity> findByOrderAmount(@Param("outletId")Long outletId,@Param("price") Double price,@Param("current_timestamp") Timestamp current_timestamp);
}
