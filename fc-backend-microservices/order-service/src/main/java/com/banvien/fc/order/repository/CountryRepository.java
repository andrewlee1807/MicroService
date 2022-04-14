package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.CountryEntity;
import com.banvien.fc.order.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity,Long> {
}
