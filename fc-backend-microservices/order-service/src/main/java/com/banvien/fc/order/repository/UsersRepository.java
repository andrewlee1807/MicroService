package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends JpaRepository<UserEntity,Long> {
    List<UserEntity> findByUserNameContains(String phoneNumber);

}
