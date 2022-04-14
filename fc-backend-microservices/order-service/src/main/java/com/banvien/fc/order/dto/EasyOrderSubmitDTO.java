package com.banvien.fc.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EasyOrderSubmitDTO implements Serializable {
    private Long userId;
    private Long customerId;
    private Long salesManUserId;
    private Long outletId;
    private Long easyOrderId;
    private Long warehouseId;
    private String name;
    private String phone;
    private String postCode;
    private String email;
    private String note;
    private String address;
    private String paymentMethod;
    private String deliveryMethod;
    private Double deliveryFee;
    private List<ProductDTO> products;
    private Integer action;
    private String promotionCode;
    private String prefixPhoneNumber;
    private String easyOrderCode;
    private boolean isCredit;
    private Double debt;
    private String source;
    private String orderCode;
    private Boolean fromEasyOrder;
    private Double totalStoreDiscountPrice; // store discount from POS
    // Grab refund
    private String txId;
    private String token;
    private String grabGroupTxId;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public boolean isCredit() {
        return isCredit;
    }

    public void setCredit(boolean credit) {
        isCredit = credit;
    }

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

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public Long getEasyOrderId() {
        return easyOrderId;
    }

    public void setEasyOrderId(Long easyOrderId) {
        this.easyOrderId = easyOrderId;
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

    public String getPrefixPhoneNumber() {
        return prefixPhoneNumber;
    }

    public void setPrefixPhoneNumber(String prefixPhoneNumber) {
        this.prefixPhoneNumber = prefixPhoneNumber;
    }

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getEasyOrderCode() {
        return easyOrderCode;
    }

    public void setEasyOrderCode(String easyOrderCode) {
        this.easyOrderCode = easyOrderCode;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Boolean getFromEasyOrder() {
        return fromEasyOrder;
    }

    public void setFromEasyOrder(Boolean fromEasyOrder) {
        this.fromEasyOrder = fromEasyOrder;
    }

    public Double getTotalStoreDiscountPrice() {
        return totalStoreDiscountPrice;
    }

    public void setTotalStoreDiscountPrice(Double totalStoreDiscountPrice) {
        this.totalStoreDiscountPrice = totalStoreDiscountPrice;
    }

    public Long getSalesManUserId() {
        return salesManUserId;
    }

    public void setSalesManUserId(Long salesManUserId) {
        this.salesManUserId = salesManUserId;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGrabGroupTxId() {
        return grabGroupTxId;
    }

    public void setGrabGroupTxId(String grabGroupTxId) {
        this.grabGroupTxId = grabGroupTxId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}

