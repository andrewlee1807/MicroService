package com.banvien.fc.promotion.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Table(name = "campaign")
@Entity
public class CampaignEntity {
    private Long campaignId;
    private String title;
    private String image;
    private String description;
    private Integer status;
    private String sponsor;
    private UserEntity createdBy;
    private Timestamp createdDate;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer totalLike;
    private Integer totalShare;
    private Integer maxParticipantNumber;
    private String postUrl;
    private Boolean playSequence;
    private Integer maxReplay;
    private OutletEntity outlet;

    @Id
    @Column(name = "campaignid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
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
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    @Column(name = "totallike")
    public Integer getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
    }

    @Basic
    @Column(name = "totalshare")
    public Integer getTotalShare() {
        return totalShare;
    }

    public void setTotalShare(Integer totalShare) {
        this.totalShare = totalShare;
    }

    @ManyToOne()
    @JoinColumn(name = "createdbyid", referencedColumnName = "userid")
    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "maxParticipantNumber")
    public Integer getMaxParticipantNumber() {
        return maxParticipantNumber;
    }

    public void setMaxParticipantNumber(Integer maxParticipantNumber) {
        this.maxParticipantNumber = maxParticipantNumber;
    }

    @Basic
    @Column(name = "sponsor")
    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    @Basic
    @Column(name = "posturl")
    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    @Basic
    @Column(name = "playsequence")
    public Boolean getPlaySequence() {
        return playSequence;
    }

    public void setPlaySequence(Boolean playSequence) {
        this.playSequence = playSequence;
    }


    @Basic
    @Column(name = "maxreplay")
    public Integer getMaxReplay() {
        return maxReplay;
    }

    public void setMaxReplay(Integer maxReplay) {
        this.maxReplay = maxReplay;
    }

    @ManyToOne()
    @JoinColumn(name = "outletId", referencedColumnName = "outletId")
    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }
}
