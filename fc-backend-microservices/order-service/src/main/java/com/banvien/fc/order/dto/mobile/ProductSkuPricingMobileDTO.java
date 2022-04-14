package com.banvien.fc.order.dto.mobile;

import java.io.Serializable;

public class ProductSkuPricingMobileDTO implements Serializable {

    private Long pricingId;
    private Long pricingSkuId;
    private Long productOutletSkuId;
    private Long offerPriceId;
    private String name;
    private Double salePrice;

    public Long getPricingId() {
        return pricingId;
    }

    public void setPricingId(Long pricingId) {
        this.pricingId = pricingId;
    }

    public Long getPricingSkuId() {
        return pricingSkuId;
    }

    public void setPricingSkuId(Long pricingSkuId) {
        this.pricingSkuId = pricingSkuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    public Long getOfferPriceId() {
        return offerPriceId;
    }

    public void setOfferPriceId(Long offerPriceId) {
        this.offerPriceId = offerPriceId;
    }
}