package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.CustomerEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<CustomerEnity, Long> {
    @Query(value = "select c from CustomerEnity c where c.userId = :userId")
    CustomerEnity findByUserId(@Param("userId") Long id);
}
