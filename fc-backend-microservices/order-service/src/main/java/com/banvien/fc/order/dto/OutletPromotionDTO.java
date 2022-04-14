package com.banvien.fc.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutletPromotionDTO implements Serializable {
    private Long outletPromotionId;
    private OutletDTO outlet;
    private String title;
    private Timestamp startDate;
    private Timestamp endDate;

    private List<Long> customerGroupIds;
    private String groupCode;

    private String contentXML;
    private Boolean status;
    private Integer limitPlayer;
    private String description;
    private Timestamp createdDate;
    private UserDTO createdBy;
    private Integer maxQuantityCanBuy;
    private Integer timesApplied;
    private Integer remainGift;
    private Integer totalGift;
    private Boolean neverEnd;
    private String notifyContent;
    private String notifyType; // SMS,NOTIFY,EMAIL
    private Timestamp notifySendDate;
    private String thumbnail;
    private String newPromotionJson;
    private String promotionRule;

    private Long idCondition;    // Probable for : CategoryId or BrandId or ProductId
    private Integer typePromotion; // 0: Category Promotion, 1: Brand Promotion, 2: Product Promotion, 3: Order, Shipping Promotion, 4: Discount for the next purchase
    private List<Long> outletPromotionIds;

    public OutletPromotionDTO() {
    }

    public OutletPromotionDTO(Long idCondition, Long outletPromotionId, Integer typePromotion) {
        this.outletPromotionId = outletPromotionId;
        this.idCondition = idCondition;
        this.typePromotion = typePromotion;
    }

    public OutletPromotionDTO(Long idCondition, Integer typePromotion, List<Long> outletPromotionIds) {
        this.idCondition = idCondition;
        this.typePromotion = typePromotion;
        this.outletPromotionIds = outletPromotionIds;
    }

    public String getNewPromotionJson() {
        return newPromotionJson;
    }

    public void setNewPromotionJson(String newPromotionJson) {
        this.newPromotionJson = newPromotionJson;
    }

    public Integer getTotalGift() {
        return totalGift;
    }

    public void setTotalGift(Integer totalGift) {
        this.totalGift = totalGift;
    }

    public Integer getRemainGift() {
        return remainGift;
    }

    public void setRemainGift(Integer remainGift) {
        this.remainGift = remainGift;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }

    public OutletDTO getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletDTO outlet) {
        this.outlet = outlet;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
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

    public Integer getLimitPlayer() {
        return limitPlayer;
    }

    public void setLimitPlayer(Integer limitPlayer) {
        this.limitPlayer = limitPlayer;
    }

    public Integer getMaxQuantityCanBuy() {
        return maxQuantityCanBuy;
    }

    public void setMaxQuantityCanBuy(Integer maxQuantityCanBuy) {
        this.maxQuantityCanBuy = maxQuantityCanBuy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Timestamp getNotifySendDate() {
        return notifySendDate;
    }

    public void setNotifySendDate(Timestamp notifySendDate) {
        this.notifySendDate = notifySendDate;
    }

    public List<Long> getCustomerGroupIds() {
        return customerGroupIds;
    }

    public void setCustomerGroupIds(List<Long> customerGroupIds) {
        this.customerGroupIds = customerGroupIds;
    }

    public Integer getTimesApplied() {
        return timesApplied;
    }

    public void setTimesApplied(Integer timesApplied) {
        this.timesApplied = timesApplied;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public String getPromotionRule() {
        return promotionRule;
    }

    public void setPromotionRule(String promotionRule) {
        this.promotionRule = promotionRule;
    }

    public Long getIdCondition() {
        return idCondition;
    }

    public void setIdCondition(Long idCondition) {
        this.idCondition = idCondition;
    }

    public Integer getTypePromotion() {
        return typePromotion;
    }

    public void setTypePromotion(Integer typePromotion) {
        this.typePromotion = typePromotion;
    }

    public List<Long> getOutletPromotionIds() {
        return outletPromotionIds;
    }

    public void setOutletPromotionIds(List<Long> outletPromotionIds) {
        this.outletPromotionIds = outletPromotionIds;
    }
}
