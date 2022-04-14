package com.banvien.fc.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EasyOrderItemDTO implements Serializable {

    private Long easyOrderItemId;
    private EasyOrderDTO easyOrderDTO;
    private ProductOutletSkuDTO productOutletSkuDTO;

    public EasyOrderItemDTO() {
    }

    public Long getEasyOrderItemId() {
        return easyOrderItemId;
    }

    public void setEasyOrderItemId(Long easyOrderItemId) {
        this.easyOrderItemId = easyOrderItemId;
    }

    public EasyOrderDTO getEasyOrderDTO() {
        return easyOrderDTO;
    }

    public void setEasyOrderDTO(EasyOrderDTO easyOrderDTO) {
        this.easyOrderDTO = easyOrderDTO;
    }

    public ProductOutletSkuDTO getProductOutletSkuDTO() {
        return productOutletSkuDTO;
    }

    public void setProductOutletSkuDTO(ProductOutletSkuDTO productOutletSkuDTO) {
        this.productOutletSkuDTO = productOutletSkuDTO;
    }
}
