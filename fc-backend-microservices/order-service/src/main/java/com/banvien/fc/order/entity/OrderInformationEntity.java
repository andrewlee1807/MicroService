package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "orderinformation")
@Entity
public class OrderInformationEntity implements Serializable {

    private Long orderInformationId;
    private String receiverName;
    private String receiverPhone;
    private String paymentMethod;
    private String deliveryMethod;
    private String receiverAddress;
    private Double receiverLng;
    private Double receiverLat;

    @Id
    @Column(name = "orderinformationid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOrderInformationId() {
        return orderInformationId;
    }

    public void setOrderInformationId(Long orderInformationId) {
        this.orderInformationId = orderInformationId;
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
    @Column(name = "paymentmethod")
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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
    @Column(name = "receiveraddress")
    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
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
    @Column(name = "receiverlat")
    public Double getReceiverLat() {
        return receiverLat;
    }

    public void setReceiverLat(Double receiverLat) {
        this.receiverLat = receiverLat;
    }
}
