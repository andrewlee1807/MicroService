package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.RawOrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RawOrderStatusRepository extends JpaRepository<RawOrderStatusEntity, Long> {
    @Query(value = "select * from raworderstatus where processstatus = 0 order by createddate", nativeQuery = true)
    List<RawOrderStatusEntity> findByProcessStatus();
}
