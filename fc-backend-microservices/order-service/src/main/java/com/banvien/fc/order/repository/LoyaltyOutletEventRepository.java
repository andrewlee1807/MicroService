package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.LoyaltyOutletEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface LoyaltyOutletEventRepository extends JpaRepository<LoyaltyOutletEventEntity, Long> {

    @Query("select l from LoyaltyOutletEventEntity l where l.status='ACTIVE' and l.loyaltyMasterEventId = :masterEvent and l.outletId = :outletId")
    LoyaltyOutletEventEntity findPointLoyaltyEvent(@Param("masterEvent") Long masterEvent, @Param("outletId") Long outletId);

    List<LoyaltyOutletEventEntity> findByStatusAndLoyaltyMasterEventIdAndOutletId(String status, Long masterEvent, Long outletId);


    @Query(value = "select loe.* from loyaltyeventorderproductproperty leopp " +
            "inner join loyaltyeventorderproduct leop on " +
            "leopp.loyaltyeventorderproductid = leop.loyaltyeventorderproductid " +
            "inner join loyaltyoutletevent loe on " +
            "loe.loyaltyoutleteventid = leop.loyaltyoutleteventid " +
            "where loe.point in (select max(point) from (select productoutletskuid, point from loyaltyeventorderproduct leop " +
            "inner join loyaltyeventorderproductproperty leopp on leop.loyaltyeventorderproductid = leopp.loyaltyeventorderproductid " +
            "group by leop.loyaltyeventorderproductid, leopp.loyaltyeventorderproductpropertyid, productoutletskuid) as maxpointleopp " +
            "group by maxpointleopp.productoutletskuid) " +
            "and leopp.productoutletskuid in (:productOutletSkuIds) " +
            "and loe.status = 'ACTIVE' " +
            "and case " +
            "when leop.hasDuration then loe.startdate < :currentTime " +
            "and loe.enddate > :currentTime " +
            "else 1 = 1 " +
            "end " +
            "group by leop.loyaltyeventorderproductid,loe.loyaltyoutleteventid,leopp.productoutletskuid",nativeQuery = true)
    List<Object[]> getEventByProductOutletSkuIds(@Param("productOutletSkuIds")List<Long> productOutletSkuIds, @Param("currentTime")Timestamp currentTime);


    @Query(value= "SELECT sku.productoutletskuid  " +
            "FROM productoutletsku sku  "+
            "INNER JOIN loyaltyeventorderproductproperty leopp ON leopp.productoutletskuid = sku.productoutletskuid  " +
            "LEFT JOIN loyaltyeventorderproduct leop on leop.loyaltyeventorderproductid = leopp.loyaltyeventorderproductid  " +
            "LEFT JOIN loyaltyoutletevent loe on loe.loyaltyoutleteventid = leop.loyaltyoutleteventid  " +
            "WHERE loe.loyaltyoutleteventid = :loyaltyOutletEventId",nativeQuery = true)
    List<Object> findCorrespondingSkuJoinInEventOrder(@Param("loyaltyOutletEventId")Long loyaltyOutletEventId);

    @Query(value = "SELECT * " +
            "FROM loyaltyoutletevent loe  " +
            "join loyaltymasterevent lme on loe.loyaltymastereventid = lme.loyaltymastereventid  " +
            "AND loe.outletid = :outletId " +
            "AND lme.eventType = :eventType  " +
            "and lme.title =:title",nativeQuery = true)
    List<LoyaltyOutletEventEntity> findByOutletIdAndMasterEventAndTitle(@Param("outletId")Long outletId,@Param("eventType")String eventType,@Param("title")String title);

    List<LoyaltyOutletEventEntity> findByOutletIdAndLoyaltyMasterEventId(Long outletId,Long loyaltyMasterEventId);

    List<LoyaltyOutletEventEntity> findByOutletIdAndStatusAndStartDateBeforeAndEndDateAfter(long outletId, String status, Timestamp startDate, Timestamp endDate);


}
