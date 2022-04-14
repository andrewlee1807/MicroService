package com.banvien.fc.order.dto;

import java.io.Serializable;

public class GiftPromotionDTO implements Serializable {
    private Long id;
    private long productOutletId;
    private Long productOutletSkuId;
    private String type; // PRODUsCT, GIFT
    private String name;
    private String imgUrl;
    private Integer quantity;
    private Double promotionPrice;
    private Long outletPromotionId;
    private Long belongToSkuId;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }

    public long getProductOutletId() {
        return productOutletId;
    }

    public void setProductOutletId(long productOutletId) {
        this.productOutletId = productOutletId;
    }

    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    public Long getBelongToSkuId() {
        return belongToSkuId;
    }

    public void setBelongToSkuId(Long belongToSkuId) {
        this.belongToSkuId = belongToSkuId;
    }
}
