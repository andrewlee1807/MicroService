package com.banvien.fc.promotion.dto;

import com.banvien.fc.promotion.dto.rules.PromotionRuleDTO;

import java.util.ArrayList;
import java.util.List;

public class AdminPromotionResponseDTO {
    private Long outletPromotionId;
    private Long outletId;
    private String outletName;
    private int maxPerPromotion;
    private String[] customerGroupIds;
    private int priority;
    private int remains;
    private PromotionRuleDTO promotionRule;
    private List lstItemCondition;
    private List lstItemExcludes;
    private List lstItemDiscount;
    private List customerTargets;

    public List getCustomerTargets() {
        return customerTargets;
    }

    public void setCustomerTargets(List customerTargets) {
        this.customerTargets = customerTargets;
    }

    public List getLstItemDiscount() {
        return lstItemDiscount;
    }

    public void setLstItemDiscount(List lstItemDiscount) {
        this.lstItemDiscount = lstItemDiscount;
        if (lstItemDiscount == null) {
            this.lstItemDiscount = new ArrayList();
        }
    }

    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }

    public String[] getCustomerGroupIds() {
        if (customerGroupIds == null) return new String[0];
        return customerGroupIds;
    }

    public void setCustomerGroupIds(String[] customerGroupIds) {
        this.customerGroupIds = customerGroupIds;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public Integer getMaxPerPromotion() {
        return maxPerPromotion;
    }

    public List getLstItemCondition() {
        return lstItemCondition;
    }

    public void setLstItemCondition(List lstItemCondition) {
        this.lstItemCondition = lstItemCondition;
    }

    public List getLstItemExcludes() {
        return lstItemExcludes;
    }

    public void setLstItemExcludes(List lstItemExcludes) {
        this.lstItemExcludes = lstItemExcludes;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public void setMaxPerPromotion(int maxPerPromotion) {
        this.maxPerPromotion = maxPerPromotion;
    }

    public int getPriority() {
        return priority;
    }

    public int getRemains() {
        return remains;
    }

    public void setRemains(int remains) {
        this.remains = remains;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public PromotionRuleDTO getPromotionRule() {
        return promotionRule;
    }

    public void setPromotionRule(PromotionRuleDTO promotionRule) {
        this.promotionRule = promotionRule;
    }
}
