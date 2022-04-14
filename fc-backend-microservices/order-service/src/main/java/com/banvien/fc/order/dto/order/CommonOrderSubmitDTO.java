package com.banvien.fc.order.dto.order;

import com.banvien.fc.order.dto.OrderInformationDTO;
import com.banvien.fc.order.dto.ProductDTO;

import java.io.Serializable;
import java.util.List;

public class CommonOrderSubmitDTO implements Serializable {

    private Long outletId;
    private Long warehouseId;
    private Long salesManUserId;
    private String note;
    private CustomerSubmitInforDTO customerInformation;
    private OrderInformationDTO informationMobile;
    private List<ProductDTO> productsSummary;
    private Double totalStoreDiscount;
    private Double debt;
    private Boolean isCredit;
    private String source;
    private String code;
    private String promotionCode;

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public CustomerSubmitInforDTO getCustomerInformation() {
        return customerInformation;
    }

    public void setCustomerInformation(CustomerSubmitInforDTO customerInformation) {
        this.customerInformation = customerInformation;
    }

    public OrderInformationDTO getInformationMobile() {
        return informationMobile;
    }

    public void setInformationMobile(OrderInformationDTO informationMobile) {
        this.informationMobile = informationMobile;
    }

    public List<ProductDTO> getProductsSummary() {
        return productsSummary;
    }

    public void setProductsSummary(List<ProductDTO> productsSummary) {
        this.productsSummary = productsSummary;
    }

    public Double getTotalStoreDiscount() {
        return totalStoreDiscount;
    }

    public void setTotalStoreDiscount(Double totalStoreDiscount) {
        this.totalStoreDiscount = totalStoreDiscount;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Boolean getCredit() {
        return isCredit;
    }

    public void setCredit(Boolean credit) {
        isCredit = credit;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getSalesManUserId() {
        return salesManUserId;
    }

    public void setSalesManUserId(Long salesManUserId) {
        this.salesManUserId = salesManUserId;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }
}
