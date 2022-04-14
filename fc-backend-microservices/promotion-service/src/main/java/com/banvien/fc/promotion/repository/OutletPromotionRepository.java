package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.OutletPromotionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface OutletPromotionRepository
        extends JpaRepository<OutletPromotionEntity, Long>, PagingAndSortingRepository<OutletPromotionEntity, Long> {

    List<OutletPromotionEntity> findByOutletOutletId(Long outletId);

    @Query(value = "from OutletPromotionEntity o " +
            "where o.status = true and o.outlet.outletId = :outletId and " +
            "o.remainGift > 0 and o.priority IS NOT NULL and o.easyOrderCode is null and " +
            "o.startDate <= :timestamp and o.endDate >= :timestamp " +
            "order by o.priority DESC, o.startDate DESC, o.createdDate DESC")
    List<OutletPromotionEntity> getAllOutLetPromotionByOutletIdOrderByPriority(@Param("outletId") Long outletId, @Param("timestamp") Timestamp timestamp);

    @Query(value = "from OutletPromotionEntity o " +
            "where o.status = true and o.outlet.outletId = :outletId and " +
            "o.remainGift > 0 and o.priority IS NOT NULL and " +
            "o.startDate <= :timestamp and o.endDate >= :timestamp " +
            "order by o.priority DESC, o.startDate DESC, o.createdDate DESC ")
    Page<OutletPromotionEntity> getAllOutLetPromotionByOutletId(@Param("outletId") Long outletId, @Param("timestamp") Timestamp timestamp, Pageable pageable);

    @Query(value = "from OutletPromotionEntity o " +
            "where o.status = true and o.outlet.outletId = :outletId and " +
            "o.remainGift > 0 and o.priority IS NOT NULL and " +
            "o.startDate <= :timestamp and o.endDate >= :timestamp " +
            "order by o.priority DESC, o.startDate DESC, o.createdDate DESC ")
    List<OutletPromotionEntity> getAllOutLetPromotionByOutletId(@Param("outletId") Long outletId, @Param("timestamp") Timestamp timestamp);

    @Query(value = "from OutletPromotionEntity o " +
            "where o.outlet.outletId = :outletId and (:title is null or :title like '' or lower(o.title) like  '%' || lower(:title) || '%' ) " +
            "order by o.status DESC, o.startDate DESC, o.priority DESC ")
    Page<OutletPromotionEntity> getAllOutLetPromotion4Wholesaler(@Param("title") String title, @Param("outletId") Long outletId, Pageable pageable);

    @Query(value = "from OutletPromotionEntity o " +
            "where o.outlet.outletId = :outletId and o.priority IS NOT NULL and " +
            "o.groupCode IS NOT NULL and o.endDate >= :currentDate")
    List<OutletPromotionEntity> getByOutletIdCreateByAdmin(@Param("outletId") Long outletId, @Param("currentDate") Timestamp currentDate);

    @Query(value = "from OutletPromotionEntity o " +
            "where o.outlet.outletId = :outletId and " +
            "o.status = true and o.endDate < :currentDate ")
    List<OutletPromotionEntity> getByExpiredDateAndActiveStatus(@Param("outletId") Long outletId, @Param("currentDate") Timestamp currentDate);


    @Query(value = "from OutletPromotionEntity o " +
            "where o.outlet.outletId = :outletId and " +
            "o.status = true and o.endDate > :currentDate ")
    List<OutletPromotionEntity> findPromotionIsActive(@Param("outletId") Long outletId, @Param("currentDate") Timestamp currentDate);

    @Query(value = "from OutletPromotionEntity o " +
            "where o.outlet.outletId = :outletId and " +
            "o.status = true and o.remainGift = 0 and o.priority IS NOT NULL ")
    List<OutletPromotionEntity> getByRemainAndActiveStatus(@Param("outletId") Long outletId);

    @Query(value = "from OutletPromotionEntity o " +
            "where o.status = true and o.outlet.outletId = :outletId and " +
            "o.remainGift > 0 and o.priority IS NOT NULL and " +
            "o.endDate >= :timestamp ")
    List<OutletPromotionEntity> getByActiveStatus(@Param("outletId") Long outletId, @Param("timestamp") Timestamp timestamp);

    @Query(value = "select o.easyOrderCode from OutletPromotionEntity o " +
            "where o.easyOrderCode is not null and o.outlet.outletId = :outletId and o.status = true and " +
            " o.remainGift > 0 and o.startDate <= :currentDate and o.endDate >= :currentDate ")
    List<String> getAllEasyOrderCodeByOutletId(@Param("outletId") Long outletId, @Param("currentDate") Timestamp currentDate);

    @Query(value = "from OutletPromotionEntity o " +
            "where o.status = true and o.remainGift > 0 and o.startDate <= :currentDate and o.endDate >= :currentDate and o.easyOrderCode like :easyOrderCode ")
    OutletPromotionEntity getOutletPromotionByEasyOrderCode(@Param("easyOrderCode") String easyOrderCode, @Param("currentDate") Timestamp currentDate);

    @Query(value = "from OutletPromotionEntity o " +
            "where o.outlet.outletId = :outletId and (:title like '' or lower(o.title) like  '%' || lower(:title) || '%' ) and (:status is null or o.status = :status) and o.startDate >= :startDate and o.endDate <= :endDate ")
    Page<OutletPromotionEntity> getByTitleAndStatusAndStartEndDate(String title, Timestamp startDate, Timestamp endDate, long outletId, Boolean status, Pageable pageable);

    List<OutletPromotionEntity> findAllByTitleContainsIgnoreCaseAndStatusAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndOutletOutletId(String title, Boolean status, Date startDate, Date endDate, long outletId);

    List<OutletPromotionEntity> findAllByTitleContainsIgnoreCaseAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndOutletOutletId(String title, Date startDate, Date endDate, long outletId);

    List<OutletPromotionEntity> findByGroupCode(String groupCode);

    List<OutletPromotionEntity> findByOutletOutletIdAndEndDateAfter(Long outletId, Timestamp endDate);

    Page<OutletPromotionEntity> findByOutletOutletIdAndModifiedDateAfterAndEasyOrderCodeIsNullAndPenetrationType(Long outletId, Timestamp modifiedDate, Integer penetrationType, Pageable pageable);

    List<OutletPromotionEntity> findAllByOutletOutletIdAndModifiedDateAfterAndEasyOrderCodeIsNullAndPenetrationType(Long outletId, Timestamp modifiedDate, Integer penetrationType);
}
