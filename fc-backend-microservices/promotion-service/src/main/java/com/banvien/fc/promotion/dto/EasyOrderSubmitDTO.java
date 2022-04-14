package com.banvien.fc.promotion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EasyOrderSubmitDTO implements Serializable {
    private Long userId;
    private Long outletId;
    private Long easyOrderId;
    private String name;
    private String phone;
    private String email;
    private String note;
    private String address;
    private String paymentMethod;
    private String deliveryMethod;
    private List<ListProductDTO> products;
    private Integer action;
    private String promotionCode;
    private String easyOrderCode;
    private Boolean fromEasyOrder;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public Long getEasyOrderId() {
        return easyOrderId;
    }

    public void setEasyOrderId(Long easyOrderId) {
        this.easyOrderId = easyOrderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public List<ListProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ListProductDTO> products) {
        this.products = products;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getEasyOrderCode() {
        return easyOrderCode;
    }

    public void setEasyOrderCode(String easyOrderCode) {
        this.easyOrderCode = easyOrderCode;
    }

    public Boolean getFromEasyOrder() {
        return fromEasyOrder;
    }

    public void setFromEasyOrder(Boolean fromEasyOrder) {
        this.fromEasyOrder = fromEasyOrder;
    }
}

