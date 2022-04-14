package com.banvien.fc.catalog.entity;

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
    private BigDecimal Longitude;
    private BigDecimal latitude;
    private Long createdby;
    private Timestamp createdDate;
    private Long modifiedBy;
    private Timestamp modifiedDate;
    private String image;
    private Boolean status;
    private Boolean top;
    private String referralCode;
    private Double loyaltyRate;
    private Long priceRate;
    private String approveCreditKey;
    private Long countryId;
    private Long shopManId;

    @Column(name = "outletId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    @Column(name = "distributorId")
    @Basic
    public Long getDistributorId() {
        return distributorId;
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

    @Column(name = "phoneNumber")
    @Basic
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "Longitude")
    @Basic
    public BigDecimal getLongitude() {
        return Longitude;
    }

    public void setLongitude(BigDecimal Longitude) {
        this.Longitude = Longitude;
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

    @Column(name = "createdDate")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "modifiedBy")
    @Basic
    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Column(name = "modifiedDate")
    @Basic
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
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

    public OutletEntity() {
    }

    public OutletEntity(Long outletId) {
        this.outletId = outletId;
    }

    @Column(name = "address")
    @Basic
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "referralCode")
    @Basic
    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    @Basic
    @Column(name = "loyaltyrate")
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

    @Column(name = "approvecreditkey")
    @Basic
    public String getApproveCreditKey() {
        return approveCreditKey;
    }

    public void setApproveCreditKey(String approveCreditKey) {
        this.approveCreditKey = approveCreditKey;
    }

    @Column(name = "countryid")
    @Basic
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    @Column(name = "shopman")
    @Basic
    public Long getShopManId() {
        return shopManId;
    }

    public void setShopManId(Long shopManId) {
        this.shopManId = shopManId;
    }
}
