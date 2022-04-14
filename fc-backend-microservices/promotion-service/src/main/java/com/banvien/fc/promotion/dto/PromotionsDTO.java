package com.banvien.fc.promotion.dto;

import com.banvien.fc.common.util.CommonUtils;
import com.banvien.fc.promotion.dto.rules.Enum.DiscountType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionsDTO {
    private Long id; // OutletPromotionId
    private String startdate;
    private String enddate;
    private Long startDate;
    private Long endDate;
    private String programName; // OutletPromotionEntity's title
    private String programCode;
    private Boolean status; // OutletPromotionEntity
    private String condition;   // And/Or
    private List<Long> whenBuy; // exception case : Order, Shipping Promotion
    private Double valueDiscount;   // Price/percent/ProductId/GiftId
    private DiscountType typeDiscount;
    private Double minValue;
    private String type;
    private boolean isAdmin;

    public PromotionsDTO() {
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate.getTime();
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate.getTime();
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
