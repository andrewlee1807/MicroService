package com.banvien.fc.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BlockProductEntity {

    private Long productOutletSkuId;
    private String name;
    private String category;
    private Long totalView;
    private String skuCode;
    private String image;
    private Double salePrice;
    private Integer status;

    public BlockProductEntity() {
    }

    @Id
    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getTotalView() {
        return totalView;
    }

    public void setTotalView(Long totalView) {
        this.totalView = totalView;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
