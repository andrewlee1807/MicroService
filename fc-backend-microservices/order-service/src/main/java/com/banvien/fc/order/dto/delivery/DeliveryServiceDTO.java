package com.banvien.fc.order.dto.delivery;


import com.banvien.fc.order.dto.OutletDTO;

import java.io.Serializable;

public class DeliveryServiceDTO implements Serializable {

    private Long deliveryServiceId;
    private OutletDTO outlet;
    private String groupCode;
    private String code;
    private String title;
    private Double price;
    private Boolean status;
    private Integer orderBy;
    private Double distance;
    private Double distanceService;
    private Integer estimateDays;

    public DeliveryServiceDTO() {
    }

    public DeliveryServiceDTO(String code, String title, Double price, Boolean status) {
        this.code = code;
        this.title = title;
        this.price = price;
        this.status = status;
    }

    public Long getDeliveryServiceId() {
        return deliveryServiceId;
    }

    public void setDeliveryServiceId(Long deliveryServiceId) {
        this.deliveryServiceId = deliveryServiceId;
    }

    public OutletDTO getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletDTO outlet) {
        this.outlet = outlet;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupTitle) {
        this.groupCode = groupTitle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDistanceService() {
        return distanceService;
    }

    public void setDistanceService(Double distanceService) {
        this.distanceService = distanceService;
    }

    public Integer getEstimateDays() {
        return estimateDays;
    }

    public void setEstimateDays(Integer estimateDays) {
        this.estimateDays = estimateDays;
    }
}
