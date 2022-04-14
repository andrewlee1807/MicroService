package com.banvien.fc.order.service.mapper;

import com.banvien.fc.common.service.mapper.EntityMapper;
import com.banvien.fc.order.dto.OrderOutletDTO;
import com.banvien.fc.order.entity.OrderOutletEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface OrderOutletMapper extends EntityMapper<OrderOutletDTO, OrderOutletEntity > {
}
