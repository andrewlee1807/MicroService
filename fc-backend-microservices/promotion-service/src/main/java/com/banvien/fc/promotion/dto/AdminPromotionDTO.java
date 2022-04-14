package com.banvien.fc.promotion.dto;

import com.banvien.fc.common.util.CommonUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminPromotionDTO implements Serializable {
    private Long id;
    private String description;
    private String thumbnail;
    private String startdate;
    private String enddate;
    private Long startDate;
    private Long endDate;
    private String groupCode;
    private String name;
    private Integer numberPromotion;
    private String createdDate;
    private Boolean status;
    private List<OutletPromotionDTO> listPromotion;
    private String programName;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AdminPromotionDTO() {
        listPromotion = new ArrayList<>();
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public List<OutletPromotionDTO> getListPromotion() {
        return listPromotion;
    }

    public void setListPromotion(List<OutletPromotionDTO> listPromotion) {
        this.listPromotion = listPromotion;
        if (listPromotion == null) {
            this.listPromotion = new ArrayList<>();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(Timestamp startdate) {
        Date date = new Date(startdate.getTime());
        this.startdate = CommonUtils.convertDateToString(date);
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(Timestamp enddate) {
        Date date = new Date(enddate.getTime());
        this.enddate = CommonUtils.convertDateToString(date);
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Integer getNumberPromotion() {
        return numberPromotion;
    }

    public void setNumberPromotion(Integer numberPromotion) {
        this.numberPromotion = numberPromotion;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        Date date = new Date(createdDate.getTime());
        this.createdDate = CommonUtils.convertDateToString(date);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
