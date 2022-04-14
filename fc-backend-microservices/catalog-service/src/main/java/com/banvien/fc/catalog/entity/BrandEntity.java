package com.banvien.fc.catalog.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "brand")
public class BrandEntity {
    @Id
    @Column(name = "brandId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long brandId;
    private String name;
    private String image;
    private Integer top;
    private Long outletId;
    private Long countryId;
    private Long createdById;
    private Long modifiedById;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    private Set<OutletBrandEntity> outletBrandEntity;

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "image", nullable = false, length = -1)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "top", nullable = true)
    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    @Basic
    @Column(name = "outletId", nullable = true)
    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    @Basic
    @Column(name = "createdById", nullable = true)
    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    @Basic
    @Column(name = "modifiedById", nullable = true)
    public Long getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(Long modifiedById) {
        this.modifiedById = modifiedById;
    }

    @Basic
    @Column(name = "createdDate", nullable = false)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "modifiedDate", nullable = true)
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<OutletBrandEntity> getOutletBrandEntity() {
        return outletBrandEntity;
    }

    public void setOutletBrandEntity(Set<OutletBrandEntity> outletBrandEntity) {
        this.outletBrandEntity = outletBrandEntity;
    }

    @Basic
    @Column(name = "countryId")
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
