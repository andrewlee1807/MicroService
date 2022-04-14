package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.CustomerAddressDTO;
import com.banvien.fc.order.dto.mobile.CustomerAddressMobileDTO;
import com.banvien.fc.order.entity.CustomerAddressEntity;

public class CustomerAddressBeanUtil {

    public static CustomerAddressEntity DTO2Entity(CustomerAddressDTO dto) {
        CustomerAddressEntity entity = new CustomerAddressEntity(dto.getCustomerAddressId(), dto.getAddress(), dto.getAddressName(),
                dto.getLongitude(), dto.getLatitude(), dto.getType(), dto.getDefault());
        if (dto.getCustomer() != null) {
            entity.setCustomer(CustomerBeanUtil.dto2Entity(dto.getCustomer()));
        }
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setAddress(dto.getAddress());
        entity.setAddressName(dto.getAddressName());
        return entity;
    }

    public static CustomerAddressDTO Entity2DTO(CustomerAddressEntity entity) {
        CustomerAddressDTO dto = new CustomerAddressDTO(entity.getCustomerAddressId(), entity.getAddress(),
                entity.getAddressName(), entity.getLongitude(), entity.getLatitude(), entity.getType(), entity.getIsDefault());
        if (entity.getCustomer() != null) {
            dto.setCustomer(CustomerBeanUtil.entity2DTO(entity.getCustomer()));
        }
        return dto;
    }

    public static CustomerAddressMobileDTO DTO2MobileDTO(CustomerAddressDTO dto) {
        return new CustomerAddressMobileDTO(dto.getCustomerAddressId(), dto.getAddress(),
                dto.getAddressName(), dto.getLongitude(), dto.getLatitude(), dto.getType(), dto.getDefault());
    }

    public static CustomerAddressMobileDTO entity2MobileDTO(CustomerAddressEntity entity) {
        return new CustomerAddressMobileDTO(entity.getCustomerAddressId(), entity.getAddress(),
                entity.getAddressName(), entity.getLongitude(), entity.getLatitude(), entity.getType(), entity.getIsDefault());
    }

}
