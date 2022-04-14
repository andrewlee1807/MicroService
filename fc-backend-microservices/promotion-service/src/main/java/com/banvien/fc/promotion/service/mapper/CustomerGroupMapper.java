package com.banvien.fc.promotion.service.mapper;

import com.banvien.fc.common.service.mapper.EntityMapper;
import com.banvien.fc.promotion.dto.CustomerGroupDTO;
import com.banvien.fc.promotion.entity.CustomerGroupEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by son.nguyen on 9/13/2020.
 */
@Mapper(componentModel = "spring")
@Component
public interface CustomerGroupMapper extends EntityMapper<CustomerGroupDTO, CustomerGroupEntity> {
}
