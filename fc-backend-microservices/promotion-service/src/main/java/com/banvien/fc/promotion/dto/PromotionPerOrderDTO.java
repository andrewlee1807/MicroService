package com.banvien.fc.promotion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionPerOrderDTO {
    private List<ReductionDTO> reductionDTOS;
    private Double totalDiscountValue;
    private Boolean invalidCode;            // easyOrderCode or nextPurchase

    public PromotionPerOrderDTO() {
    }

    public PromotionPerOrderDTO(List<ReductionDTO> reductionDTOS, Double totalDiscountValue) {
        this.reductionDTOS = reductionDTOS;
        this.totalDiscountValue = totalDiscountValue;
    }

    public PromotionPerOrderDTO(List<ReductionDTO> reductionDTOS, Double totalDiscountValue, Boolean invalidCode) {
        this.reductionDTOS = reductionDTOS;
        this.totalDiscountValue = totalDiscountValue;
        this.invalidCode = invalidCode;
    }

    public List<ReductionDTO> getReductionDTOS() {
        return reductionDTOS;
    }

    public void setReductionDTOS(List<ReductionDTO> reductionDTOS) {
        this.reductionDTOS = reductionDTOS;
    }

    public Double getTotalDiscountValue() {
        return totalDiscountValue;
    }

    public void setTotalDiscountValue(Double totalDiscountValue) {
        this.totalDiscountValue = totalDiscountValue;
    }

    public Boolean getInvalidCode() {
        return invalidCode;
    }

    public void setInvalidCode(Boolean invalidCode) {
        this.invalidCode = invalidCode;
    }
}
