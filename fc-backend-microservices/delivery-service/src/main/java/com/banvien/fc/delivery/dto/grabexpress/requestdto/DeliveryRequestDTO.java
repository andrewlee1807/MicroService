package com.banvien.fc.delivery.dto.grabexpress.requestdto;

import com.banvien.fc.delivery.dto.grabexpress.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestDTO implements Serializable {
    private Long outletId;
    private String merchantOrderID;
    private String serviceType;
    private String paymentMethod;
    private List<PackagesDTO> packages;
    private CashOnDeliveryDTO cashOnDelivery;
    private SenderDTO sender;
    private RecipientDTO recipient;
    private OriginDTO origin;
    private DestinationDTO destination;
    private ScheduleDTO schedule;
}
