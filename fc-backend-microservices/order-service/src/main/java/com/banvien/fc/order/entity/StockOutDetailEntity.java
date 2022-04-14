package com.banvien.fc.order.entity;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "stockoutdetail")
public class StockOutDetailEntity {

    private Long stockOutDetailId;
    private Long stockOutId;
    private Long productOutletSkuId;
    private int quantity;
    private double price;
    private Timestamp expireDate;
    private Long productOutletStorageId;

    public StockOutDetailEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getStockOutDetailId() {
        return stockOutDetailId;
    }

    public void setStockOutDetailId(Long stockOutDetailId) {
        this.stockOutDetailId = stockOutDetailId;
    }

    public Timestamp getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Timestamp expireDate) {
        this.expireDate = expireDate;
    }

    public Long getStockOutId() {
        return stockOutId;
    }

    public void setStockOutId(Long stockOutId) {
        this.stockOutId = stockOutId;
    }

    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getProductOutletStorageId() {
        return productOutletStorageId;
    }

    public void setProductOutletStorageId(Long productOutletStorageId) {
        this.productOutletStorageId = productOutletStorageId;
    }
}
