package com.banvien.fc.account.service;

import com.banvien.fc.account.entity.FeatureSettingCountryEntity;
import com.banvien.fc.account.repository.FeatureSettingCountryRepository;
import com.banvien.fc.account.repository.LoyaltyMemberRepository;
import com.banvien.fc.common.enums.FeatureSettingCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by son.nguyen on 7/28/2020.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties = {"spring.config.location=classpath:test.yml"})
public class LoyaltyMemberRepoTest {

    @Autowired
    CustomerService customerService;

    @Test
    public void findLastCustomerCode() {
        customerService.welcomingGift(457);
    }

    @Test
    public void findByCountryIdAndFeatureSettingCode() {
        FeatureSettingCountryEntity featureSetting = featureSettingCountryRepository.findByCountryIdAndFeatureSettingCode(
                21L, FeatureSettingCode.ADMIN_APPROVE_CUSTOMER.name());
        int a= 1;
    }



    @Autowired
    private LoyaltyMemberRepository loyaltyMemberRepository;
    @Autowired
    private FeatureSettingCountryRepository featureSettingCountryRepository;
}
