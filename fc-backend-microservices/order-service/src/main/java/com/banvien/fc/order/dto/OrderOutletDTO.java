package com.banvien.fc.order.dto;

import com.banvien.fc.order.entity.ProductOrderItemEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderOutletDTO {
    private String status;
    private String code;
    private Timestamp dateOrdered;
    private Double totalOriginalPrice;                  // Gia truoc khi app dung giam gia & khuyen mai: gia goc
    private Double totalStoreDiscountPrice;            // Gia giam tai cua hang: gia goc - gia sale
    private Double totalPromotionDiscountPrice;        // Giam gia theo khuyen mai
    private Double deliveryPrice;                      // Gia van chuyen
    private Double amount;                         // Tong tien phai tra
    private Integer totalItem;
    private Timestamp shipDate;
    private OrderInformationDTO orderInformation;
    private List<ProductOrderItemDTO> products;
    private List<ProductOrderItemEntity> productOrderItemEntities;
    private String note;
    private OutletDTO outlet;
    public OrderOutletDTO() {
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<ProductOrderItemDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrderItemDTO> products) {
        this.products = products;
    }

    public OrderInformationDTO getOrderInformation() {
        return orderInformation;
    }

    public void setOrderInformation(OrderInformationDTO orderInformation) {
        this.orderInformation = orderInformation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(Timestamp dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public Double getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    public void setTotalOriginalPrice(Double totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
    }

    public Double getTotalStoreDiscountPrice() {
        return totalStoreDiscountPrice;
    }

    public void setTotalStoreDiscountPrice(Double totalStoreDiscountPrice) {
        this.totalStoreDiscountPrice = totalStoreDiscountPrice;
    }

    public Double getTotalPromotionDiscountPrice() {
        return totalPromotionDiscountPrice;
    }

    public void setTotalPromotionDiscountPrice(Double totalPromotionDiscountPrice) {
        this.totalPromotionDiscountPrice = totalPromotionDiscountPrice;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public Timestamp getShipDate() {
        return shipDate;
    }

    public void setShipDate(Timestamp shipDate) {
        this.shipDate = shipDate;
    }

    public OutletDTO getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletDTO outlet) {
        this.outlet = outlet;
    }

    public List<ProductOrderItemEntity> getProductOrderItemEntities() {
        return productOrderItemEntities;
    }

    public void setProductOrderItemEntities(List<ProductOrderItemEntity> productOrderItemEntities) {
        this.productOrderItemEntities = productOrderItemEntities;
    }
}
