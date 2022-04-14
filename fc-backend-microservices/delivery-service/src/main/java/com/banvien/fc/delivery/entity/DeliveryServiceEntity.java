package com.banvien.fc.delivery.entity;

import javax.persistence.*;

@Table(name = "deliveryservice")
@Entity
public class DeliveryServiceEntity {

    private Long deliveryServiceId;
    private OutletEntity outlet;
    private String groupCode;
    private String code;
    private String title;
    private Double price;
    private Boolean status;
    private Integer orderBy;
    private Double distance;
    private Integer estimateDays;
    private String clientId;
    private String clientSecret;

    @Id
    @Column(name = "deliveryserviceid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getDeliveryServiceId() {
        return deliveryServiceId;
    }

    public void setDeliveryServiceId(Long deliveryServiceId) {
        this.deliveryServiceId = deliveryServiceId;
    }

    @ManyToOne()
    @JoinColumn(name = "outletid", referencedColumnName = "outletid")
    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    @Basic
    @Column(name = "groupcode")
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupTitle) {
        this.groupCode = groupTitle;
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
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Basic
    @Column(name = "orderby")
    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
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
    @Column(name = "estimatedays")
    public Integer getEstimateDays() {
        return estimateDays;
    }

    public void setEstimateDays(Integer estimateDays) {
        this.estimateDays = estimateDays;
    }

    @Basic
    @Column(name = "clientid")
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "clientsecret")
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
