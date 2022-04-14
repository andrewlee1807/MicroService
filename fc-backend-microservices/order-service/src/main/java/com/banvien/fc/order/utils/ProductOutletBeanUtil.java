package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.mobile.ProductOutletMobileDTO;
import com.banvien.fc.order.dto.mobile.ProductOutletSkuMobileDTO;
import com.banvien.fc.order.entity.ProductOutletEntity;
import com.banvien.fc.order.entity.ProductOutletSkuEntity;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductOutletBeanUtil {

    public static ProductOutletMobileDTO entityToMobile(ProductOutletEntity entity, Map<Long, Integer> mapProductQuantity, DecimalFormat df) {
        ProductOutletMobileDTO productOutletMobileDTO = new ProductOutletMobileDTO();
        productOutletMobileDTO.setProductOutletId(entity.getProductOutletId());
        productOutletMobileDTO.setOutletId(entity.getOutlet().getOutletId());
        productOutletMobileDTO.setTop(entity.getTop());
        if (entity.getProduct().getBrand() != null) {
            productOutletMobileDTO.setBrandId(entity.getProduct().getBrand().getBrandId());
        }
        if (entity.getProduct().getCatGroup() != null) {
            productOutletMobileDTO.setCatGroupId(entity.getProduct().getCatGroup().getCatGroupId());
        }
        productOutletMobileDTO.setProductName(entity.getName());
        productOutletMobileDTO.setProductImage(entity.getThumbnail());
        if (mapProductQuantity != null && mapProductQuantity.containsKey(entity.getProductOutletId())) {
            productOutletMobileDTO.setNumberInCart(mapProductQuantity.get(entity.getProductOutletId()));
        }
        List<ProductOutletSkuEntity> skuEntities = entity.getProductOutletSkus();
        List<ProductOutletSkuMobileDTO> skuMobileDTOS = new ArrayList<ProductOutletSkuMobileDTO>();
        if (skuEntities != null && skuEntities.size() > 0) {
            for (ProductOutletSkuEntity productOutletSkuEntity : skuEntities) {
                if(productOutletSkuEntity.getStatus() != null && productOutletSkuEntity.getStatus().equals(CoreConstants.ACTIVE_STATUS)) {
                    skuMobileDTOS.add(productOutletSkuSkuEntity2DTO(productOutletSkuEntity, df));
                }
            }
        }
        productOutletMobileDTO.setSkus(skuMobileDTOS);

        return productOutletMobileDTO;
    }

    public static ProductOutletMobileDTO entityToMobileKeepIndex(ProductOutletEntity entity, Map<Long, Integer> mapProductQuantity, DecimalFormat df, List<Long> productOutletSkuIds) {
        ProductOutletMobileDTO productOutletMobileDTO = new ProductOutletMobileDTO();
        productOutletMobileDTO.setProductOutletId(entity.getProductOutletId());
        productOutletMobileDTO.setOutletId(entity.getOutlet().getOutletId());
        productOutletMobileDTO.setProductMasterId(entity.getProduct().getProductId());
        productOutletMobileDTO.setTop(entity.getTop());
        if (entity.getProduct().getBrand() != null) {
            productOutletMobileDTO.setBrandId(entity.getProduct().getBrand().getBrandId());
        }
        if (entity.getProduct().getCatGroup() != null) {
            productOutletMobileDTO.setCatGroupId(entity.getProduct().getCatGroup().getCatGroupId());
        }
        productOutletMobileDTO.setProductName(entity.getName());
        productOutletMobileDTO.setProductImage(entity.getThumbnail());
        if (mapProductQuantity != null && mapProductQuantity.containsKey(entity.getProductOutletId())) {
            productOutletMobileDTO.setNumberInCart(mapProductQuantity.get(entity.getProductOutletId()));
        }
        List<ProductOutletSkuEntity> skuEntities = entity.getProductOutletSkus();
        List<ProductOutletSkuMobileDTO> skuMobileDTOS = new ArrayList<ProductOutletSkuMobileDTO>();
        if (skuEntities != null && skuEntities.size() > 0) {
            for (ProductOutletSkuEntity productOutletSkuEntity : skuEntities) {
                if (productOutletSkuIds.contains(productOutletSkuEntity.getProductOutletSkuId())) {
                    skuMobileDTOS.add(productOutletSkuSkuEntity2DTO(productOutletSkuEntity, df));
                    productOutletSkuIds.remove(productOutletSkuEntity.getProductOutletSkuId());
                    break;
                }
            }
        }
        productOutletMobileDTO.setSkus(skuMobileDTOS);

        return productOutletMobileDTO;
    }

    public static ProductOutletSkuMobileDTO productOutletSkuSkuEntity2DTO(ProductOutletSkuEntity productOutletSkuEntity, DecimalFormat df) {
        ProductOutletSkuMobileDTO sku = new ProductOutletSkuMobileDTO();
        sku.setProductOutletSkuId(productOutletSkuEntity.getProductOutletSkuId());
        sku.setSalePrice(productOutletSkuEntity.getSalePrice());
        sku.setOriginalPrice(productOutletSkuEntity.getSalePrice());
        sku.setImage(productOutletSkuEntity.getImage());
        sku.setSkuCode(productOutletSkuEntity.getSkuCode());
        sku.setTitle(productOutletSkuEntity.getTitle());
        sku.setDisplayOrder(productOutletSkuEntity.getDisplayOrder());
        sku.setUnit(productOutletSkuEntity.getUnit());
        sku.setStatus(productOutletSkuEntity.getStatus());
        sku.setDiscountPercent(calculatePercent(sku.getOriginalPrice(), sku.getSalePrice(), df));

        return sku;
    }


    public static ProductOutletMobileDTO entityToMobile(ProductOutletEntity entity, Map<Long, Integer> mapProductQuantity, DecimalFormat df, Map<Long, Double> percentDiscountSkuMap) {
        ProductOutletMobileDTO productOutletMobileDTO = new ProductOutletMobileDTO();
        productOutletMobileDTO.setProductOutletId(entity.getProductOutletId());
        productOutletMobileDTO.setOutletId(entity.getOutlet().getOutletId());
        productOutletMobileDTO.setProductName(entity.getName());
        productOutletMobileDTO.setProductImage(entity.getThumbnail());
        if (mapProductQuantity != null && mapProductQuantity.containsKey(entity.getProductOutletId())) {
            productOutletMobileDTO.setNumberInCart(mapProductQuantity.get(entity.getProductOutletId()));
        }
        List<ProductOutletSkuEntity> skuEntities = entity.getProductOutletSkus();
        List<ProductOutletSkuMobileDTO> skuMobileDTOS = new ArrayList<ProductOutletSkuMobileDTO>();
        for (ProductOutletSkuEntity productOutletSkuEntity : skuEntities) {
            ProductOutletSkuMobileDTO sku = new ProductOutletSkuMobileDTO();
            sku.setProductOutletSkuId(productOutletSkuEntity.getProductOutletSkuId());
            sku.setSalePrice(productOutletSkuEntity.getSalePrice());
            sku.setOriginalPrice(productOutletSkuEntity.getSalePrice());
            sku.setImage(productOutletSkuEntity.getImage());
            sku.setTitle(productOutletSkuEntity.getTitle());
            sku.setDisplayOrder(productOutletSkuEntity.getDisplayOrder());
            sku.setUnit(productOutletSkuEntity.getUnit());
            if (!percentDiscountSkuMap.isEmpty() && percentDiscountSkuMap.containsKey(productOutletSkuEntity.getProductOutletSkuId())) {
                sku.setDiscountPercent(df.format(percentDiscountSkuMap.get(sku.getProductOutletSkuId())));
                sku.setPromotionPrice(sku.getSalePrice() - (sku.getSalePrice() * (percentDiscountSkuMap.get(sku.getProductOutletSkuId()).doubleValue() / 100)));
            }

            skuMobileDTOS.add(sku);
        }
        productOutletMobileDTO.setSkus(skuMobileDTOS);

        return productOutletMobileDTO;
    }


    public static String calculatePercent(Double originalPrice, Double discountPrice, DecimalFormat df) {
        if (originalPrice != null && discountPrice != null) {
            if (originalPrice != 0 && discountPrice != 0 && originalPrice > discountPrice) {
                Double val = ((originalPrice - discountPrice) / originalPrice) * 100;
                if(df == null) {
                    df = new DecimalFormat("#.##");
                }
                return df.format(val);
            }
        }
        return "0";
    }

    public static String getProductStatus(int status) {
        if (status == 1) {
            return "ACTIVE";
        } else if (status == 0) {
            return "INACTIVE";
        } else {
            return "STOP";
        }
    }
}
