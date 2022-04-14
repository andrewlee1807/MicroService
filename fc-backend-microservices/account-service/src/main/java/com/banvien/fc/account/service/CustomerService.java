package com.banvien.fc.account.service;

import com.banvien.fc.account.dto.CustomerDTO;
import com.banvien.fc.account.dto.CustomerGroupDTO;
import com.banvien.fc.account.dto.RawStoreDTO;
import com.banvien.fc.account.dto.UserDTO;
import com.banvien.fc.account.entity.*;
import com.banvien.fc.account.repository.*;
import com.banvien.fc.account.service.mapper.CustomerGroupMapper;
import com.banvien.fc.account.service.mapper.CustomerMapper;
import com.banvien.fc.common.dto.PhoneDTO;
import com.banvien.fc.common.enums.ErrorCodeMap;
import com.banvien.fc.common.enums.FeatureSettingCode;
import com.banvien.fc.common.enums.UserStatus;
import com.banvien.fc.common.rest.errors.BadRequestException;
import com.banvien.fc.common.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by son.nguyen on 7/26/2020.
 */
@Service
public class CustomerService {
    public List<CustomerGroupDTO> getCustomerGroups(Long outletId) {
        List<CustomerGroupDTO> allCustomerGroups = new ArrayList<>();
        List<CustomerGroupEntity> groupList = customerGroupRepository.findIdByOutletOutletId(outletId);
        for (CustomerGroupEntity customerGroup : groupList) {
            CustomerGroupDTO customerGroupDto = customerGroupMapper.toDto(customerGroup);
            allCustomerGroups.add(customerGroupDto);
        }
        return allCustomerGroups;
    }

    public boolean isExist(String fullPhoneNumber, String prefixCountry, long countryId) {
        PhoneDTO phoneDTO = CommonUtils.parsePhoneNumber(fullPhoneNumber, prefixCountry);
        Optional<CustomerEntity> customerEntity = customerRepository.findByUserName(phoneDTO.getFullNumber(), countryId);
        return customerEntity.isPresent();
    }

