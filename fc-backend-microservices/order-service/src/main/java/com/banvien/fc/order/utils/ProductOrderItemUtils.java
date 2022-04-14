package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.ProductOrderItemDTO;
import com.banvien.fc.order.dto.ProductOutletSkuDTO;
import com.banvien.fc.order.entity.ProductOrderItemEntity;

public class ProductOrderItemUtils {
    public static ProductOrderItemDTO entity2DTO(ProductOrderItemEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProductOrderItemDTO productOrderItemDTO = new ProductOrderItemDTO();

        productOrderItemDTO.setQuantity(entity.getQuantity());
        productOrderItemDTO.setPrice(entity.getPrice());
        productOrderItemDTO.setPriceDiscount(entity.getPriceDiscount());
        productOrderItemDTO.setQuantityDiscount(entity.getQuantityDiscount());
        productOrderItemDTO.setWeight(entity.getWeight());
        productOrderItemDTO.setProductOrderItemID(entity.getProductOrderItemId());

        ProductOutletSkuDTO productOutletSkuDTO = new ProductOutletSkuDTO();
        productOutletSkuDTO.setProductOutletSkuId(entity.getProductOutletSkuId());
        productOrderItemDTO.setProductOutletSku(productOutletSkuDTO);

        return productOrderItemDTO;
    }

    public static ProductOrderItemEntity DTO2entity(ProductOrderItemDTO dto) {
        if (dto == null ) {
            return null;
        }

        ProductOrderItemEntity productOrderItemEntity = new ProductOrderItemEntity();

        productOrderItemEntity.setQuantity(dto.getQuantity());
        productOrderItemEntity.setPrice(dto.getPrice());
        productOrderItemEntity.setPriceDiscount(dto.getPriceDiscount());
        productOrderItemEntity.setQuantityDiscount(dto.getQuantityDiscount());
        productOrderItemEntity.setWeight(dto.getWeight());
        productOrderItemEntity.setProductOrderItemId(dto.getProductOrderItemID());
        productOrderItemEntity.setProductOutletSkuId(dto.getProductOutletSku().getProductOutletSkuId());

        return productOrderItemEntity;
    }
}
