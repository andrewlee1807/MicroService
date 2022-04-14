package com.banvien.fc.order.dto;

import java.io.Serializable;

public class SubmitOrderResponseDTO implements Serializable {
    private Boolean isSuccess;
    private Long orderId;
    private String responseMessageKey; // used to localize
    private String description;        // short description for message key;
    private String warningType;


    public SubmitOrderResponseDTO() {

    }

    public SubmitOrderResponseDTO(Boolean isSuccess, String responseMessageKey, String description) {
        this.isSuccess = isSuccess;
        this.responseMessageKey = responseMessageKey;
        this.description = description;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getResponseMessageKey() {
        return responseMessageKey;
    }

    public void setResponseMessageKey(String responseMessageKey) {
        this.responseMessageKey = responseMessageKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }
}
