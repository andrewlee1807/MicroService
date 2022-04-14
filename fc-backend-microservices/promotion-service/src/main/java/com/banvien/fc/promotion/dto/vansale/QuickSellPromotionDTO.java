package com.banvien.fc.promotion.dto.vansale;

import com.banvien.fc.promotion.dto.OutletPromotionDTO;
import com.banvien.fc.promotion.dto.ProductOutletSkuDTO;
import com.banvien.fc.promotion.entity.ProductOutletSkuEntity;

import java.util.ArrayList;
import java.util.List;

public class QuickSellPromotionDTO {
    private List<CampaignDTO> campaigns;
    private List<OutletPromotionDTO> hotPromotions;
    private List<ProductOutletSkuDTO> recommendProducts;
    private List<ProductOutletSkuDTO> productsWithPromotion;



    public QuickSellPromotionDTO(List<CampaignDTO> campaigns, List<OutletPromotionDTO> hotPromotions, List<ProductOutletSkuDTO> recommendProducts, List<ProductOutletSkuDTO> productsWithPromotion) {
        this.campaigns = campaigns;
        this.hotPromotions = hotPromotions;
        this.recommendProducts = recommendProducts;
        this.productsWithPromotion = productsWithPromotion;
    }

    public List<OutletPromotionDTO> getHotPromotions() {
        return hotPromotions;
    }

    public void setHotPromotions(List<OutletPromotionDTO> hotPromotions) {
        this.hotPromotions = hotPromotions;
        if (hotPromotions == null) {
            this.hotPromotions = new ArrayList<>();
        }
    }

    public List<ProductOutletSkuDTO> getRecommendProducts() {
        return recommendProducts;
    }

    public void setRecommendProducts(List<ProductOutletSkuDTO> recommendProducts) {
        this.recommendProducts = recommendProducts;
        if (recommendProducts == null) {
            this.recommendProducts = new ArrayList<>();
        }
    }

    public List<ProductOutletSkuDTO> getProductsWithPromotion() {
        return productsWithPromotion;
    }

    public void setProductsWithPromotion(List<ProductOutletSkuDTO> productsWithPromotion) {
        this.productsWithPromotion = productsWithPromotion;
        if (productsWithPromotion == null) {
            this.productsWithPromotion = new ArrayList<>();
        }
    }

    public List<CampaignDTO> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<CampaignDTO> campaigns) {
        this.campaigns = campaigns;
        if (campaigns == null) {
            this.campaigns = new ArrayList<>();
        }
    }
}
