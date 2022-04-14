package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.ProductOutletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOutletRepository extends JpaRepository<ProductOutletEntity, Long> {

    @Query(value = "select * FROM productoutlet po " +
            "join product p2 on po.productid = p2.productid " +
            " WHERE po.outletid = :outletId " +
            " AND po.status = :active AND p2.active = :activeBool " +
            " AND (po.hotdealpriority IS NOT NULL OR p2.top IS NOT NULL)" +
            " ORDER BY p2.top , po.hotdealpriority ASC ", nativeQuery = true)
    List<ProductOutletEntity> findProductHotDeals(@Param("outletId") Long outletId, @Param("active") int active, @Param("activeBool") Boolean activeBool);

    @Query(value = "select * from productoutlet p " +
            "join productoutletsku p2 on p.productoutletid = p2.productoutletid " +
            "where p2.productoutletskuid = :skuId and p.outletid = :outletId",nativeQuery = true)
    ProductOutletEntity findProductFixPrice(@Param("skuId") Long skuId,@Param("outletId")Long outletId);
}
