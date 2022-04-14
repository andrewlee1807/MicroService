package com.banvien.fc.promotion.dto;

public class ProductDTO {
    private long productId;
    private Long catGroupId;
    private Long brandId;
    private String name;
    private String thumbnail;

    public ProductDTO() {
    }

    public ProductDTO(long productId, Long catGroupId, Long brandId) {
        this.productId = productId;
        this.catGroupId = catGroupId;
        this.brandId = brandId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Long getCatGroupId() {
        return catGroupId;
    }

    public void setCatGroupId(Long catGroupId) {
        this.catGroupId = catGroupId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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
}
