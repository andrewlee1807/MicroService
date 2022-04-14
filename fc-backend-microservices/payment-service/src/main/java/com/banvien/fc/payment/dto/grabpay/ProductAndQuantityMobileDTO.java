package com.banvien.fc.payment.dto.grabpay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAndQuantityMobileDTO {
    private Integer productOutletSkuId;
    private Integer quantity;
    private Integer productOutletId;
    private Long shoppingCartId;
}
