package com.banvien.fc.delivery.dto.grabexpress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.boot.jaxb.Origin;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuotesDTO implements Serializable {

    private ServiceDTO service;
    private CurrencyDTO currency;
    private Float amount;
    private EstimatedTimelineDTO estimatedTimeline;
    private Integer distance;
    private List<PackagesDTO> packages;
    private OriginDTO origin;
    private DestinationDTO destination;
}
