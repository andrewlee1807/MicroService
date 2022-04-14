package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.ProductOrderItemDTO;
import com.banvien.fc.order.dto.ProductOutletSkuDTO;
import com.banvien.fc.order.entity.ProductOrderItemEntity;
import com.banvien.fc.order.entity.ProductOutletSkuEntity;

public class OrderItemUtils {
    public static ProductOrderItemDTO entity2DTO(ProductOrderItemEntity entity){
        ProductOrderItemDTO rs = new ProductOrderItemDTO();
        rs.setPrice(entity.getPrice());
        rs.setQuantity(entity.getQuantity());
        rs.setPriceDiscount(entity.getPriceDiscount());
        rs.setQuantityDiscount(entity.getQuantityDiscount());
        return rs;
    }

}
