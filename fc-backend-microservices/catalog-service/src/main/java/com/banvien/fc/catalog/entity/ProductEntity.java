package com.banvien.fc.catalog.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @Column(name = "productId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private Boolean active;
    private String code;
    private Long createdBy;
    private Timestamp createdDate;
    private String description;
    private Integer displayOrder;
    private Long modifiedBy;
    private Timestamp modifiedDate;
    private String name;
    private String thumbnail;
    private Long catGroupId;
    private Long brandId;
    private Integer top;
    private Long outletId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catGroupId", referencedColumnName = "catGroupId", insertable = false, updatable = false)
    private CatGroupEntity catGroup;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @OrderBy("displayOrder ASC")
    Set<ProductSkuEntity> productSkus;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "active", nullable = true)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "createdBy", nullable = false)
    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "createdDate", nullable = true)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "displayOrder", nullable = true)
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Basic
    @Column(name = "modifiedBy", nullable = true)
    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Basic
    @Column(name = "modifiedDate", nullable = true)
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "thumbnail", nullable = true, length = 255)
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Basic
    @Column(name = "catGroupId", nullable = true)
    public Long getCatGroupId() {
        return catGroupId;
    }

    public void setCatGroupId(Long catGroupId) {
        this.catGroupId = catGroupId;
    }

    @Basic
    @Column(name = "brandId", nullable = true)
    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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

    public CatGroupEntity getCatGroup() {
        return catGroup;
    }

    public Set<ProductSkuEntity> getProductSkus() {
        return productSkus;
    }

    public void setProductSkus(Set<ProductSkuEntity> productSkus) {
        this.productSkus = productSkus;
    }
}
