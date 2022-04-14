package com.banvien.fc.catalog.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "productoutletsku")
@Entity
public class ProductOutletSkuEntity {
    @Id
    @Column(name = "productoutletskuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOutletSkuId;
    @Column(name = "productoutletid")
    private Long productOutletId;

    private String skuCode;
    private String title;
    private Double originalPrice;
    private Double salePrice;
    private String unit;
    private Integer displayOrder;
    private String barCode;
    private String alias;
    private Integer status;
    private Double weight;
    private Timestamp modifiedDate;

    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getProductOutletId() {
        return productOutletId;
    }

    public void setProductOutletId(Long productOutletId) {
        this.productOutletId = productOutletId;
    }
}
