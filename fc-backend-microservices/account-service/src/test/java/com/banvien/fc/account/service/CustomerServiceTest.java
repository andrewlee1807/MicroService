package com.banvien.fc.account.service;

import com.banvien.fc.account.dto.CustomerDTO;
import com.banvien.fc.account.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by son.nguyen on 7/26/2020.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:test.yml"})
public class CustomerServiceTest {

    @Test
    public void getOrCreate() throws Exception {
        CustomerDTO input = new CustomerDTO();
        input.setOutletId(5235L);
        input.setAddress("34 Cống Lỡ, Phường 15, Tan Binh, Ho Chi Minh City, Vietnam");
        UserDTO userDTO = new UserDTO();
        userDTO.setFullPhoneNumber("+84938789903");
        userDTO.setFullName("Son Nguyen");
        userDTO.setEmail("son.nguyen2@banvien.com.vn");
        input.setUser(userDTO);
        CustomerDTO rs = customerService.getOrCreate(input);
//        System.out.println(rs.getCustomerId());
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(rs));
    }

    @Autowired
    private CustomerService customerService;
}
