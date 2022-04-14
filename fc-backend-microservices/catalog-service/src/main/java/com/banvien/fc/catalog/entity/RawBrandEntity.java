package com.banvien.fc.catalog.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "RawBrand")
@Entity
public class RawBrandEntity {
    @Id
    @Column(name = "RawBrandId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brandName;
    private Integer brandPriority;
    private Timestamp createdDate;

    public RawBrandEntity(String brandName, Integer brandPriority, Timestamp createdDate) {
        this.brandName = brandName;
        this.brandPriority = brandPriority;
        this.createdDate = createdDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setBrandPriority(Integer brandPriority) {
        this.brandPriority = brandPriority;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
