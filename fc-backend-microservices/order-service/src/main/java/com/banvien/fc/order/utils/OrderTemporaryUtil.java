package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.OrderInformationMobileDTO;
import com.banvien.fc.order.entity.OrderTemporaryEntity;

import java.sql.Timestamp;

public class OrderTemporaryUtil {
    public static OrderInformationMobileDTO entity2OrderMobileDTO(OrderTemporaryEntity orderTemporaryEntity){
        OrderInformationMobileDTO dto = new OrderInformationMobileDTO();
        dto.setOutletId(orderTemporaryEntity.getOutletId());
        dto.setCustomerId(orderTemporaryEntity.getCustomerId());
        dto.setAmountAfterUsePoint(orderTemporaryEntity.getAmountAfterUsePoint());
        dto.setOrderOutletCode(RandomTokenBeanUtil.generateOrderCode());
        dto.setCreateDate(new Timestamp(System.currentTimeMillis()));
        dto.setPromotionCode(orderTemporaryEntity.getPromotionCode());
        dto.setReceiverName(orderTemporaryEntity.getReceiverName());
        dto.setReceiverPhone(orderTemporaryEntity.getReceiverPhone());
        dto.setReceiverAddress(orderTemporaryEntity.getReceiverAddress());
        dto.setReceiverLat(orderTemporaryEntity.getReceiverLat());
        dto.setReceiverLng(orderTemporaryEntity.getReceiverLng());
        dto.setDeliveryMethod(orderTemporaryEntity.getDeliveryMethod());
        dto.setSalesManUserId(orderTemporaryEntity.getSalesManUserId());
        dto.setUsedPoint(orderTemporaryEntity.getUsedPoint());
        dto.setAmountAfterUsePoint(orderTemporaryEntity.getAmountAfterUsePoint());
        dto.setPayment(orderTemporaryEntity.getPayment());
        dto.setNote(orderTemporaryEntity.getNote());
        dto.setDeliveryDate(orderTemporaryEntity.getDeliveryDate());
        dto.setGrabTxId(orderTemporaryEntity.getGrabTxId());
        dto.setGrabGroupTxId(orderTemporaryEntity.getGrabGroupTxId());
        dto.setToken(orderTemporaryEntity.getToken());
        dto.setOrderOnBeHalf(orderTemporaryEntity.getOrderOnBeHalf());
        dto.setConfirmed(orderTemporaryEntity.getConfirmed());
        dto.setState(orderTemporaryEntity.getState());
        return dto;
    }
}
