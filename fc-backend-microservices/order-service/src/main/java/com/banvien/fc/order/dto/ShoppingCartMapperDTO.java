package com.banvien.fc.order.dto;

import com.banvien.fc.order.dto.mobile.OutletPromotionMobileDTO;
import com.banvien.fc.order.dto.mobile.ShoppingCartItemMobileDTO;
import com.banvien.fc.order.entity.OutletEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartMapperDTO implements Serializable {
    private OutletEntity outlet;
    private Integer totalItem;
    private Double totalOriginalPrice;
    private Double totalStoreDiscountPrice;
    private Double totalPromotionDiscountPrice;
    private Double totalPrice;
    private Double deliveryPrice;
    private List<ShoppingCartItemMobileDTO> shoppingCartItems;
    private List<OutletPromotionMobileDTO> promotionActiveInCart;
    private List<Long> productOutletIds4RelativeProducts;

    public ShoppingCartMapperDTO(){
        this.totalItem = 0;
        this.totalOriginalPrice = 0d;
        this.totalStoreDiscountPrice = 0d;
        this.totalPromotionDiscountPrice = 0d;
        this.totalPrice = 0d;
        this.shoppingCartItems = new ArrayList<>();
        this.promotionActiveInCart = new ArrayList<>();
        this.productOutletIds4RelativeProducts = new ArrayList<>();
    }

    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public List<ShoppingCartItemMobileDTO> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setShoppingCartItems(List<ShoppingCartItemMobileDTO> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    public List<OutletPromotionMobileDTO> getPromotionActiveInCart() {
        return promotionActiveInCart;
    }

    public void setPromotionActiveInCart(List<OutletPromotionMobileDTO> promotionActiveInCart) {
        this.promotionActiveInCart = promotionActiveInCart;
    }

    public List<Long> getProductOutletIds4RelativeProducts() {
        return productOutletIds4RelativeProducts;
    }

    public void setProductOutletIds4RelativeProducts(List<Long> productOutletIds4RelativeProducts) {
        this.productOutletIds4RelativeProducts = productOutletIds4RelativeProducts;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    public void setTotalOriginalPrice(Double totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
        if (deliveryPrice == null) {
            this.deliveryPrice = 0.0;
        }
    }
}
