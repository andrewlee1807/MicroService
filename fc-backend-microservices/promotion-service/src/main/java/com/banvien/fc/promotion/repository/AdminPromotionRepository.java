package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.AdminPromotionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface AdminPromotionRepository extends JpaRepository<AdminPromotionEntity, Long> {

    List<AdminPromotionEntity> findAllByNameContainsIgnoreCaseAndStartDateAfterAndEndDateBeforeAndCountryId(String title, Date startDate, Date endDate, Long countryId);

    List<AdminPromotionEntity> findAllByNameContainsIgnoreCaseAndStatusAndStartDateAfterAndEndDateBeforeAndCountryId(String title, Boolean status, Date startDate, Date endDate, Long countryId);


    @Query(value = "from AdminPromotionEntity a " +
            "where a.countryId = :countryId and a.status = true and a.endDate < :currentDate")
    List<AdminPromotionEntity> getByExpiredDateAndActiveStatus(@Param("countryId") Long countryId, @Param("currentDate") Timestamp currentDate);

    @Query(value = "from AdminPromotionEntity a " +
            "where a.countryId = :countryId and (:title like '' or lower(a.name) like  '%' || lower(:title) || '%' ) and (:status is null or a.status = :status) and a.startDate >= :startDate and a.endDate <= :endDate")
    Page<AdminPromotionEntity> getByTitleAndStatusAndStartEndDate(String title, Timestamp startDate, Timestamp endDate, Boolean status, @Param("countryId") Long countryId, Pageable pageable);
}
