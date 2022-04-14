package com.banvien.fc.order.dto.delivery;

import java.io.Serializable;
import java.util.List;

public class ServiceItemDTO implements Serializable {
    private Long deliveryServiceId;
    private String code;
    private String title;
    private Boolean status;
    private Integer orderBy;
    private List<DeliveryPriceOnDistanceDTO> prices;
    private Double maxDistance;
    private Integer estimateDays;
    private String message;

    public Long getDeliveryServiceId() {
        return deliveryServiceId;
    }

    public void setDeliveryServiceId(Long deliveryServiceId) {
        this.deliveryServiceId = deliveryServiceId;
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

    public List<DeliveryPriceOnDistanceDTO> getPrices() {
        return prices;
    }

    public void setPrices(List<DeliveryPriceOnDistanceDTO> prices) {
        this.prices = prices;
    }

    public Double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Integer getEstimateDays() {
        return estimateDays;
    }

    public void setEstimateDays(Integer estimateDays) {
        this.estimateDays = estimateDays;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
