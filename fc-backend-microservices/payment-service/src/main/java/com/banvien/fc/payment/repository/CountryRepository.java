package com.banvien.fc.payment.repository;


import com.banvien.fc.payment.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity,Long> {
}
