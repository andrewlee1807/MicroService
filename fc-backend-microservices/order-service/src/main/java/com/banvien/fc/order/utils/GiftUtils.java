package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.GiftDTO;
import com.banvien.fc.order.dto.GiftPromotionDTO;
import com.banvien.fc.order.entity.GiftEntity;
import com.banvien.fc.order.entity.ProductOutletSkuEntity;

import java.util.ArrayList;
import java.util.List;

public class GiftUtils {
    public static List<GiftDTO> entity2DTO(List<GiftEntity> entities){
        List<GiftDTO> gifts = new ArrayList<>();
        for (GiftEntity entity : entities){
            gifts.add(entity2DTO(entity));
        }
        return gifts;
    }
    public static GiftDTO entity2DTO(GiftEntity entity){
        GiftDTO giftDTO = new GiftDTO();
        giftDTO.setGiftId(entity.getGiftId());
        giftDTO.setImage(entity.getImage());
        giftDTO.setName(entity.getName());
        return giftDTO;
    }

    public static List<GiftPromotionDTO> changeGiftEntity2GiftProductDto(List<ProductOutletSkuEntity> productOutletSkuEntities){
        List<GiftPromotionDTO> giftPromotionDTOS = new ArrayList<>();
        for (ProductOutletSkuEntity entity :productOutletSkuEntities){
            giftPromotionDTOS.add(changeGiftEntity2GiftProductDto(entity));
        }
        return giftPromotionDTOS;
    }

    public static GiftPromotionDTO changeGiftEntity2GiftProductDto(ProductOutletSkuEntity productOutletSkuEntity) {
        GiftPromotionDTO freeProductPromotionDTO = new GiftPromotionDTO();
        freeProductPromotionDTO.setProductOutletId(productOutletSkuEntity.getProductOutlet().getProductOutletId());
        freeProductPromotionDTO.setProductOutletSkuId(productOutletSkuEntity.getProductOutletSkuId());
        freeProductPromotionDTO.setType(CoreConstants.PRODUCT_PROMOTION_GET_PRODUCT);
        freeProductPromotionDTO.setImgUrl(productOutletSkuEntity.getImage());
        freeProductPromotionDTO.setName(productOutletSkuEntity.getProductOutlet().getName());
        freeProductPromotionDTO.setPromotionPrice(0d);
        return freeProductPromotionDTO;
    }

    public static List<GiftPromotionDTO> changeGiftEntity2GiftDto(List<GiftEntity> giftEntities){
        List<GiftPromotionDTO> giftPromotionDTOS = new ArrayList<>();
        for (GiftEntity entity :giftEntities){
            giftPromotionDTOS.add(changeGiftEntity2GiftDto(entity));
        }
        return giftPromotionDTOS;
    }

    public static GiftPromotionDTO changeGiftEntity2GiftDto(GiftEntity giftEntity) {
        GiftPromotionDTO freeProductPromotionDTO = new GiftPromotionDTO();
        freeProductPromotionDTO.setId(giftEntity.getGiftId());
        freeProductPromotionDTO.setType(CoreConstants.PRODUCT_PROMOTION_GET_GIFT);
        freeProductPromotionDTO.setImgUrl(giftEntity.getImage());
        freeProductPromotionDTO.setName(giftEntity.getName());
        freeProductPromotionDTO.setPromotionPrice(0d);
        return freeProductPromotionDTO;
    }

}
