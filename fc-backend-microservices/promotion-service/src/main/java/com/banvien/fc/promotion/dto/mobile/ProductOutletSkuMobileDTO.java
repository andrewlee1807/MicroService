package com.banvien.fc.promotion.dto.mobile;

import java.io.Serializable;

public class ProductOutletSkuMobileDTO implements Serializable {     //use in case promotion FIX_PRICE
    private Long productOutletSkuId;
    private String title;
    private String image;
    private String typeImage;
    private String skuCode;
    private String barCode;
    private Double originalPrice;
    private Double salePrice;
    private Double promotionPrice;
    private String discountPercent;
    private String unit;
    private Integer displayOrder;
    private Integer quantityInCart;
    private Boolean isInUsed;                               // Mark in used to be cannot deleted
    private String aliasCode;
    private Integer status;
    private boolean hasPromotion;

    public Integer getQuantityInCart() {
        return quantityInCart;
    }

    public void setQuantityInCart(Integer quantityInCart) {
        this.quantityInCart = quantityInCart;
    }

    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
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

    public Double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(String typeImage) {
        this.typeImage = typeImage;
    }

    public Boolean getInUsed() {
        return isInUsed;
    }

    public void setInUsed(Boolean inUsed) {
        isInUsed = inUsed;
    }

    public String getAliasCode() {
        return aliasCode;
    }

    public void setAliasCode(String aliasCode) {
        this.aliasCode = aliasCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isHasPromotion() {
        return hasPromotion;
    }

    public void setHasPromotion(boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
    }
}
