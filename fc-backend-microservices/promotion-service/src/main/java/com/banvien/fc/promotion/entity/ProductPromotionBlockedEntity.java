package com.banvien.fc.promotion.entity;

import javax.persistence.*;

@Entity
@Table(name = "productpromotionblocked")
public class ProductPromotionBlockedEntity {
    private Long productblockedId;
    private Long productOutletSkuId;
    private Long outletId;
    private Long productId;

    @Id
    @Column(name = "productpromotionblockedid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getProductblockedId() {
        return productblockedId;
    }

    public void setProductblockedId(Long productblockedId) {
        this.productblockedId = productblockedId;
    }

    @Column(name = "productoutletskuid")
    @Basic
    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    @Column(name = "outletid")
    @Basic
    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    @Column(name = "productid")
    @Basic
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
