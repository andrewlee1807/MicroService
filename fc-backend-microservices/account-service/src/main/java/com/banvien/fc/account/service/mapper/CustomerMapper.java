package com.banvien.fc.account.service.mapper;

import com.banvien.fc.account.dto.CustomerDTO;
import com.banvien.fc.account.dto.UserDTO;
import com.banvien.fc.account.entity.CustomerEntity;
import com.banvien.fc.common.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by son.nguyen on 7/26/2020.
 */
@Mapper(componentModel = "spring")
@Component
public interface CustomerMapper extends EntityMapper<CustomerDTO, CustomerEntity> {
    default CustomerDTO toDto(CustomerEntity customerEntity, boolean getUserInfo) {
        CustomerDTO customerDTO = toDto(customerEntity);
        if (getUserInfo && customerEntity.getUser() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(customerEntity.getUser().getUserId());
            userDTO.setPhoneNumber(customerEntity.getUser().getPhoneNumber());
            userDTO.setEmail(customerEntity.getUser().getEmail());
            userDTO.setPostCode(customerEntity.getUser().getPostCode());
            userDTO.setFullName(customerEntity.getUser().getFullName());
            customerDTO.setUser(userDTO);
        }
        return customerDTO;
    }
}
