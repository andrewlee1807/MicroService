package com.banvien.fc.order.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "featuresettinggroup")
@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class FeatureSettingGroupEntity implements Serializable {
    private Long featureSettingGroupId;
    private String code;
    private String displayName;
    private Integer displayOrder;
    private Integer status;
    private String icon;
    private List<FeatureSettingEntity> featureSettings;

    @Id
    @Column(name = "featuresettinggroupid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getFeatureSettingGroupId() {
        return featureSettingGroupId;
    }

    public void setFeatureSettingGroupId(Long featureSettingGroupId) {
        this.featureSettingGroupId = featureSettingGroupId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @OneToMany(mappedBy = "featureSettingGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy(value = "displayOrder ASC")
    public List<FeatureSettingEntity> getFeatureSettings() {
        return featureSettings;
    }

    public void setFeatureSettings(List<FeatureSettingEntity> featureSettings) {
        this.featureSettings = featureSettings;
    }
}
