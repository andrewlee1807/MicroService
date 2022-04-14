package com.banvien.fc.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RawProductDTO {
    public String CategoryCode;
    public String CategoryName;
    public String BrandName;
    public String ProductCode;
    public String ProductName;
    public String UnitOfMeasure;
    public String BarCode;
    public Double CostPrice;
    public Double SalesPrice;
    public Integer Status;
    public Integer UnitsInCarton;
    public Integer UnitsInVirtualPack;
    public Double PricePerCarton;

    public String getCategoryCode() {
        return CategoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        CategoryCode = categoryCode;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getUnitOfMeasure() {
        return UnitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        UnitOfMeasure = unitOfMeasure;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public Double getCostPrice() {
        return CostPrice;
    }

    public void setCostPrice(Double costPrice) {
        CostPrice = costPrice;
    }

    public Double getSalesPrice() {
        return SalesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        SalesPrice = salesPrice;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer getUnitsInCarton() {
        return UnitsInCarton;
    }

    public void setUnitsInCarton(Integer unitsInCarton) {
        UnitsInCarton = unitsInCarton;
    }

    public Integer getUnitsInVirtualPack() {
        return UnitsInVirtualPack;
    }

    public void setUnitsInVirtualPack(Integer unitsInVirtualPack) {
        UnitsInVirtualPack = unitsInVirtualPack;
    }

    public Double getPricePerCarton() {
        return PricePerCarton;
    }

    public void setPricePerCarton(Double pricePerCarton) {
        PricePerCarton = pricePerCarton;
    }
}
