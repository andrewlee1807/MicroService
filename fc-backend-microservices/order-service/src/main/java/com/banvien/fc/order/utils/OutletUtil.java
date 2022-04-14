package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.*;
import com.banvien.fc.order.entity.*;
//import com.google.maps.GeoApiContext;

import java.util.ArrayList;
import java.util.List;

public class OutletUtil {

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
            UserGroupEntity userGroupEntity = entity.getShopman().getUserGroup();
            dto.setShopman(shopman);
        }
        dto.setApproveCreditKey(entity.getApproveCreditKey());
        dto.setLoyaltyPointExpiredMonth(entity.getLoyaltyPointExpiredMonth());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setAddress(entity.getAddress());
        return dto;
    }
}
