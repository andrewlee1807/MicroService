package com.banvien.fc.order.dto;

import java.util.List;

public class ShoppingCartRequestBodyDTO {
    private String transactionID;
    private List<SubmitShoppingCartDTO> items;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public List<SubmitShoppingCartDTO> getItems() {
        return items;
    }

    public void setItems(List<SubmitShoppingCartDTO> items) {
        this.items = items;
    }
}
