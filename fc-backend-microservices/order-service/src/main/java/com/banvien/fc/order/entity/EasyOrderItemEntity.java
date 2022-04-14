package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "easyorderitem")
@Entity
public class EasyOrderItemEntity implements Serializable {

    private Long easyOrderItemId;
    private EasyOrderEntity easyOrderEntity;
    private ProductOutletSkuEntity productOutletSkuEntity;

    public EasyOrderItemEntity() {
    }

    @Id
    @Column(name = "easyorderitemid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getEasyOrderItemId() {
        return easyOrderItemId;
    }

    public void setEasyOrderItemId(Long easyOrderItemId) {
        this.easyOrderItemId = easyOrderItemId;
    }

    @ManyToOne
    @JoinColumn(name = "easyorderid", referencedColumnName = "easyorderid")
    public EasyOrderEntity getEasyOrderEntity() {
        return easyOrderEntity;
    }

    public void setEasyOrderEntity(EasyOrderEntity easyOrderEntity) {
        this.easyOrderEntity = easyOrderEntity;
    }

    @ManyToOne
    @JoinColumn(name = "productoutletskuid", referencedColumnName = "productoutletskuid")
    public ProductOutletSkuEntity getProductOutletSkuEntity() {
        return productOutletSkuEntity;
    }

    public void setProductOutletSkuEntity(ProductOutletSkuEntity productOutletSkuEntity) {
        this.productOutletSkuEntity = productOutletSkuEntity;
    }
}
