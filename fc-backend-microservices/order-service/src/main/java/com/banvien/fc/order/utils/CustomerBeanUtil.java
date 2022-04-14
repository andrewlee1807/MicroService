package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.CustomerDTO;
import com.banvien.fc.order.dto.UserDTO;
import com.banvien.fc.order.entity.CustomerEntity;
import com.banvien.fc.order.entity.UserEntity;
import com.banvien.fc.order.entity.UserGroupEntity;
import org.apache.commons.lang3.StringUtils;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CustomerBeanUtil {

    public static String autoCreateCustomerCode(String theLastCode) {
        String code = "";
        if (StringUtils.isNotBlank(theLastCode)) {
            DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
            df.setMaximumFractionDigits(340);
            Double numCode = Double.parseDouble(theLastCode.replaceAll("CUS","")) + 1;
             String numStr = df.format(numCode);
             if (numStr.length() < 5) {
                 for (int i=0 ; i < (5 - numStr.length()) ; i++) {
                     code += "0";
                 }
             }
             code = CoreConstants.CUSTOMER_GENERAL + code + numStr;
        } else {
            code = CoreConstants.CUSTOMER_GENERAL + "00001";
        }
        return code;
    }
    public static CustomerEntity dto2Entity(CustomerDTO dto) {
        CustomerEntity entity = new CustomerEntity();
        UserEntity userEntity = new UserEntity();
        entity.setCustomerId(dto.getCustomerId());
        if(dto.getUser() != null) {
            userEntity.setUserId(dto.getUser().getUserId());
            userEntity.setFirstName(dto.getUser().getFirstName());
            userEntity.setLastName(dto.getUser().getLastName());
            userEntity.setFullName(dto.getUser().getFullName());
            userEntity.setUserName(dto.getUser().getUserName());
            userEntity.setPhoneNumber(dto.getUser().getPhoneNumber());
            userEntity.setEmail(dto.getUser().getEmail());
            userEntity.setCode(dto.getUser().getCode());
        }
        entity.setUserId(userEntity);
        if (dto.getAddress() != null) {
            entity.setAddress(dto.getAddress());
        }
        return entity;
    }

    public static CustomerDTO entity2DTO(CustomerEntity entity) {

        CustomerDTO dto = new CustomerDTO();
        UserDTO userDTO = new UserDTO();
        dto.setUser(userDTO);
        dto.setCustomerId(entity.getCustomerId());
        if (entity.getAddress() != null) {
            dto.setAddress(entity.getAddress());
        }
        return dto;
    }
}
