package com.banvien.fc.promotion.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productoutletstorage")
public class ProductOutletStorageEntity {
    private Long productOutletStorageId;
    private Integer quantity;
    private Integer minimumOrderQuantity;
    private Integer maximumOrderQuantity;
    private WareHouseEntity wareHouse;
    private ProductOutletSkuEntity productOutletSku;
    private List<ProductOutletStorageDetailEntity> productOutletStorageDetails;

    public ProductOutletStorageEntity() {
    }

    public ProductOutletStorageEntity(Long productOutletStorageId) {
        this.productOutletStorageId = productOutletStorageId;
    }

    @Id
    @Column(name = "productoutletstorageid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getProductOutletStorageId() {
        return productOutletStorageId;
    }

    public void setProductOutletStorageId(Long productOutletStorageId) {
        this.productOutletStorageId = productOutletStorageId;
    }

    @OneToMany(mappedBy = "productOutletStorage")
    public List<ProductOutletStorageDetailEntity> getProductOutletStorageDetails() {
        if (productOutletStorageDetails == null) {
            productOutletStorageDetails = new ArrayList<>();
        }
        return productOutletStorageDetails;
    }

    public void setProductOutletStorageDetails(List<ProductOutletStorageDetailEntity> productOutletStorageDetailEntities) {
        this.productOutletStorageDetails = productOutletStorageDetailEntities;
    }

    @Basic
    @Column(name = "quantity", nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "minimumorderquantity", nullable = false)
    public Integer getMinimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    public void setMinimumOrderQuantity(Integer minimumOrderQuantity) {
        this.minimumOrderQuantity = minimumOrderQuantity;
    }

    @Basic
    @Column(name = "maximumorderquantity", nullable = false)
    public Integer getMaximumOrderQuantity() {
        return maximumOrderQuantity;
    }

    public void setMaximumOrderQuantity(Integer maximumOrderQuantity) {
        this.maximumOrderQuantity = maximumOrderQuantity;
    }

    @ManyToOne()
    @JoinColumn(name = "warehouseid", referencedColumnName = "warehouseid")
    public WareHouseEntity getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(WareHouseEntity wareHouse) {
        this.wareHouse = wareHouse;
    }

    @ManyToOne()
    @JoinColumn(name = "productoutletskuid", referencedColumnName = "productoutletskuid")
    public ProductOutletSkuEntity getProductOutletSku() {
        return productOutletSku;
    }

    public void setProductOutletSku(ProductOutletSkuEntity productOutletSku) {
        this.productOutletSku = productOutletSku;
    }
}
