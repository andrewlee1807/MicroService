package com.banvien.fc.promotion.dto;

import com.banvien.fc.promotion.dto.rules.PromotionRuleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnhancedPromotionDTO {
    private Long outletPromotionId;
    private int type;   // 0: Category Promotion, 1: Brand Promotion, 2: Product Promotion, 3:Order, Shipping Promotion, 4:Discount for the next purchase, 5: Easy Order Discount
    private Long outletId;
    private String title;
    private Long startDate;
    private Long endDate;
    private String[] customerGroupIds;
    private String contentXML;
    private Boolean status;
    private String description;
    private UserDTO createdBy;
    private Boolean neverEnd;
    private String notifyContent;
    private int totalGift;
    private int remains;
    private String notifyType; // SMS,NOTIFY,EMAIL
    private Long notifySendDate;
    private String thumbnail;
    private int priority;
    private Integer promotionProperty;
    private String newPromotionJson;
    private Integer maxPerPromotion;
    private Integer maxPerCustomer;
    private Integer maxPerOrder;
    private Long promotionAdmin;
    private List<ApplyShop> lstApplyShop;
    private PromotionRuleDTO promotionRule;
    private PenetrationDTO penetration;
    private ApplyShop applyShopByAdmin;
    private List<CustomerGroupDTO> allCustomerGroups;
    private Long offerNextProductId;
    private List<Long> customerTargetIds;

    public List<Long> getCustomerTargetIds() {
        return customerTargetIds;
    }

    public void setCustomerTargetIds(List<Long> customerTargetIds) {
        this.customerTargetIds = customerTargetIds;
    }

    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }

    public Long getOfferNextProductId() {
        return offerNextProductId;
    }

    public void setOfferNextProductId(Long offerNextProductId) {
        this.offerNextProductId = offerNextProductId;
    }

    public PromotionRuleDTO getPromotionRule() {
        return promotionRule;
    }

    public void setPromotionRule(PromotionRuleDTO promotionRule) {
        this.promotionRule = promotionRule;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getContentXML() {
        return contentXML;
    }

    public void setContentXML(String contentXML) {
        this.contentXML = contentXML;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getNeverEnd() {
        return neverEnd;
    }

    public void setNeverEnd(Boolean neverEnd) {
        this.neverEnd = neverEnd;
    }

    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public Long getNotifySendDate() {
        return notifySendDate;
    }

    public void setNotifySendDate(Long notifySendDate) {
        this.notifySendDate = notifySendDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getNewPromotionJson() {
        return newPromotionJson;
    }

    public void setNewPromotionJson(String newPromotionJson) {
        this.newPromotionJson = newPromotionJson;
    }

    public String[] getCustomerGroupIds() {
        return customerGroupIds;
    }

    public void setCustomerGroupIds(String[] customerGroupIds) {
        this.customerGroupIds = customerGroupIds;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Integer getPromotionProperty() {
        return promotionProperty;
    }

    public void setPromotionProperty(Integer promotionProperty) {
        this.promotionProperty = promotionProperty;
    }

    public PenetrationDTO getPenetration() {
        return penetration;
    }

    public void setPenetration(PenetrationDTO penetration) {
        this.penetration = penetration;
    }

    public Integer getMaxPerPromotion() {
        return maxPerPromotion;
    }

    public void setMaxPerPromotion(Integer maxPerPromotion) {
        this.maxPerPromotion = maxPerPromotion;
    }

    public Integer getMaxPerCustomer() {
        return maxPerCustomer;
    }

    public void setMaxPerCustomer(Integer maxPerCustomer) {
        this.maxPerCustomer = maxPerCustomer;
    }

    public Integer getMaxPerOrder() {
        return maxPerOrder;
    }

    public void setMaxPerOrder(Integer maxPerOrder) {
        this.maxPerOrder = maxPerOrder;
    }

    public int getTotalGift() {
        return totalGift;
    }

    public void setTotalGift(int totalGift) {
        this.totalGift = totalGift;
    }

    public int getRemains() {
        return remains;
    }

    public void setRemains(int remains) {
        this.remains = remains;
    }

    public List<ApplyShop> getLstApplyShop() {
        return lstApplyShop;
    }

    public void setLstApplyShop(List<ApplyShop> lstApplyShop) {
        this.lstApplyShop = lstApplyShop;
    }

    public Long getPromotionAdmin() {
        return promotionAdmin;
    }

    public void setPromotionAdmin(Long promotionAdmin) {
        this.promotionAdmin = promotionAdmin;
    }

    public ApplyShop getApplyShopByAdmin() {
        return applyShopByAdmin;
    }

    public void setApplyShopByAdmin(ApplyShop applyShopByAdmin) {
        this.applyShopByAdmin = applyShopByAdmin;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<CustomerGroupDTO> getAllCustomerGroups() {
        return allCustomerGroups;
    }

    public void setAllCustomerGroups(List<CustomerGroupDTO> allCustomerGroups) {
        this.allCustomerGroups = allCustomerGroups;
    }
}
