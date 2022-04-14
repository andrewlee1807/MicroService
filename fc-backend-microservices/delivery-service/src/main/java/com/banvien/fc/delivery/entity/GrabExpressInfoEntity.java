package com.banvien.fc.delivery.entity;

import javax.persistence.*;

@Table(name = "GrabExpressInfo")
@Entity
public class GrabExpressInfoEntity {
    private Long grabexpressinfoid;
    private String jsonresponse;
    private Integer status;
    private String note;
    private String deliveryid;
    private String ordermerchantid;
    private Double destinationlatitude;
    private Double destinationlongitude;

    @Id
    @Column(name = "grabexpressinfoid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getGrabexpressinfoid() {
        return grabexpressinfoid;
    }

    public void setGrabexpressinfoid(Long grabexpressinfoid) {
        this.grabexpressinfoid = grabexpressinfoid;
    }

    @Basic
    @Column(name = "jsonresponse")
    public String getJsonresponse() {
        return jsonresponse;
    }

    public void setJsonresponse(String jsonresponse) {
        this.jsonresponse = jsonresponse;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    @Column(name = "deliveryid")
    public String getDeliveryid() {
        return deliveryid;
    }

    public void setDeliveryid(String deliveryid) {
        this.deliveryid = deliveryid;
    }

    @Basic
    @Column(name = "ordermerchantid")
    public String getOrdermerchantid() {
        return ordermerchantid;
    }

    public void setOrdermerchantid(String ordermerchantid) {
        this.ordermerchantid = ordermerchantid;
    }

    @Basic
    @Column(name = "destinationlatitude")
    public Double getDestinationlatitude() {
        return destinationlatitude;
    }

    public void setDestinationlatitude(Double destinationlatitude) {
        this.destinationlatitude = destinationlatitude;
    }
    @Basic
    @Column(name = "destinationlongitude")
    public Double getDestinationlongitude() {
        return destinationlongitude;
    }

    public void setDestinationlongitude(Double destinationlongitude) {
        this.destinationlongitude = destinationlongitude;
    }
}

