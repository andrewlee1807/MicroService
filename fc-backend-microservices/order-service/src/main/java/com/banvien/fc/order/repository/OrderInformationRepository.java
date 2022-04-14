package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.OrderInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInformationRepository extends JpaRepository<OrderInformationEntity,Long> {
}
