package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "shoppingcart")
@Entity
public class ShoppingCartEntity {

    private Long shoppingCartId;
    private ProductOutletSkuEntity productOutletSku;
    private Integer quantity;
    private CustomerEntity customer;
    private Timestamp createdDate;
    private String device;
    private String temporaryOrderCode;

    @Column(name = "shoppingCartId", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    @Column(name = "quantity")
    @Basic
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ManyToOne()
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    @Column(name = "createdDate")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "device")
    @Basic
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productoutletskuid", referencedColumnName = "productoutletskuid")
    public ProductOutletSkuEntity getProductOutletSku() {
        return productOutletSku;
    }

    public void setProductOutletSku(ProductOutletSkuEntity productOutletSku) {
        this.productOutletSku = productOutletSku;
    }

    @Column(name = "temporaryordercode")
    @Basic
    public String getTemporaryOrderCode() {
        return temporaryOrderCode;
    }

    public void setTemporaryOrderCode(String temporaryOrderCode) {
        this.temporaryOrderCode = temporaryOrderCode;
    }

}
