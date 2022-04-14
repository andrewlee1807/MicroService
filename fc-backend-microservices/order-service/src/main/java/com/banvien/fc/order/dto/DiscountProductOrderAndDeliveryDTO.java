package com.banvien.fc.order.dto;

import com.banvien.fc.order.dto.mobile.ProductOutletSkuMobileDTO;

import java.util.ArrayList;
import java.util.List;

public class DiscountProductOrderAndDeliveryDTO {
    private List<ReductionDTO> deliveryDiscount;
    private List<ProductOrderItemDTO> productOrderItems;
    private Double totalWeight;
    private Double totalOriginalPrice;
    private List<Long> shoppingCartIds;
    private List<ReductionDTO> lstOutletPromotion;
    private List<GiftPromotionDTO> giftPromotions;
    private List<GiftPromotionDTO> listFreeProductToChoose;
    private List<ProductOutletSkuMobileDTO> productsFixPrice;
    private double totalStoreDiscount;

    public DiscountProductOrderAndDeliveryDTO() {
        this.productOrderItems = new ArrayList<>();
    }

    public DiscountProductOrderAndDeliveryDTO(List<ReductionDTO> deliveryDiscount, List<ProductOrderItemDTO> productOrderItems) {
        this.deliveryDiscount = deliveryDiscount;
        this.productOrderItems = productOrderItems;
        if (productOrderItems == null) {
            this.productOrderItems = new ArrayList<>();
        }
    }

    public DiscountProductOrderAndDeliveryDTO(List<ReductionDTO> deliveryDiscount, List<ProductOrderItemDTO> productOrderItems, Double totalWeight, Double totalOriginalPrice, List<Long> shoppingCartIds, List<ReductionDTO> lstOutletPromotion, List<GiftPromotionDTO> giftPromotions, List<GiftPromotionDTO> listFreeProductToChoose, List<ProductOutletSkuMobileDTO> productsFixPrice, double totalStoreDiscount) {
        this.deliveryDiscount = deliveryDiscount;
        if (deliveryDiscount == null) {
            this.deliveryDiscount = new ArrayList<>();
        }
        this.productOrderItems = productOrderItems;
        this.totalWeight = totalWeight;
        this.totalOriginalPrice = totalOriginalPrice;
        this.shoppingCartIds = shoppingCartIds;
        this.lstOutletPromotion = lstOutletPromotion;
        if (lstOutletPromotion == null) {
            this.lstOutletPromotion = new ArrayList<>();
        }
        this.giftPromotions = giftPromotions;
        this.listFreeProductToChoose = listFreeProductToChoose;
        this.productsFixPrice = productsFixPrice;
        this.totalStoreDiscount = totalStoreDiscount;
    }

    public List<ReductionDTO> getDeliveryDiscount() {
        return deliveryDiscount;
    }

    public void setDeliveryDiscount(List<ReductionDTO> deliveryDiscount) {
        this.deliveryDiscount = deliveryDiscount;
        if (deliveryDiscount == null) {
            this.deliveryDiscount = new ArrayList<>();
        }
    }

    public List<ProductOrderItemDTO> getProductOrderItems() {
        return productOrderItems;
    }

    public void setProductOrderItems(List<ProductOrderItemDTO> productOrderItems) {
        this.productOrderItems = productOrderItems;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Double getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    public void setTotalOriginalPrice(Double totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
    }

    public List<Long> getShoppingCartIds() {
        return shoppingCartIds;
    }

    public void setShoppingCartIds(List<Long> shoppingCartIds) {
        this.shoppingCartIds = shoppingCartIds;
    }

    public List<ReductionDTO> getLstOutletPromotion() {
        return lstOutletPromotion;
    }

    public void setLstOutletPromotion(List<ReductionDTO> lstOutletPromotion) {
        this.lstOutletPromotion = lstOutletPromotion;
        if (lstOutletPromotion == null) {
            this.lstOutletPromotion = new ArrayList<>();
        }
    }

    public List<GiftPromotionDTO> getGiftPromotions() {
        return giftPromotions;
    }

    public void setGiftPromotions(List<GiftPromotionDTO> giftPromotions) {
        this.giftPromotions = giftPromotions;
        if (giftPromotions == null) {
            this.giftPromotions = new ArrayList<>();
        }
    }

    public List<GiftPromotionDTO> getListFreeProductToChoose() {
        return listFreeProductToChoose;
    }

    public void setListFreeProductToChoose(List<GiftPromotionDTO> listFreeProductToChoose) {
        this.listFreeProductToChoose = listFreeProductToChoose;
        if (listFreeProductToChoose == null) {
            this.listFreeProductToChoose = new ArrayList<>();
        }
    }

    public List<ProductOutletSkuMobileDTO> getProductsFixPrice() {
        return productsFixPrice;
    }

    public void setProductsFixPrice(List<ProductOutletSkuMobileDTO> productsFixPrice) {
        this.productsFixPrice = productsFixPrice;
        if (productsFixPrice == null) {
            this.productsFixPrice = new ArrayList<>();
        }
    }

    public double getTotalStoreDiscount() {
        return totalStoreDiscount;
    }

    public void setTotalStoreDiscount(double totalStoreDiscount) {
        this.totalStoreDiscount = totalStoreDiscount;
    }
}
