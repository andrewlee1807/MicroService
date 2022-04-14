package com.banvien.fc.account.repository;

import com.banvien.fc.account.entity.FeatureSettingCountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureSettingCountryRepository extends JpaRepository<FeatureSettingCountryEntity, Long> {
    FeatureSettingCountryEntity findByCountryIdAndFeatureSettingCode(Long countryId, String code);
}
