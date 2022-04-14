package com.banvien.fc.order.dto;

import java.sql.Timestamp;
import java.util.List;

public class CollectionDTO {
    private Long id;
    private Long outletId;
    private String name;
    private Integer status;
    private String image;
    private Integer totalItem;
    private Double totalPrice;
    private Double salePrice;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private List<ProductOutletSkuDTO> productOutletSkuDTOList;

    public CollectionDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<ProductOutletSkuDTO> getProductOutletSkuDTOList() {
        return productOutletSkuDTOList;
    }

    public void setProductOutletSkuDTOList(List<ProductOutletSkuDTO> productOutletSkuDTOList) {
        this.productOutletSkuDTOList = productOutletSkuDTOList;
    }
}
