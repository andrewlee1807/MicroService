package com.banvien.fc.promotion.dto;

import com.banvien.fc.promotion.entity.ProductOrderItemEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderOutletDTO {
    private String status;
    private Double totalOriginalPrice;                  // Gia truoc khi app dung giam gia & khuyen mai: gia goc
    private Double totalStoreDiscountPrice;            // Gia giam tai cua hang: gia goc - gia sale
    private Double totalPromotionDiscountPrice;        // Giam gia theo khuyen mai
    private Double deliveryPrice;                      // Gia van chuyen
    private Double amount;                         // Tong tien phai tra
    private Integer totalItem;
    private List<ProductOrderItemEntity> productOrderItemEntities;
    public OrderOutletDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    public void setTotalOriginalPrice(Double totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
    }

    public Double getTotalStoreDiscountPrice() {
        return totalStoreDiscountPrice;
    }

    public void setTotalStoreDiscountPrice(Double totalStoreDiscountPrice) {
        this.totalStoreDiscountPrice = totalStoreDiscountPrice;
    }

    public Double getTotalPromotionDiscountPrice() {
        return totalPromotionDiscountPrice;
    }

    public void setTotalPromotionDiscountPrice(Double totalPromotionDiscountPrice) {
        this.totalPromotionDiscountPrice = totalPromotionDiscountPrice;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public List<ProductOrderItemEntity> getProductOrderItemEntities() {
        return productOrderItemEntities;
    }

    public void setProductOrderItemEntities(List<ProductOrderItemEntity> productOrderItemEntities) {
        this.productOrderItemEntities = productOrderItemEntities;
    }
}
