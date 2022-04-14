package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.ProductOutletStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOutletStorageRepository extends JpaRepository<ProductOutletStorageEntity, Long> {

    @Query(value = "select sum(p.quantity) from productoutletstorage p " +
            "join warehouse w on w.warehouseid = p.warehouseid " +
            "where w.outletid = :outletId and p.productoutletskuid = :skuId and w.vansalesvehicleid is null", nativeQuery = true)
    Integer findProductQuantityInWareHouse(@Param("outletId") Long outletId, @Param("skuId") Long skuId);

    @Query(value = "select * from productoutletstorage where warehouseid = :warehouseId and productoutletskuid = :productOutletSkuId", nativeQuery = true)
    ProductOutletStorageEntity findByWareHouseIdAnAndProductOutletSkuId(@Param("warehouseId")Long warehouseId,@Param("productOutletSkuId") Long productOutletSkuId);

    List<ProductOutletStorageEntity> findByProductOutletSkuProductOutletSkuId(Long id);
}
