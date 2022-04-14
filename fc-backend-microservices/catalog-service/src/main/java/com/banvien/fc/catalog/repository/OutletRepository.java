package com.banvien.fc.catalog.repository;

import com.banvien.fc.catalog.entity.OutletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface OutletRepository extends JpaRepository<OutletEntity,Long> {
    List<OutletEntity> findByNameContainsIgnoreCaseAndCountryId(String name, Long countryId);
    List<OutletEntity> findByCountryId(Long countryId);

    @Query(value = "select distinct c FROM OutletEntity c WHERE c.countryId = :countryId and c.code in (:codeList)")
    List<OutletEntity> findByCodeListInAndCountryId(@Param("codeList") Collection<String> codeList,@Param("countryId") Long countryId);

    @Transactional
    @Modifying
    @Query(value = "update {h-schema}outlet set status = false where countryId = :countryId and code not in (:codeList) ", nativeQuery = true)
    void deActiveOutletNotInCode(@Param("countryId") Long countryId, @Param("codeList") Collection<String> codeList);

    @Query(value = "select CAST(regexp_replace(code, '\\D', '', 'g') as INTEGER) as codeNum " +
            "FROM {h-schema}outlet WHERE countryid = :countryId order by codeNum desc limit 1", nativeQuery = true)
    Object findMaxWSCode(@Param("countryId") Long countryId);
}
