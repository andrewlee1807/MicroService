package com.banvien.fc.order.dto.mobile;

import com.banvien.fc.common.service.NumberUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ShoppingCartSummaryMobileDTO implements Serializable {
    private Long outletId;
    private String outletName;
    private String outletAddress;
    private Integer totalItem;
    private Double totalOriginalPrice;
    private Double totalSellingPrice;
    private Double totalStoreDiscountPrice;
    private Double totalPromotionDiscountPrice;
    private Double totalPrice;
    private Integer loyaltyPoint;
    private Integer creditTerms;
    private Boolean terms;
    private Timestamp paymentDueDate;
    private Double creditLimit;
    private Double creditBalance;
    private Double paymentDueAmount;
    private List<ShoppingCartItemMobileDTO> productItems;
    private List<OutletPromotionMobileDTO> outletPromotion;
    private List<ProductOutletMobileDTO> productOutletMobileRelative;
    private List<CustomerReward4MobileDTO> customerRewards; // rewards gift for customer
    private List<ProductOutletMobileDTO> hotDeals;
    private List<ProductOutletMobileDTO> productsFixPrice;
    /** for master cart salesman */
    private String customerName;
    private Long loyaltyMemberId;
    private Long shipDate;
    private Boolean invalidCode;

    public ShoppingCartSummaryMobileDTO(){
        this.totalItem = 0;
        this.totalOriginalPrice = 0d;
        this.totalPromotionDiscountPrice = 0d;
        this.totalStoreDiscountPrice = 0d;
        this.totalPrice = 0d;
        this.loyaltyPoint = 0;
        this.creditLimit = 0d;
        this.creditBalance = 0d;
        this.paymentDueAmount = 0d;
    }

    public Double getTotalSellingPrice() {
        return totalSellingPrice;
    }

    public void setTotalSellingPrice(Double totalSellingPrice) {
        this.totalSellingPrice = totalSellingPrice;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }

    public Integer getCreditTerms() {
        return creditTerms;
    }

    public void setCreditTerms(Integer creditTerms) {
        this.creditTerms = creditTerms;
    }

    public Timestamp getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Timestamp paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(Double creditBalance) {
        this.creditBalance = creditBalance;
    }

    public Double getPaymentDueAmount() {
        return paymentDueAmount;
    }

    public void setPaymentDueAmount(Double paymentDueAmount) {
        this.paymentDueAmount = paymentDueAmount;
    }

    public List<ProductOutletMobileDTO> getProductOutletMobileRelative() {
        return productOutletMobileRelative;
    }

    public void setProductOutletMobileRelative(List<ProductOutletMobileDTO> productOutletMobileRelative) {
        this.productOutletMobileRelative = productOutletMobileRelative;
    }

    public String getOutletAddress() {
        return outletAddress;
    }

    public void setOutletAddress(String outletAddress) {
        this.outletAddress = outletAddress;
    }

    public List<OutletPromotionMobileDTO> getOutletPromotion() {
        return outletPromotion;
    }

    public void setOutletPromotion(List<OutletPromotionMobileDTO> outletPromotion) {
        this.outletPromotion = outletPromotion;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public List<ShoppingCartItemMobileDTO> getProductItems() {
        return productItems;
    }

    public void setProductItems(List<ShoppingCartItemMobileDTO> productItems) {
        this.productItems = productItems;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public Integer getLoyaltyPoint() {
        return loyaltyPoint;
    }

    public void setLoyaltyPoint(Integer loyaltyPoint) {
        this.loyaltyPoint = loyaltyPoint;
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

    public Double getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    public void setTotalOriginalPrice(Double totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = NumberUtils.calculate(totalPrice);
    }

    public List<CustomerReward4MobileDTO> getCustomerRewards() {
        return customerRewards;
    }

    public void setCustomerRewards(List<CustomerReward4MobileDTO> customerRewards) {
        this.customerRewards = customerRewards;
    }

    public List<ProductOutletMobileDTO> getHotDeals() {
        return hotDeals;
    }

    public void setHotDeals(List<ProductOutletMobileDTO> hotDeals) {
        this.hotDeals = hotDeals;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getLoyaltyMemberId() {
        return loyaltyMemberId;
    }

    public void setLoyaltyMemberId(Long loyaltyMemberId) {
        this.loyaltyMemberId = loyaltyMemberId;
    }

    public List<ProductOutletMobileDTO> getProductsFixPrice() {
        return productsFixPrice;
    }

    public void setProductsFixPrice(List<ProductOutletMobileDTO> productsFixPrice) {
        this.productsFixPrice = productsFixPrice;
    }

    public Long getShipDate() {
        return shipDate;
    }

    public void setShipDate(Long shipDate) {
        this.shipDate = shipDate;
    }

    public Boolean getInvalidCode() {
        return invalidCode;
    }

    public void setInvalidCode(Boolean invalidCode) {
        this.invalidCode = invalidCode;
    }
}
