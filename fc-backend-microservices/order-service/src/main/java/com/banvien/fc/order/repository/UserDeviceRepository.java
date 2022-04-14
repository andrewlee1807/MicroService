package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.UserDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDeviceRepository extends JpaRepository<UserDeviceEntity,Long> {

    @Query(value="select distinct u.lang from userdevice u where u.userid = :userId",nativeQuery = true)
    List<String> findDistinctByUserId(@Param("userId")Long userId);
}
