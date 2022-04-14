package com.banvien.fc.promotion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewPromotionDTO {

    private String operator;
    private String quality;
    private String brand;
    private String discountPercent;

    public NewPromotionDTO(String operator, String quality, String brand, String discountPercent) {
        this.operator = operator;
        this.quality = quality;
        this.brand = brand;
        this.discountPercent = discountPercent;
    }

    public NewPromotionDTO(){}

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }
}
