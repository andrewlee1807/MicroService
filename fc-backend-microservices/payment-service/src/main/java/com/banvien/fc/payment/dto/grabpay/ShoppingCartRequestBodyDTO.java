package com.banvien.fc.payment.dto.grabpay;

import java.util.List;

public class ShoppingCartRequestBodyDTO {
    private String transactionID;
    private List<ProductAndQuantityMobileDTO> items;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public List<ProductAndQuantityMobileDTO> getItems() {
        return items;
    }

    public void setItems(List<ProductAndQuantityMobileDTO> items) {
        this.items = items;
    }
}
