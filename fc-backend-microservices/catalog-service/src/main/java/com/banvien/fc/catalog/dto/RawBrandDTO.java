package com.banvien.fc.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RawBrandDTO implements Serializable {
    public String BrandName;
    public Integer BrandPriority;

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public Integer getBrandPriority() {
        return BrandPriority;
    }

    public void setBrandPriority(Integer brandPriority) {
        BrandPriority = brandPriority;
    }
}
