package com.banvien.fc.payment.dto.grabpay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompletedBodyEasyOrderDTO {
    private String orderCode;
    private String txId;
    private String token;
    private String paymentStatus;
}
