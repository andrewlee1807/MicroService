package com.banvien.fc.payment.dto.grabpay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseChargeCompleteDTO {
    private String msgID;
    private String txID;
    private String status;
    private String description;
    private String txStatus;
    private String reason;
}
