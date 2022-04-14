package com.banvien.fc.account.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "RawStore")
@Entity
public class RawStoreEntity {
    @Id
    @Column(name = "RawStoreId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String storeCode;
    private String storeName;
    private String companyStoreCode;
    private String birthday;
    private String countryCode;
    private String email;
    private String address;
    private String customerGroup;
    private Integer status;
    private String salesmanCode;
    private Double latitude;
    private Double longitude;
    private String distributorCode;
    private String phoneNumber;
    private Timestamp createdDate;

    public void setId(Long id) {
        this.id = id;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setCompanyStoreCode(String companyStoreCode) {
        this.companyStoreCode = companyStoreCode;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
