package com.banvien.fc.order.dto.mobile;

import com.banvien.fc.order.dto.GiftPromotionDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ProductOutletMobileDTO implements Serializable {
    private Long productOutletId;
    private Long outletId;
    private Long catGroupId;
    private Long brandId;
    private String outletName;

    private Long productMasterId;
    private String productName;
    private String productImage;
    private String productCode;
    private String description;

    private Integer status;
    private Integer numberInCart;
    private Integer top;
    private String statusStr;
    private Integer index;

    private List<String> imgPaths;
    private List<Long> relativeProductOutletIds;

    private List<ProductOutletMobileDTO> listRelativeProduct;
    private List<OutletPromotionMobileDTO> promotion;
    private List<GiftPromotionDTO> giftPromotion;
    private List<ProductOutletSkuMobileDTO> skus;
    private List<Long> catGroupAttributeIds; // Set catgroup attribute to productoutlet properties
    private Long modifiedBy;
    private List<Long> outletSaleChannel;
    private Double collectionSalePrice;

    public Long getCatGroupId() {
        return catGroupId;
    }

    public void setCatGroupId(Long catGroupId) {
        this.catGroupId = catGroupId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public Long getProductOutletId() {
        return productOutletId;
    }

    public void setProductOutletId(Long productOutletId) {
        this.productOutletId = productOutletId;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
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

    public Integer getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(Integer numberInCart) {
        this.numberInCart = numberInCart;
    }

    public List<ProductOutletSkuMobileDTO> getSkus() {
        return skus;
    }

    public void setSkus(List<ProductOutletSkuMobileDTO> skus) {
        this.skus = skus;
    }

    public List<OutletPromotionMobileDTO> getPromotion() {
        return promotion;
    }

    public void setPromotion(List<OutletPromotionMobileDTO> promotion) {
        this.promotion = promotion;
    }

    public List<GiftPromotionDTO> getGiftPromotion() {
        return giftPromotion;
    }

    public void setGiftPromotion(List<GiftPromotionDTO> giftPromotion) {
        this.giftPromotion = giftPromotion;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public List<ProductOutletMobileDTO> getListRelativeProduct() {
        return listRelativeProduct;
    }

    public void setListRelativeProduct(List<ProductOutletMobileDTO> listRelativeProduct) {
        this.listRelativeProduct = listRelativeProduct;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public List<Long> getRelativeProductOutletIds() {
        return relativeProductOutletIds;
    }

    public void setRelativeProductOutletIds(List<Long> relativeProductOutletIds) {
        this.relativeProductOutletIds = relativeProductOutletIds;
    }

    public List<String> getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(List<String> imgPaths) {
        this.imgPaths = imgPaths;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public List<Long> getCatGroupAttributeIds() {
        return catGroupAttributeIds;
    }

    public void setCatGroupAttributeIds(List<Long> catGroupAttributeIds) {
        this.catGroupAttributeIds = catGroupAttributeIds;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getProductMasterId() {
        return productMasterId;
    }

    public void setProductMasterId(Long productMasterId) {
        this.productMasterId = productMasterId;
    }

    public List<Long> getOutletSaleChannel() {
        return outletSaleChannel;
    }

    public void setOutletSaleChannel(List<Long> outletSaleChannel) {
        this.outletSaleChannel = outletSaleChannel;
    }


    public Double getCollectionSalePrice() {
        return collectionSalePrice;
    }

    public void setCollectionSalePrice(Double collectionSalePrice) {
        this.collectionSalePrice = collectionSalePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOutletMobileDTO that = (ProductOutletMobileDTO) o;
        return Objects.equals(productOutletId, that.productOutletId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productOutletId);
    }
}
