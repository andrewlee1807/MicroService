package com.banvien.fc.promotion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "outletpromotion")
public class OutletPromotionEntity implements Serializable {
    private Long outletPromotionId;
    @JsonIgnore //test only
    private OutletEntity outlet;
    private String title;
    private Timestamp startDate;
    private Timestamp endDate;
    private String contentXML;
    private Boolean status;
    private Integer limitPlayer;
    private String description;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    @JsonIgnore //test only
    private UserEntity createdBy;
    private Integer maxQuantityCanBuy;
    private Integer timesApplied;
    private Integer totalGift;
    private Integer remainGift;
    private Boolean neverEnd;
    private String notifyContent;
    private String notifyType; // SMS,NOTIFY,EMAIL
    private Timestamp notifySendDate;
    private Timestamp notifySentDate;
    @JsonIgnore //test only
    private CommonTemplateEntity commonTemplate;
    private String thumbnail;
    private String groupCode;
    private String newPromotionJson;
    private String promotionRule;
    private Integer priority;
    private Integer isCombined;
    private Integer maxPerPromotion;
    private Integer maxPerCustomer;
    private Integer maxPerOrder;
    private Integer penetrationType;
    private String penetrationValue;
    private Long promotionAdmin;
    private String applyShopByAdmin;
    private String easyOrderCode;

    public OutletPromotionEntity() {
    }

    @Id
    @Column(name = "outletpromotionid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOutletPromotionId() {
        return outletPromotionId;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Basic
    @Column(name = "newpromotionjson")
    public String getNewPromotionJson() {
        return newPromotionJson;
    }

    public void setNewPromotionJson(String newpromotionjson) {
        this.newPromotionJson = newpromotionjson;
    }

    public void setOutletPromotionId(Long outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
    }

    @ManyToOne
    @JoinColumn(name = "commontemplateid", referencedColumnName = "commontemplateid")
    public CommonTemplateEntity getCommonTemplate() {
        return commonTemplate;
    }

    public void setCommonTemplate(CommonTemplateEntity commonTemplate) {
        this.commonTemplate = commonTemplate;
    }

    @ManyToOne
    @JoinColumn(name = "outletid", referencedColumnName = "outletid")
    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    @Basic
    @Column(name = "startdate")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "enddate")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "contentxml")
    public String getContentXML() {
        return contentXML;
    }

    public void setContentXML(String contentXML) {
        this.contentXML = contentXML;
    }

    @Basic
    @Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Basic
    @Column(name = "limitplayer")
    public Integer getLimitPlayer() {
        return limitPlayer;
    }

    public void setLimitPlayer(Integer limitPlayer) {
        this.limitPlayer = limitPlayer;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "createddate")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "maxquantitycanbuy")
    public Integer getMaxQuantityCanBuy() {
        return maxQuantityCanBuy;
    }

    public void setMaxQuantityCanBuy(Integer maxQuantityCanBuy) {
        this.maxQuantityCanBuy = maxQuantityCanBuy;
    }

    @Basic
    @Column(name = "totalgift")
    public Integer getTotalGift() {
        return totalGift;
    }

    public void setTotalGift(Integer totalGift) {
        this.totalGift = totalGift;
    }

    @Basic
    @Column(name = "remaingift")
    public Integer getRemainGift() {
        return remainGift;
    }

    public void setRemainGift(Integer remainGift) {
        this.remainGift = remainGift;
    }

    @Basic
    @Column(name = "neverend")
    public Boolean getNeverEnd() {
        return neverEnd;
    }

    public void setNeverEnd(Boolean neverEnd) {
        this.neverEnd = neverEnd;
    }

    @Basic
    @Column(name = "notifycontent")
    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    @Basic
    @Column(name = "notifytype")
    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    @Basic
    @Column(name = "notifysenddate")
    public Timestamp getNotifySendDate() {
        return notifySendDate;
    }

    public void setNotifySendDate(Timestamp notifySendDate) {
        this.notifySendDate = notifySendDate;
    }

    @Basic
    @Column(name = "notifysentdate")
    public Timestamp getNotifySentDate() {
        return notifySentDate;
    }

    public void setNotifySentDate(Timestamp notifySentDate) {
        this.notifySentDate = notifySentDate;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "timesapplied")
    public Integer getTimesApplied() {
        return timesApplied;
    }

    public void setTimesApplied(Integer timesApplied) {
        this.timesApplied = timesApplied;
    }

    @Basic
    @Column(name = "thumbnail")
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Basic
    @Column(name = "groupcode")
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @ManyToOne
    @JoinColumn(name = "createdbyid", referencedColumnName = "userid")
    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "promotionrule")
    public String getPromotionRule() {
        return promotionRule;
    }

    public void setPromotionRule(String promotionRule) {
        this.promotionRule = promotionRule;
    }

    @Basic
    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "iscombined")
    public Integer getIsCombined() {
        return isCombined;
    }

    public void setIsCombined(Integer isCombined) {
        this.isCombined = isCombined;
    }

    @Basic
    @Column(name = "maxperpromotion")
    public Integer getMaxPerPromotion() {
        return maxPerPromotion;
    }

    public void setMaxPerPromotion(Integer maxPerPromotion) {
        this.maxPerPromotion = maxPerPromotion;
    }

    @Basic
    @Column(name = "maxpercustomer")
    public Integer getMaxPerCustomer() {
        return maxPerCustomer;
    }


    public void setMaxPerCustomer(Integer maxPerCustomer) {
        this.maxPerCustomer = maxPerCustomer;
    }

    @Basic
    @Column(name = "maxperorder")
    public Integer getMaxPerOrder() {
        return maxPerOrder;
    }

    public void setMaxPerOrder(Integer maxPerOrder) {
        this.maxPerOrder = maxPerOrder;
    }

    @Basic
    @Column(name = "penetrationtype")
    public Integer getPenetrationType() {
        return penetrationType;
    }

    public void setPenetrationType(Integer penetrationType) {
        this.penetrationType = penetrationType;
    }

    @Basic
    @Column(name = "penetrationvalue")
    public String getPenetrationValue() {
        return penetrationValue;
    }

    public void setPenetrationValue(String penetrationValue) {
        this.penetrationValue = penetrationValue;
    }

    @Basic
    @Column(name ="promotionadmin")
    public Long getPromotionAdmin() {
        return promotionAdmin;
    }

    public void setPromotionAdmin(Long promotionAdmin) {
        this.promotionAdmin = promotionAdmin;
    }

    @Basic
    @Column(name ="applyshopbyadmin")
    public String getApplyShopByAdmin() {
        return applyShopByAdmin;
    }

    public void setApplyShopByAdmin(String applyShopByAdmin) {
        this.applyShopByAdmin = applyShopByAdmin;
    }

    @Basic
    @Column(name ="easyordercode")
    public String getEasyOrderCode() {
        return easyOrderCode;
    }

    public void setEasyOrderCode(String easyorderCode) {
        this.easyOrderCode = easyorderCode;
    }
}
