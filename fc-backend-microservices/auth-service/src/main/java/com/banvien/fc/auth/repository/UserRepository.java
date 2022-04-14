package com.banvien.fc.auth.repository;

import com.banvien.fc.auth.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByUsername(String username);

    List<UserEntity> findByUsernameAndCountryId(String username, Long countryId);

    UserEntity findByUsernameAndRoleCode(String username, String code);

    UserEntity findByUsernameAndRoleCodeAndCountryId(String username, String code, Long countryId);

    List<UserEntity> findByUsernameAndPassword(String username, String password);

    List<UserEntity> findByUsernameAndPasswordAndCountryId(String username, String password, Long countryId);

    @Query(value = "select outletId from outlet where shopman = ?1 limit 1", nativeQuery = true)
    Long getOutletId(long userId);

    @Query(value = "select outletId from outletemployee where userid = ?1 limit 1", nativeQuery = true)
    Long getEmployeeOutletId(long userId);
}
