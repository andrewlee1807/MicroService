package com.banvien.fc.payment.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Table(name = "outlet")
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public
class OutletEntity implements Serializable {
    private Long outletId;
    private Long distributorId;
    private String code;
    private String name;
    private String phoneNumber;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Long createdby;
    private Timestamp createdDate;
    private Long modifiedBy;
    private Timestamp modifiedDate;
    private UserEntity shopman;
    private String image;
    private Boolean status;
    private Boolean top;
    private Long totalView;
    private String referralCode;
    private Double averageRating;
    private Integer totalRating;
    private Double loyaltyRate;
    private Long priceRate;
    private String approveCreditKey;
    private Integer loyaltyPointExpiredMonth;
    private Long countryId;
    private boolean enableInventory;

    public OutletEntity() {
    }

    @Column(name = "outletid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    @Column(name = "distributorid")
    @Basic
    public Long getDistributorId() {
        return distributorId;
    }

    @Basic
    @Column(name = "countryId")
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    @Column(name = "code")
    @Basic
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "phonenumber")
    @Basic
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Column(name = "longitude")
    @Basic
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Column(name = "latitude")
    @Basic
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Column(name = "createdby")
    @Basic
    public Long getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Long createdby) {
        this.createdby = createdby;
    }

    @Column(name = "createddate")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "modifiedby")
    @Basic
    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Column(name = "modifieddate")
    @Basic
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @ManyToOne
    @JoinColumn(name = "shopman", referencedColumnName = "userid")
    public UserEntity getShopman() {
        return shopman;
    }

    public void setShopman(UserEntity shopman) {
        this.shopman = shopman;
    }

    @Column(name = "image")
    @Basic
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "status")
    @Basic
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Column(name = "top")
    @Basic
    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    @Basic
    @Column(name = "totalview")
    public Long getTotalView() {
        return totalView;
    }

    public void setTotalView(Long totalView) {
        this.totalView = totalView;
    }

    public OutletEntity(Long outletId) {
        this.outletId = outletId;
    }

    @PrePersist
    @PreUpdate
    protected void updateDefaultValue() {
        if (totalView == null) {
            totalView = new Long(0);
        }
    }

    @Column(name = "address")
    @Basic
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "referralcode")
    @Basic
    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    @Column(name = "averagerating")
    @Basic
    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    @Column(name = "totalrating")
    @Basic
    public Integer getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
    }

    @Basic
    @Column(name = "loyaltyrate")
    public Double getLoyaltyRate() {
        return loyaltyRate;
    }

    public void setLoyaltyRate(Double loyaltyRate) {
        this.loyaltyRate = loyaltyRate;
    }

    @Basic
    @Column(name = "pricerate")
    public Long getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(Long priceRate) {
        this.priceRate = priceRate;
    }

    @Column(name = "approvecreditkey")
    @Basic
    public String getApproveCreditKey() {
        return approveCreditKey;
    }

    public void setApproveCreditKey(String approveCreditKey) {
        this.approveCreditKey = approveCreditKey;
    }

    @Column(name = "loyaltypointexpiredmonth")
    @Basic
    public Integer getLoyaltyPointExpiredMonth() {
        return loyaltyPointExpiredMonth;
    }

    public void setLoyaltyPointExpiredMonth(Integer loyaltyPointExpiredMonth) {
        this.loyaltyPointExpiredMonth = loyaltyPointExpiredMonth;
    }

    @Basic
    @Column(name = "enableinventory")
    public boolean getEnableInventory() {
        return enableInventory;
    }

    public void setEnableInventory(boolean enableInventory) {
        this.enableInventory = enableInventory;
    }
}
