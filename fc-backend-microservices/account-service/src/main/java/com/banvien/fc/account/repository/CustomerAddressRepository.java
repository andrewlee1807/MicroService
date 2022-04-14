package com.banvien.fc.account.repository;

import com.banvien.fc.account.entity.CustomerAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by son.nguyen on 7/26/2020.
 */
@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddressEntity, Long> {
    Optional<CustomerAddressEntity> findByCustomerCustomerId(Long aLong);
}
