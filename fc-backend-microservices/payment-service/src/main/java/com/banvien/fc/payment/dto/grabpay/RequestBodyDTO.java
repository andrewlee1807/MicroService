package com.banvien.fc.payment.dto.grabpay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestBodyDTO {
    private String partnerGroupTxID;
    private String partnerTxID;
    private String currency;
    private String amount;
    private String description;
    private String merchantID;
    private String token;
    private String originTxID;
    private SubmitParamDTO submitParams;
    private Long outletId;
    private Long customerId;
}
