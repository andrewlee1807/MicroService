package com.banvien.fc.promotion.utils;

import com.banvien.fc.common.util.CommonUtils;
import com.banvien.fc.promotion.dto.AdminPromotionDTO;
import com.banvien.fc.promotion.entity.AdminPromotionEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AdminPromotionUtils {

    public static AdminPromotionDTO entity2DTO(AdminPromotionEntity entity) {
        AdminPromotionDTO dto = new AdminPromotionDTO();
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setEnddate(entity.getEndDate());
        dto.setStartdate(entity.getStartDate());
        dto.setGroupCode(entity.getGroupCode());
        dto.setNumberPromotion(entity.getNumberPromotion());
        dto.setStatus(entity.getStatus());
        dto.setId(entity.getAdminPromotionId());
        dto.setName(entity.getName());
        return dto;
    }

    public static List<AdminPromotionDTO> entity2DTO(List<AdminPromotionEntity> entities) {
        List<AdminPromotionDTO> dtos = new ArrayList<>();
        for (AdminPromotionEntity entity : entities) {
            dtos.add(entity2DTO(entity));
        }
        return dtos;
    }

    public static AdminPromotionEntity dto2Entity(AdminPromotionDTO dto) {
        AdminPromotionEntity entity = new AdminPromotionEntity();
        entity.setCreatedDate(Timestamp.valueOf(dto.getCreatedDate()));
        entity.setEndDate(Timestamp.valueOf(dto.getEnddate()));
        entity.setStartDate(Timestamp.valueOf(dto.getStartdate()));
        entity.setGroupCode(dto.getGroupCode());
        entity.setNumberPromotion(dto.getNumberPromotion());
        entity.setStatus(dto.getStatus());
        entity.setAdminPromotionId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static List<AdminPromotionEntity> dto2Entity(List<AdminPromotionDTO> dtos) {
        List<AdminPromotionEntity> entities = new ArrayList<>();
        for (AdminPromotionDTO dto : dtos) {
            entities.add(dto2Entity(dto));
        }
        return entities;
    }
}
