package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.BlockProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlockProductRepository extends JpaRepository<BlockProductEntity, Long> {
    @Query(value = "select productoutletskuid, (name || ' | ' || title) as name, category, totalview, skucode, image, saleprice, status from " +
            "(select p.productoutletid, name, productid,totalview,skucode, image,saleprice,status,productoutletskuid,title " +
            "from (select productoutletid, name, productid, totalview from productoutlet where lower(name) like '%' || lower(:name) || '%' and (outletid is null or outletid = :outletId )) p inner join " +
            "(select skucode, image, saleprice, productoutletid, status, productoutletskuid, title from productoutletsku where productoutletskuid in " +
            "(select productoutletskuid from productpromotionblocked p where p.outletid is not null and p.outletid = :outletId) " +
            "and skucode like %:skuCode%) p2 on p.productoutletid = p2.productoutletid) p inner join " +
            "(select p.name as category, productid from product inner join " +
            "(select catgroupid, name from catgroup where countryid = :countryId and lower(name) like '%' || lower(:category) || '%') p on product.catgroupid = p.catgroupid) p1 " +
            "on p.productid = p1.productid"
            , countQuery = "select count(*) from " +
            "(select p.productoutletid, name, productid,totalview,skucode, image,saleprice,status,productoutletskuid,title " +
            "from (select productoutletid, name, productid, totalview from productoutlet where lower(name) like '%' || lower(:name) || '%' and (outletid is null or outletid = :outletId)) p inner join " +
            "(select skucode, image, saleprice, productoutletid, status, productoutletskuid, title from productoutletsku where productoutletskuid in " +
            "(select productoutletskuid from productpromotionblocked p where p.outletid is not null and p.outletid = :outletId) " +
            "and skucode like %:skuCode%) p2 on p.productoutletid = p2.productoutletid) p inner join " +
            "(select p.name as category, productid from product inner join " +
            "(select catgroupid, name from catgroup where countryid = :countryId and lower(name) like '%' || lower(:category) || '%') p on product.catgroupid = p.catgroupid) p1 " +
            "on p.productid = p1.productid",
            nativeQuery = true)
    Page<BlockProductEntity> findBlockProduct(@Param("outletId") long outletId,
                                              @Param("category") String category,
                                              @Param("name") String name,
                                              @Param("skuCode") String skuCode,
                                              @Param("countryId") Long countryId, Pageable pageable);

    @Query(value = "select productoutletskuid, (name || ' | ' || title) as name, category, totalview, skucode, image, saleprice, status from " +
            "(select p.productoutletid, name, productid,totalview,skucode, image,saleprice,status,productoutletskuid, title from" +
            "(select productoutletid, name, productid, totalview from productoutlet where lower(name) like '%' || lower(:name) || '%' and (outletid is null or outletid = :outletId )) p inner join " +
            "(select skucode, image, saleprice, productoutletid, status, productoutletskuid, title from productoutletsku where status = 1 and productoutletskuid not in " +
            "(select productoutletskuid from productpromotionblocked p where p.outletid is not null and p.outletid = :outletId) " +
            "and skucode like %:skuCode%) p2 on p.productoutletid = p2.productoutletid) p inner join " +
            "(select p.name as category, productid from product inner join " +
            "(select catgroupid, name from catgroup where countryid = :countryId and lower(name) like '%' || lower(:category) || '%') p on product.catgroupid = p.catgroupid) p1 " +
            "on p.productid = p1.productid"
            , countQuery = "select count(*) from " +
            "(select p.productoutletid, name, productid,totalview,skucode, image,saleprice,status,productoutletskuid, title from" +
            "(select productoutletid, name, productid, totalview from productoutlet where lower(name) like '%' || lower(:name) || '%' and (outletid is null or outletid = :outletId )) p inner join " +
            "(select skucode, image, saleprice, productoutletid, status, productoutletskuid, title from productoutletsku where status = 1 and productoutletskuid not in " +
            "(select productoutletskuid from productpromotionblocked p where p.outletid is not null and p.outletid = :outletId) " +
            "and skucode like %:skuCode%) p2 on p.productoutletid = p2.productoutletid) p inner join " +
            "(select p.name as category, productid from product inner join " +
            "(select catgroupid, name from catgroup where countryid = :countryId and lower(name) like '%' || lower(:category) || '%') p on product.catgroupid = p.catgroupid) p1 " +
            "on p.productid = p1.productid"
            , nativeQuery = true)
    Page<BlockProductEntity> findNonBlockProduct(@Param("outletId") long outletId,
                                                 @Param("category") String category,
                                                 @Param("name") String name,
                                                 @Param("skuCode") String skuCode,
                                                 @Param("countryId") Long countryId, Pageable pageable);


}
