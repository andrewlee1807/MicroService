package com.banvien.fc.order.utils;


import com.banvien.fc.order.dto.NotifyTemplateDTO;
import com.banvien.fc.order.entity.NotifyTemplateEntity;


public class NotifyTemplateBeanUtil {
    public static NotifyTemplateDTO entity2DTO(NotifyTemplateEntity entity) {
        NotifyTemplateDTO dto = new NotifyTemplateDTO(
                entity.getNotifyTemplateId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getStatus(),
                entity.getContentEn(),
                entity.getContentVi(),
                entity.getContentMa(),
                entity.getContentId(),
                entity.getContentCn(),
                entity.getNote()
        );

        return dto;
    }


    public static NotifyTemplateEntity dto2Entity(NotifyTemplateDTO dto) {
        NotifyTemplateEntity entity = new NotifyTemplateEntity(dto.getNotifyTemplateId(),
                dto.getCode(),
                dto.getTitle(),
                dto.getStatus(),
                dto.getContentEn(),
                dto.getContentVi(),
                dto.getContentMa(),
                dto.getContentId(),
                dto.getContentCn(),
                dto.getNote()
        );

        return entity;
    }
}
