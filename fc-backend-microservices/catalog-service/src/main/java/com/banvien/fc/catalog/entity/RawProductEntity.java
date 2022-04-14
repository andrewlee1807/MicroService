package com.banvien.fc.catalog.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "RawProduct")
@Entity
public class RawProductEntity {
    @Id
    @Column(name = "RawProductId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryCode;
    private String categoryName;
    private String brandName;
    private String productCode;
    private String productName;
    private String unitOfMeasure;
    private String barCode;
    private Double costPrice;
    private Double salesPrice;
    private Double pricePerCarton;
    private Integer unitsInCarton;
    private Integer unitsInVirtualPack;
    private Integer status;
    private Timestamp createdDate;

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public void setPricePerCarton(Double pricePerCarton) {
        this.pricePerCarton = pricePerCarton;
    }

    public void setUnitsInCarton(Integer unitsInCarton) {
        this.unitsInCarton = unitsInCarton;
    }

    public void setUnitsInVirtualPack(Integer unitsInVirtualPack) {
        this.unitsInVirtualPack = unitsInVirtualPack;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
