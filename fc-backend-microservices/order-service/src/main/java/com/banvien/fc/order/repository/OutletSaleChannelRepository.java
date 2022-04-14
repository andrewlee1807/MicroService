package com.banvien.fc.order.repository;


import com.banvien.fc.order.entity.OutletSaleChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutletSaleChannelRepository extends JpaRepository<OutletSaleChannelEntity, Long> {
    @Query(value = "select * from outletsalechannel " +
            "    inner join salechannel s on outletsalechannel.salechannelid = s.salechannelid " +
            "    inner join productoutletsalechannel p on outletsalechannel.outletsalechannelid = p.outletsalechannelid " +
            "where code = :code and outletid = :outletId and p.productoutletid = :productOutletId", nativeQuery = true)
    OutletSaleChannelEntity findSaleChannelByCodeAndOutletId(@Param("code")String code,@Param("outletId") Long outletId,@Param("productOutletId") Long productOutletId);

    @Query("FROM OutletSaleChannelEntity o WHERE o.outletId = :outletId AND o.saleChannel.code = :code")
    List<OutletSaleChannelEntity> findByOutletIdAndSaleChannelCode(@Param("outletId") Long outletId,@Param("code")String code);
}
