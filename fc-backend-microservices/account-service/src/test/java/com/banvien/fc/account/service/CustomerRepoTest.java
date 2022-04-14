package com.banvien.fc.account.service;

import com.banvien.fc.account.entity.CustomerEntity;
import com.banvien.fc.account.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by son.nguyen on 7/26/2020.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:test.yml"})
public class CustomerRepoTest {

    @Test
    public void findByPhoneNumber() {
        Optional<CustomerEntity> customerEntity = customerRepository.findByPhoneNumber("103016121", "+60");
        System.out.println(customerEntity.isPresent());
        System.out.println(customerEntity.get().getCustomerId());
    }
    @Test
    public void findByUserName() {
        List<CustomerEntity> customers = customerRepository.findByCodeAndOutlet("N00000072750", 15994L);
        System.out.println(String.format("size: %s, id: %s", customers.size(), customers.get(0).getCustomerId()));
    }

    @Test
    public void deActiveByCode() {
        customerRepository.deActiveCustomerByCountryId(23L);
        customerRepository.deActiveUserByCountryId(23L);
        customerRepository.deActiveLoyaltyMemberByCountryId(23L);
    }
    @Autowired
    private CustomerRepository customerRepository;
}
