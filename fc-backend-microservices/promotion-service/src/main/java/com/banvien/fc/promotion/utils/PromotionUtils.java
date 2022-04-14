package com.banvien.fc.promotion.utils;

import com.banvien.fc.promotion.dto.OutletPromotionDTO;
import com.banvien.fc.promotion.dto.PromotionsDTO;
import com.banvien.fc.promotion.dto.rules.DiscountDTO;
import com.banvien.fc.promotion.entity.OutletPromotionEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Date;

public class PromotionUtils {

    public static PromotionsDTO from(OutletPromotionEntity outletPromotionEntity) {
        if (outletPromotionEntity == null) {
            return null;
        }

        PromotionsDTO promotionsDTO = new PromotionsDTO();

        if (outletPromotionEntity != null) {
            promotionsDTO.setEnddate(outletPromotionEntity.getEndDate());
            promotionsDTO.setProgramName(outletPromotionEntity.getTitle());
            promotionsDTO.setId(outletPromotionEntity.getOutletPromotionId());
            promotionsDTO.setStartdate(outletPromotionEntity.getStartDate());
            promotionsDTO.setStatus(outletPromotionEntity.getStatus());
        }

        return promotionsDTO;
    }

    public static OutletPromotionDTO entity2DtoForVansales(OutletPromotionEntity entity, int quantityApplied) {
        OutletPromotionDTO outletPromotionDTO = new OutletPromotionDTO();
        outletPromotionDTO.setOutletPromotionId(entity.getOutletPromotionId());
        outletPromotionDTO.setThumbnail(entity.getThumbnail());
        outletPromotionDTO.setDescription(entity.getDescription());
        outletPromotionDTO.setMaxQuantityCanBuy(entity.getMaxQuantityCanBuy());
        outletPromotionDTO.setStatus(entity.getStatus());
        outletPromotionDTO.setStartDate(entity.getStartDate());
        outletPromotionDTO.setEndDate(entity.getEndDate());
        outletPromotionDTO.setTitle(entity.getTitle());
        outletPromotionDTO.setTotalGift(entity.getTotalGift());
        outletPromotionDTO.setRemainGift(entity.getRemainGift());
        outletPromotionDTO.setQuantityApplied(quantityApplied);
        outletPromotionDTO.setStartDateLong(entity.getStartDate().getTime());
        outletPromotionDTO.setEndDateLong(entity.getEndDate().getTime());
        return outletPromotionDTO;
    }

    public static DiscountDTO getDiscountDTOFromPromotion(OutletPromotionEntity entity){
        DiscountDTO discountDTO = new DiscountDTO();
        try {
            JSONObject jsonObject = new JSONObject(entity.getNewPromotionJson());
            JSONObject jsonObjectRule = jsonObject.getJSONObject("promotionRule");
            JSONObject discountsJS = jsonObject.getJSONObject("promotionRule").getJSONArray("discounts").getJSONObject(0);
            ObjectMapper oMapper = new ObjectMapper();
            discountDTO = oMapper.readValue(discountsJS.toString(), DiscountDTO.class); // json 2 obj
            Long expiredDate = null;
            try {
                expiredDate = jsonObjectRule.getLong("expiredDate");
            } catch (JSONException e) {
            }
            discountDTO.setExpiredDate(expiredDate);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return discountDTO;
    }
}
