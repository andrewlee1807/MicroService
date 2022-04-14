package com.banvien.fc.promotion.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "adminpromotion")
public class AdminPromotionEntity {
    private long adminPromotionId;
    private Timestamp startDate;
    private Timestamp endDate;
    private String groupCode;
    private String name;
    private int numberPromotion;
    private boolean status;
    private Timestamp createdDate;
    private String newPromotionJson;
    private Long countryId;

    @Id
    @Column(name = "adminpromotionid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getAdminPromotionId() {
        return adminPromotionId;
    }

    public void setAdminPromotionId(long adminpromotionid) {
        this.adminPromotionId = adminpromotionid;
    }

    @Basic
    @Column(name = "startdate", nullable = false)
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startdate) {
        this.startDate = startdate;
    }

    @Basic
    @Column(name = "enddate", nullable = false)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp enddate) {
        this.endDate = enddate;
    }

    @Basic
    @Column(name = "groupcode", nullable = false, length = -1)
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupcode) {
        this.groupCode = groupcode;
    }

    @Basic
    @Column(name = "name", nullable = true, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "numberpromotion", nullable = false)
    public int getNumberPromotion() {
        return numberPromotion;
    }

    public void setNumberPromotion(int numberpromotion) {
        this.numberPromotion = numberpromotion;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Basic
    @Column(name = "createddate", nullable = false)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createddate) {
        this.createdDate = createddate;
    }

    public String getNewPromotionJson() {
        return newPromotionJson;
    }

    public void setNewPromotionJson(String newPromotionJson) {
        this.newPromotionJson = newPromotionJson;
    }

    @Basic
    @Column(name = "countryid")
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
