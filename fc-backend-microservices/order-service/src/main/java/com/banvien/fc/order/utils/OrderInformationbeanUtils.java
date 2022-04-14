package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.OrderInformationDTO;
import com.banvien.fc.order.dto.OrderInformationMobileDTO;
import com.banvien.fc.order.entity.OrderInformationEntity;

public class OrderInformationbeanUtils {
    public static OrderInformationDTO entity2DTO(OrderInformationEntity entity) {
        OrderInformationDTO orderInformationDTO = new OrderInformationDTO();
        orderInformationDTO.setDeliveryMethod(entity.getDeliveryMethod());
        orderInformationDTO.setPaymentMethod(entity.getPaymentMethod());
        orderInformationDTO.setReceiverName(entity.getReceiverName());
        orderInformationDTO.setReceiverPhone(entity.getReceiverPhone());
        orderInformationDTO.setReceiverAddress(entity.getReceiverAddress());
        return orderInformationDTO;
    }

    public static OrderInformationEntity dto2Entity(OrderInformationMobileDTO dto) {
        OrderInformationEntity entity = new OrderInformationEntity();
        if (dto != null) {
            entity.setDeliveryMethod(dto.getDeliveryMethod());
            entity.setOrderInformationId(dto.getOrderInformationId());
            entity.setPaymentMethod(dto.getPayment());
            entity.setReceiverName(dto.getReceiverName());
            entity.setReceiverPhone(dto.getReceiverPhone());
            entity.setReceiverAddress(dto.getReceiverAddress());
            entity.setReceiverLat(dto.getReceiverLat());
            entity.setReceiverLng(dto.getReceiverLng());
        }
        return entity;
    }
}
