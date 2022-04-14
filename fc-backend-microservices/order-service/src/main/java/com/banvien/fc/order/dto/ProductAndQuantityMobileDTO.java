package com.banvien.fc.order.dto;

public class ProductAndQuantityMobileDTO {

    private Integer productOutletSkuId;
    private Integer quantity;

    public ProductAndQuantityMobileDTO() {
    }

    public Integer getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Integer productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
