package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.CatGroupOutletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CatGroupOutletRepository extends JpaRepository<CatGroupOutletEntity, Long> {
    @Query(value = "select distinct c FROM CatGroupOutletEntity c WHERE c.catGroup.catgroupId = :catgroupId and c.outlet.outletId = :outletId")
    List<CatGroupOutletEntity> findByCatGroupAndOutlet(@Param("catgroupId") Long catgroupId, @Param("outletId") Long outletId);
}
