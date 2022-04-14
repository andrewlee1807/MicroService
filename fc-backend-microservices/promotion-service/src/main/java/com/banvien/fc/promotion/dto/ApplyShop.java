package com.banvien.fc.promotion.dto;

import com.banvien.fc.promotion.dto.rules.PromotionRuleDTO;

import java.util.List;

public class ApplyShop {
    private Long outletPromotionId;
    private Long outletId;
    private String outletName;
    private int maxPerPromotion;
    private String[] customerGroupIds;
    private int priority;
    private PromotionRuleDTO promotionRule;
    private Long offerNextProductId;
    private List<Long> customerTargetIds;

    public List<Long> getCustomerTargetIds() {
        return customerTargetIds;
    }

    public void setCustomerTargetIds(List<Long> customerTargetIds) {
        this.customerTargetIds = customerTargetIds;
    }

    public Long getOfferNextProductId() {
        return offerNextProductId;
    }

    public void setOfferNextProductId(Long offerNextProductId) {
        this.offerNextProductId = offerNextProductId;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public int getMaxPerPromotion() {
        return maxPerPromotion;
    }

    public void setMaxPerPromotion(int maxPerPromotion) {
        this.maxPerPromotion = maxPerPromotion;
    }

    public String[] getCustomerGroupIds() {
        if (customerGroupIds == null) return new String[0];
        return customerGroupIds;
    }

    public void setCustomerGroupIds(String[] customerGroupIds) {
        if (customerGroupIds == null) this.customerGroupIds = new String[0];
        this.customerGroupIds = customerGroupIds;
    }

    public PromotionRuleDTO getPromotionRule() {
        return promotionRule;
    }

    public void setPromotionRule(PromotionRuleDTO promotionRule) {
        this.promotionRule = promotionRule;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }
}
