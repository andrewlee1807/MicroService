package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.BrandEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    @Query(value = "select distinct c FROM BrandEntity c WHERE c.countryId = :countryId and c.name in (:brandNameList) and c.outletId is null")
    List<BrandEntity> findByNameListAndCountryId(@Param("brandNameList") Collection<String> brandName,@Param("countryId") Long countryId);

//    @Transactional
//    @Modifying
//    @Query(value = "update {h-schema}brand set markfordelete = 1 where countryid = :countryid and name not in (:nameList) ", nativeQuery = true)
//    void deleteBrandNotInNames(@Param("countryid") Long countryid, @Param("nameList") Collection<String> nameList);

    @Query(value = " select * from " +
            " (select * from brand b " +
            "where b.brandid in (select p.brandid from product p where (p.outletid is null or p.outletid = :outletId )) ) cc where lower(cc.name) like '%' || lower(:name) || '%' order by cc.name ", nativeQuery = true)
    List<BrandEntity> getByBrandNameInOutlet(@Param("name") String name, @Param("outletId") Long outletId, Pageable pageable);

    @Query(value = " select * from " +
            " (select * from brand b " +
            "where b.brandid in (select p.brandid from product p where p.outletid is null)) cc where lower(cc.name) like '%' || lower(:name) || '%' order by cc.name ", nativeQuery = true)
    List<BrandEntity> getByBrandNameAdmin(@Param("name") String name, Pageable pageable);
}
