package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.OrderOutletDTO;
import com.banvien.fc.order.entity.OrderOutletEntity;

public class EasyOrderSumitBeanUtils {
    public static OrderOutletDTO entity2DTO(OrderOutletEntity entity) {
        OrderOutletDTO dto = new OrderOutletDTO();
        dto.setCode(entity.getCode());
        dto.setStatus(entity.getStatus());
        dto.setDateOrdered(entity.getCreatedDate());
        dto.setTotalPromotionDiscountPrice(entity.getTotalPromotionDiscountPrice());
        dto.setTotalStoreDiscountPrice(entity.getTotalStoreDiscountPrice());
        dto.setDeliveryPrice(entity.getDeliveryPrice());
        dto.setAmount(entity.getTotalPrice());
        dto.setTotalOriginalPrice(entity.getTotalOriginalPrice());
        dto.setTotalItem(entity.getTotalItem());
        dto.setShipDate(entity.getShipDate());
        dto.setOrderInformation(OrderInformationbeanUtils.entity2DTO(entity.getOrderInformation()));
        dto.setNote(entity.getNote());
        return dto;
    }
}
