package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "productoutletsku")
@Entity
public class ProductOutletSkuEntity {
    private Long productOutletSkuId;
    private String skuCode;
    private String title;
    private String image;
    private Double originalPrice;
    private Double salePrice;
    private String unit;
    private Integer displayOrder;
    private String barCode;
    private String alias;
    private Double tax;
    private Integer status;
    private Double weight;
    private Timestamp modifiedDate;
    private ProductOutletEntity productOutlet;

    public ProductOutletSkuEntity() {
    }

    public ProductOutletSkuEntity(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    @Id
    @Column(name = "productoutletskuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    @Basic
    @Column(name = "skucode")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "barcode")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Basic
    @Column(name = "originalprice")
    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Basic
    @Column(name = "saleprice")
    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    @Basic
    @Column(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "tax")
    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    @Basic
    @Column(name = "modifieddate")
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productoutletid", referencedColumnName = "productoutletid")
    public ProductOutletEntity getProductOutlet() {
        return productOutlet;
    }

    public void setProductOutlet(ProductOutletEntity productOutlet) {
        this.productOutlet = productOutlet;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
