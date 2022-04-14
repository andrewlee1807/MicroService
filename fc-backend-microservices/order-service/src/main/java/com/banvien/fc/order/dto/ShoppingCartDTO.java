package com.banvien.fc.order.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class ShoppingCartDTO implements Serializable {
    private Long shoppingCartId;
    private ProductOutletSkuDTO productOutletSku;
    private Integer quantity;
    private CustomerDTO customer;
    private Timestamp createdDate;
    private String device;
    private String temporaryOrderCode;

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getTemporaryOrderCode() {
        return temporaryOrderCode;
    }

    public void setTemporaryOrderCode(String temporaryOrderCode) {
        this.temporaryOrderCode = temporaryOrderCode;
    }

    public ProductOutletSkuDTO getProductOutletSku() {
        return productOutletSku;
    }

    public void setProductOutletSku(ProductOutletSkuDTO productOutletSku) {
        this.productOutletSku = productOutletSku;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }
}
