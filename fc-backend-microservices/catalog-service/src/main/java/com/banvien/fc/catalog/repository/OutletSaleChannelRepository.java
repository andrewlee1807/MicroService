package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.OutletSaleChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface OutletSaleChannelRepository extends JpaRepository<OutletSaleChannelEntity, Long> {

    @Query(value = "select c FROM OutletSaleChannelEntity c WHERE c.outlet.outletId = :outletId and c.saleChannel.code = :code")
    OutletSaleChannelEntity findByOutletAndSaleChannel(@Param("outletId") Long outletId, @Param("code") String code);
}
