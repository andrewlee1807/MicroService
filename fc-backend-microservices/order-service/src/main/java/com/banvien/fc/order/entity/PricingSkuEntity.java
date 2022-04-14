package com.banvien.fc.order.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pricingsku")
public class PricingSkuEntity implements Serializable {
    private Long pricingSkuId;
    private Double salePrice;
    private Long pricingId;
    private Long productOutletSkuId;

    @Id
    @Column(name = "pricingskuid")
    public Long getPricingSkuId() {
        return pricingSkuId;
    }

    public void setPricingSkuId(Long pricingSkuId) {
        this.pricingSkuId = pricingSkuId;
    }

    @Column(name = "saleprice")
    @Basic
    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    @Column(name = "pricingid")
    @Basic
    public Long getPricingId() {
        return pricingId;
    }

    public void setPricingId(Long pricingId) {
        this.pricingId = pricingId;
    }

    @Column(name = "productoutletskuid")
    @Basic
    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }
}
