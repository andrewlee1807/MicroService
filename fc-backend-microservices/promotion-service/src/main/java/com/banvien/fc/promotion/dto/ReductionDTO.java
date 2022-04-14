package com.banvien.fc.promotion.dto;

import com.banvien.fc.promotion.dto.rules.Enum.DiscountType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReductionDTO {
    private Double minValue;    // threshold accepted Promotion
    private Long outletPromotionId;
    private Double valueDiscount;   // value : $ or null
    private DiscountType discountType;  // Offer for discountPrice
    private Long discountForTarget;   // value : ProductId or GiftId or null
    private List<Long> discountByProducts; // products were acceptable Promotion (productSkuId)
    private Integer promotionType;  // 0: Category Promotion, 1: Brand Promotion, 2: Product Promotion, 3:Order, Shipping Promotion, 4:Discount for the next purchase, 5: EasyOrderCode
    private String codeNextPurchase;


    public ReductionDTO() {
    }

    public ReductionDTO(Double valueDiscount, DiscountType discountType, List<Long> discountByProducts, Integer promotionType) {
        this.valueDiscount = valueDiscount;
        this.discountType = discountType;
        this.discountByProducts = discountByProducts;
        this.promotionType = promotionType;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }

    public Double getValueDiscount() {
        return valueDiscount;
    }

    public void setValueDiscount(Double valueDiscount) {
        this.valueDiscount = valueDiscount;
    }

    public Long getDiscountForTarget() {
        return discountForTarget;
    }

    public void setDiscountForTarget(Long discountForTarget) {
        this.discountForTarget = discountForTarget;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(Integer promotionType) {
        this.promotionType = promotionType;
    }

    public String getCodeNextPurchase() {
        return codeNextPurchase;
    }

    public void setCodeNextPurchase(String codeNextPurchase) {
        this.codeNextPurchase = codeNextPurchase;
    }

    public List<Long> getDiscountByProducts() {
        return discountByProducts;
    }

    public void setDiscountByProducts(List<Long> discountByProducts) {
        this.discountByProducts = discountByProducts;
    }
}
