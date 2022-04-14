package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByOutletId(long outletId);
    List<ProductEntity> findByOutletIdAndActiveTrue(long outletId);
    List<ProductEntity> findByBrandId(long brandId);
    List<ProductEntity> findByCatGroupId(long catGroupId);
    List<ProductEntity> findByOutletIdAndBrandId(long outletId, long brandId);
    List<ProductEntity> findByOutletIdAndCatGroupId(long outletId, long catGroupId);
    List<ProductEntity> findByBrandIdIn(List<Long> brandIds);
    List<ProductEntity> findByCatGroupIdIn(List<Long> catGroupIds);
    List<ProductEntity> findByOutletIdAndBrandIdIn(long outletId, List<Long> brandIds);
    List<ProductEntity> findByOutletIdAndCatGroupIdIn(long outletId, List<Long> catGroupIds);
    List<ProductEntity> findByNameContainsIgnoreCaseAndOutletId(String name,Long outletId);

    @Transactional
    @Modifying
    @Query(value = "update {h-schema}product p " +
            "set active = false, modifieddate = :currentTime from {h-schema}catgroup c " +
            "where c.catgroupid = p.catgroupid and c.countryid = :countryId", nativeQuery = true)
    void deActiveProductByCountryId(@Param("countryId") Long countryId, @Param("currentTime") Timestamp currentTime);

    List<ProductEntity> findByCodeInAndCatGroupCountryIdAndOutletIdIsNull(Collection<String> codeList, Long countryId);

    @Query(value = "select distinct c FROM ProductEntity c WHERE c.code in (:codeList) and c.catGroup.countryId = :countryId and c.outletId is null")
    List<ProductEntity> findByCodeAndCountryId(@Param("codeList") Collection<String> codeList, @Param("countryId") Long countryId);

    @Query(value = "select distinct c FROM ProductEntity c left join fetch c.productSkus WHERE c.code in (:codeList) and c.outletId is null")
    List<ProductEntity> findByCodeList(@Param("codeList") Collection<String> codeList);
}
