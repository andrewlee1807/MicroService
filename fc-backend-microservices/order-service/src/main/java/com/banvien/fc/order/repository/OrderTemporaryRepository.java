package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.OrderTemporaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OrderTemporaryRepository extends JpaRepository<OrderTemporaryEntity,Long> {
    OrderTemporaryEntity findByState(String state);

    @Query(value = "from OrderTemporaryEntity o where o.orderOutletCode = :orderCode ")
    OrderTemporaryEntity getByOrderCode(@Param("orderCode") String orderCode);

    @Transactional
    @Modifying
    @Query("update OrderTemporaryEntity o set o.paymentStatus = :paymentStatus where o.orderOutletCode = :orderOutletCode")
    Integer updateOrder(@Param("paymentStatus") String paymentStatus, @Param("orderOutletCode") String orderOutletCode);

}
