package com.banvien.fc.promotion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDTO {
    private Long countryId;
    private String code;
    private String name;
    private Integer displayOrder;

    public CountryDTO() {
    }

    public Long getCountryId() { return this.countryId; }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getCode() { return this.code; }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDisplayOrder() {
        return this.displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

}
