package com.banvien.fc.promotion.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "productoutletstoragedetail")
public class ProductOutletStorageDetailEntity {
    private Long productOutletStorageDetailId;
    private ProductOutletStorageEntity productOutletStorage;
    private Integer quantity;
    private Timestamp expireDate;

    @Id
    @Column(name = "productoutletstoragedetailid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getProductOutletStorageDetailId() {
        return productOutletStorageDetailId;
    }

    public void setProductOutletStorageDetailId(Long productOutletStorageDetailId) {
        this.productOutletStorageDetailId = productOutletStorageDetailId;
    }

    @Basic
    @Column(name = "quantity", nullable = true)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ManyToOne
    @JoinColumn(name = "productoutletstorageid", referencedColumnName = "productoutletstorageid")
    public ProductOutletStorageEntity getProductOutletStorage() {
        return productOutletStorage;
    }

    public void setProductOutletStorage(ProductOutletStorageEntity productOutletStorage) {
        this.productOutletStorage = productOutletStorage;
    }

    @Basic
    @Column(name = "expiredate", nullable = true)
    public Timestamp getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Timestamp expireDate) {
        this.expireDate = expireDate;
    }
}
