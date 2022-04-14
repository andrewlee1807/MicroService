package com.banvien.fc.payment.dto.grabpay;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class OrderInformationMobileDTO implements Serializable {
    private String codeVerifier;
    private Long userId;
    private Long orderInformationId;
    private Long outletId;
    private String receiverAddress;
    private String note;
    private String receiverName;
    private String receiverPhone;
    private String deliveryMethod;
    private String deliveryMethodTitle;
    private String payment;
    private String paymentTitle;
    private Long customerId;
    private String device;
    private Double receiverLat;
    private Double receiverLng;
    private Integer usedPoint;
    private Double amountAfterUsePoint;
    private Boolean isOrderOnBeHalf;
    private Long salesManUserId;
    private Boolean isConfirmed;
    private Timestamp deliveryDate;
    private Timestamp createDate;
    private List<Long> customerRewards; // reward gift
    private String paymentDueDate; // just return simple date
    private String currency;
    private String transactionCode;
    private List<ProductAndQuantityMobileDTO> listProductAndQuantity;
    private String grabTxId;
    private String grabGroupTxId;
    private String token;
    private String partnerId;
    private String partnerSecret;
    private String clientId;
    private String clientSecret;
    private String orderOutletId;
    private String promotionCode;
    private String state;
    private String orderOutletCode;
    private String source;

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getPaymentTitle() {
        return paymentTitle;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public String getDeliveryMethodTitle() {
        return deliveryMethodTitle;
    }

    public void setDeliveryMethodTitle(String deliveryMethodTitle) {
        this.deliveryMethodTitle = deliveryMethodTitle;
    }

    public Double getReceiverLat() {
        return receiverLat;
    }

    public void setReceiverLat(Double receiverLat) {
        this.receiverLat = receiverLat;
    }

    public Double getReceiverLng() {
        return receiverLng;
    }

    public void setReceiverLng(Double receiverLng) {
        this.receiverLng = receiverLng;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Long getOrderInformationId() {
        return orderInformationId;
    }

    public void setOrderInformationId(Long orderInformationId) {
        this.orderInformationId = orderInformationId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Boolean getOrderOnBeHalf() {
        return isOrderOnBeHalf;
    }

    public void setOrderOnBeHalf(Boolean orderOnBeHalf) {
        isOrderOnBeHalf = orderOnBeHalf;
    }

    public Long getSalesManUserId() {
        return salesManUserId;
    }

    public void setSalesManUserId(Long salesManUserId) {
        this.salesManUserId = salesManUserId;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public List<Long> getCustomerRewards() {
        return customerRewards;
    }

    public void setCustomerRewards(List<Long> customerRewards) {
        this.customerRewards = customerRewards;
    }

    public String getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(String paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<ProductAndQuantityMobileDTO> getListProductAndQuantity() {
        return listProductAndQuantity;
    }

    public void setListProductAndQuantity(List<ProductAndQuantityMobileDTO> listProductAndQuantity) {
        this.listProductAndQuantity = listProductAndQuantity;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getCodeVerifier() {
        return codeVerifier;
    }

    public void setCodeVerifier(String codeVerifier) {
        this.codeVerifier = codeVerifier;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGrabTxId() {
        return grabTxId;
    }

    public void setGrabTxId(String grabTxId) {
        this.grabTxId = grabTxId;
    }

    public String getGrabGroupTxId() {
        return grabGroupTxId;
    }

    public void setGrabGroupTxId(String grabGroupTxId) {
        this.grabGroupTxId = grabGroupTxId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerSecret() {
        return partnerSecret;
    }

    public void setPartnerSecret(String partnerSecret) {
        this.partnerSecret = partnerSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getOrderOutletId() {
        return orderOutletId;
    }

    public void setOrderOutletId(String orderOutletId) {
        this.orderOutletId = orderOutletId;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderOutletCode() {
        return orderOutletCode;
    }

    public void setOrderOutletCode(String orderOutletCode) {
        this.orderOutletCode = orderOutletCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

