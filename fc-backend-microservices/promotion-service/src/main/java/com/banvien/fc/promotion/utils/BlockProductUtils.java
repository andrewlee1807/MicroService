package com.banvien.fc.promotion.utils;

import com.banvien.fc.promotion.dto.BlockProductDTO;
import com.banvien.fc.promotion.entity.BlockProductEntity;

import java.util.ArrayList;
import java.util.List;

public class BlockProductUtils {
    public static BlockProductDTO entity2dto(BlockProductEntity entity) {
        BlockProductDTO dto = new BlockProductDTO();
        dto.setCategory(entity.getCategory());
        dto.setCode(entity.getSkuCode());
        dto.setId(entity.getProductOutletSkuId());
        dto.setImage(entity.getImage());
        dto.setName(entity.getName());
        dto.setSellingPrice(entity.getSalePrice());
        dto.setStatus(entity.getStatus());
        dto.setTotalview(entity.getTotalView());
        return dto;
    }

    public static List<BlockProductDTO> entity2dto(List<BlockProductEntity> entities){
        List<BlockProductDTO> rs = new ArrayList<>();
        for (BlockProductEntity entity : entities) {
            rs.add(entity2dto(entity));
        }
        return rs;
    }
}
