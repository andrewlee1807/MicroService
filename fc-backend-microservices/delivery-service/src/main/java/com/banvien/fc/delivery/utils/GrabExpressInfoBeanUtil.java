package com.banvien.fc.delivery.utils;


import com.banvien.fc.delivery.dto.grabexpress.GrabExpressInfoDTO;
import com.banvien.fc.delivery.entity.GrabExpressInfoEntity;

public class GrabExpressInfoBeanUtil {
    public static GrabExpressInfoEntity dto2Entity(GrabExpressInfoDTO grabExpressInfoDTO) {
        GrabExpressInfoEntity grabExpressInfoEntity = new GrabExpressInfoEntity();
        grabExpressInfoEntity.setGrabexpressinfoid(grabExpressInfoDTO.getGrabexpressinfoid());
        grabExpressInfoEntity.setDeliveryid(grabExpressInfoDTO.getDeliveryid());
        grabExpressInfoEntity.setOrdermerchantid(grabExpressInfoDTO.getOrdermerchantid());
        grabExpressInfoEntity.setJsonresponse(grabExpressInfoDTO.getJsonresponse());
        grabExpressInfoEntity.setNote(grabExpressInfoDTO.getNote());
        grabExpressInfoEntity.setStatus(grabExpressInfoDTO.getStatus());
        return grabExpressInfoEntity;
    }

    public static GrabExpressInfoDTO entity2Dto(GrabExpressInfoEntity grabExpressInfoEntity) {
        GrabExpressInfoDTO grabExpressInfoDTO = new GrabExpressInfoDTO();
        grabExpressInfoDTO.setGrabexpressinfoid(grabExpressInfoEntity.getGrabexpressinfoid());
        grabExpressInfoDTO.setDeliveryid(grabExpressInfoEntity.getDeliveryid());
        grabExpressInfoDTO.setOrdermerchantid(grabExpressInfoEntity.getOrdermerchantid());
        grabExpressInfoDTO.setJsonresponse(grabExpressInfoEntity.getJsonresponse());
        grabExpressInfoDTO.setNote(grabExpressInfoEntity.getNote());
        grabExpressInfoDTO.setStatus(grabExpressInfoEntity.getStatus());
        return grabExpressInfoDTO;
    }
}
