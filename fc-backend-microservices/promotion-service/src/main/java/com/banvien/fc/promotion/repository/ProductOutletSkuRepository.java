package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.ProductOutletSkuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOutletSkuRepository extends JpaRepository<ProductOutletSkuEntity, Long> {
    List<ProductOutletSkuEntity> findByProductOutletIdIn(List<Long> ids);

    @Query(value = "select * from productoutletsku p " +
            "join productoutletstorage p2 on p.productoutletskuid = p2.productoutletskuid " +
            "join warehouse w on w.warehouseid = p2.warehouseid " +
            "where w.warehouseid = :warehouseId and w.vansalesvehicleid is not null", nativeQuery = true)
    List<ProductOutletSkuEntity> findProductsInWarehouseVansale(@Param("warehouseId") Long warehouseId);

    @Query(value = "select * from productoutletsku p " +
            " where p.productoutletid in (select p2.productoutletid from productoutlet p2 " +
            " where p2.productid in (select p3.productid from product p3 where p3.productid = :productId))", nativeQuery = true)
    List<ProductOutletSkuEntity> findByProductId(@Param("productId") Long productId);

    List<ProductOutletSkuEntity> findByProductOutletId(Long productOutletId);

}
