package com.banvien.fc.promotion.utils;

import com.banvien.fc.promotion.dto.ProductOutletDTO;
import com.banvien.fc.promotion.dto.ProductOutletSkuDTO;
import com.banvien.fc.promotion.entity.ProductOutletSkuEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductOutletSkuBeanUtils {
    public static ProductOutletSkuDTO entity2DTO(ProductOutletSkuEntity entity) {
        ProductOutletSkuDTO dto = new ProductOutletSkuDTO();
        dto.setProductOutletSkuId(entity.getProductOutletSkuId());

        ProductOutletDTO productOutletDTO = new ProductOutletDTO();
        productOutletDTO.setProductOutletId(entity.getProductOutletId());
        dto.setProductOutlet(productOutletDTO);
        dto.setTitle(entity.getTitle());
        dto.setImage(entity.getImage());
        dto.setOriginalPrice(Double.parseDouble(entity.getOriginalPrice()));
        dto.setSalePrice(entity.getSalePrice());
        dto.setUnit(entity.getUnit());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public static ProductOutletSkuEntity dto2Entity(ProductOutletSkuDTO dto) {
        ProductOutletSkuEntity entity = new ProductOutletSkuEntity();
        entity.setProductOutletSkuId(dto.getProductOutletSkuId());
        entity.setTitle(dto.getTitle());
        entity.setStatus(dto.getStatus());
        entity.setImage(dto.getImage());
        entity.setSalePrice(dto.getSalePrice());
        entity.setUnit(dto.getUnit());
        return entity;
    }

    public static List<ProductOutletSkuDTO> entity2DTO(List<ProductOutletSkuEntity> entities) {
        List<ProductOutletSkuDTO> rs = new ArrayList<>();
        for (ProductOutletSkuEntity entity : entities) {
            rs.add(entity2DTO(entity));
        }
        return rs;
    }

}
