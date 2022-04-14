package com.banvien.fc.delivery.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    private CountryEntity country;
    private BigDecimal Longitude;
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
    private List<OutletAssetEntity> outletAssets;
    private List<FrameEntity> frames;
    private String referralCode;
    private Double averageRating;
    private Integer totalRating;
    private Double loyaltyRate;
    private Long priceRate;
    private List<SocialChatEntity> socialChats;
    private String approveCreditKey;
    private Integer loyaltyPointExpiredMonth;

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

    @ManyToOne
    @JoinColumn(name = "countryid", referencedColumnName = "countryid")

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
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

    @ManyToOne
    @JoinColumn(name = "shopman", referencedColumnName = "userId")
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

    private OutletWorkingTimeEntity outletWorkingTimeEntity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "outletworkingtimeid", referencedColumnName = "outletworkingtimeid")
    public OutletWorkingTimeEntity getOutletWorkingTimeEntity() {
        return outletWorkingTimeEntity;
    }

    public void setOutletWorkingTimeEntity(OutletWorkingTimeEntity outletWorkingTimeEntity) {
        this.outletWorkingTimeEntity = outletWorkingTimeEntity;
    }

    @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy(value = "displayPos ASC")
    public List<OutletAssetEntity> getOutletAssets() {
        return outletAssets;
    }

    public void setOutletAssets(List<OutletAssetEntity> outletAssets) {
        this.outletAssets = outletAssets;
    }

    public void setOutletAsset(OutletAssetEntity outletAssetEntity) {
        outletAssetEntity.setOutlet(this);
        this.outletAssets.add(outletAssetEntity);
    }

    @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<FrameEntity> getFrames() {
        return frames;
    }

    public void setFrames(List<FrameEntity> frames) {
        this.frames = frames;
    }

    public void setFrame(FrameEntity frame) {
        frame.setOutlet(this);
        this.frames.add(frame);
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

    public OutletEntity(Long outletId, Long distributorId, String code, String name, String phoneNumber, String address, BigDecimal longitude, BigDecimal latitude, Long createdby,
                        Timestamp createdDate, Long modifiedBy, Timestamp modifiedDate, Boolean top, Long totalView, String referralCode, Double averageRating, Integer totalRating, Double loyaltyRate, Long priceRate) {
        this.outletId = outletId;
        this.distributorId = distributorId;
        this.code = code;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.Longitude = longitude;
        this.latitude = latitude;
        this.createdby = createdby;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.outletAssets = new ArrayList<>();
        this.top = top;
        this.totalView = totalView;
        this.referralCode = referralCode;
        this.averageRating = averageRating != null ? averageRating : 0d;
        this.totalRating = totalRating != null? totalRating : 0;
        this.loyaltyRate = loyaltyRate;
        this.priceRate = priceRate;
    }

    public OutletEntity() {
        this.outletAssets = new ArrayList<>();
    }

    public OutletEntity(Long outletId) {
        this.outletId = outletId;
    }

    @PrePersist
    @PreUpdate
    protected void updateDefaultValue(){
        if(totalView==null){
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

    @Column(name = "referralCode")
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

    public Long getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(Long priceRate) {
        this.priceRate = priceRate;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "outlet", cascade = CascadeType.ALL)
    @OrderBy(value = "socialChatId asc")
    public List<SocialChatEntity> getSocialChats() {
        return socialChats;
    }

    public void setSocialChats(List<SocialChatEntity> socialChats) {
        this.socialChats = socialChats;
    }

    @Column(name = "approvecreditkey")
    @Basic
    public String getApproveCreditKey() {
        return approveCreditKey;
    }

    public void setApproveCreditKey(String approveCreditKey) {
        this.approveCreditKey = approveCreditKey;
    }

    @Column(name = "LoyaltyPointExpiredMonth")
    @Basic
    public Integer getLoyaltyPointExpiredMonth() {
        return loyaltyPointExpiredMonth;
    }

    public void setLoyaltyPointExpiredMonth(Integer loyaltyPointExpiredMonth) {
        this.loyaltyPointExpiredMonth = loyaltyPointExpiredMonth;
    }
}
