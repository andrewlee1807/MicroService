package com.banvien.fc.account.repository;

import com.banvien.fc.account.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
    @Query(value = "FROM CustomerEntity c WHERE c.user.phoneNumber = :phoneNumber and c.user.postCode = :postCode")
    Optional<CustomerEntity> findByPhoneNumber(@Param("phoneNumber") String phoneNumber, @Param("postCode") String postCode);

    @Query(value = "FROM CustomerEntity c WHERE c.user.userName = :userName and c.user.countryId = :countryId ")
    Optional<CustomerEntity> findByUserName(@Param("userName") String userName, @Param("countryId") Long countryId);

    Optional<CustomerEntity> findByUserUserId(Long userId);

    @Query(value = "select distinct d FROM CustomerEntity d join fetch d.loyaltyMembers c join fetch d.user left join fetch d.customerAddresses " +
            "WHERE c.customerCode = :code and c.outlet.outletId = :outletId ")
    List<CustomerEntity> findByCodeAndOutlet(@Param("code") String code, @Param("outletId") Long outletId);

    @Transactional
    @Modifying
    @Query(value = "update {h-schema}customer c1 set status = 0 from {h-schema}users c2 " +
            "where c2.userid = c1.userid and c2.countryid = :countryId ", nativeQuery = true)
    void deActiveCustomerByCountryId(@Param("countryId") Long countryId);

    @Transactional
    @Modifying
    @Query(value = "update {h-schema}users c1 set status = 0 from {h-schema}customer c2 " +
            "where  c2.userid = c1.userid and c1.countryid = :countryId", nativeQuery = true)
    void deActiveUserByCountryId(@Param("countryId") Long countryId);

    @Transactional
    @Modifying
    @Query(value = "update {h-schema}loyaltymember c1 set status = 'NOT_MEMBER', active = 0 from {h-schema}outlet c2 " +
            "where c2.outletid = c1.outletid and c2.countryid = :countryId", nativeQuery = true)
    void deActiveLoyaltyMemberByCountryId(@Param("countryId") Long countryId);
}
