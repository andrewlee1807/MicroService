package com.banvien.fc.delivery.dto.grabexpress.responsedto;

import com.banvien.fc.delivery.dto.grabexpress.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponseDTO {
    private String deliveryID;
    private String merchantOrderID;
    private String paymentMethod;
    private String status;
    private String trackingURL;
    private CourierDTO courier;
    private Object timeline;
    private ScheduleDTO schedule;
    private CashOnDeliveryDTO cashOnDelivery;
    private String invoiceNo;
    private String pickupPin;
    private String advanceInfo;
    private SenderDTO sender;
    private RecipientDTO recipient;
    private QuotesDTO quote;
}
