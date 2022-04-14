package com.banvien.fc.promotion.dto.vansale;

import com.banvien.fc.promotion.dto.ViewItemDTO;

import java.util.ArrayList;
import java.util.List;

public class QuickSellCategoryDTO {
    private List<ViewItemDTO> categories;
    private List<ViewItemDTO> subCat;

    public QuickSellCategoryDTO(List<ViewItemDTO> categories, List<ViewItemDTO> subCat) {
        this.categories = categories;
        this.subCat = subCat;
    }

    public List<ViewItemDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<ViewItemDTO> categories) {
        this.categories = categories;
        if (categories == null) {
            this.categories = new ArrayList<>();
        }
    }

    public List<ViewItemDTO> getSubCat() {
        return subCat;
    }

    public void setSubCat(List<ViewItemDTO> subCat) {
        this.subCat = subCat;
        if (subCat == null) {
            this.subCat = new ArrayList<>();
        }
    }
}
