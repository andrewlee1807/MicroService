package com.banvien.fc.account.repository;

import com.banvien.fc.account.entity.OutletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by son.nguyen on 7/26/2020.
 */
@Repository
public interface OutletRepository extends JpaRepository<OutletEntity, Long> {
    List<OutletEntity> findByCode(String code);
}
