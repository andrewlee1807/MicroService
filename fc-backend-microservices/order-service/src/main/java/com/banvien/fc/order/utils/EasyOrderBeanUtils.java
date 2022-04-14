package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.EasyOrderDTO;
import com.banvien.fc.order.dto.OutletDTO;
import com.banvien.fc.order.dto.UserDTO;
import com.banvien.fc.order.entity.EasyOrderEntity;
import com.banvien.fc.order.entity.EasyOrderItemEntity;
import com.banvien.fc.order.entity.OutletEntity;
import com.banvien.fc.order.entity.UserEntity;

import java.util.ArrayList;

public class EasyOrderBeanUtils {
    public static EasyOrderDTO entity2DTO(EasyOrderEntity entity) {
        EasyOrderDTO dto = new EasyOrderDTO();

        dto.setEasyOrderId(entity.getEasyOrderId());
        dto.setName(entity.getName());
        dto.setTotalViewer(entity.getTotalViewer());
        dto.setPcImage(entity.getPcImage());
        dto.setMobileImage(entity.getMobileImage());
        dto.setEasyOrderCode(entity.getEasyOrderCode());
        dto.setOutletDTO(entity2DTO(entity.getOutletEntity()));

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(entity.getCreatedBy().getUserId());
        dto.setCreatedBy(userDTO);
        dto.setCreatedDate(entity.getCreatedDate());

        dto.setModifiedDate(entity.getModifiedDate());
        if (entity.getModifiedBy() != null) {
            userDTO = new UserDTO();
            userDTO.setUserId(entity.getModifiedBy().getUserId());
            dto.setModifiedBy(userDTO);
        }

        if (entity.getEasyOrderItemEntities() != null && !entity.getEasyOrderItemEntities().isEmpty()) {
            dto.setEasyOrderItemDTOS(new ArrayList<>());
            for(EasyOrderItemEntity itemEntity : entity.getEasyOrderItemEntities()) {
                dto.getEasyOrderItemDTOS().add(EasyOrderItemBeanUtils.entity2DTO(itemEntity));
            }
        }

        return dto;
    }

    public static OutletDTO entity2DTO(OutletEntity entity) {
        OutletDTO dto = new OutletDTO(entity.getOutletId(), entity.getDistributorId(), entity.getCode(),
                entity.getName(), entity.getPhoneNumber(), entity.getAddress(),
                entity.getLongitude(), entity.getLatitude(), entity.getCreatedby(), entity.getCreatedDate(),
                entity.getModifiedBy(), entity.getModifiedDate(), entity.getTop(), entity.getTotalView(), entity.getReferralCode(), entity.getAverageRating(), entity.getTotalRating(), entity.getLoyaltyRate(), entity.getPriceRate());
        dto.setImage(entity.getImage());
        dto.setStatus(entity.getStatus());
        UserEntity userEntity = entity.getShopman();
        if (userEntity != null) {
            UserDTO shopman = new UserDTO();
            shopman.setUserId(userEntity.getUserId());
            shopman.setFullName(userEntity.getFullName());
            shopman.setEmail(userEntity.getEmail());
            shopman.setUserName(userEntity.getUserName());
            shopman.setUserId(userEntity.getUserId());
            shopman.setStatus(userEntity.getStatus());
            dto.setShopman(shopman);
        }
        dto.setApproveCreditKey(entity.getApproveCreditKey());
        dto.setLoyaltyPointExpiredMonth(entity.getLoyaltyPointExpiredMonth());
        dto.setCountryId(entity.getCountryId());
        return dto;
    }

    public static EasyOrderEntity dto2Entity(EasyOrderDTO dto) {
        EasyOrderEntity entity = new EasyOrderEntity();
        entity.setName(dto.getName());
        entity.setTotalViewer(dto.getTotalViewer());
        entity.setPcImage(dto.getPcImage());
        entity.setEasyOrderCode(dto.getEasyOrderCode());

        if (dto.getOutletDTO() != null) {
            OutletEntity outletEntity = new OutletEntity();
            outletEntity.setOutletId(dto.getOutletDTO().getOutletId());
            entity.setOutletEntity(outletEntity);
        }

        entity.setCreatedDate(dto.getCreatedDate());
        if (dto.getCreatedBy() != null && dto.getCreatedBy().getUserId() != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(dto.getCreatedBy().getUserId());
            entity.setCreatedBy(userEntity);
        }
        entity.setModifiedDate(dto.getModifiedDate());
        if (dto.getModifiedBy() != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(dto.getModifiedBy().getUserId());
            entity.setModifiedBy(userEntity);
        }

        return entity;
    }
}
