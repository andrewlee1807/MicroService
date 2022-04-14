package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.ProductOutletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ProductOutletRepository extends JpaRepository<ProductOutletEntity, Long> {
    List<ProductOutletEntity> findByProductIdIn(List<Long> productIds);
    List<ProductOutletEntity> findByProductIdInAndOutletId(List<Long> productIds, Long outletId);
    @Query(value = "select distinct c FROM ProductOutletEntity c left join fetch c.productOutletSkus WHERE c.outlet.countryId = :countryId")
    List<ProductOutletEntity> findByOutletCountry(@Param("countryId") Long countryId);

    @Transactional
    @Modifying
    @Query(value = "update {h-schema}productoutlet c1 " +
            "set status = 0, modifieddate = :currentTime from {h-schema}outlet c2 " +
            "where c1.outletId = c2.outletId and c2.countryid = :countryId", nativeQuery = true)
    void deActiveProductOutletByCountryId(@Param("countryId") Long countryId, @Param("currentTime") Timestamp currentTime);
}
