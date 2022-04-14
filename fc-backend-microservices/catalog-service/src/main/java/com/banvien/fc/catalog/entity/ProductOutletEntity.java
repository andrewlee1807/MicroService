package com.banvien.fc.catalog.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "productoutlet")
public class ProductOutletEntity {
    @Id
    @Column(name = "productOutletId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productOutletId;
    private Long outletId;
    private long productId;
    private Integer displayOrder;
    private Integer status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private long createdBy;
    private Integer top;
    private long totalView;
    private Integer fastSellingPriority;
    private Integer hotDealPriority;
    private String name;
    private String thumbnail;
    private String description;
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", referencedColumnName = "productId", insertable = false, updatable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outletId", referencedColumnName = "outletId", insertable = false, updatable = false)
    private OutletEntity outlet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productOutletId")
    @OrderBy("displayOrder ASC")
    Set<ProductOutletSkuEntity> productOutletSkus;

    public long getProductOutletId() {
        return productOutletId;
    }

    public void setProductOutletId(long productOutletId) {
        this.productOutletId = productOutletId;
    }

    @Basic
    @Column(name = "outletId", nullable = false)
    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    @Basic
    @Column(name = "productId", nullable = false)
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Basic
    @Column(name = "createdBy", nullable = false)
    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
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
    @Column(name = "totalView", nullable = false)
    public long getTotalView() {
        return totalView;
    }

    public void setTotalView(long totalView) {
        this.totalView = totalView;
    }

    @Basic
    @Column(name = "fastSellingPriority", nullable = true)
    public Integer getFastSellingPriority() {
        return fastSellingPriority;
    }

    public void setFastSellingPriority(Integer fastSellingPriority) {
        this.fastSellingPriority = fastSellingPriority;
    }

    @Basic
    @Column(name = "hotDealPriority", nullable = true)
    public Integer getHotDealPriority() {
        return hotDealPriority;
    }

    public void setHotDealPriority(Integer hotDealPriority) {
        this.hotDealPriority = hotDealPriority;
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
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Set<ProductOutletSkuEntity> getProductOutletSkus() {
        return productOutletSkus;
    }

    public void setProductOutletSkus(Set<ProductOutletSkuEntity> productOutletSkus) {
        this.productOutletSkus = productOutletSkus;
    }

    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }
}
