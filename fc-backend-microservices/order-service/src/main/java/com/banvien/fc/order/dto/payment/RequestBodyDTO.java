package com.banvien.fc.order.dto.payment;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestBodyDTO {
    private String partnerGroupTxID;
    private String partnerTxID;
    private String currency;
    private String amount;
    private String description;
    private String merchantID;
    private String token;
    private String originTxID;
    private SubmitParamDTO submitParams;
    private Long outletId;
    private Long customerId;

    public RequestBodyDTO() {
    }

    public String getPartnerGroupTxID() {
        return partnerGroupTxID;
    }

    public void setPartnerGroupTxID(String partnerGroupTxID) {
        this.partnerGroupTxID = partnerGroupTxID;
    }

    public String getPartnerTxID() {
        return partnerTxID;
    }

    public void setPartnerTxID(String partnerTxID) {
        this.partnerTxID = partnerTxID;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOriginTxID() {
        return originTxID;
    }

    public void setOriginTxID(String originTxID) {
        this.originTxID = originTxID;
    }

    public SubmitParamDTO getSubmitParams() {
        return submitParams;
    }

    public void setSubmitParams(SubmitParamDTO submitParams) {
        this.submitParams = submitParams;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
