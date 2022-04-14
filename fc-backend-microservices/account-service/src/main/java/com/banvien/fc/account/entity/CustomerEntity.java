package com.banvien.fc.account.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Table(name = "customer")
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class CustomerEntity implements Serializable {

    private Long customerId;
    private UserEntity user;
    private String address;
    private Integer status;
    private String zipCode;
    public Timestamp birthday;
    private Timestamp createdDate;
    private Timestamp lastLogin;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private OutletEntity outlet;
    private Set<CustomerAddressEntity> customerAddresses;
    Set<LoyaltyMemberEntity> loyaltyMembers;

    public CustomerEntity() {
    }

    @Column(name = "zipcode")
    @Basic
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public CustomerEntity(Long customerId) {
        this.customerId = customerId;
    }

    @Id
    @Column(name = "customerid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Column(name = "address")
    @Basic
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "birthday")
    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    @Column(name = "createddate")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @ManyToOne()
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Column(name = "lastlogin")
    @Basic
    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defaultOutlet", referencedColumnName = "outletId")
    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @OrderBy("createdDate desc")
    public Set<CustomerAddressEntity> getCustomerAddresses() {
        return customerAddresses;
    }

    public void setCustomerAddresses(Set<CustomerAddressEntity> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @OrderBy("joinDate desc")
    public Set<LoyaltyMemberEntity> getLoyaltyMembers() {
        return loyaltyMembers;
    }

    public void setLoyaltyMembers(Set<LoyaltyMemberEntity> loyaltyMembers) {
        this.loyaltyMembers = loyaltyMembers;
    }
}

