package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.BlockProductAdminEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlockProductAdminRepository extends JpaRepository<BlockProductAdminEntity, Long> {
    @Query(value = "select productid, name, category, code, image, status from " +
            " (select p.productid, p.active as status, p.code, p.name, p.thumbnail as image, p.catgroupid from product p " +
            " where p.active is true and p.outletid is null and " +
            " lower(p.name) like '%' || lower(:name) || '%' and " +
            " p.code like '%' || :code || '%' and " +
            " p.productid not in (select productid from productpromotionblocked p where p.outletid is null) ) pro inner join " +
            " (select c.catgroupid, c.name as category from catgroup c where c.countryId = :countryId and lower(c.name) like '%' || lower(:category) || '%') cc on pro.catgroupid = cc.catgroupid ",
            countQuery = "select count(*) from " +
            " (select p.productid, p.active as status, p.code, p.name, p.thumbnail as image, p.catgroupid from product p " +
            " where p.active is true and p.outletid is null and " +
            " lower(p.name) like '%' || lower(:name) || '%' and " +
            " p.code like '%' || :code || '%' and " +
            " p.productid not in (select productid from productpromotionblocked p where p.outletid is null) ) pro inner join " +
            " (select c.catgroupid, c.name as category from catgroup c where c.countryId = :countryId and lower(c.name) like '%' || lower(:category) || '%') cc on pro.catgroupid = cc.catgroupid ",
            nativeQuery = true)
    Page<BlockProductAdminEntity> findNonBlockProduct4Admin(@Param("category") String category,
                                                            @Param("name") String name,
                                                            @Param("code") String code,
                                                            @Param("countryId") Long countryId, Pageable pageable);


    @Query(value = "select productid, name, category, code, image, status from " +
            " (select p.productid, p.active as status, p.code, p.name, p.thumbnail as image, p.catgroupid from product p " +
            " where p.outletid is null and " +
            " lower(p.name) like '%' || lower(:name) || '%' and " +
            " p.code like '%' || :code || '%' and " +
            " p.productid in (select productid from productpromotionblocked p where p.outletid is null) ) pro inner join " +
            " (select c.catgroupid, c.name as category from catgroup c where c.countryId = :countryId and lower(c.name) like '%' || lower(:category) || '%') cc on pro.catgroupid = cc.catgroupid ",
            countQuery = "select count(*) from " +
                    " (select p.productid, p.active as status, p.code, p.name, p.thumbnail as image, p.catgroupid from product p " +
                    " where p.active is true and p.outletid is null and " +
                    " lower(p.name) like '%' || lower(:name) || '%' and " +
                    " p.code like '%' || :code || '%' and " +
                    " p.productid in (select productid from productpromotionblocked p where p.outletid is null) ) pro inner join " +
                    " (select c.catgroupid, c.name as category from catgroup c where c.countryId = :countryId and lower(c.name) like '%' || lower(:category) || '%') cc on pro.catgroupid = cc.catgroupid ",
            nativeQuery = true)
    Page<BlockProductAdminEntity> findBlockProduct4Admin(@Param("category") String category,
                                                         @Param("name") String name,
                                                         @Param("code") String code,
                                                         @Param("countryId") Long countryId, Pageable pageable);
}
