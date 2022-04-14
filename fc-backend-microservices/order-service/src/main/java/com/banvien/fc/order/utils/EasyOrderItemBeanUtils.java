package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.EasyOrderItemDTO;
import com.banvien.fc.order.entity.EasyOrderItemEntity;

public class EasyOrderItemBeanUtils {
    public static EasyOrderItemDTO entity2DTO(EasyOrderItemEntity entity) {
        EasyOrderItemDTO dto = new EasyOrderItemDTO();
        dto.setEasyOrderItemId(entity.getEasyOrderItemId());
        dto.setProductOutletSkuDTO(ProductOutletSkuBeanUtils.entity2DTO(entity.getProductOutletSkuEntity()));
        return dto;
    }

    public static EasyOrderItemEntity dto2Entity(EasyOrderItemDTO dto) {
        EasyOrderItemEntity entity = new EasyOrderItemEntity();
        if (dto.getEasyOrderDTO() != null) {
            entity.setEasyOrderEntity(EasyOrderBeanUtils.dto2Entity(dto.getEasyOrderDTO()));
        }
        return entity;
    }
}
