package com.banvien.fc.promotion.utils;

import com.banvien.fc.promotion.dto.BlockProductDTO;
import com.banvien.fc.promotion.entity.BlockProductAdminEntity;

import java.util.ArrayList;
import java.util.List;

public class BlockProductAdminUtils {
    public static BlockProductDTO entity2dto(BlockProductAdminEntity entity) {
        BlockProductDTO dto = new BlockProductDTO();
        dto.setCategory(entity.getCategory());
        dto.setCode(entity.getCode());
        dto.setId(entity.getProductId());
        dto.setImage(entity.getImage());
        dto.setName(entity.getName());
        if (entity.getStatus())
            dto.setStatus(1);
        else dto.setStatus(0);
        return dto;
    }

    public static List<BlockProductDTO> entity2dto(List<BlockProductAdminEntity> entities){
        List<BlockProductDTO> rs = new ArrayList<>();
        for (BlockProductAdminEntity entity : entities) {
            rs.add(entity2dto(entity));
        }
        return rs;
    }
}
