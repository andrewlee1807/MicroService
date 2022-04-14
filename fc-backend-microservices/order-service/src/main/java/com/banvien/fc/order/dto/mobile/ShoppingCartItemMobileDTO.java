package com.banvien.fc.order.dto.mobile;

import com.banvien.fc.order.dto.GiftPromotionDTO;
import com.banvien.fc.order.dto.ProductOutletSkuDTO;

import java.io.Serializable;
import java.util.List;

public class ShoppingCartItemMobileDTO implements Serializable {
    private Long shoppingCartId;
    private Long productOutletSkuId;
    private Long productOutletId;
    private String skuName;
    private String productName;
    private String productImage;
    private Double originalPrice;                               //gia goc : TH productoutletsku.orginalprice == null   => productoutletsku.saleprice
    private Double salePrice;                                   //gia cua hang ban: TH user thuoc group(Gold,Silver,....) => pricingsku.saleprice
                                                                //                  TH con lai => productoutletsku.saleprice
    private Double promotionPrice;                              //gia da ap dung khuyen mai
    private Double discountPromotionPrice;                      //gia giam trên san pham (salePrice - promotionPrice)
    private Integer quantity;                                   //so luong san pham
    private String device;
    private List<ProductOutletSkuDTO> freeProductPromotions;
    private List<GiftPromotionDTO> giftPromotions;                      //qua tang kem (BUY PRODUCT X GET GIFT Y)
    private List<OutletPromotionMobileDTO> promotions;                  //thong tin promotion cua san pham
    private List<GiftPromotionDTO> listFreeProductToChoose;             //san pham tang kem (BUY PRODUCT X GET PRODUCT Y)
    private Double totalAmount;                                 //TH khong co gia khuyen mai: salePrice * quantity
                                                                //TH co gia khuyen mai : promotionPrice * quantity
    private boolean available;                                   //True : quantity in warehouse > 0
    public ShoppingCartItemMobileDTO(){
        this.originalPrice = 0d;
        this.salePrice = 0d;
        this.promotionPrice = 0d;
        this.quantity = 0;
        this.discountPromotionPrice = 0d;
    }

    public List<GiftPromotionDTO> getListFreeProductToChoose() {
        return listFreeProductToChoose;
    }

    public void setListFreeProductToChoose(List<GiftPromotionDTO> listFreeProductToChoose) {
        this.listFreeProductToChoose = listFreeProductToChoose;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public List<ProductOutletSkuDTO> getFreeProductPromotions() {
        return freeProductPromotions;
    }

    public void setFreeProductPromotions(List<ProductOutletSkuDTO> freeProductPromotions) {
        this.freeProductPromotions = freeProductPromotions;
    }

    public List<GiftPromotionDTO> getGiftPromotions() {
        return giftPromotions;
    }

    public void setGiftPromotions(List<GiftPromotionDTO> giftPromotions) {
        this.giftPromotions = giftPromotions;
    }

    public List<OutletPromotionMobileDTO> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<OutletPromotionMobileDTO> promotions) {
        this.promotions = promotions;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Long getProductOutletSkuId() {
        return productOutletSkuId;
    }

    public void setProductOutletSkuId(Long productOutletSkuId) {
        this.productOutletSkuId = productOutletSkuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Double getDiscountPromotionPrice() {
        return discountPromotionPrice;
    }

    public void setDiscountPromotionPrice(Double discountPromotionPrice) {
        this.discountPromotionPrice = discountPromotionPrice;
    }

    public Long getProductOutletId() {
        return productOutletId;
    }

    public void setProductOutletId(Long productOutletId) {
        this.productOutletId = productOutletId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
