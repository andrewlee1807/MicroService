package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.FeatureSettingCountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeatureSettingCountryRepository extends JpaRepository<FeatureSettingCountryEntity, Long> {

    @Query("FROM FeatureSettingCountryEntity A WHERE A.country.countryId = :countryId and A.featureSetting.code = :code")
    FeatureSettingCountryEntity findByCodeAndCountryId(@Param("code") String code, @Param("countryId") Long countryId);
}