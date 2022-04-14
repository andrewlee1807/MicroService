package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "productOutlet")
public class ProductOutletEntity {
    private Long productOutletId;
    private String name;
    private String code;
    private String thumbnail;
    private String description;
    private OutletEntity outlet;
    private Integer displayOrder;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private UserEntity createdBy;
    private Integer status;
    private Integer top;
    private Integer hotDealPriority;
    private Integer fastSellingPriority;
    private Long totalView;
    private ProductEntity product;
    private List<ProductOutletSkuEntity> productOutletSkus;

    public ProductOutletEntity() {

    }

    public ProductOutletEntity(Long productOutletId) {
        this.productOutletId = productOutletId;
    }

    @Column(name = "productoutletid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getProductOutletId() {
        return productOutletId;
    }

    public void setProductOutletId(Long productOutletId) {
        this.productOutletId = productOutletId;
    }

    @ManyToOne()
    @JoinColumn(name = "outletid", referencedColumnName = "outletid")
    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    @ManyToOne()
    @JoinColumn(name = "createdBy", referencedColumnName = "userid")
    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "displayorder")
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Basic
    @Column(name = "createdDate")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "modifiedDate")
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    @Column(name = "top")
    @Basic
    public Integer getTop() {
        return top;
    }

    @Column(name = "hotDealPriority")
    @Basic
    public Integer getHotDealPriority() {
        return hotDealPriority;
    }

    public void setHotDealPriority(Integer hotDealPriority) {
        this.hotDealPriority = hotDealPriority;
    }

    @Column(name = "fastSellingPriority")
    @Basic
    public Integer getFastSellingPriority() {
        return fastSellingPriority;
    }

    public void setFastSellingPriority(Integer fastSellingPriority) {
        this.fastSellingPriority = fastSellingPriority;
    }

    @Basic
    @Column(name = "totalview")
    public Long getTotalView() {
        return totalView;
    }

    public void setTotalView(Long totalView) {
        this.totalView = totalView;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productOutlet", cascade = CascadeType.ALL)
    @OrderBy(value = "displayOrder ASC")
    public List<ProductOutletSkuEntity> getProductOutletSkus() {
        return productOutletSkus;
    }

    public void setProductOutletSkus(List<ProductOutletSkuEntity> productOutletSkus) {
        this.productOutletSkus = productOutletSkus;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne()
    @JoinColumn(name = "productid", referencedColumnName = "productid")
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
