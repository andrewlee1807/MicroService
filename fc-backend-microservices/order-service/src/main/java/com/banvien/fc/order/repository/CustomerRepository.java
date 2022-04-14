package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
    List<CustomerEntity> findByUserIdUserId(Long userId);
    CustomerEntity findByCustomerId(Long customerId);
}
