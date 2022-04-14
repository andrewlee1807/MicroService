package com.banvien.fc.order.dto.rules;


import com.banvien.fc.order.dto.rules.Enum.DiscountType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscountDTO {
    private double minValue;
    private double discountValue;
    private DiscountType discountType;
    private List<Long> targetIds;
    private List<Long> productIds;
    private String targetName;
    private String easyOrderCode;

    public DiscountDTO() {
        this.discountType = DiscountType.PERCENT_OFF;
        this.targetIds = new ArrayList<>();
        this.productIds = new ArrayList<>();
    }

    public void reformat(){
        this.targetIds.clear();
        this.productIds.clear();
    }

    public DiscountDTO(double minValue, double discountValue, DiscountType discountType, List<Long> targetIds) {
        this.minValue = minValue;
        this.discountValue = discountValue;
        this.discountType = discountType;
        this.targetIds = targetIds;
        if (this.targetIds == null) this.targetIds = new ArrayList<>();
        this.productIds = new ArrayList<>();
    }

    public DiscountDTO(Object minValue, Object discountValue, Object discountType, Object targetIds) {
        this.minValue = Double.parseDouble((String) minValue);
        this.discountValue = Double.parseDouble((String) discountValue);
        this.productIds = new ArrayList<>();
        switch ((String) discountType) {
            case "percentOff":
                this.discountType = DiscountType.PERCENT_OFF;
                break;
            case "amountOff":
                this.discountType = DiscountType.AMOUNT_OFF;
                break;
            case "gift":
                this.discountType = DiscountType.GIFT;
                break;
            case "product":
                this.discountType = DiscountType.PRODUCT;
                break;
            case "fixPrice":
                this.discountType = DiscountType.FIX_PRICE;
                break;
            case "deliveryFixedPrice":
                this.discountType = DiscountType.DELIVERY_FIXED_PRICE;
                break;
            case "deliveryDiscount":
                this.discountType = DiscountType.DELIVERY_DISCOUNT;
                break;
            case "shippingDiscount":
                this.discountType = DiscountType.SHIPPING_DISCOUNT;
                break;
            case "shippingFixPrice":
                this.discountType = DiscountType.SHIPPING_FIX_PRICE;
                break;
        }
        this.targetIds = new ArrayList<>();
        try {
            for (Double i : (Collection<Double>) targetIds) {
                this.targetIds.add(i.longValue());
            }
        } catch (Exception e) {
        }
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public void setDiscountType(String discountType) {
        switch (discountType) {
            case "percentOff":
            case "PERCENT_OFF":
                this.discountType = DiscountType.PERCENT_OFF;
                break;
            case "amountOff":
            case "AMOUNT_OFF":
                this.discountType = DiscountType.AMOUNT_OFF;
                break;
            case "gift":
            case "GIFT":
                this.discountType = DiscountType.GIFT;
                break;
            case "product":
            case "PRODUCT":
                this.discountType = DiscountType.PRODUCT;
                break;
            case "fixPrice":
            case "FIX_PRICE":
                this.discountType = DiscountType.FIX_PRICE;
                break;
            case "deliveryFixedPrice":
            case "DELIVERY_FIXED_PRICE":
                this.discountType = DiscountType.DELIVERY_FIXED_PRICE;
                break;
            case "deliveryDiscount":
            case "DELIVERY_DISCOUNT":
                this.discountType = DiscountType.DELIVERY_DISCOUNT;
                break;
            case "shippingDiscount":
            case "SHIPPING_DISCOUNT":
                this.discountType = DiscountType.SHIPPING_DISCOUNT;
                break;
            case "shippingFixPrice":
            case "SHIPPING_FIX_PRICE":
                this.discountType = DiscountType.SHIPPING_FIX_PRICE;
                break;
        }
    }

    public List<Long> getTargetIds() {
        if (targetIds == null) this.targetIds = new ArrayList<>();
        return targetIds;
    }

    public void setTargetIds(List<Long> targetIds) {
        this.targetIds = targetIds;
    }


        public String getTargetName() {
            return targetName;
        }

        public void setTargetName(String targetName) {
            this.targetName = targetName;
        }

        public String getEasyOrderCode() {
            return easyOrderCode;
        }

        public void setEasyOrderCode(String easyOrderCode) {
            this.easyOrderCode = easyOrderCode;
        }
}
