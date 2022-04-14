package com.banvien.fc.promotion.utils;

import com.banvien.fc.promotion.dto.vansale.CampaignDTO;
import com.banvien.fc.promotion.entity.CampaignEntity;
import com.banvien.fc.promotion.entity.OutletEntity;

import java.util.ArrayList;
import java.util.List;

public class CampaignBeanUtils {
    public static CampaignDTO entity2DTO(CampaignEntity entity) {
        CampaignDTO dto = new CampaignDTO();
        dto.setCampaignId(entity.getCampaignId());
        dto.setTitle(entity.getTitle());
        dto.setImage(entity.getImage());
        dto.setStatus(entity.getStatus());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setTotalLike(entity.getTotalLike());
        dto.setTotalShare(entity.getTotalShare());
        dto.setDescription(entity.getDescription());
        dto.setSponsor(entity.getSponsor());
        dto.setPostUrl(entity.getPostUrl());
        dto.setPlaySequence(entity.getPlaySequence());
        dto.setMaxReplay(entity.getMaxReplay());
        dto.setMaxParticipantNumber(entity.getMaxParticipantNumber());
        return dto;
    }

    public static List<CampaignDTO> entity2DTO(List<CampaignEntity> entities) {
        List<CampaignDTO> dtos = new ArrayList<CampaignDTO>();

        for (CampaignEntity entity : entities)
            dtos.add(entity2DTO(entity));

        return dtos;
    }

    public static CampaignEntity dto2Entity(CampaignDTO dto) {
        CampaignEntity entity = new CampaignEntity();
        entity.setCampaignId(dto.getCampaignId());
        entity.setTitle(dto.getTitle());
        entity.setImage(dto.getImage());
        entity.setStatus(dto.getStatus());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setTotalLike(dto.getTotalLike());
        entity.setSponsor(dto.getSponsor());
        entity.setTotalShare(dto.getTotalShare());
        entity.setDescription(dto.getDescription());
        entity.setMaxParticipantNumber(dto.getMaxParticipantNumber());
        entity.setPostUrl(dto.getPostUrl());
        entity.setPlaySequence(dto.getPlaySequence() != null ? dto.getPlaySequence() : Boolean.FALSE);
        entity.setMaxReplay(dto.getMaxReplay());

        if (dto.getOutlet() != null) {
            OutletEntity outletEntity = new OutletEntity();
            outletEntity.setOutletId(dto.getOutlet().getOutletId());
            entity.setOutlet(outletEntity);
        }

        return entity;
    }

}
