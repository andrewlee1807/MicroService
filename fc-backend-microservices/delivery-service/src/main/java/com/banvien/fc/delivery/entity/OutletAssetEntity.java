package com.banvien.fc.delivery.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "outletasset")
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class OutletAssetEntity implements Serializable {
    @Id
    @Column(name = "outletassetId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outletAssetId;

    @ManyToOne
    @JoinColumn(name = "outletId", referencedColumnName = "outletId")
    private OutletEntity outlet;

    @Basic
    @Column(name = "url")
    private String url;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "type")
    private String type;

    @Basic
    @Column(name = "displayPos")
    private Integer displayPos;

    public OutletAssetEntity() {
    }

    public OutletAssetEntity(Long outletAssetId) {
        this.outletAssetId = outletAssetId;
    }

    public OutletAssetEntity(Long outletAssetId, OutletEntity outlet, String url, String type) {
        this.outletAssetId = outletAssetId;
        this.outlet = outlet;
        this.url = url;
        this.type = type;
    }

    public Long getOutletAssetId() {
        return outletAssetId;
    }

    public void setOutletAssetId(Long outletAssetId) {
        this.outletAssetId = outletAssetId;
    }

    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDisplayPos() {
        return displayPos;
    }

    public void setDisplayPos(Integer displayPos) {
        this.displayPos = displayPos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
