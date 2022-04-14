package com.banvien.fc.catalog.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "RawDistributor")
@Entity
public class RawDistributorEntity {
    @Id
    @Column(name = "RawDistributorId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String distributorCode;
    private String distributorName;
    private String distributorPhone;
    private String distributorAddress;
    private Double longitude;
    private Double latitude;
    private Integer status;
    private Timestamp createdDate;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public void setDistributorPhone(String distributorPhone) {
        this.distributorPhone = distributorPhone;
    }

    public void setDistributorAddress(String distributorAddress) {
        this.distributorAddress = distributorAddress;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
