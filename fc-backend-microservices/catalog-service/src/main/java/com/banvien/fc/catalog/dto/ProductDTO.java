package com.banvien.fc.catalog.dto;

public class ProductDTO {
    private long productId;
    private String name;
    private String thumbnail;
    private Long catGroupId;
    private Long brandId;

    public ProductDTO() {
    }

    public ProductDTO(long productId, Long catGroupId, Long brandId, String name) {
        this.productId = productId;
        this.catGroupId = catGroupId;
        this.brandId = brandId;
        this.name = name;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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
}
