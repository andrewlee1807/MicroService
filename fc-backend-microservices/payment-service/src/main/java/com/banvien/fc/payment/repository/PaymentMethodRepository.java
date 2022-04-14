package com.banvien.fc.payment.repository;

import com.banvien.fc.payment.entity.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long> {

    @Query(value = "select * from paymentmethod p  " +
            "where p.outletid = :outletId AND p.code = :code", nativeQuery = true)
    PaymentMethodEntity findPaymentMethodByCode(@Param("code") String code, @Param("outletId") Long outletId);
}
