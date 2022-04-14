package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.ProductOutletDTO;
import com.banvien.fc.order.dto.ProductOutletSkuDTO;
import com.banvien.fc.order.entity.ProductOutletSkuEntity;

import java.util.ArrayList;
import java.util.List;

public class ProductOutletSkuBeanUtils {
    public static ProductOutletSkuDTO entity2DTO(ProductOutletSkuEntity entity) {
        ProductOutletSkuDTO dto = new ProductOutletSkuDTO();
        dto.setProductOutletSkuId(entity.getProductOutletSkuId());
        dto.setSkuCode(entity.getSkuCode());
        dto.setTitle(entity.getTitle());
        dto.setImage(entity.getImage());
        dto.setOriginalPrice(entity.getOriginalPrice());
        dto.setSalePrice(entity.getSalePrice());
        dto.setUnit(entity.getUnit());
        dto.setDisplayOrder(entity.getDisplayOrder());
        dto.setTax(entity.getTax());
        dto.setAlias(entity.getAlias());
        dto.setStatus(entity.getStatus());

        ProductOutletDTO productOutlet = new ProductOutletDTO();

        if (entity.getProductOutlet() != null) {
            productOutlet.setCode(entity.getProductOutlet().getCode());
            productOutlet.setName(entity.getProductOutlet().getName());
            productOutlet.setDescription(entity.getProductOutlet().getDescription());
            productOutlet.setOutletId(entity.getProductOutlet().getOutlet().getOutletId());
        }
        dto.setProductOutlet(productOutlet);

        return dto;
    }

    public static ProductOutletSkuEntity dto2Entity(ProductOutletSkuDTO dto) {
        ProductOutletSkuEntity entity = new ProductOutletSkuEntity();
        entity.setProductOutletSkuId(dto.getProductOutletSkuId());
        entity.setSkuCode(dto.getSkuCode());
        entity.setTitle(dto.getTitle());
        entity.setStatus(dto.getStatus());
        entity.setImage(dto.getImage());
        entity.setOriginalPrice(dto.getOriginalPrice());
        entity.setSalePrice(dto.getSalePrice());
        entity.setUnit(dto.getUnit());
        entity.setDisplayOrder(dto.getDisplayOrder());
        entity.setBarCode(dto.getBarCode());
        entity.setAlias(dto.getAlias());
        entity.setTax(dto.getTax());
        return entity;
    }

    public static List<ProductOutletSkuDTO> entity2DTO(List<ProductOutletSkuEntity> entities){
        List<ProductOutletSkuDTO> rs = new ArrayList<>();
        for (ProductOutletSkuEntity entity : entities) {
            rs.add(entity2DTO(entity));
        }
        return rs;
    }

}