    public void updateSalesFlowCustomers(String dataContent, Long countryId) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        System.out.println("updateSalesFlowCustomers start: " + now);
        ObjectMapper mapper = new ObjectMapper();
        List<RawStoreDTO> rawStoreDTOS = null;
        try {
            rawStoreDTOS = mapper.readValue(GzCompress.uncompress(dataContent), new TypeReference<List<RawStoreDTO>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        List<RawStoreDTO> rawStoreDTOS = rawStoreDTOSS.subList(0, 500);
        if (!CollectionUtils.isEmpty(rawStoreDTOS)) {
            //deActive all customer by Country Id
            customerRepository.deActiveLoyaltyMemberByCountryId(countryId);
            customerRepository.deActiveCustomerByCountryId(countryId);
            customerRepository.deActiveUserByCountryId(countryId);
            Set<String> storeUniqueFields = new HashSet<>();
            rawStoreDTOS.parallelStream().forEach(dto -> {
                try {
                    rawStoreRepository.save(convertRawStore2Entity(dto, now));
                    if (StringUtils.isNotBlank(dto.getStoreCode()) && !storeUniqueFields.contains(dto.getStoreCode()) && !storeUniqueFields.contains(dto.getEmail())) {
                        storeUniqueFields.add(dto.getStoreCode());
                        if (StringUtils.isNotBlank(dto.getEmail())) {
                            storeUniqueFields.add(dto.getEmail());
                        }
                        List<OutletEntity> outlets = outletRepository.findByCode(dto.getDistributorCode());
                        if (!CollectionUtils.isEmpty(outlets)) {
                            CustomerDTO customerDTO = new CustomerDTO();
                            UserDTO userDTO = new UserDTO();
                            userDTO.setFullPhoneNumber(StringUtils.isNotBlank(dto.getPhoneNumber()) ? dto.getPhoneNumber().replaceAll("\\s+", "") : dto.getPhoneNumber());
                            userDTO.setCode(dto.getStoreCode());
                            userDTO.setPostCode(dto.getCountryCode());
                            userDTO.setFullName(dto.getStoreName());
                            userDTO.setEmail(dto.getEmail());
                            userDTO.setCountryId(countryId);
                            userDTO.setStatus(dto.getStatus() == null || dto.getStatus() == 1 ? 1 : 0);
                            customerDTO.setUser(userDTO);
                            customerDTO.setAddress(dto.getAddress());
                            customerDTO.setOutletId(outlets.get(0).getOutletId());
                            customerDTO.setLatitude(dto.getLatitude());
                            customerDTO.setLongitude(dto.getLongtitude());
                            customerDTO.setCustomerCode(dto.getStoreCode());
                            List<CustomerEntity> customers = customerRepository.findByCodeAndOutlet(dto.getStoreCode(), outlets.get(0).getOutletId());
                            addOrUpdateCustomer(customerDTO, now, CollectionUtils.isEmpty(customers) ? null : customers.get(0));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("updateSalesFlowCustomers done: " + new Timestamp(System.currentTimeMillis()));
    }

    public void addOrUpdateCustomer(CustomerDTO dto, Timestamp now, CustomerEntity customer) {
        UserDTO userDtoResult = addOrUpdateUser(dto.getUser(), Constants.USER_GROUP_SHOPPER, now, customer != null ? customer.getUser() : null, true);
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDtoResult.getUserId());
        OutletEntity outletEntity = new OutletEntity();
        outletEntity.setOutletId(dto.getOutletId());
        if (customer != null) {
            if (dto.getLatitude() != null && dto.getLongitude() != null) {
                customer.setLatitude(BigDecimal.valueOf(dto.getLatitude()));
                customer.setLongitude(BigDecimal.valueOf(dto.getLongitude()));
            }
            customer.setStatus(userDtoResult.getStatus());
            if (StringUtils.isNotBlank(dto.getAddress())) {
                Set<CustomerAddressEntity> customerAddresses = customer.getCustomerAddresses();
                CustomerAddressEntity customerAddress = CollectionUtils.isEmpty(customerAddresses) ? new CustomerAddressEntity() : customerAddresses.iterator().next();
                customerAddress.setAddress(dto.getAddress());
                customerAddress.setAddressName(Constants.LOYALTY_MEMBER_CUSTOMER_DEFAULT_ADDRESS);
                customerAddress.setCustomer(customer);
                customerAddress.setModifiedDate(now);
                if (dto.getLatitude() != null && dto.getLongitude() != null) {
                    customerAddress.setLatitude(BigDecimal.valueOf(dto.getLatitude()));
                    customerAddress.setLongitude(BigDecimal.valueOf(dto.getLongitude()));
                }
                customerAddressRepository.save(customerAddress);
            }
            customerRepository.save(customer);
            this.createLoyaltyMember(dto, customer, outletEntity, now, "UPDATE");
        } else {
            customer = new CustomerEntity();
            customer.setUser(userEntity);
            customer.setAddress(dto.getAddress());
            customer.setCreatedDate(now);
            if (dto.getLatitude() != null && dto.getLongitude() != null) {
                customer.setLatitude(BigDecimal.valueOf(dto.getLatitude()));
                customer.setLongitude(BigDecimal.valueOf(dto.getLongitude()));
            }
            customer.setOutlet(outletEntity);
            customer.setStatus(userDtoResult.getStatus());
            customerRepository.save(customer);
            if (StringUtils.isNotBlank(dto.getAddress())) {
                CustomerAddressEntity customerAddress = new CustomerAddressEntity();
                customerAddress.setAddress(dto.getAddress());
                customerAddress.setAddressName(Constants.LOYALTY_MEMBER_CUSTOMER_DEFAULT_ADDRESS);
                customerAddress.setCustomer(customer);
                customerAddress.setCreatedDate(now);
                if (dto.getLatitude() != null && dto.getLongitude() != null) {
                    customerAddress.setLatitude(BigDecimal.valueOf(dto.getLatitude()));
                    customerAddress.setLongitude(BigDecimal.valueOf(dto.getLongitude()));
                }
                customerAddressRepository.save(customerAddress);
            }
            this.createLoyaltyMember(dto, customer, outletEntity, now, "ADD");
        }
    }

    public UserDTO addOrUpdateUser(UserDTO dto, String userGroupType, Timestamp now, UserEntity existUser, Boolean getExistUser) {
        String fullPhoneNumber = dto.getFullPhoneNumber();
        UserEntity userEntity;
        if (getExistUser && existUser != null) {
            userEntity = existUser;
        } else {
            userEntity = userRepository.findByCode(dto.getCode());
        }
        if (userEntity != null) {
            userEntity.setModifiedDate(now);
        } else {
            Optional<UserGroupEntity> userGroupEntity = userGroupRepository.findByCode(userGroupType);
            userEntity = new UserEntity();
            userEntity.setPhoneNumber(fullPhoneNumber);
            userEntity.setPostCode(dto.getPostCode());
            userEntity.setPassword(DesEncryptionUtils.getInstance().encrypt(Constants.SET_PASSWORD_DEFAULT));
            userEntity.setCreatedDate(now);
            userEntity.setUserGroup(userGroupEntity.get());
            userEntity.setCountryId(dto.getCountryId());
            userEntity.setIsPasswordChanged(false);
        }
        if (StringUtils.isNotBlank(dto.getCode())) {
            userEntity.setCode(dto.getCode());
            userEntity.setUserName("+92" + dto.getCode());
        } else {
            userEntity.setUserName(UUID.randomUUID().toString());
            userEntity.setCode(userEntity.getUserName() + "-" + (now.toString()));
        }
        if (StringUtils.isNotBlank(dto.getEmail())) {
            userEntity.setEmail(dto.getEmail());
        }
        userEntity.setFullName(dto.getFullName());
        userEntity.setStatus(dto.getStatus() == null || dto.getStatus() == 1 ? UserStatus.ACTIVE.getValue() : UserStatus.DELETED.getValue());
        userRepository.save(userEntity);
        dto.setUserId(userEntity.getUserId());
        dto.setStatus(userEntity.getStatus());
        return dto;
    }

    public CustomerDTO getOrCreate(CustomerDTO dto) {
        CustomerDTO rs = new CustomerDTO();
        try {
            //PhoneDTO phoneDTO = CommonUtils.parsePhoneNumber(dto.getUser().getFullPhoneNumber(), dto.getPrefixCountry());
//        if (phoneDTO != null && dto.getOutletId() != null) {
////            String phone = phoneDTO.getNationalNumber();
////            String postCode = phoneDTO.getCountryCode();
//            Optional<CustomerEntity> customerEntity = customerRepository.findByUserName(fullPhoneNumber, dto.getUser().getCountryId());
//            if (customerEntity.isPresent()) {
//                if (dto.getLoyaltyMemberCheck() != null && dto.getLoyaltyMemberCheck()) {
//                    this.createLoyaltyMember(dto, customerEntity.get());
//                }
//                rs = customerMapper.toDto(customerEntity.get(), true);
//            } else {
//                if (dto.getUser().getEmail() != null && dto.getUser().getEmail().length() > 0) {
//                    List<UserEntity> users = userRepository.findByEmail(dto.getUser().getEmail());
//                    if (users != null && !users.isEmpty()) {
//                        throw new BadRequestException(ErrorCodeMap.FAILURE_EMAIl_EXISTED.name());
//                    }
//                } else {
//                    dto.getUser().setEmail(null);
//                }
//                Optional<UserGroupEntity> userGroupEntity = userGroupRepository.findByCode(Constants.USER_GROUP_SHOPPER);
//                UserEntity userEntity = new UserEntity();
//                userEntity.setFullName(dto.getUser().getFullName());
//                userEntity.setEmail(dto.getUser().getEmail());
//                userEntity.setPhoneNumber(dto.getUser().getPhoneNumber());
//                userEntity.setUserName(fullPhoneNumber);
//                userEntity.setPostCode(postCode);
//                userEntity.setStatus(UserStatus.ACTIVE.getValue());
//                userEntity.setPassword(DesEncryptionUtils.getInstance().encrypt(Constants.SET_PASSWORD_DEFAULT));
//                Timestamp now = new Timestamp(System.currentTimeMillis());
//                userEntity.setCode(userEntity.getUserName() + "-" + (now.toString()));
//                userEntity.setCreatedDate(now);
//                userEntity.setUserGroup(userGroupEntity.get());
//                userEntity.setCountryId(dto.getUser().getCountryId());
//                userRepository.save(userEntity);
//                CustomerEntity customer = new CustomerEntity();
//                customer.setUser(userEntity);
//                customer.setAddress(dto.getAddress());
//                customer.setCreatedDate(now);
//                OutletEntity outletEntity = new OutletEntity();
//                outletEntity.setOutletId(dto.getOutletId());
//                customer.setOutlet(outletEntity);
//                customer.setStatus(UserStatus.ACTIVE.getValue());
//                customer = customerRepository.save(customer);
//                if (StringUtils.isNotBlank(dto.getAddress())) {
//                    CustomerAddressEntity customerAddress = new CustomerAddressEntity();
//                    customerAddress.setAddress(dto.getAddress());
//                    customerAddress.setAddressName(Constants.LOYALTY_MEMBER_CUSTOMER_DEFAULT_ADDRESS);
//                    customerAddress.setCustomer(customer);
//                    customerAddress.setCreatedDate(now);
//                    List<Double> location = GoogleLocationUtil.getCoordinate(dto.getAddress());
//                    if (location.size() > 1) {
//                        customerAddress.setLatitude(BigDecimal.valueOf(location.get(0)));
//                        customerAddress.setLongitude(BigDecimal.valueOf(location.get(1)));
//                    }
//                    customerAddressRepository.save(customerAddress);
//                }
//                if (dto.getLoyaltyMemberCheck() != null && dto.getLoyaltyMemberCheck()) {
//                    this.createLoyaltyMember(dto, customer);
//                }
//                rs = customerMapper.toDto(customer, true);
//            }
//        }
            String fullPhoneNumber = dto.getUser().getFullPhoneNumber();
            Optional<CustomerEntity> customerEntity;
            if (dto.getCustomerId() != null) {
                customerEntity = customerRepository.findById(dto.getCustomerId());
            } else {
                customerEntity = customerRepository.findByUserName(fullPhoneNumber, dto.getUser().getCountryId());
            }
            if (customerEntity.isPresent()) {
                if (dto.getLoyaltyMemberCheck() != null && dto.getLoyaltyMemberCheck()) {
                    this.createLoyaltyMember(dto, customerEntity.get());
                }
                rs = customerMapper.toDto(customerEntity.get(), true);
            } else {
                UserEntity userEntity;
                Timestamp now = new Timestamp(System.currentTimeMillis());
                if (dto.getUser().getUserId() == null) {
                    String postCode = dto.getUser().getPostCode();                                           //get PostCode
                    String phoneNumber = fullPhoneNumber.substring(postCode.length());                       //phone number not include postcode
                    if (dto.getUser().getEmail() != null && dto.getUser().getEmail().length() > 0) {
                        List<UserEntity> users = userRepository.findByEmail(dto.getUser().getEmail());
                        if (users != null && !users.isEmpty()) {
                            throw new BadRequestException(ErrorCodeMap.FAILURE_EMAIl_EXISTED.name());
                        }
                    } else {
                        dto.getUser().setEmail(null);
                    }
                    Optional<UserGroupEntity> userGroupEntity = userGroupRepository.findByCode(Constants.USER_GROUP_SHOPPER);
                    userEntity = new UserEntity();
                    userEntity.setFullName(dto.getUser().getFullName());
                    userEntity.setEmail(dto.getUser().getEmail());
                    userEntity.setPhoneNumber(phoneNumber);
                    userEntity.setUserName(fullPhoneNumber);
                    userEntity.setPostCode(postCode);
                    userEntity.setStatus(UserStatus.ACTIVE.getValue());
                    userEntity.setPassword(DesEncryptionUtils.getInstance().encrypt(Constants.SET_PASSWORD_DEFAULT));
                    userEntity.setIsPasswordChanged(Boolean.FALSE);
                    userEntity.setCode(userEntity.getUserName() + "-" + (now.toString()));
                    userEntity.setCreatedDate(now);
                    userEntity.setUserGroup(userGroupEntity.get());
                    userEntity.setCountryId(dto.getUser().getCountryId());
                    userRepository.save(userEntity);
                } else {
                    userEntity = userRepository.getOne(dto.getUser().getUserId());
                }
                CustomerEntity customer = new CustomerEntity();
                customer.setUser(userEntity);
                customer.setAddress(dto.getAddress());
                customer.setCreatedDate(now);
                OutletEntity outletEntity = new OutletEntity();
                outletEntity.setOutletId(dto.getOutletId());
                customer.setOutlet(outletEntity);
                customer.setStatus(UserStatus.ACTIVE.getValue());
                customer = customerRepository.save(customer);
                if (StringUtils.isNotBlank(dto.getAddress())) {
                    CustomerAddressEntity customerAddress = new CustomerAddressEntity();
                    customerAddress.setAddress(dto.getAddress());
                    customerAddress.setAddressName(Constants.LOYALTY_MEMBER_CUSTOMER_DEFAULT_ADDRESS);
                    customerAddress.setCustomer(customer);
                    customerAddress.setCreatedDate(now);
                    List<Double> location = GoogleLocationUtil.getCoordinate(dto.getAddress());
                    if (location.size() > 1) {
                        customerAddress.setLatitude(BigDecimal.valueOf(location.get(0)));
                        customerAddress.setLongitude(BigDecimal.valueOf(location.get(1)));
                    }
                    customerAddressRepository.save(customerAddress);
                }
                if (dto.getLoyaltyMemberCheck() != null && dto.getLoyaltyMemberCheck()) {
                    this.createLoyaltyMember(dto, customer);
                }
                rs = customerMapper.toDto(customer, true);
            }
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public void welcomingGift(long loyaltyMemberId) {
        LoyaltyMemberEntity loyaltyMemberEntity = loyaltyMemberRepository.findById(loyaltyMemberId).get();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<LoyaltyOutletEventEntity> evens = loyaltyOutletEvenRepository.findByOutletIdAndStatusAndStartDateAfterAndEndDateBefore(
                loyaltyMemberEntity.getOutlet().getOutletId(), Constants.LOYALTY_MEMBERSHIP_ACTIVE_STATUS, now, now);
        if (evens != null && !evens.isEmpty()) {
            LoyaltyOutletEventEntity even = evens.get(0);
            List<LoyaltyEventWelcomeEntity> welcomeEvens = loyaltyEvenWelcomeRepository.findByLoyaltyOutletEventId(even.getLoyaltyOutletEventId());
            if (welcomeEvens != null && !welcomeEvens.isEmpty()) {
                LoyaltyEventWelcomeEntity welcomeEven = welcomeEvens.get(0);
                List<LoyaltyCustomerTargetEntity> targets = loyaltyCustomerTargetRepository.findByLoyaltyOutletEventId(even.getLoyaltyOutletEventId());
                List<CustomerGroupEntity> targetGroups = new ArrayList<>();
                for (LoyaltyCustomerTargetEntity target : targets) {
                    targetGroups.add(customerGroupRepository.findById(target.getCustomerGroupId()).get());
                }
                if (!(targetGroups.size() > 0 && targetGroups.get(0).getCustomerGroupId() != null &&
                        (loyaltyMemberEntity.getCustomerGroupId() == null ||
                                targetGroups.stream().filter(x -> x.getCustomerGroupId() == loyaltyMemberEntity.getCustomerGroupId()).count() == 0))) {
                    long totalPlay = loyaltyPointHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(loyaltyMemberEntity.getCustomer().getCustomerId(), welcomeEven.getLoyaltyOutletEventId());
                    Integer maxPlay = loyaltyOutletEvenRepository.findById(welcomeEven.getLoyaltyOutletEventId()).get().getMaxPlay();
                    if (maxPlay == null || totalPlay < maxPlay) {
                        saveLoyaltyHistory(loyaltyMemberEntity, even, even.getPoint());
                    }
                }
            }
        }
    }

    private void saveLoyaltyHistory(LoyaltyMemberEntity loyaltyMemberEntity, LoyaltyOutletEventEntity loyaltyOutletEventEntity, Integer point) {
        LoyaltyPointHistoryEntity history = new LoyaltyPointHistoryEntity();
        history.setOutletId(loyaltyMemberEntity.getOutlet().getOutletId());
        history.setLoyaltyOutletEventId(loyaltyOutletEventEntity.getLoyaltyOutletEventId());
        history.setPoint(point);
        history.setCompletionDate(new Timestamp(System.currentTimeMillis()));
        history.setCustomerId(loyaltyMemberEntity.getCustomer().getCustomerId());
        loyaltyPointHistoryRepository.save(history);
        Integer pointLM = loyaltyMemberEntity.getPoint() + point;
        loyaltyMemberEntity.setPoint(pointLM);
        Integer monthExpired = 0;
        if (loyaltyMemberEntity.getOutlet().getLoyaltyPointExpiredMonth() == null) {
            OutletEntity outletEntity = outletRepository.findById(loyaltyMemberEntity.getOutlet().getOutletId()).get();
            monthExpired = outletEntity.getLoyaltyPointExpiredMonth();
        } else {
            monthExpired = loyaltyMemberEntity.getOutlet().getLoyaltyPointExpiredMonth();
        }
        loyaltyMemberEntity.setPointExpiredDate(CommonUtils.calculatorExpiredFromNowWithMonthInput(monthExpired));
    }

    public List<UserDTO> findUserByOutlet(long outletId, String key, Pageable pageable) {
        System.out.println(" Search customer for outletId : " + outletId);
        return userRepository.findCustomerInOutlet(outletId, key, pageable).stream().map(entity -> {
            UserDTO rs = new UserDTO();
            rs.setUserId(entity.getUserId());
            rs.setFullName(entity.getFullName());
            rs.setPhoneNumber(entity.getPhoneNumber());
            rs.setEmail(entity.getEmail());
            return rs;
        }).filter(userDTO -> userDTO.getFullName().toLowerCase().contains(key) || userDTO.getPhoneNumber().contains(key)).collect(Collectors.toList());
    }

    public UserDTO getDefaultUser(long countryId) {
        UserEntity entity = userRepository.findUser(Constants.DEFAULT_CUSTOMER_NAME,
                Constants.DEFAULT_USER_PHONE,
                Constants.DEFAULT_USER_NAME,
                Constants.DEFAULT_USER_PASSWORD,
                Constants.DEFAULT_USER_CODE,
                Constants.DEFAULT_USER_STATUS,
                countryId);
        UserDTO rs = new UserDTO();
        rs.setEmail(entity.getEmail());
        rs.setPhoneNumber(entity.getPhoneNumber());
        rs.setFullName(entity.getFullName());
        rs.setStatus(entity.getStatus());
        rs.setUserId(entity.getUserId());
        rs.setUserName(entity.getUserName());
        rs.setCountryId(countryId);
        return rs;
    }

    public boolean invalidPhoneNumber(String prefix, String phoneNumber) {
        try {
            if (CommonUtils.parsePhoneNumber(phoneNumber, prefix) == null) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return true;
        }
        return false;
    }

    private void createLoyaltyMember(CustomerDTO customerDTO, CustomerEntity customer) {
        createLoyaltyMember(customerDTO, customer, null, new Timestamp(System.currentTimeMillis()), null);
    }

    private void createLoyaltyMember(CustomerDTO customerDTO, CustomerEntity customer, OutletEntity outletEntity, Timestamp now, String action) {
        LoyaltyMemberEntity loyaltyMemberEntity;
        Boolean status = customerDTO.getUser().getStatus() == null || customerDTO.getUser().getStatus() == 1;
        if (outletEntity == null) {
            outletEntity = outletRepository.findById(customerDTO.getOutletId()).get();
        }
        if ("ADD".equals(action)) {
            loyaltyMemberEntity = new LoyaltyMemberEntity();
            loyaltyMemberEntity.setStatus(status ? Constants.LOYALTY_MEMBERSHIP_MEMBER : Constants.LOYALTY_MEMBERSHIP_NOT_MEMBER);
            loyaltyMemberEntity.setActive(status ? 1 : 0);
        } else if ("UPDATE".equals(action)) {
            Set<LoyaltyMemberEntity> loyaltyMembers = customer.getLoyaltyMembers();
            loyaltyMemberEntity = CollectionUtils.isEmpty(loyaltyMembers) ? new LoyaltyMemberEntity() : loyaltyMembers.iterator().next();
            loyaltyMemberEntity.setStatus(status ? Constants.LOYALTY_MEMBERSHIP_MEMBER : Constants.LOYALTY_MEMBERSHIP_NOT_MEMBER);
            loyaltyMemberEntity.setActive(status ? 1 : 0);
        } else {
            loyaltyMemberEntity = loyaltyMemberRepository.findByOutletAndCustomer(customerDTO.getOutletId(), customer.getCustomerId());
            if (loyaltyMemberEntity != null) return;
            loyaltyMemberEntity = new LoyaltyMemberEntity();
            FeatureSettingCountryEntity featureSetting = featureSettingCountryRepository.findByCountryIdAndFeatureSettingCode(
                    customerDTO.getUser().getCountryId(), FeatureSettingCode.ADMIN_APPROVE_CUSTOMER.name());
            if (featureSetting == null || featureSetting.getStatus() == null || featureSetting.getStatus() != 0) {
                loyaltyMemberEntity.setQuitStoreStatus(Constants.LOYALTY_QUIT_STORE_STATUS_NOT_QUIT);
                loyaltyMemberEntity.setStatus(Constants.LOYALTY_MEMBERSHIP_NOT_MEMBER);
            } else {
                loyaltyMemberEntity.setStatus(Constants.LOYALTY_MEMBERSHIP_MEMBER);
            }
            loyaltyMemberEntity.setActive(1);
        }
        loyaltyMemberEntity.setOutlet(outletEntity);
        loyaltyMemberEntity.setCustomer(customer);
        loyaltyMemberEntity.setJoinDate(now);
        loyaltyMemberEntity.setPoint(0);
        loyaltyMemberEntity.setReferedby(outletEntity.getShopman());

        if (StringUtils.isNotBlank(customerDTO.getCustomerCode())) {
            loyaltyMemberEntity.setCustomerCode(customerDTO.getCustomerCode());
        } else {
            List<String> lastCodeList = loyaltyMemberRepository.findLastCustomerCode(customerDTO.getOutletId());
            String lastCode = CollectionUtils.isEmpty(lastCodeList) ? null : lastCodeList.get(0);
            String customerCode = CommonUtils.autoCreateCustomerCode(lastCode);
            loyaltyMemberEntity.setCustomerCode(customerCode);
        }
        loyaltyMemberEntity.setUpdatedDate(now);
        loyaltyMemberEntity.setTerms(false);
        loyaltyMemberEntity.setTermType(Constants.LOYALTY_PERIOD);

        loyaltyMemberRepository.save(loyaltyMemberEntity);
    }

    private RawStoreEntity convertRawStore2Entity(RawStoreDTO rawStoreDTO, Timestamp currentTime) {
        RawStoreEntity rawStore = new RawStoreEntity();
        rawStore.setStoreCode(rawStoreDTO.getStoreCode());
        rawStore.setStoreName(rawStoreDTO.getStoreName());
        rawStore.setAddress(rawStoreDTO.getAddress());
        rawStore.setBirthday(rawStoreDTO.getBirthday());
        rawStore.setCompanyStoreCode(rawStoreDTO.getCompanyStoreCode());
        rawStore.setCountryCode(rawStoreDTO.getCountryCode());
        rawStore.setCustomerGroup(rawStoreDTO.getCustomerGroup());
        rawStore.setDistributorCode(rawStoreDTO.getDistributorCode());
        rawStore.setEmail(rawStoreDTO.getEmail());
        rawStore.setSalesmanCode(rawStoreDTO.getSalesmanCode());
        rawStore.setLatitude(rawStoreDTO.getLatitude());
        rawStore.setLongitude(rawStoreDTO.getLongtitude());
        rawStore.setStatus(rawStoreDTO.getStatus());
        rawStore.setCreatedDate(currentTime);
        return rawStore;
    }

    @Autowired
    private FeatureSettingCountryRepository featureSettingCountryRepository;
    @Autowired
    private OutletRepository outletRepository;
    @Autowired
    private LoyaltyPointHistoryRepository loyaltyPointHistoryRepository;
    @Autowired
    private LoyaltyCustomerTargetRepository loyaltyCustomerTargetRepository;
    @Autowired
    private CustomerGroupRepository customerGroupRepository;
    @Autowired
    private LoyaltyEvenWelcomeRepository loyaltyEvenWelcomeRepository;
    @Autowired
    private LoyaltyOutletEvenRepository loyaltyOutletEvenRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerAddressRepository customerAddressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerGroupMapper customerGroupMapper;
    @Autowired
    private LoyaltyMemberRepository loyaltyMemberRepository;
    @Autowired
    private RawStoreRepository rawStoreRepository;
}
