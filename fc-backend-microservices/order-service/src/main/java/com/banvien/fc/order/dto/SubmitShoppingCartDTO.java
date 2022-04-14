package com.banvien.fc.order.dto;

public class SubmitShoppingCartDTO {
    private Integer productOutletSkuId;
    private Integer productOutletId;
    private Long shoppingCartId;

    public Integer getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Integer productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    public Integer getProductOutletId() {
        return productOutletId;
    }

    public void setProductOutletId(Integer productOutletId) {
        this.productOutletId = productOutletId;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }
}
