package com.banvien.fc.catalog.dto;

import com.banvien.fc.catalog.dto.Enum.PromotionType;

import java.util.List;

public class CatalogPromotionDTO {
    private Long outletId;
    private List<Long> ids;
    private PromotionType type;

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public PromotionType getType() {
        return type;
    }

    public void setType(PromotionType type) {
        this.type = type;
    }
}
