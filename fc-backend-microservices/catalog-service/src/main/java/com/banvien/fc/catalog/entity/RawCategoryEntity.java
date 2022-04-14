package com.banvien.fc.catalog.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "RawCategory")
@Entity
public class RawCategoryEntity {
    @Id
    @Column(name = "RawCategoryId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryCode;
    private String categoryName;
    private String description;
    private Timestamp createdDate;

    public RawCategoryEntity(String categoryCode, String categoryName, String description, Timestamp createdDate) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.description = description;
        this.createdDate = createdDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
