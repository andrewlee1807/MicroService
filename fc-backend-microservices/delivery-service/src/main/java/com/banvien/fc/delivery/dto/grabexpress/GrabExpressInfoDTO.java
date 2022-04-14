package com.banvien.fc.delivery.dto.grabexpress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GrabExpressInfoDTO {
    private Long grabexpressinfoid;
    private String jsonresponse;
    private Integer status;
    private String note;
    private String deliveryid;
    private String ordermerchantid;
    private Double destinationlatitude;
    private Double destinationlongitude;
}
