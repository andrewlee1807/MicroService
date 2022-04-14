package com.banvien.fc.order.dto.rules;

public class GroupTypeDTO {
    private Long id;
    private Integer quantity;
    private Double amount;

    public GroupTypeDTO() {
    }

    public GroupTypeDTO(Long id, Integer quantity, Double amount) {
        this.id = id;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
