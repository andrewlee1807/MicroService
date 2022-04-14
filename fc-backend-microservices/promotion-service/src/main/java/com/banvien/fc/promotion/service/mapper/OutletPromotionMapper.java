package com.banvien.fc.promotion.service.mapper;

import com.banvien.fc.promotion.dto.OutletPromotionDTO;
import com.banvien.fc.promotion.entity.OutletPromotionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {})
@Component
public interface OutletPromotionMapper {
    @Mapping(source = "outletPromotionEntity.outletPromotionId", target = "outletPromotionId")
    @Mapping(source = "outletPromotionEntity.title", target = "title")
    @Mapping(source = "outletPromotionEntity.startDate", target = "startDate")
    @Mapping(source = "outletPromotionEntity.endDate", target = "endDate")
    @Mapping(source = "outletPromotionEntity.description", target = "description")
    @Mapping(source = "outletPromotionEntity.thumbnail", target = "thumbnail")
    OutletPromotionDTO from(OutletPromotionEntity outletPromotionEntity);
}
