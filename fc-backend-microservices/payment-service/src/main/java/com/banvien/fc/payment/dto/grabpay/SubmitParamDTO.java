package com.banvien.fc.payment.dto.grabpay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubmitParamDTO {
    private String receiverName;
    private Long outletId;
    private String outletName;
    private String note;
    private String receiverPhone;
    private String deliveryMethod;
    private String payment;
    private Integer usedPoint;
    private Double amountAfterUsePoint;
    private List<ProductAndQuantityMobileDTO> items;
    private String source;
    private String receiverAddress;
    private Double receiverLat;
    private Double receiverLng;
}
