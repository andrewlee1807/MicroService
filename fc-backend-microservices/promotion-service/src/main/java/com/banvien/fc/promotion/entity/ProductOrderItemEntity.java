package com.banvien.fc.promotion.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Table(name = "productorderitem")
@Entity
public class ProductOrderItemEntity {

    private Long productOrderItemId;
    private Long orderOutletId;
    private Integer quantity;
    private Double price;
    private Double priceDiscount;
    private Integer quantityDiscount;
    private Double weight;
    private Long productOutletSkuId;

    @Column(name = "productoutletskuid")
    @Basic
    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    @Column(name = "productorderitemid", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getProductOrderItemId() {
        return productOrderItemId;
    }

    public void setProductOrderItemId(Long productOrderItemId) {
        this.productOrderItemId = productOrderItemId;
    }

    @Basic
    @Column(name = "orderoutletid")
    public Long getOrderOutletId() {
        return orderOutletId;
    }

    public void setOrderOutletId(Long orderOutlet) {
        this.orderOutletId = orderOutlet;
    }

    @Column(name = "quantity")
    @Basic
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "price")
    @Basic
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductOrderItemEntity(Long productOrderItemId, Long orderOutletId , Integer quantity, Double price) {
        this.productOrderItemId = productOrderItemId;
        this.orderOutletId = orderOutletId;
        this.quantity = quantity;
        this.price = price;
    }

    public ProductOrderItemEntity() {
    }

    @Column(name = "pricediscount")
    @Basic

    public Double getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(Double priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    @Column(name = "quantitydiscount")
    @Basic
    public Integer getQuantityDiscount() {
        return quantityDiscount;
    }

    public void setQuantityDiscount(Integer quantityDiscount) {
        this.quantityDiscount = quantityDiscount;
    }

    @Column(name = "weight")
    @Basic
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

}
