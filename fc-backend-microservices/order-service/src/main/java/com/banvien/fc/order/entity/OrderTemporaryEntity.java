package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ordertemporary")
public class OrderTemporaryEntity {
    private Long orderTemporaryId;
    private Long customerId;
    private Double amount;
    private String orderOutletCode;
    private String status;
    private Long createdBy;
    private Timestamp createDate;
    private String  promotionCode;
    private String  receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private Double receiverLat;
    private Double receiverLng;
    private String deliveryMethod;
    private Long salesManUserId;
    private Integer usedPoint;
    private Double amountAfterUsePoint;
    private String payment;
    private String note;
    private Timestamp deliveryDate;
    private String grabTxId;
    private String grabGroupTxId;
    private String token;
    private Boolean isOrderOnBeHalf;
    private Boolean isConfirmed;
    private String state;
    private Long outletId;
    private String paymentStatus;
    private String fromSource;
    private String orderDetail;
    private Long orderId;

    @Id
    @Column(name = "ordertemporaryid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOrderTemporaryId() {
        return orderTemporaryId;
    }

    public void setOrderTemporaryId(Long orderTemporaryId) {
        this.orderTemporaryId = orderTemporaryId;
    }

    @Basic
    @Column(name = "customerid")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "orderoutletcode")
    public String getOrderOutletCode() {
        return orderOutletCode;
    }

    public void setOrderOutletCode(String orderOutletCode) {
        this.orderOutletCode = orderOutletCode;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "createdby")
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "createdate")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "promotioncode")
    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    @Basic
    @Column(name = "receivername")
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Basic
    @Column(name = "receiverphone")
    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    @Basic
    @Column(name = "receiveraddress")
    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    @Basic
    @Column(name = "receiverlat")
    public Double getReceiverLat() {
        return receiverLat;
    }

    public void setReceiverLat(Double receiverLat) {
        this.receiverLat = receiverLat;
    }

    @Basic
    @Column(name = "receiverlng")
    public Double getReceiverLng() {
        return receiverLng;
    }

    public void setReceiverLng(Double receiverLng) {
        this.receiverLng = receiverLng;
    }

    @Basic
    @Column(name = "deliverymethod")
    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    @Basic
    @Column(name = "salesmanuserid")
    public Long getSalesManUserId() {
        return salesManUserId;
    }

    public void setSalesManUserId(Long salesManUserId) {
        this.salesManUserId = salesManUserId;
    }

    @Basic
    @Column(name = "usedpoint")
    public Integer getUsedPoint() {
        return usedPoint;
    }

    public void setUsedPoint(Integer usedPoint) {
        this.usedPoint = usedPoint;
    }

    @Basic
    @Column(name = "amountafterusepoint")
    public Double getAmountAfterUsePoint() {
        return amountAfterUsePoint;
    }

    public void setAmountAfterUsePoint(Double amountAfterUsePoint) {
        this.amountAfterUsePoint = amountAfterUsePoint;
    }

    @Basic
    @Column(name = "payment")
    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "deliverydate")
    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Basic
    @Column(name = "grabtxid")
    public String getGrabTxId() {
        return grabTxId;
    }

    public void setGrabTxId(String grabTxId) {
        this.grabTxId = grabTxId;
    }

    @Basic
    @Column(name = "grabgrouptxid")
    public String getGrabGroupTxId() {
        return grabGroupTxId;
    }

    public void setGrabGroupTxId(String grabGroupTxId) {
        this.grabGroupTxId = grabGroupTxId;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "isorderonbehalf")
    public Boolean getOrderOnBeHalf() {
        return isOrderOnBeHalf;
    }

    public void setOrderOnBeHalf(Boolean orderOnBeHalf) {
        isOrderOnBeHalf = orderOnBeHalf;
    }

    @Basic
    @Column(name = "isconfirmed")
    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "outletid")
    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    @Basic
    @Column(name = "paymentstatus")
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentMethod) {
        this.paymentStatus = paymentMethod;
    }

    @Basic
    @Column(name = "fromsource")
    public String getFromSource() {
        return fromSource;
    }

    public void setFromSource(String fromSource) {
        this.fromSource = fromSource;
    }

    @Basic
    @Column(name = "orderdetail")
    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Basic
    @Column(name = "orderid")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}



