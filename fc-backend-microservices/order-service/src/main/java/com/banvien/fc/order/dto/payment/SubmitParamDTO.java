package com.banvien.fc.order.dto.payment;

import com.banvien.fc.order.dto.ProductAndQuantityMobileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitParamDTO {
    private String receiverName;
    private Long outletId;
    private String outletName;
    private String note;
    private String receiverPhone;
    private String deliveryMethod;
    private String payment;
    private Integer usedPoint;
    private Double amountAfterUsePoint;
    private List<ProductAndQuantityMobileDTO> items;

    public SubmitParamDTO() {
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Integer getUsedPoint() {
        return usedPoint;
    }

    public void setUsedPoint(Integer usedPoint) {
        this.usedPoint = usedPoint;
    }

    public Double getAmountAfterUsePoint() {
        return amountAfterUsePoint;
    }

    public void setAmountAfterUsePoint(Double amountAfterUsePoint) {
        this.amountAfterUsePoint = amountAfterUsePoint;
    }

    public List<ProductAndQuantityMobileDTO> getItems() {
        return items;
    }

    public void setItems(List<ProductAndQuantityMobileDTO> items) {
        this.items = items;
    }
}
