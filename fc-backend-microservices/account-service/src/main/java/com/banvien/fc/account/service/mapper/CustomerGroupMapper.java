package com.banvien.fc.account.service.mapper;

import com.banvien.fc.account.dto.CustomerGroupDTO;
import com.banvien.fc.account.entity.CustomerGroupEntity;
import com.banvien.fc.common.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by son.nguyen on 9/13/2020.
 */
@Mapper(componentModel = "spring")
@Component
public interface CustomerGroupMapper extends EntityMapper<CustomerGroupDTO, CustomerGroupEntity> {
}
