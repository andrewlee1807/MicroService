package com.banvien.fc.promotion.utils;

import com.banvien.fc.promotion.dto.mobile.ProductOutletMobileDTO;
import com.banvien.fc.promotion.dto.mobile.ProductOutletSkuMobileDTO;
import com.banvien.fc.promotion.entity.ProductOutletEntity;
import com.banvien.fc.promotion.entity.ProductOutletSkuEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductOutletBeanUtils {
    public static ProductOutletMobileDTO entity2DtoMobile(ProductOutletEntity productOutletEntity, List<ProductOutletSkuEntity> productOutletSkuEntities) {
        ProductOutletMobileDTO productOutletMobileDTO = new ProductOutletMobileDTO();
        productOutletMobileDTO.setProductOutletId(productOutletEntity.getProductOutletId());
        productOutletMobileDTO.setOutletId(productOutletEntity.getOutletId());
        productOutletMobileDTO.setProductName(productOutletEntity.getName());
        productOutletMobileDTO.setProductImage(productOutletEntity.getThumbnail());

        //skus
        List<ProductOutletSkuMobileDTO> skus = new ArrayList<>();
        if (productOutletSkuEntities.size() > 0) {
            for (ProductOutletSkuEntity productOutletSkuEntity : productOutletSkuEntities) {
                ProductOutletSkuMobileDTO sku = new ProductOutletSkuMobileDTO();
                sku.setProductOutletSkuId(productOutletSkuEntity.getProductOutletSkuId());
                sku.setSalePrice(productOutletSkuEntity.getSalePrice());
                sku.setOriginalPrice(productOutletSkuEntity.getSalePrice());
                sku.setImage(productOutletSkuEntity.getImage());
                sku.setTitle(productOutletSkuEntity.getTitle());
                sku.setUnit(productOutletSkuEntity.getUnit());
                sku.setHasPromotion(true);
                skus.add(sku);
            }
        }

        productOutletMobileDTO.setSkus(skus);
        productOutletMobileDTO.setProductCode(productOutletEntity.getCode());
        productOutletMobileDTO.setStatus(productOutletEntity.getStatus());
        productOutletMobileDTO.setHasPromotion(true);

        return productOutletMobileDTO;
    }

    public static List<ProductOutletMobileDTO> entities2DtoMobile(List<ProductOutletEntity> productOutletEntities, Map<Long, List<ProductOutletSkuEntity>> skusMap) {
        List<ProductOutletMobileDTO> rs = new ArrayList<>();
        for (ProductOutletEntity productOutletEntity : productOutletEntities) {
            List<ProductOutletSkuEntity> skus = new ArrayList<>();
            if (skusMap.size() > 0) {
                skus = skusMap.get(productOutletEntity.getProductOutletId());
            }
            rs.add(ProductOutletBeanUtils.entity2DtoMobile(productOutletEntity, skus));
        }
        return rs;
    }
}
