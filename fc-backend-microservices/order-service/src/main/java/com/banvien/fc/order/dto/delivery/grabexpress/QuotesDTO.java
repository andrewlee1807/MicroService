package com.banvien.fc.order.dto.delivery.grabexpress;

import java.io.Serializable;
import java.util.List;

public class QuotesDTO implements Serializable {
    private ServiceDTO service;
    private CurrencyDTO currency;
    private Float amount;
    private EstimatedTimelineDTO estimatedTimeline;
    private Integer distance;
    private List<PackagesDTO> packages;
    private OriginDTO origin;
    private DestinationDTO destination;

    public ServiceDTO getService() {
        return service;
    }

    public void setService(ServiceDTO service) {
        this.service = service;
    }

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public EstimatedTimelineDTO getEstimatedTimeline() {
        return estimatedTimeline;
    }

    public void setEstimatedTimeline(EstimatedTimelineDTO estimatedTimeline) {
        this.estimatedTimeline = estimatedTimeline;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public List<PackagesDTO> getPackages() {
        return packages;
    }

    public void setPackages(List<PackagesDTO> packages) {
        this.packages = packages;
    }

    public OriginDTO getOrigin() {
        return origin;
    }

    public void setOrigin(OriginDTO origin) {
        this.origin = origin;
    }

    public DestinationDTO getDestination() {
        return destination;
    }

    public void setDestination(DestinationDTO destination) {
        this.destination = destination;
    }
}
