package com.banvien.fc.promotion.dto.vansale;

import com.banvien.fc.promotion.dto.ViewItemDTO;

import java.util.List;

public class VansaleProductDTO {
    private List<ViewItemDTO> category;
    private List<SubCatDTO> subCat;

    public List<ViewItemDTO> getCategory() {
        return category;
    }

    public void setCategory(List<ViewItemDTO> category) {
        this.category = category;
    }

    public List<SubCatDTO> getSubCat() {
        return subCat;
    }

    public void setSubCat(List<SubCatDTO> subCat) {
        this.subCat = subCat;
    }
}
