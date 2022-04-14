package com.banvien.fc.payment.dto.grabpay;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonRawValue;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WebHookDTO {
    private String txType;
    private String partnerTxID;
    private String txID;
    private String origTxID;
    private Long amount;
    private String currency;
    private String status;
    //private Enum paymentMethod;
    private Long completedAt;
    private Long createdAt;
//    @JsonRawValue
//    private String payload;
    private String txStatus;
    private String reason;
}
