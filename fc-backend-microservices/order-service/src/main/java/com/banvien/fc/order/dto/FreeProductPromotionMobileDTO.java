package com.banvien.fc.order.dto;

import java.io.Serializable;

public class FreeProductPromotionMobileDTO implements Serializable {

    private Long shoppingCartId;
    private Long productOutletSkuId;
    private Long productChooseId;
    private Long outletPromotionId;

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    public Long getProductChooseId() {
        return productChooseId;
    }

    public void setProductChooseId(Long productChooseId) {
        this.productChooseId = productChooseId;
    }

    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }
}
