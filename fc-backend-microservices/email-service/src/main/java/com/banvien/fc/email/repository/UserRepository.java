package com.banvien.fc.email.repository;


import com.banvien.fc.email.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "select userid from customer where defaultoutlet = :outletId", nativeQuery = true)
    List<Long> findUserIdsInOutlet(@Param("outletId") Long outletId);

    @Query(value = "select distinct lang from userdevice  where userid = :userId", nativeQuery = true)
    List<String> findDistinctByUserId(@Param("userId") Long userId);

    @Query(value = "select distinct userid from customer where customerid in ( " +
            "select customerid from loyaltymember where customergroupid in " +
            "(select customergroupid from customergroup where outletid = :outletId and groupname in :groups))", nativeQuery = true)
    List<Long> findUserIdsByGroupAndOutlet(@Param("groups") List<String> groups, @Param("outletId") Long outletId);
}
