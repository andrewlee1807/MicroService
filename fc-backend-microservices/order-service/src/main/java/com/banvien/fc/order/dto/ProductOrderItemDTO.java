package com.banvien.fc.order.dto;
import com.banvien.fc.order.entity.ProductOutletSkuEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductOrderItemDTO implements Serializable {
    private Long productOrderItemID;
    private Long productOutletId;
    private List<Long> outletPromotionId;
    private OrderOutletDTO orderOutlet;
    private ProductOutletSkuDTO productOutletSku;
    private String code;
    private String name;
    private Integer quantity;
    private Integer quantityFinalAfterReturn;
    private Double price;
    private Double tax;
    private Double priceDiscount;
    private boolean isSelected;
    private boolean check;
    private Integer goodQuantity;
    private Integer demagedQuantity;
    private String note;
    private Integer quantityDiscount;
    private Double totalDiscountAmount;
    private Double totalPricePerOneItem;
    private Double originalPrice;                                           //gia goc : TH productoutletsku.orginalprice == null   => productoutletsku.saleprice
    private Double salePrice;                                               //gia cua hang ban: TH user thuoc group(Gold,Silver,....) => pricingsku.saleprice
                                                                            //                  TH con lai => productoutletsku.saleprice
    private Double promotionPrice;                                          //gia da ap dung khuyen mai
    private Double discountPromotionPrice;                                  //gia giam trên san pham (salePrice - promotionPrice)
    private Double totalAmount;
    private Double weight;
    private Long shoppingCartId;
    private String device;
    private String productOutletName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getProductOrderItemID() {
        return productOrderItemID;
    }

    public void setProductOrderItemID(Long productOrderItemID) {
        this.productOrderItemID = productOrderItemID;
    }

    public OrderOutletDTO getOrderOutlet() {
        return orderOutlet;
    }

    public void setOrderOutlet(OrderOutletDTO orderOutlet) {
        this.orderOutlet = orderOutlet;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ProductOrderItemDTO() {
    }

    public ProductOrderItemDTO(Long productOrderItemID, OrderOutletDTO orderOutlet, ProductOutletSkuDTO productOutletSku, Integer quantity, Double price) {
        this.productOrderItemID = productOrderItemID;
        this.orderOutlet = orderOutlet;
        this.productOutletSku = productOutletSku;
        this.quantity = quantity;
        this.price = price;
    }

    public ProductOutletSkuDTO getProductOutletSku() {
        return productOutletSku;
    }

    public void setProductOutletSku(ProductOutletSkuDTO productOutletSku) {
        this.productOutletSku = productOutletSku;
    }

    public Double getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(Double priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Integer getGoodQuantity() {
        return goodQuantity;
    }

    public void setGoodQuantity(Integer goodQuantity) {
        this.goodQuantity = goodQuantity;
    }

    public Integer getDemagedQuantity() {
        return demagedQuantity;
    }

    public void setDemagedQuantity(Integer demagedQuantity) {
        this.demagedQuantity = demagedQuantity;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getQuantityFinalAfterReturn() {
        return quantityFinalAfterReturn;
    }

    public void setQuantityFinalAfterReturn(Integer quantityFinalAfterReturn) {
        this.quantityFinalAfterReturn = quantityFinalAfterReturn;
    }

    public Integer getQuantityDiscount() {
        return quantityDiscount;
    }

    public void setQuantityDiscount(Integer quantityDiscount) {
        this.quantityDiscount = quantityDiscount;
    }

    public Double getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(Double totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public Double getTotalPricePerOneItem() {
        return totalPricePerOneItem;
    }

    public void setTotalPricePerOneItem(Double totalPricePerOneItem) {
        this.totalPricePerOneItem = totalPricePerOneItem;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Long getProductOutletId() {
        return productOutletId;
    }

    public void setProductOutletId(Long productOutletId) {
        this.productOutletId = productOutletId;
    }

    public String getProductOutletName() {
        return productOutletName;
    }

    public void setProductOutletName(String productOutletName) {
        this.productOutletName = productOutletName;
    }

    public List<Long> getOutletPromotionId() {
        return outletPromotionId;
    }

    public void setOutletPromotionId(List<Long> outletPromotionId) {
        this.outletPromotionId = outletPromotionId;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
