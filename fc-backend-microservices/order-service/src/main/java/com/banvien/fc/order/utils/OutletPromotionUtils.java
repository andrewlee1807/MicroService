package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.OutletPromotionDTO;
import com.banvien.fc.order.entity.OutletPromotionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OutletPromotionUtils {
    public static OutletPromotionDTO entity2Dto(OutletPromotionEntity outletPromotionEntity){
        OutletPromotionDTO outletPromotionDTO = new OutletPromotionDTO();
        outletPromotionDTO.setOutletPromotionId(outletPromotionEntity.getOutletPromotionId());
        outletPromotionDTO.setTitle(outletPromotionEntity.getTitle());
        outletPromotionDTO.setContentXML(outletPromotionEntity.getContentXML());
        outletPromotionDTO.setDescription(outletPromotionEntity.getDescription());
        outletPromotionDTO.setNotifyContent(outletPromotionEntity.getNotifyContent());
        outletPromotionDTO.setCreatedDate(outletPromotionEntity.getCreatedDate());
        outletPromotionDTO.setStartDate(outletPromotionEntity.getStartDate());
        outletPromotionDTO.setEndDate(outletPromotionEntity.getEndDate());
        outletPromotionDTO.setNotifySendDate(outletPromotionEntity.getNotifySendDate());
        outletPromotionDTO.setRemainGift(outletPromotionEntity.getRemainGift());
        outletPromotionDTO.setTotalGift(outletPromotionEntity.getTotalGift());
        outletPromotionDTO.setThumbnail(outletPromotionEntity.getThumbnail());
        outletPromotionDTO.setStatus(outletPromotionEntity.getStatus());
        return outletPromotionDTO;
    }

    public static List<OutletPromotionDTO> entity2dto(List<OutletPromotionEntity> entities){
        return entities.stream().map(OutletPromotionUtils::entity2Dto).collect(Collectors.toList());
    }
}

