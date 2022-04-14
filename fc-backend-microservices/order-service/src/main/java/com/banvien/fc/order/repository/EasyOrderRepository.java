package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.EasyOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EasyOrderRepository extends JpaRepository<EasyOrderEntity, Long> {
    @Transactional
    @Modifying
    @Query(" UPDATE EasyOrderEntity SET totalViewer = totalViewer + 1 WHERE easyOrderId = :easyOrderId")
    void updateTotalViewer(@Param("easyOrderId") Long easyOrderId);
}
