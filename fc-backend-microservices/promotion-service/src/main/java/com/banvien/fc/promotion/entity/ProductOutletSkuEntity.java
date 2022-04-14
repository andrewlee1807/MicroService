package com.banvien.fc.promotion.entity;

import javax.persistence.*;

@Table(name = "productoutletsku")
@Entity
public class ProductOutletSkuEntity {
    @Id
    @Column(name = "productoutletskuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOutletSkuId;
    @Column(name = "productoutletid")
    private Long productOutletId;
    private String title;
    @Column(name = "saleprice")
    private Double salePrice;
    private String unit;
    private String barcode;
    @Column(name = "originalprice")
    private String originalPrice;
    private Integer status;
    private String image;


    public ProductOutletSkuEntity() {
    }


    public ProductOutletSkuEntity(Long productOutletSkuId,Long productOutletId, String title, Double salePrice, String unit, String barcode, String originalPrice, Integer status, String image) {
        this.productOutletSkuId = productOutletSkuId;
        this.productOutletId = productOutletId;
        this.title = title;
        this.salePrice = salePrice;
        this.unit = unit;
        this.barcode = barcode;
        this.originalPrice = originalPrice;
        this.status = status;
        this.image = image;
    }

    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    public Long getProductOutletId() {
        return productOutletId;
    }

    public void setProductOutletId(Long productOutletId) {
        this.productOutletId = productOutletId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
