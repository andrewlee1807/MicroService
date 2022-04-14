package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.OrderOutletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface OrderOutletRepository extends JpaRepository<OrderOutletEntity, Long> {

    @Transactional
    @Modifying
    @Query("update OrderOutletEntity o set o.totalOriginalPrice = :totalOriginalPrice,o.totalPrice = :totalPrice where o.orderOutletID = :orderOutletId")
    void updateTotalPrice(@Param("totalOriginalPrice") Double totalOriginalPrice, @Param("totalPrice") Double totalPrice, @Param("orderOutletId") Long orderOutletId);

    @Query("SELECT o FROM OrderOutletEntity o WHERE o.status <> 'CANCELED' AND o.customerId = :customerId AND o.outlet.outletId = :outletId AND o.createdDate >= :timestamp")
    List<OrderOutletEntity> getOrderOutletByCustomerAndOutletFarDay(@Param("customerId") Long customerId, @Param("outletId") Long outletId, @Param("timestamp") Timestamp timestamp);

    @Query("SELECT CASE WHEN COUNT(o)> 0 THEN TRUE ELSE FALSE END FROM OrderOutletEntity o WHERE o.status <> 'CANCELED' AND o.customerId = :customerId AND o.outlet.outletId = :outletId")
    boolean existsByOutletIdAndCustomerId(@Param("customerId") Long customerId, @Param("outletId") Long outletId);

    @Query("SELECT o FROM OrderOutletEntity o WHERE o.status <> 'CANCELED' AND o.customerId = :customerId AND o.outlet.outletId = :outletId")
    List<OrderOutletEntity> getOrderOutletByCustomerAndOutlet(@Param("customerId") Long customerId, @Param("outletId") Long outletId);

    Integer countByCustomerId(Long customerId);

    Integer countByOutletOutletIdAndCustomerId(Long outletId, Long customerId);

    OrderOutletEntity findByOrderOutletID(Long orderId);

    @Transactional
    @Modifying
    @Query("update OrderOutletEntity o set o.grabTxId = :grabTxId,o.token = :token where o.orderOutletID = :orderOutletId")
    Integer updateOrder(@Param("grabTxId") String grabTxId, @Param("token") String token, @Param("orderOutletId") Long orderOutletId);


    OrderOutletEntity findByCode(String code);
}
