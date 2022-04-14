package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value = "select * from product p " +
            "where p.productid in " +
            "(select p2.productid from productoutlet p2 where p2.outletid = :outletId and p2.productoutletid in " +
            "(select p3.productoutletid from productoutletsku p3 where p3.productoutletskuid = :skuId))",nativeQuery = true)
    ProductEntity findProductsInWarehouseVanSale(@Param("outletId")Long outletId,@Param("skuId")Long skuId);

    List<ProductEntity> findByCatGroupCatGroupIdAndOutletId(Long catId,Long outletId);

    List<ProductEntity> findByBrandBrandIdAndOutletId(Long brandId,Long outletId);
}
