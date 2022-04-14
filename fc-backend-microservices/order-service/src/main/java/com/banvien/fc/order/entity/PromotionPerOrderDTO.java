package com.banvien.fc.order.entity;

import com.banvien.fc.order.dto.ReductionDTO;

import java.util.ArrayList;
import java.util.List;

public class PromotionPerOrderDTO {
    private List<ReductionDTO> reductionDTOS;
    private Double totalDiscountValue;
    private Boolean invalidCode;    //  easyOrderCode or nextPurchase

    public PromotionPerOrderDTO() {
    }

    public PromotionPerOrderDTO(List<ReductionDTO> reductionDTOS, Double totalDiscountValue) {
        this.reductionDTOS = reductionDTOS;
        this.totalDiscountValue = totalDiscountValue;
        if (reductionDTOS == null) {
            this.reductionDTOS = new ArrayList<>();
        }

        if (totalDiscountValue == null) {
            this.totalDiscountValue = 0.0;
        }
    }

    public List<ReductionDTO> getReductionDTOS() {
        if (reductionDTOS == null) {
            this.reductionDTOS = new ArrayList<>();
        }
        return reductionDTOS;
    }

    public void setReductionDTOS(List<ReductionDTO> reductionDTOS) {
        this.reductionDTOS = reductionDTOS;
        if (reductionDTOS == null) {
            this.reductionDTOS = new ArrayList<>();
        }
    }

    public Double getTotalDiscountValue() {
        if (totalDiscountValue == null) {
            this.totalDiscountValue = 0.0;
        }
        return totalDiscountValue;
    }

    public void setTotalDiscountValue(Double totalDiscountValue) {
        this.totalDiscountValue = totalDiscountValue;
        if (totalDiscountValue == null) {
            this.totalDiscountValue = 0.0;
        }
    }

    public Boolean getInvalidCode() {
        return invalidCode;
    }

    public void setInvalidCode(Boolean invalidCode) {
        this.invalidCode = invalidCode;
    }
}
