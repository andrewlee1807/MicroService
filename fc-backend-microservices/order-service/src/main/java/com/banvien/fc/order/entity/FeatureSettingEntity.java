package com.banvien.fc.order.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "featuresetting")
@Entity

@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class FeatureSettingEntity implements Serializable {
    private Long featureSettingId;
    private String code;
    private String displayName;
    private Integer displayOrder;
    private Integer status;
    private String icon ;
    private FeatureSettingGroupEntity featureSettingGroup;

    @Id
    @Column(name = "featuresettingid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getFeatureSettingId() {
        return featureSettingId;
    }

    public void setFeatureSettingId(Long featureSettingId) {
        this.featureSettingId = featureSettingId;
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


    @ManyToOne()
    @JoinColumn(name="featuresettinggroupid", referencedColumnName = "featuresettinggroupid")

    public FeatureSettingGroupEntity getFeatureSettingGroup() {
        return featureSettingGroup;
    }

    public void setFeatureSettingGroup(FeatureSettingGroupEntity featureSettingGroup) {
        this.featureSettingGroup = featureSettingGroup;
    }
}
