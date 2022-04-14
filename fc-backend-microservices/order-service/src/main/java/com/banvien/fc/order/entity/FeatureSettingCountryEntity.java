package com.banvien.fc.order.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "featuresettingcountry")
@Entity

@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class FeatureSettingCountryEntity implements Serializable {
    private Long featureSettingCountryId;
    private FeatureSettingEntity featureSetting;
    private Integer status;
    private CountryEntity country ;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    @Id
    @Column(name = "featuresettingcountryid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getFeatureSettingCountryId() {
        return featureSettingCountryId;
    }

    public void setFeatureSettingCountryId(Long featureSettingCountryId) {
        this.featureSettingCountryId = featureSettingCountryId;
    }

    @OneToOne()
    @JoinColumn(name="featuresettingid", referencedColumnName = "featuresettingid")
    public FeatureSettingEntity getFeatureSetting() {
        return featureSetting;
    }

    public void setFeatureSetting(FeatureSettingEntity featureSetting) {
        this.featureSetting = featureSetting;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ManyToOne()
    @JoinColumn(name="countryid", referencedColumnName = "countryid")
    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
