package com.banvien.fc.order.dto;

import com.banvien.fc.common.service.NumberUtils;
import com.banvien.fc.order.dto.mobile.ProductOutletSkuMobileDTO;

import java.util.ArrayList;
import java.util.List;

public class EasyOrderRedeemPromotionDTO {
    private double totalSalePrice ;
    private double totalOriginalPrice ;
    private double totalStoreDiscount ;
    private double totalPrice ;
    private double finalTotalPrice;                                     // total price - deliveryDiscount
    private double deliveryDiscount ;
    private double deliveryFee;
    private double totalPromotionDiscountPrice;
    private double totalWeight;
    private int totalItems;
    private List<GiftDTO> gifts;                                        //gift promotion (BUY PRODUCT X GET GIFT)
    private List<ProductOrderItemDTO> products;                         //products ordered
    private List<ProductOutletSkuDTO> freeProducts;                     //free product promotin (BUY PRODUCT X GET PRODUCT Y)
    private List<ReductionDTO> outletPromotionPass;                     //promotion applied on order
    private List<ProductOutletSkuMobileDTO> productsFixPrice;           //products have fix price
    private List<ReductionDTO> lstPromotionDelivery;                    //promotions for delivery

    public EasyOrderRedeemPromotionDTO() {
    }

    public EasyOrderRedeemPromotionDTO(double totalSalePrice, double totalOriginalPrice, double totalStoreDiscount, double totalPrice, double finalTotalPrice, double deliveryDiscount, double deliveryFee, double totalPromotionDiscountPrice, double totalWeight, int totalItems, List<GiftDTO> gifts, List<ProductOrderItemDTO> products, List<ProductOutletSkuDTO> freeProducts, List<ReductionDTO> outletPromotionPass, List<ProductOutletSkuMobileDTO> productsFixPrice) {
        this.totalSalePrice = totalSalePrice;
        this.totalOriginalPrice = totalOriginalPrice;
        this.totalStoreDiscount = totalStoreDiscount;
        this.totalPrice = NumberUtils.calculate(totalPrice);
        this.finalTotalPrice = finalTotalPrice;
        this.deliveryDiscount = deliveryDiscount;
        this.deliveryFee = deliveryFee;
        this.totalPromotionDiscountPrice = totalPromotionDiscountPrice;
        this.totalWeight = totalWeight;
        this.totalItems = totalItems;
        this.gifts = gifts;
        this.products = products;
        this.freeProducts = freeProducts;
        this.outletPromotionPass = outletPromotionPass;
        this.productsFixPrice = productsFixPrice;
    }

    public double getTotalSalePrice() {
        return totalSalePrice;
    }

    public void setTotalSalePrice(double totalSalePrice) {
        this.totalSalePrice = totalSalePrice;
    }

    public double getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    public void setTotalOriginalPrice(double totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
    }

    public double getTotalStoreDiscount() {
        return totalStoreDiscount;
    }

    public void setTotalStoreDiscount(double totalStoreDiscount) {
        this.totalStoreDiscount = totalStoreDiscount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = NumberUtils.calculate(totalPrice);
    }

    public double getDeliveryDiscount() {
        return deliveryDiscount;
    }

    public void setDeliveryDiscount(double deliveryDiscount) {
        this.deliveryDiscount = deliveryDiscount;
    }

    public double getTotalPromotionDiscountPrice() {
        return totalPromotionDiscountPrice;
    }

    public void setTotalPromotionDiscountPrice(double totalPromotionDiscountPrice) {
        this.totalPromotionDiscountPrice = totalPromotionDiscountPrice;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<GiftDTO> getGifts() {
        return gifts;
    }

    public void setGifts(List<GiftDTO> gifts) {
        this.gifts = gifts;
        if (gifts == null) {
            this.gifts = new ArrayList<>();
        }
    }

    public List<ProductOrderItemDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrderItemDTO> products) {
        this.products = products;
        if (products == null) {
            this.products = new ArrayList<>();
        }
    }

    public List<ReductionDTO> getOutletPromotionPass() {
        return outletPromotionPass;
    }

    public void setOutletPromotionPass(List<ReductionDTO> outletPromotionPass) {
        this.outletPromotionPass = outletPromotionPass;
    }

    public double getFinalTotalPrice() {
        return finalTotalPrice;
    }

    public void setFinalTotalPrice(double finalTotalPrice) {
        this.finalTotalPrice = finalTotalPrice;
    }

    public List<ProductOutletSkuDTO> getFreeProducts() {
        return freeProducts;
    }

    public void setFreeProducts(List<ProductOutletSkuDTO> freeProducts) {
        this.freeProducts = freeProducts;
        if (freeProducts == null) {
            this.freeProducts = new ArrayList<>();
        }
    }

    public List<ProductOutletSkuMobileDTO> getProductsFixPrice() {
        return productsFixPrice;
    }

    public void setProductsFixPrice(List<ProductOutletSkuMobileDTO> productsFixPrice) {
        this.productsFixPrice = productsFixPrice;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public List<ReductionDTO> getLstPromotionDelivery() {
        return lstPromotionDelivery;
    }

    public void setLstPromotionDelivery(List<ReductionDTO> lstPromotionDelivery) {
        this.lstPromotionDelivery = lstPromotionDelivery;
    }
}
