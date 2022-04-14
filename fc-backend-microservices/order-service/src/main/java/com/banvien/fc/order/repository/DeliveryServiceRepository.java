package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.DeliveryServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryServiceRepository extends JpaRepository<DeliveryServiceEntity,Long> {
    List<DeliveryServiceEntity> findByOutletId(Long id);
    List<DeliveryServiceEntity> findByOutletIdOrderByOrderBy(Long outletId);
    List<DeliveryServiceEntity> findByOutletIdAndCodeAndStatusOrderByOrderBy(Long outletId, String code, boolean status);
    @Query(value = "select * from deliveryservice d " +
            "where d.outletid = :outletId and d.code = :code " +
            "order by d.orderby ,d.distance asc ",nativeQuery =  true)
    List<DeliveryServiceEntity> findByOutletIdAndCodeOrderByDistanceAscOrderByAsc(@Param("outletId")Long outletId,@Param("code") String code);
}
