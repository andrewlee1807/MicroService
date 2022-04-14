package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.ProductOutletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOutletRepository extends JpaRepository<ProductOutletEntity, Long> {
    ProductOutletEntity findByProductOutletId(long productOutletId);

    @Query("from ProductOutletEntity where productId in :ids and (outletId = :outletId or :outletId is null)")
    List<ProductOutletEntity> findByProductIdInAndOutletId(@Param("ids") List<Long> ids,
                                                           @Param("outletId") Long outletId);

    @Query(value = "select * from productoutlet p " +
            "join product p2 on p2.productid = p.productid " +
            "where p.outletid = :outletId " +
            "and (p2.catgroupid = :catId or :catId = -1) and (p2.brandid = :brandId or :brandId = -1) and p.status = 1 limit 10" , nativeQuery = true)
    List<ProductOutletEntity> findByCatGroupAndBrand(@Param("outletId")Long outletId ,@Param("catId") Long cateId , @Param("brandId")Long brandId);
}
