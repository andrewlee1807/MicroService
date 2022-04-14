package com.banvien.fc.promotion.dto.vansale;

import com.banvien.fc.promotion.dto.ProductOutletSkuDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubCatDTO {
    private Long catGroupId;
    private List<ProductOutletSkuDTO> products;
    private String name;
    private String code;
    private String image;

    public SubCatDTO() {

    }

    public SubCatDTO(Long catGroupId, List<ProductOutletSkuDTO> products, String name, String code, String image) {
        this.catGroupId = catGroupId;
        this.products = products;
        if (products == null) {
            this.products = new ArrayList<>();
        }
        this.name = name;
        this.code = code;
        this.image = image;
    }

    public Long getCatGroupId() {
        return catGroupId;
    }

    public void setCatGroupId(Long catGroupId) {
        this.catGroupId = catGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ProductOutletSkuDTO> getProducts() {
        if (products == null) {
            this.products = new ArrayList<>();
        }
        return products;
    }

    public void setProducts(List<ProductOutletSkuDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubCatDTO subCatDTO = (SubCatDTO) o;
        return Objects.equals(catGroupId, subCatDTO.catGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catGroupId);
    }
}
