package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByUserIdIn(List<Long> ids);
}
