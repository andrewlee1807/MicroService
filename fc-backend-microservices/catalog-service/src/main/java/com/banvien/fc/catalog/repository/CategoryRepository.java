package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.CatGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository extends JpaRepository<CatGroupEntity,Long> {
    List<CatGroupEntity> findByParentIdIsNull();
    List<CatGroupEntity> findByParentId(long parentId);
    List<CatGroupEntity> findByParentIdNotNull();

    @Query(value = " select * from catgroup c " +
            "where c.active is true and c.countryid = :countryId and c.parentid is not null and c.catgroupid in (select p.catgroupid from product p where p.outletid = :outletId or p.outletid is null) ", nativeQuery = true)
    List<CatGroupEntity> getAllSubCatByOutlet(@Param("outletId") Long outletId, @Param("countryId") Long countryId);

    @Query(value = " select * from catgroup c " +
            "where c.countryid = :countryId and c.active is true and c.parentid is not null and c.catgroupid in (select p.catgroupid from product p where p.outletid is null) ", nativeQuery = true)
    List<CatGroupEntity> getAllSubCatByAdmin(@Param("countryId") Long countryId);

    @Query(value = " select * from catgroup cc " +
            " where cc.countryid = :countryId and cc.parentid is null and cc.catgroupid in (select c.parentid from catgroup c " +
            "where c.active is true and c.parentid is not null and c.catgroupid in (select p.catgroupid from product p where p.outletid = :outletId or p.outletid is null)) ", nativeQuery = true)
    List<CatGroupEntity> getByOutlet(@Param("outletId") Long outletId, @Param("countryId") Long countryId);

    @Query(value = " select * from catgroup cc " +
            " where cc.countryid = :countryId and cc.parentid is null and cc.catgroupid in (select c.parentid from catgroup c " +
            "where c.active is true and c.parentid is not null and c.catgroupid in (select p.catgroupid from product p where p.outletid is null)) ", nativeQuery = true)
    List<CatGroupEntity> getByAdmin(@Param("countryId") Long countryId);

    @Query(value = " select * from " +
            " (select * from catgroup c " +
            "where c.active is true and c.catgroupid in (select p.catgroupid from product p where p.outletid = :outletId or p.outletid is null)) cc where cc.countryid = :countryId and cc.parentid = :parentId ", nativeQuery = true)
    List<CatGroupEntity> getByParentId(@Param("outletId") Long outletId, @Param("parentId") Long parentId, @Param("countryId") Long countryId);

    @Query(value = " select * from " +
            " (select * from catgroup c " +
            "where c.active is true and c.catgroupid in (select p.catgroupid from product p where p.outletid is null)) cc where cc.countryid = :countryId and cc.parentid = :parentId ", nativeQuery = true)
    List<CatGroupEntity> getByParentIdAdmin(@Param("parentId") Long parentId, @Param("countryId") Long countryId);

    @Transactional
    @Modifying
    @Query(value = "update {h-schema}catgroup set active = false where countryId = :countryId and code not in (:codeList) ", nativeQuery = true)
    void deActiveCategoryNotInCodes(@Param("countryId") Long countryId, @Param("codeList") Collection<String> codeList);

    @Query(value = "select distinct c FROM CatGroupEntity c WHERE c.countryId = :countryId and c.code in (:codeList) and c.outletId is null")
    List<CatGroupEntity> findByCodeListAndCountryId( @Param("codeList") Collection<String> codeList,@Param("countryId") Long countryId);
}
