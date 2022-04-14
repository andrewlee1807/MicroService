package com.banvien.fc.order.dto.rules;

/**
 * Created by son.nguyen on 6/18/2020.
 */
public class RuleProductDTO {
    private Long id;
    private Double amount;
    private Integer quantity;
    private Long categoryId;
    private Long brandId;

    public RuleProductDTO() {
    }

    public RuleProductDTO(Long id, Double amount, Integer quantity, Long categoryId, Long brandId) {
        this.id = id;
        this.amount = amount;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.brandId = brandId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
