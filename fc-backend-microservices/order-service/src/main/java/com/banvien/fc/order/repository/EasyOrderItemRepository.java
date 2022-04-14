package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.EasyOrderItemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EasyOrderItemRepository extends JpaRepository<EasyOrderItemEntity, Long> {
    List<EasyOrderItemEntity> findByEasyOrderEntityEasyOrderId(Long easyOrderId ,Pageable pageable);

    @Query(value = "select * from easyorderitem e inner join productoutletsku sku on e.productoutletskuid = sku.productoutletskuid INNER JOIN productoutlet po ON sku.productoutletid = po.productoutletid" +
            " WHERE e.easyorderid = :easyOrderId AND sku.status = 1 AND EXISTS (select 1 from  outletsalechannel " +
            "    inner join salechannel s on outletsalechannel.salechannelid = s.salechannelid " +
            "    inner join productoutletsalechannel p on outletsalechannel.outletsalechannelid = p.outletsalechannelid " +
            "   where code = 'ONLINE' and outletid = :outletId and p.productoutletid = po.productoutletid) ", nativeQuery = true)
    List<EasyOrderItemEntity> getByEasyOrderIdActiveAndSold(@Param("easyOrderId") Long easyOrderId, @Param("outletId") Long outletId, Pageable pageable);
}
