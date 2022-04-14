package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.dto.ProductPromotionDTO;
import com.banvien.fc.catalog.entity.ProductOutletSkuEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductOutletSkuRepository extends JpaRepository<ProductOutletSkuEntity, Long> {
    @Query(value = " select p2.name as pname,p.image as pimage,p.productoutletskuid as pproductoutletskuid,p.title as ptitle from productoutletsku p" +
            " join productoutlet p2 on p2.productoutletid = p.productoutletid where p2.outletid = :outletId and p2.status = 1 and p.status = 1 " +
            "and p.productoutletskuid not in (select p3.productoutletskuid from productpromotionblocked p3)", nativeQuery = true)
    List<ProductPromotionDTO> findAllProduct(@Param("outletId") Long outletId);

    @Query(value = " select p2.name as pname,p.image as pimage,p.productoutletskuid as pproductoutletskuid,p.title as ptitle from productoutletsku p " +
            "join productoutlet p2 on p2.productoutletid = p.productoutletid " +
            "join product p3 on p2.productid = p3.productid " +
            "where p3.brandid in (:brandIds) and p2.status = 1 and p.status = 1 and p2.outletid = :outletId and p.productoutletskuid not in (select p3.productoutletskuid from productpromotionblocked p3 )", nativeQuery = true)
    List<ProductPromotionDTO> findProductByBrand(@Param("brandIds") List<Long> brandIds, @Param("outletId") Long outletId);

    @Query(value = "select p2.name as pname,p.image as pimage,p.productoutletskuid as pproductoutletskuid,p.title as ptitle from productoutletsku p " +
            "join productoutlet p2 on p2.productoutletid = p.productoutletid " +
            "join product p3 on p2.productid = p3.productid " +
            " where p3.catgroupid in (:catGroupIds) and p2.status = 1 and p.status = 1 and p2.outletid = :outletId and  p.productoutletskuid not in (select p3.productoutletskuid from productpromotionblocked p3 )", nativeQuery = true)
    List<ProductPromotionDTO> findProductByCatGroup(@Param("catGroupIds") List<Long> catGroupIds, @Param("outletId") Long outletId);

    @Query(value = " select p2.name as pname,p.image as pimage,p.productoutletskuid as pproductoutletskuid,p.title as ptitle from productoutletsku p" +
            " join productoutlet p2 on p2.productoutletid = p.productoutletid where p2.outletid = :outletId " +
            "and lower(p2.name) like '%' || lower(:key) || '%' " +
            "and p.productoutletskuid not in (select p3.productoutletskuid from productpromotionblocked p3 where p3.productoutletskuid is not null) and p2.status = 1 and p.status = 1 order by p2.name", nativeQuery = true)
    List<ProductPromotionDTO> findAllProductByName(@Param("outletId") Long outletId, @Param("key") String key, Pageable pageable);

    @Query(value = " select p2.name as pname,p.image as pimage,p.productoutletskuid as pproductoutletskuid,p.title as ptitle from productoutletsku p" +
            " join productoutlet p2 on p2.productoutletid = p.productoutletid where p2.outletid = :outletId and p2.createdby = :userId and p2.status = 1 and p.status = 1 " +
            "and p.productoutletskuid not in (select p3.productoutletskuid from productpromotionblocked p3)", nativeQuery = true)
    List<ProductPromotionDTO> findAllProductAdmin(@Param("userId") Long userId, @Param("outletId") Long outletId);

    @Query(value = " select p2.name as pname,p.image as pimage,p.productoutletskuid as pproductoutletskuid,p.title as ptitle from productoutletsku p" +
            " join productoutlet p2 on p2.productoutletid = p.productoutletid where p2.outletid = :outletId and p2.createdby = :userId " +
            "and lower(p2.name) like '%' || lower(:key) || '%' " +
            "and p.productoutletskuid not in (select p3.productoutletskuid from productpromotionblocked p3 where p3.productoutletskuid is not null) and p2.status = 1 and p.status = 1 order by p2.name", nativeQuery = true)
    List<ProductPromotionDTO> findAllProductAdminByName(@Param("userId") Long userId, @Param("outletId") Long outletId, @Param("key") String key, Pageable pageable);

    @Query(value = "select p2.name as pname,p.image as pimage,p.productoutletskuid as pproductoutletskuid,p.title as ptitle from productoutletsku p " +
            "join productoutlet p2 on p2.productoutletid = p.productoutletid " +
            "join product p3 on p2.productid = p3.productid " +
            " where p3.catgroupid in (:catGroupIds) and p2.status = 1 and p.status = 1 and p2.createdby = :userId and p2.outletid = :outletId and  p.productoutletskuid not in (select p3.productoutletskuid from productpromotionblocked p3 )", nativeQuery = true)
    List<ProductPromotionDTO> findProductAdminByCatGroup(@Param("userId") Long userId, @Param("catGroupIds") List<Long> catGroupIds, @Param("outletId") Long outletId);

    @Query(value = " select p2.name as pname,p.image as pimage,p.productoutletskuid as pproductoutletskuid,p.title as ptitle from productoutletsku p " +
            "join productoutlet p2 on p2.productoutletid = p.productoutletid " +
            "join product p3 on p2.productid = p3.productid " +
            "where p3.brandid in (:brandIds) and p2.status = 1 and p.status = 1 and p2.createdby = :userId and p2.outletid = :outletId and p.productoutletskuid not in (select p3.productoutletskuid from productpromotionblocked p3 )", nativeQuery = true)
    List<ProductPromotionDTO> findProductAdminByBrand(@Param("userId") Long userId, @Param("brandIds") List<Long> brandIds, @Param("outletId") Long outletId);

    @Query(value = "select productoutletskuid from productoutletsku where productoutletid in " +
            "(select productoutletid from productoutlet where outletid = :outletId and productid in " +
            "(select productid from product where catgroupid in " +
            "(with recursive subcatgroup as " +
            "(select catgroupid from catgroup where catgroupid in :catIds " +
            "union " +
            "select c.catgroupid from catgroup c " +
            "inner join subcatgroup s on s.catgroupid = c.parentid " +
            "inner join catgroupoutlet co on c.catgroupid = co.catgroupid " +
            "where co.outletid = :outletId) " +
            "select catgroupid from subcatgroup)))", nativeQuery = true)
    Set<Long> findAllSkuIdByCatGroupIds(Long outletId, List<Long> catIds);

    @Query(value = "select productoutletskuid from productoutletsku where productoutletid in  " +
            "(select productoutletid from productoutlet where outletid = :outletId and productid in  " +
            "(select productid from product where brandid in :brandIds));",nativeQuery = true)
    Set<Long> findAllSkuIdByBrandIds(Long outletId, List<Long> brandIds);
}
