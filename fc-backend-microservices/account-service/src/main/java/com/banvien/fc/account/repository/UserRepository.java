package com.banvien.fc.account.repository;

import com.banvien.fc.account.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by son.nguyen on 7/26/2020.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByEmail(String email);
    UserEntity findByCode(String userCode);

    @Query(value = "select * from users where userid in " +
            "(select userid from customer where customerid in " +
            "(select customerid from loyaltymember where active = 1 and outletid = :outletId and status = 'MEMBER')) " +
            "and (lower(fullname) like '%' || :key || '%' or username like '%' || :key || '%') " +
            "and status = 1"
            , nativeQuery = true)
    List<UserEntity> findCustomerInOutlet(long outletId, String key, Pageable pageable);

    @Query(value = "select * from users where fullname = :name " +
            "and phonenumber = :phone " +
            "and status = :status " +
            "and username = :username " +
            "and password = :password " +
            "and code = :code " +
            "and countryid = :countryid "
            , nativeQuery = true)
    UserEntity findUser(@Param("name")String name, @Param("phone") String phone, @Param("username") String username, @Param("password") String password, @Param("code")String code, @Param("status") int status, @Param("countryid") long countryId);
}
