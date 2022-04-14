package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.CustomerGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by son.nguyen on 7/28/2020.
 */
@Repository
public interface CustomerGroupRepository extends JpaRepository<CustomerGroupEntity, Long> {
}
