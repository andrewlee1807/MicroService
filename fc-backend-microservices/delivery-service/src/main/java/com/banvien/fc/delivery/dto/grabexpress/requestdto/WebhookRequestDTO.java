package com.banvien.fc.delivery.dto.grabexpress.requestdto;

import com.banvien.fc.delivery.dto.grabexpress.DriverDTO;
import com.banvien.fc.delivery.dto.grabexpress.RecipientDTO;
import com.banvien.fc.delivery.dto.grabexpress.SenderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WebhookRequestDTO implements Serializable {

    private String deliveryID;
    private String merchantOrderID;
    private Integer timestamp;
    private String status;
    private String trackURL;
    private String pickupPin;
    private String failedReason;
    private SenderDTO sender;
    private RecipientDTO recipient;
    private DriverDTO driver;
}
