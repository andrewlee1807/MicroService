package com.banvien.fc.order.dto.vansale;

import java.util.List;

public class ShoppingCartVansaleDTO {
    private Long warehouseId;
    private Long vansalesUserId;
    private List<ShoppingCartDTO> products;
    private String paymentMethod;

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<ShoppingCartDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ShoppingCartDTO> products) {
        this.products = products;
    }

    public Long getVansalesUserId() {
        return vansalesUserId;
    }

    public void setVansalesUserId(Long vansalesUserId) {
        this.vansalesUserId = vansalesUserId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
