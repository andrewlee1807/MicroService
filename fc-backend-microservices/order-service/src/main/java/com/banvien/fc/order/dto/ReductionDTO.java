package com.banvien.fc.order.dto;


import com.banvien.fc.order.dto.rules.DiscountDTO;
import com.banvien.fc.order.dto.rules.Enum.DiscountType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReductionDTO {
    private Double minValue;    // threshold accepted Promotion
    private Long outletPromotionId;
    private Double valueDiscount;   // value : % or $ or null
    private DiscountType discountType;  // Offer for discountPrice
    private Long discountForTarget;   // value : ProductId or GiftId or null
    private List<Long> discountByProducts; // products were acceptable Promotion (productSkuId)
    private Integer promotionType;  // 0: Category Promotion, 1: Brand Promotion, 2: Product Promotion, 3:Order, Shipping Promotion, 4:Discount for the next purchase, 5: EasyOrderCode
    private String codeNextPurchase;
    private DiscountDTO discountDTO;
    private String easyOrderCode;

    public ReductionDTO() {
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

    public DiscountDTO getDiscountDTO() {
        return discountDTO;
    }

    public void setDiscountDTO(DiscountDTO discountDTO) {
        this.discountDTO = discountDTO;
    }

    public List<Long> getDiscountByProducts() {
        return discountByProducts;
    }

    public void setDiscountByProducts(List<Long> discountByProducts) {
        this.discountByProducts = discountByProducts;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getEasyOrderCode() {
        return easyOrderCode;
    }

    public void setEasyOrderCode(String easyOrderCode) {
        this.easyOrderCode = easyOrderCode;
    }
}
