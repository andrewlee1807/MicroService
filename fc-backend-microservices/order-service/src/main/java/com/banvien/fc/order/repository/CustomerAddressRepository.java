package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.CustomerAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddressEntity,Long> {
    List<CustomerAddressEntity> findByCustomerCustomerIdAndIsDefaultIsTrue(Long customerId);
    List<CustomerAddressEntity> findByCustomerCustomerId(Long customerId);

}
