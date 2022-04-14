package com.banvien.fc.promotion.dto.vansale;

import com.banvien.fc.promotion.dto.OutletDTO;

import java.io.Serializable;
import java.sql.Timestamp;

public class CampaignDTO implements Serializable {
    private Long campaignId;
    private String title;
    private String image;
    private String description;
    private Integer status;
    private String sponsor;
    private Timestamp createdDate;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer totalLike;
    private Integer totalShare;
    private Integer maxParticipantNumber;
    private Integer giftLeft;
    private Integer slotLeft;
    private Boolean hasLike;
    private Boolean hasShare;
    private String postUrl;
    private Boolean playSequence;
    private Integer maxReplay;
    private OutletDTO outlet;

    public Integer getGiftLeft() {
        return giftLeft;
    }

    public void setGiftLeft(Integer giftLeft) {
        this.giftLeft = giftLeft;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
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

    public Integer getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
    }

    public Integer getTotalShare() {
        return totalShare;
    }

    public void setTotalShare(Integer totalShare) {
        this.totalShare = totalShare;
    }

    public Integer getMaxParticipantNumber() {
        return maxParticipantNumber;
    }

    public void setMaxParticipantNumber(Integer maxParticipantNumber) {
        this.maxParticipantNumber = maxParticipantNumber;
    }

    public Boolean getHasLike() {
        return hasLike;
    }

    public void setHasLike(Boolean hasLike) {
        this.hasLike = hasLike;
    }

    public Boolean getHasShare() {
        return hasShare;
    }

    public void setHasShare(Boolean hasShare) {
        this.hasShare = hasShare;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public Integer getSlotLeft() {
        return slotLeft;
    }

    public void setSlotLeft(Integer slotLeft) {
        this.slotLeft = slotLeft;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public Boolean getPlaySequence() {
        return playSequence;
    }

    public void setPlaySequence(Boolean playSequence) {
        this.playSequence = playSequence;
    }

    public Integer getMaxReplay() {
        return maxReplay;
    }

    public void setMaxReplay(Integer maxReplay) {
        this.maxReplay = maxReplay;
    }

    public OutletDTO getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletDTO outlet) {
        this.outlet = outlet;
    }
}
