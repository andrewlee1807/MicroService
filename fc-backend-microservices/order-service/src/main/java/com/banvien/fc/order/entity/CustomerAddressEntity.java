package com.banvien.fc.order.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Table(name = "customeraddress")
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class CustomerAddressEntity {
    private Long customerAddressId;
    private CustomerEntity customer;
    private String address;
    private String addressName;
    private String type;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Boolean isDefault;

    public CustomerAddressEntity() {
    }

    public CustomerAddressEntity(Long customerAddressId, String address, String addressName, BigDecimal longitude, BigDecimal latitude, String type, Boolean isDefault) {
        this.customerAddressId = customerAddressId;
        this.address = address;
        this.addressName = addressName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
        this.isDefault = isDefault;
    }

    @Id
    @Column(name = "customeraddressid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCustomerAddressId() {
        return customerAddressId;
    }

    public void setCustomerAddressId(Long customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    @ManyToOne
    @JoinColumn(name = "customer", referencedColumnName = "customerid")
    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    @Column(name = "address")
    @Basic
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "addressname")
    @Basic
    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
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

    @Column(name = "createdDate")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "modifiedDate")
    @Basic
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Column(name = "type")
    @Basic
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "isDefault")
    @Basic
    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
}
