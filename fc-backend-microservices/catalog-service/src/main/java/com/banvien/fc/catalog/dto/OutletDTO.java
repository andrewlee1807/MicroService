package com.banvien.fc.catalog.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class OutletDTO implements Serializable {
    private static final long serialVersionUID = -7998380199650154907L;
    private Long outletId;
    private Long distributorId;
    private String code;
    private String name;
    private String phoneNumber;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Long createdBy;
    private Timestamp createdDate;
    private Long modifiedBy;
    private Timestamp modifiedDate;
    private String image;
    private Boolean status;
    private Boolean top;
    private Long totalView;
    private String referralCode;
    private Double averageRating;
    private Integer totalRating;
    private Double loyaltyRate;
    private Long priceRate;
    private Integer loyaltyPointExpiredMonth;

    public String getNameOwner() {
        return nameOwner;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

    private String nameOwner;

    private String approveCreditKey;

    public OutletDTO(Long outletId, Long distributorId, String code, String name, String phoneNumber,
                     String address, BigDecimal longitude, BigDecimal latitude,
                     Long createdBy, Timestamp createdDate, Long modifiedBy, Timestamp modifiedDate, Boolean top, Long totalView, String referralCode, Double averageRating, Integer totalRating, Double loyaltyRate, Long priceRate) {
        this.outletId = outletId;
        this.distributorId = distributorId;
        this.code = code;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address= address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.top = top;
        this.totalView = totalView;
        this.referralCode = referralCode;
        this.averageRating = averageRating;
        this.totalRating = totalRating;
        this.loyaltyRate = loyaltyRate;
        this.priceRate = priceRate;
    }

    public OutletDTO() {

    }

    public OutletDTO(Long outletId) {
        this.outletId = outletId;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Long getTotalView() {
        return totalView;
    }

    public void setTotalView(Long totalView) {
        this.totalView = totalView;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
    }

    public Double getLoyaltyRate() {
        return loyaltyRate;
    }

    public void setLoyaltyRate(Double loyaltyRate) {
        this.loyaltyRate = loyaltyRate;
    }

    public Long getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(Long priceRate) {
        this.priceRate = priceRate;
    }

    public String getApproveCreditKey() {
        return approveCreditKey;
    }

    public void setApproveCreditKey(String approveCreditKey) {
        this.approveCreditKey = approveCreditKey;
    }

    public Integer getLoyaltyPointExpiredMonth() {
        return loyaltyPointExpiredMonth;
    }

    public void setLoyaltyPointExpiredMonth(Integer loyaltyPointExpiredMonth) {
        this.loyaltyPointExpiredMonth = loyaltyPointExpiredMonth;
    }
}
