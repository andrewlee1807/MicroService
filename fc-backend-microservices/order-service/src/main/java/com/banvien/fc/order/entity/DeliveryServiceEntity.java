package com.banvien.fc.order.entity;

import javax.persistence.*;

@Entity
@Table(name = "deliveryservice")
public class DeliveryServiceEntity {
    private long deliveryServiceId;
    private long outletId;
    private String groupCode;
    private String code;
    private String title;
    private Double price;
    private boolean status;
    private Double distance;
    private int orderBy;
    private Integer estimateDays;

    @Id
    @Column(name = "deliveryserviceid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getDeliveryServiceId() {
        return deliveryServiceId;
    }

    public void setDeliveryServiceId(long deliveryServiceId) {
        this.deliveryServiceId = deliveryServiceId;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getEstimateDays() {
        return estimateDays;
    }

    public void setEstimateDays(Integer estimateDays) {
        this.estimateDays = estimateDays;
    }

    @Basic
    @Column(name = "outletid")
    public long getOutletId() {
        return outletId;
    }

    public void setOutletId(long outletId) {
        this.outletId = outletId;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "status")
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Basic
    @Column(name = "distance")
    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Basic
    @Column(name = "groupcode")
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}
