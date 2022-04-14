package com.banvien.fc.account.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "featuresettingcountry")
@Entity
public class FeatureSettingCountryEntity implements Serializable {
    @Id
    @Column(name = "featuresettingcountryid")
    private Long id;
    @ManyToOne
    @JoinColumn(name="featureSettingId", referencedColumnName = "featureSettingId")
    private FeatureSettingEntity featureSetting;
    private Integer status;
    private Long countryId ;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public Long getId() {
        return id;
    }

    public FeatureSettingEntity getFeatureSetting() {
        return featureSetting;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getCountryId() {
        return countryId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }
}
