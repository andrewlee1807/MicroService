package com.banvien.fc.account.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "featuresetting")
@Entity
public class FeatureSettingEntity implements Serializable {
    @Id
    @Column(name = "featuresettingid")
    private Long id;
    private String code;
    private String displayName;
    private Integer displayOrder;
    private Integer status;
    private String icon ;
    private Long featureSettingGroupId;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public String getIcon() {
        return icon;
    }

    public Long getFeatureSettingGroupId() {
        return featureSettingGroupId;
    }
}
