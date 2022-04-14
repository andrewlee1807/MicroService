package com.banvien.fc.order.service;

import com.banvien.fc.common.dto.PhoneDTO;
import com.banvien.fc.common.enums.ErrorCodeMap;
import com.banvien.fc.common.enums.Language;
import com.banvien.fc.common.rest.errors.BadRequestException;
import com.banvien.fc.common.service.NumberUtils;
import com.banvien.fc.common.util.CommonUtils;
import com.banvien.fc.common.util.Constants;
import com.banvien.fc.common.util.GoogleLocationUtil;
import com.banvien.fc.order.dto.*;
import com.banvien.fc.order.dto.delivery.PaymentMethodDTO;
import com.banvien.fc.order.dto.delivery.grabexpress.QuotesPlatformDTO;
import com.banvien.fc.order.dto.delivery.grabexpress.QuotesResponseDTO;
import com.banvien.fc.order.dto.mobile.ProductOutletSkuMobileDTO;
import com.banvien.fc.order.dto.payment.AuthenDTO;
import com.banvien.fc.order.dto.payment.CertificatesDTO;
import com.banvien.fc.order.dto.rules.Enum.ApiStatusCode;
import com.banvien.fc.order.dto.rules.Enum.DiscountType;
import com.banvien.fc.order.dto.rules.Enum.Payment;
import com.banvien.fc.order.dto.rules.Enum.PromotionType;
import com.banvien.fc.order.entity.*;
import com.banvien.fc.order.repository.*;
import com.banvien.fc.order.status.StatusResponse;
import com.banvien.fc.order.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EasyOrderService {

    @Autowired
    private OrderOutletPromotionRepository orderOutletPromotionRepository;
    @Autowired
    private CustomerDebHistoryRepository customerDebHistoryRepository;
    @Autowired
    private OrderTemporaryRepository orderTemporaryRepository;
    @Autowired
    private OutletEmployeeRepository outletEmployeeRepository;
    @Autowired
    private NotifyTemplateRepositoty notifyTemplateRepositoty;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private LoyaltyService loyaltyService;
    @Autowired
    private SalesFlowService salesFlowService;
    @Autowired
    private OutletPromotionCustomerRepository outletPromotionCustomerRepository;

    private void validateInformation(EasyOrderSubmitDTO easyOrderSubmit) {
        PhoneDTO phoneDTO = CommonUtils.parsePhoneNumber(easyOrderSubmit.getPhone(), easyOrderSubmit.getPrefixPhoneNumber());
        if (phoneDTO == null) {
            throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_PHONE.name());
        } else if (easyOrderSubmit.getEasyOrderId() == null) {
            throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_EASY_ORDER.name());
        } else if (StringUtils.isBlank(easyOrderSubmit.getName())) {
            throw new BadRequestException(ErrorCodeMap.FAILURE_FULLNAME_EMPTY.name());
        } else if (!CommonUtils.isValidEmail(easyOrderSubmit.getEmail())) {
            throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_EMAIL.name());
        } else if (StringUtils.isBlank(easyOrderSubmit.getDeliveryMethod())) {
            throw new BadRequestException(ErrorCodeMap.FAILURE_DELIVERY_METHOD_EMPTY.name());
        } else if (StringUtils.isBlank(easyOrderSubmit.getPaymentMethod())) {
            throw new BadRequestException(ErrorCodeMap.FAILURE_PAYMENT_METHOD_EMPTY.name());
        } else if (Constants.DELIVERY_SHIP_TO_HOME.equals(easyOrderSubmit.getDeliveryMethod())
                && StringUtils.isBlank(easyOrderSubmit.getAddress())) {
            throw new BadRequestException(ErrorCodeMap.FAILURE_ADDRESS_EMPTY.name());
        }
    }

    /* Get Discount Price by EasyOrder Promotion*/
    private Double getDiscountValuePromotion(ReductionDTO reductionDTO, Double totalPriceOrder) {
        if (reductionDTO.getDiscountType().equals(DiscountType.PERCENT_OFF)) {
            return totalPriceOrder * reductionDTO.getValueDiscount() / 100;
        }
        // else AMOUNT_OFF : using own reductionDTO.getValueDiscount() to return
        return reductionDTO.getValueDiscount();
    }

    public EasyOrderDTO findById(Long id, Pageable pageable) {
        Optional<EasyOrderEntity> optionalEasyOrderEntity = easyOrderRepository.findById(id);
        int totalElement = optionalEasyOrderEntity.get().getEasyOrderItemEntities().size();

        // get SKU is active and sale online in EasyOrder
        List<EasyOrderItemEntity> easyOrderItemEntities = easyOrderItemRepository.getByEasyOrderIdActiveAndSold(id, optionalEasyOrderEntity.get().getOutletEntity().getOutletId(), pageable);

        optionalEasyOrderEntity.get().setEasyOrderItemEntities(easyOrderItemEntities);
        EasyOrderDTO rs = optionalEasyOrderEntity.map(EasyOrderBeanUtils::entity2DTO).orElse(null);
        rs.setTotalElement(totalElement);
        rs.setTotalPage(((totalElement - 1) / pageable.getPageSize()) + 1);
        CountryEntity country = countryRepository.findById(rs.getOutletDTO().getCountryId()).get();
        rs.getOutletDTO().setCurrency(country.getCurrency());   // Currency
        rs.getOutletDTO().setCountryCode(Language.valueOf(country.getCode()).getValue());    // Country Code ISO
        return rs;
    }

    public void updateTotalViewer(Long easyOrderId) {
        easyOrderRepository.updateTotalViewer(easyOrderId);
    }


    //    public Map<String, Object> submitEasyOrders(EasyOrderSubmitDTO easyOrderSubmit) {
//        Map<String, Object> map = new HashMap<>();
//
//        OrderOutletEntity orderOutletEntity = new OrderOutletEntity();
//        if (easyOrderSubmit.getProducts() == null || easyOrderSubmit.getProducts().size() == 0) {
//            throw new BadRequestException(ErrorCodeMap.FAILURE_PRODUCT_EMPTY.name());
//        }
//
//        //Validate information
//        this.validateInformation(easyOrderSubmit);
//        PhoneDTO phoneDTO = CommonUtils.parsePhoneNumber(easyOrderSubmit.getPhone(), easyOrderSubmit.getPrefixPhoneNumber());
//
//        List<Double> locationCustomer = new ArrayList<>();
//        if (Constants.DELIVERY_SHIP_TO_HOME.equals(easyOrderSubmit.getDeliveryMethod())
//                && StringUtils.isNotBlank(easyOrderSubmit.getAddress())) {
//            locationCustomer = GoogleLocationUtil.getCoordinate(easyOrderSubmit.getAddress());
//            if (locationCustomer.size() != 2) {
//                throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_ADDRESS.name());
//            }
//        }
//
//        //Calculate delivery fee
//        double deliveryFee = calculateDeliveryFee(easyOrderSubmit);
//
//        EasyOrderEntity easyOrder = easyOrderRepository.getOne(easyOrderSubmit.getEasyOrderId());
//        if (easyOrder == null) {
//            throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_EASY_ORDER.name());
//        }
//
//        OrderInformationEntity orderInformationEntity = new OrderInformationEntity();
//        orderInformationEntity.setReceiverName(easyOrderSubmit.getName());
//        orderInformationEntity.setReceiverPhone(easyOrderSubmit.getPhone());
//        orderInformationEntity.setPaymentMethod(easyOrderSubmit.getPaymentMethod());
//        orderInformationEntity.setDeliveryMethod(easyOrderSubmit.getDeliveryMethod());
//        orderInformationEntity.setReceiverAddress(easyOrderSubmit.getAddress());
//        if (locationCustomer != null && locationCustomer.size() == 2) {
//            orderInformationEntity.setReceiverLat(locationCustomer.get(0));
//            orderInformationEntity.setReceiverLng(locationCustomer.get(1));
//        }
//
//        orderOutletEntity.setOutlet(easyOrder.getOutletEntity());
//        orderOutletEntity.setOrderInformation(orderInformationEntity);
//
//        orderOutletEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//        orderOutletEntity.setCode(RandomTokenBeanUtil.generateOrderCode());
//        orderOutletEntity.setEasyOrder(easyOrder);
//        orderOutletEntity.setSaleChanel("ONLINE");
//        orderOutletEntity.setStatus("WAITING_FOR_CONFIRMATION");
//        orderOutletEntity.setFromsource(Constants.SOURCE_EASY_ORDER);
//        orderOutletEntity.setNote(easyOrderSubmit.getNote());
//        //Hardcode for store in db first
//        orderOutletEntity.setLoyaltyPoint(0);
//        orderOutletEntity.setTotalItem(0);
//        orderOutletEntity.setTotalOriginalPrice(0.0);
//        orderOutletEntity.setDeliveryPrice(0.0);
//        orderOutletEntity.setTotalStoreDiscountPrice(0.0);
//        orderOutletEntity.setTotalPromotionDiscountPrice(0.0);
//        orderOutletEntity.setTotalWeight(0.0);
//
//        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setOutletId(easyOrder.getOutletEntity().getOutletId());
//        customerDTO.setAddress(easyOrderSubmit.getAddress());
//        UserDTO userDTO = new UserDTO();
//        userDTO.setFullPhoneNumber(phoneDTO.getFullNumber());
//        userDTO.setFullName(easyOrderSubmit.getName());
//        userDTO.setEmail(easyOrderSubmit.getEmail());
//        customerDTO.setUser(userDTO);
//
//        try {
//            ResponseEntity<CustomerDTO> res = accountRepository.getOrCreateCustomer(customerDTO);
//            CustomerDTO currentCustomer = res.getBody();
//            if (currentCustomer != null && currentCustomer.getCustomerId() != null) {
//                orderOutletEntity.setCustomerId(currentCustomer.getCustomerId());
//                easyOrderSubmit.setUserId(currentCustomer.getUser().getUserId());
//            }
//        } catch (Exception ex) {
//            if (ex.getMessage().contains(ErrorCodeMap.FAILURE_EMAIl_EXISTED.name())) {
//                throw new BadRequestException(ErrorCodeMap.FAILURE_EMAIl_EXISTED.name());
//            } else {
//                throw ex;
//            }
//        }
//        //Check valid order (inventory + customer + product)
//        StatusResponse message = isOrderValid(easyOrderSubmit);
//        if (!message.getCode().equals(Constants.ORDER_VALID)) {
//            throw new BadRequestException(message.getCode());
//        }
//
//        orderOutletEntity = orderOutletRepository.save(orderOutletEntity);
//
//        //get list promotion of product
//        easyOrderSubmit.setAction(1); //update DB promotion-service  action 1: store DB  . 0: view Promotions without update
//        PromotionPerOrderDTO promotionPerOrderDTO = promotionRepository.getPromotionDiscountPriceByEasyOrder(easyOrderSubmit); //call Promotion Service .DB
//
//        //Calculate price for easy order
//        EasyOrderRedeemPromotionDTO easyOrderRedeemPromotionDTO = this.calculatePriceForEasyOrder(promotionPerOrderDTO, easyOrderSubmit, orderOutletEntity);
//
//        orderOutletEntity.setTotalPromotionDiscountPrice(easyOrderRedeemPromotionDTO.getTotalPromotionDiscountPrice());
//        orderOutletEntity.setTotalOriginalPrice(easyOrderRedeemPromotionDTO.getTotalOriginalPrice());
//        orderOutletEntity.setDeliveryPrice(deliveryFee - easyOrderRedeemPromotionDTO.getDeliveryDiscount());
//        orderOutletEntity.setTotalPrice(easyOrderRedeemPromotionDTO.getTotalPrice() + orderOutletEntity.getDeliveryPrice());
//        orderOutletEntity.setSaving(easyOrderRedeemPromotionDTO.getTotalOriginalPrice() - easyOrderRedeemPromotionDTO.getTotalSalePrice());
//        orderOutletEntity.setTotalStoreDiscountPrice(easyOrderRedeemPromotionDTO.getTotalStoreDiscount());
//        orderOutletEntity.setTotalItem(easyOrderRedeemPromotionDTO.getTotalItems());
//
//        orderOutletEntity = orderOutletRepository.save(orderOutletEntity);
//
//
//        addStockOut(easyOrderSubmit.getProducts(), orderOutletEntity);
//
//        //update DB products
//        if (easyOrderRedeemPromotionDTO.getProducts() != null && easyOrderRedeemPromotionDTO.getProducts().size() > 0) {
//            for (ProductOrderItemDTO dto : easyOrderRedeemPromotionDTO.getProducts()) {
//                ProductOrderItemEntity productOrderItemEntity = ProductOrderItemUtils.DTO2entity(dto);
//                productOrderItemEntity.setOrderOutletId(orderOutletEntity.getOrderOutletID());
//                productOrderItemRepository.save(productOrderItemEntity);
//            }
//        }
//
//        List<LoyaltyMemberEntity> loyaltyMemberEntities = loyaltyMemberRepository.findByCustomerIdAndOutletOutletIdAndActive(orderOutletEntity.getCustomerId(), easyOrderSubmit.getOutletId(), 1);
//        if (loyaltyMemberEntities != null && loyaltyMemberEntities.size() > 0) {
//            Integer loyaltyPoint = calculateLoyaltyPoint4EasyOrder(orderOutletEntity, easyOrderSubmit.getOutletId(), orderOutletEntity.getCustomerId(), loyaltyMemberEntities.get(0), easyOrderSubmit.getProducts());
//            orderOutletEntity.setLoyaltyPoint(loyaltyPoint);
//        }
//
//        if (easyOrderRedeemPromotionDTO.getGitfs() != null && easyOrderRedeemPromotionDTO.getGitfs().size() > 0) {
//            List<Long> customerRewards = new ArrayList<>();
//            for (GiftDTO dto : easyOrderRedeemPromotionDTO.getGitfs()) {
//                customerRewards.add(dto.getGiftId());
//            }
//
//            // save customer reward gift
//            customerRewardOrderItemRepository.addToOrder(customerRewards, orderOutletEntity.getOrderOutletID());
//
//            // update customerRewards  to redeemed
//            customerRewardRepository.redeemRewards(customerRewards, new Timestamp(System.currentTimeMillis()));
//        }
//
//        //this.testSendSalesInvoiceMail();
//        boolean sendEmail = this.sendEmail(orderOutletEntity, easyOrderSubmit.getEmail()).getBody();
//        if (sendEmail == false) {
//            map.put("status", HttpStatus.BAD_REQUEST.value());
//            map.put("message", "Email not send!");
//        }
//        map.put("orderId", orderOutletEntity.getOrderOutletID());
//        map.put("hashId", new String(Base64.encodeBase64(orderOutletEntity.getOrderOutletID().toString().getBytes())));
//        map.put("status", HttpStatus.OK.value());
//        map.put("message", "success");
//        return map;
//    }
    public StatusResponse saveOrderAfterPaidByGrab(String orderCode, String txId, String token, String paymentStatus) {
        ObjectMapper oMapper = new ObjectMapper();
        StatusResponse statusResponse = new StatusResponse();
        OrderTemporaryEntity orderTemporaryEntity = orderTemporaryRepository.getByOrderCode(orderCode);
        EasyOrderSubmitDTO dto;
        if (orderTemporaryEntity == null) return null;
        orderTemporaryEntity.setPaymentStatus(paymentStatus);

        if (orderTemporaryEntity.getPaymentStatus().equals(Constants.ORDER_STATUS_SUCCESS)) {
            try {
                dto = oMapper.readValue(orderTemporaryEntity.getOrderDetail(), EasyOrderSubmitDTO.class); // json 2 obj
            } catch (IOException e) {
                e.printStackTrace();
                throw new BadRequestException(ErrorCodeMap.FAILURE_PARSE_EASYORDERDTO.name());
            }

            // Refund by Grab Payment
            dto.setTxId(txId);
            dto.setToken(token);
            //dto.setGrabGroupTxId(orderCode);
            dto.setOrderCode(orderCode);

            statusResponse = this.submitOrder(dto);
            if (statusResponse.getCode().equals(Constants.ORDER_VALID)) {
                orderTemporaryEntity.setOrderId(statusResponse.getId());
            }
        } else {
            statusResponse.setCode(Constants.ORDER_STATUS_FAIL);
        }
        orderTemporaryRepository.saveAndFlush(orderTemporaryEntity);
        return statusResponse;

    }

    public StatusResponse checkGrabPayStatus(String orderCode) {
        StatusResponse status = new StatusResponse();
        status.setValue("UNPAID");
//        if (!status.getCode().equals(Constants.ORDER_VALID)) {
//            return status;
//        }
        OrderTemporaryEntity orderTemporaryEntity = orderTemporaryRepository.getByOrderCode(orderCode);
        if (orderTemporaryEntity == null) { // user no pay
            return status;
        } else {
            Long orderId = orderTemporaryEntity.getOrderId();
            if (orderTemporaryEntity.getPaymentStatus() != null && orderTemporaryEntity.getPaymentStatus().equals("SUCCESS") && orderId != null) {    // payment with Grab is successful
                status.setValue("SUCCESS");
                status.setId(orderId);
                orderTemporaryRepository.delete(orderTemporaryEntity);

            } else
                status.setValue("FAILED");   // payment with Grab is fail
            return status;
        }
    }

    public StatusResponse submitEasyOrder(EasyOrderSubmitDTO order) {
        // check payment method
        if (!Constants.SOURCE_POS_ONLINE.contains(order.getSource())) {
            if (StringUtils.isBlank(order.getName())) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_FULLNAME_EMPTY.name());
            } else if (StringUtils.isBlank(order.getDeliveryMethod())) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_DELIVERY_METHOD_EMPTY.name());
            } else if (StringUtils.isBlank(order.getPaymentMethod())) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_PAYMENT_METHOD_EMPTY.name());
            }

            // Payment by Grab Pay
            if (Constants.PAYMENT_GRABPAY.equals(order.getPaymentMethod())) {
                // Get /certificates from GrabAPI
                CertificatesDTO certificatesDTO = paymentRepository.getCertificates(order.getOutletId()); // without grabgrouptxid

                // get total amount EasyOrder and CustomerId
                Map<String, Object> rs = this.checkoutEasyOrder(order);
                if (rs.get("finalTotalPrice").equals(null)) {
                    System.out.println("Can not calculate total Price EasyOrder for Grab");
                    throw new BadRequestException(ErrorCodeMap.FAILURE_EXCEPTION.name());
                }

                // validation Information Client
                CustomerEntity customerEntity = customerRepository.findByUserIdUserId(order.getUserId()).get(0);
                StatusResponse status = isOrderValid(order);
                if (!status.getCode().equals(Constants.ORDER_VALID)) {
                    return status;
                }

                // create Order Temporary
                OrderInformationMobileDTO dto = new OrderInformationMobileDTO();
                dto.setCustomerId(customerEntity.getCustomerId());
                dto.setPromotionCode(order.getPromotionCode());
                dto.setReceiverName(order.getName());
                dto.setReceiverPhone(order.getPhone());
                dto.setReceiverAddress(order.getAddress());
                dto.setDeliveryMethod(order.getDeliveryMethod());
                dto.setUsedPoint(0);
                dto.setPayment(order.getPaymentMethod());
                // get by customerId and currentTime
                dto.setGrabGroupTxId(dto.getCustomerId().toString() + "_" + System.currentTimeMillis());
                dto.setState(certificatesDTO.getState());
                dto.setOutletId(order.getOutletId());
                dto.setSource(order.getSource());
                OrderTemporaryEntity orderTemporaryEntity = orderService.saveOrderTemporary(dto, (Double) rs.get("finalTotalPrice"));
                // save easyOrder on CacheUtil
                ObjectWriter ow = new ObjectMapper().writer();
                String json = "errorParse";
                try {
                    json = ow.writeValueAsString(order);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                orderTemporaryEntity.setOrderDetail(json);

                orderTemporaryEntity = orderTemporaryRepository.saveAndFlush(orderTemporaryEntity);

                OutletEntity outletEntity = outletRepository.findById(orderTemporaryEntity.getOutletId()).get();
                CountryEntity country = countryRepository.findById(outletEntity.getCountryId()).get();
                // init charge for GrabPay
                AuthenDTO authenDTO = paymentRepository.generateAuthen(orderTemporaryEntity.getOrderTemporaryId(), country.getCurrency());

                status = new StatusResponse(Constants.ORDER_VALID);
                status.setAuthenUrl(authenDTO.getAuthenUrl());

                status.setOrderCode(orderTemporaryEntity.getOrderOutletCode());
                return status;
            }
        }

        return this.submitOrder(order);
    }

    /* Get total price discount for delivery by Promotions 4 EasyOrder and POS submit*/
    private double getDeliveryPriceDiscountByPromotion(EasyOrderRedeemPromotionDTO easyOrderRedeemPromotionDTO, double deliveryFee, Long customerId, int action) {
        int typeShipping = 0; // default  0 :corresponding for SHIPPING_FIX_PRICE ($)  ||  != 0 : SHIPPING_DISCOUNT (%)
        double totalDeliveryDiscount = 0.0;
        for (ReductionDTO reductionDTO : easyOrderRedeemPromotionDTO.getLstPromotionDelivery()) {
            // Update customer per Promotion and remain of Promotion for deliveryPromotion
            OutletPromotionEntity outletPromotionEntity = outletPromotionRepository.getOutLetPromotionById(reductionDTO.getOutletPromotionId(), new Timestamp(System.currentTimeMillis()));
            if (outletPromotionEntity == null) continue;

            OutletPromotionCustomerEntity outletPromotionCustomerEntity = null;
            int customerPerPromotion = 0;
            outletPromotionCustomerEntity = outletPromotionCustomerRepository.findByCustomerIdAndOutletPromotionId(customerId, outletPromotionEntity.getOutletPromotionId());

            // Check redemption per customer
            if (outletPromotionCustomerEntity != null) {
                customerPerPromotion = outletPromotionCustomerEntity.getQuantity();
                if (outletPromotionCustomerEntity.getQuantity() >= outletPromotionEntity.getMaxPerCustomer())
                    continue;
            }

            // Update relation of DeliveryPromotion and Customer :
            if (action == 1) {  // update DB
                outletPromotionEntity.setRemainGift(outletPromotionEntity.getRemainGift() - 1);
                outletPromotionEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
                outletPromotionRepository.save(outletPromotionEntity);  // update quantity of the Promotion
                outletPromotionRepository.flush();
                if (outletPromotionCustomerEntity == null) {
                    customerPerPromotion = 1;
                    outletPromotionCustomerEntity =
                            new OutletPromotionCustomerEntity(customerId, outletPromotionEntity.getOutletPromotionId(), 1, new Timestamp(System.currentTimeMillis()));
                } else {
                    customerPerPromotion = customerPerPromotion + 1;
                }
                // Save the Promotions are applied on Order and this Customer :
                if (outletPromotionCustomerEntity != null) {
                    outletPromotionCustomerEntity.setQuantity(customerPerPromotion);
                    outletPromotionCustomerRepository.save(outletPromotionCustomerEntity);
                    outletPromotionCustomerRepository.flush();
                }
            }
            if (reductionDTO.getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT)) {  // Discount by Percent
                totalDeliveryDiscount += deliveryFee * reductionDTO.getValueDiscount() / 100;
                reductionDTO.setValueDiscount(deliveryFee * reductionDTO.getValueDiscount() / 100);
                typeShipping += 1;
                easyOrderRedeemPromotionDTO.getOutletPromotionPass().add(reductionDTO);
            } else if (typeShipping == 0) {  // Discount by Fix-Price
                totalDeliveryDiscount = reductionDTO.getValueDiscount();
                reductionDTO.setValueDiscount(deliveryFee - totalDeliveryDiscount > 0 ? deliveryFee - totalDeliveryDiscount : totalDeliveryDiscount);
                easyOrderRedeemPromotionDTO.getOutletPromotionPass().add(reductionDTO);
            }
            if (typeShipping == 0 || deliveryFee - totalDeliveryDiscount <= 0)
                break; // choose only one for fix-price discount delivery or discount price than delivery fee
        }

        return totalDeliveryDiscount;
    }

    @Transactional
    public StatusResponse submitOrder(EasyOrderSubmitDTO order) {
        OrderOutletEntity orderOutletEntity = new OrderOutletEntity();
        Long defaultUserId = null;
        StatusResponse status = new StatusResponse();
        List<ProductDTO> productsStockOut = new ArrayList<>();
        OutletEntity outletEntity = outletRepository.findById(order.getOutletId()).get();
        orderOutletEntity.setOutlet(outletEntity);

        //1. make sure order always has customer
        if (order.getUserId() == null && StringUtils.isBlank(order.getPhone())) {
            //submit for default user
            defaultUserId = accountRepository.getDefaultUser(outletEntity.getCountryId()).getBody().getUserId();
            order.setUserId(defaultUserId);
        } else {
            //create new customerId
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setOutletId(order.getOutletId());
            customerDTO.setAddress(order.getAddress());
            customerDTO.setPrefixCountry(order.getPrefixPhoneNumber());
            customerDTO.setCustomerId(order.getCustomerId());
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(order.getUserId());
            userDTO.setFullPhoneNumber(order.getPhone());
            userDTO.setPostCode(order.getPostCode());
            userDTO.setFullName(order.getName());
            userDTO.setEmail(order.getEmail());
            userDTO.setCountryId(outletEntity.getCountryId());
            customerDTO.setUser(userDTO);
            try {
                customerDTO.setLoyaltyMemberCheck(true);  // create LoyaltyMember
                ResponseEntity<CustomerDTO> res = accountRepository.getOrCreateCustomer(customerDTO);
                CustomerDTO currentCustomer = res.getBody();
                if (currentCustomer != null && currentCustomer.getCustomerId() != null) {
                    orderOutletEntity.setCustomerId(currentCustomer.getCustomerId());
                    order.setUserId(currentCustomer.getUser().getUserId());
                } else {
                    //not created user
                    status.setCode("user.invalid");
                    return status;
                }
            } catch (Exception ex) {
                if (ex.getMessage().contains(ErrorCodeMap.FAILURE_EMAIl_EXISTED.name())) {
                    throw new BadRequestException(ErrorCodeMap.FAILURE_EMAIl_EXISTED.name());
                } else {
                    throw ex;
                }
            }
        }

        if ("PK".equals(countryRepository.getOne(outletEntity.getCountryId()).getCode())) {
            try {
                order.setOrderCode(salesFlowService.createOrder(String.valueOf(System.currentTimeMillis()), outletEntity.getCode(), usersRepository.getOne(order.getUserId()).getCode(), order.getProducts()));
            } catch (Exception e) {
                throw new BadRequestException("Can't not create order from saleflo");
            }
        }

        //2. validate order
        if (order.getProducts() == null || order.getProducts().size() == 0) {
            throw new BadRequestException(ErrorCodeMap.FAILURE_PRODUCT_EMPTY.name());
        }


        String fullnumber = order.getPhone() != null ? order.getPhone() : "";
        if (Constants.SOURCE_EASY_ORDER.equals(order.getSource())) {
            EasyOrderEntity easyOrder = easyOrderRepository.getOne(order.getEasyOrderId());
            if (easyOrder == null) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_EASY_ORDER.name());
            }
            if (StringUtils.isBlank(easyOrder.getName())) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_FULLNAME_EMPTY.name());
            }
            orderOutletEntity.setEasyOrder(easyOrder);
            // get customer for easy order
            if (defaultUserId != null) {
                PhoneDTO phoneDTO = CommonUtils.parsePhoneNumber(order.getPhone(), order.getPrefixPhoneNumber());
                if (phoneDTO == null) {
                    throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_PHONE.name());
                }
                fullnumber = phoneDTO.getFullNumber();
            }
        }

        CustomerEntity customerEntity = customerRepository.findByUserIdUserId(order.getUserId()).get(0);
        LoyaltyMemberEntity loyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletOutletId(customerEntity.getCustomerId(), outletEntity.getOutletId());

        if (!Constants.SOURCE_SMART_VANSALE.equals(order.getSource())) {
            status = isOrderValid(order);
            if (!status.getCode().equals(Constants.ORDER_VALID)) {
                return status;
            }
        }

        if (!Constants.SOURCE_POS_ONLINE.contains(order.getSource())) {
            if (StringUtils.isBlank(order.getName())) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_FULLNAME_EMPTY.name());
            } else if (StringUtils.isBlank(order.getDeliveryMethod())) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_DELIVERY_METHOD_EMPTY.name());
            } else if (StringUtils.isBlank(order.getPaymentMethod())) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_PAYMENT_METHOD_EMPTY.name());
            } else if (Constants.DELIVERY_SHIP_TO_HOME.equals(order.getDeliveryMethod())
                    && StringUtils.isBlank(order.getAddress())) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_ADDRESS_EMPTY.name());
            }
        }

        //3. calculate store discount and delivery fee
        double totalStoreDiscount = 0;
        double storeDiscountPerOrder = 0; // $ Discount by POS per Order
        if (order.getTotalStoreDiscountPrice() != null) {   // POS submitted
            storeDiscountPerOrder = order.getTotalStoreDiscountPrice();
        } else {    // easyOrder submitted
            Long customerId = customerRepository.findByUserIdUserId(order.getUserId()).get(0).getCustomerId();
            totalStoreDiscount = updatePriceForProducts(order.getProducts(), customerId, outletEntity.getOutletId());
        }

        double deliveryFee = 0;
        List<Double> locationCustomer = new ArrayList<>();
        if (Constants.DELIVERY_SHIP_TO_HOME.equals(order.getDeliveryMethod()) && StringUtils.isNotBlank(order.getAddress())) {
            locationCustomer = GoogleLocationUtil.getCoordinate(order.getAddress());
            if (locationCustomer.size() != 2) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_ADDRESS.name());
            }
            //Calculate delivery fee
            deliveryFee = DeliveryUtils.calculateDeliveryPrice(outletEntity, locationCustomer.get(0), locationCustomer.get(1), order.getDeliveryMethod(), deliveryServiceRepository.findByOutletId(outletEntity.getOutletId()));
            order.setDeliveryFee(deliveryFee);
        } else if (order.getDeliveryMethod().equals(Constants.DELIVERY_SHIP_TO_HOME_EXPRESS) && StringUtils.isNotBlank(order.getAddress())) {
            try {
                locationCustomer = GoogleLocationUtil.getCoordinate(order.getAddress());
                BigDecimal latitude = BigDecimal.valueOf(locationCustomer.get(0));
                BigDecimal longitude = BigDecimal.valueOf(locationCustomer.get(1));
                OutletEntity outlet = outletRepository.findById(order.getOutletId()).get();
                QuotesPlatformDTO quotesPlatformDTO = new QuotesPlatformDTO();
                //quotesPlatformDTO.setCustomerId(null);
                quotesPlatformDTO.setOutletId(order.getOutletId());
                quotesPlatformDTO.setLatitude(latitude.doubleValue());
                quotesPlatformDTO.setLongitude(longitude.doubleValue());
                quotesPlatformDTO.setSrcLatitude(outletEntity.getLatitude().doubleValue());
                quotesPlatformDTO.setSrcLongitude(outletEntity.getLongitude().doubleValue());
                quotesPlatformDTO.setAddress(order.getAddress());
                //quotesPlatformDTO.setPackages();
                ResponseDTO<QuotesResponseDTO> obj = deliveryRepository.getQuotes(quotesPlatformDTO);
                deliveryFee = Double.parseDouble(obj.getBody().getQuotes().get(0).getAmount().toString());
                Timestamp shipDate = obj.getBody().getQuotes().get(0).getEstimatedTimeline().getDropoff();
                orderOutletEntity.setShipDate(shipDate);
            } catch (Exception e) {
                throw new BadRequestException(e.getMessage());
            }
        }
        //4. create order information
        OrderInformationEntity orderInformationEntity = new OrderInformationEntity();
        orderInformationEntity.setReceiverName(order.getName());
        orderInformationEntity.setReceiverPhone(order.getPhone());
        orderInformationEntity.setPaymentMethod(order.getPaymentMethod());
        orderInformationEntity.setDeliveryMethod(order.getDeliveryMethod());
        orderInformationEntity.setReceiverAddress(order.getAddress());
        if (locationCustomer.size() == 2) {
            orderInformationEntity.setReceiverLat(locationCustomer.get(0));
            orderInformationEntity.setReceiverLng(locationCustomer.get(1));
        }

        orderOutletEntity.setOrderInformation(orderInformationEntity);


        orderOutletEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        orderOutletEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        orderOutletEntity.setCode(order.getOrderCode() == null ? RandomTokenBeanUtil.generateOrderCode() : order.getOrderCode());
        orderOutletEntity.setSaleChanel(Constants.SOURCE_POS.equals(order.getSource()) ? "SALE_CHANEL_DIRECT" : CoreConstants.SALE_CHANEL.ONLINE.getName());
        orderOutletEntity.setStatus(Constants.SOURCE_POS.equals(order.getSource()) || Constants.SOURCE_SMART_VANSALE.equals(order.getSource()) ? "SUCCESS" : "WAITING_FOR_CONFIRMATION");
        orderOutletEntity.setFromsource(order.getSource());
        orderOutletEntity.setNote(order.getNote());

        //order on behalf
        if (order.getSalesManUserId() != null) {
            orderOutletEntity.setReferedBy(usersRepository.findById(order.getSalesManUserId()).get());
        }

        //Hardcode for store in db first
        orderOutletEntity.setLoyaltyPoint(0);
        orderOutletEntity.setDebt(0D);
        orderOutletEntity.setTotalItem(0);
        orderOutletEntity.setTotalOriginalPrice(0.0);
        orderOutletEntity.setDeliveryPrice(0.0);
        orderOutletEntity.setTotalStoreDiscountPrice(0.0);
        orderOutletEntity.setTotalPromotionDiscountPrice(0.0);
        orderOutletEntity.setTotalWeight(0.0);
        orderOutletEntity.setCustomerId(customerEntity.getCustomerId());
        orderOutletEntity.setTotalPrice(0.0);
        // Grab refund
        orderOutletEntity.setToken(order.getToken());
        orderOutletEntity.setGrabTxId(order.getTxId());
        orderOutletEntity.setGrabGroupTxId(order.getOrderCode());


        order.setFromEasyOrder(true);

        //6. get list promotion of product
        order.setAction(1); //update DB promotion-service  action 1: store DB  . 0: view Promotions without update
        PromotionPerOrderDTO promotionPerOrderDTO = new PromotionPerOrderDTO();
        try {
            //list promotion of product
            promotionPerOrderDTO = promotionRepository.getPromotionDiscountPriceByEasyOrder(order);  //call Promotion Service .DB
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        orderOutletEntity = orderOutletRepository.save(orderOutletEntity);


        //7. Calculate and update total price for order
        EasyOrderRedeemPromotionDTO easyOrderRedeemPromotionDTO = this.calculatePriceForEasyOrder(storeDiscountPerOrder, promotionPerOrderDTO, order, orderOutletEntity);

        orderOutletEntity.setTotalPromotionDiscountPrice(easyOrderRedeemPromotionDTO.getTotalPromotionDiscountPrice());
        orderOutletEntity.setTotalOriginalPrice(easyOrderRedeemPromotionDTO.getTotalOriginalPrice());
//        orderOutletEntity.setDeliveryPrice(easyOrderRedeemPromotionDTO.getDeliveryFee() == 0 ? deliveryFee - easyOrderRedeemPromotionDTO.getDeliveryDiscount() : easyOrderRedeemPromotionDTO.getDeliveryFee());
//        // calculate delivery base on Promotion Discount Delivery
        double deliveryPrice = deliveryFee;
        double totalDeliveryDiscount = 0.0;
        int typeShipping = 0; // default  0 :corresponding for SHIPPING_FIX_PRICE ($)  ||  != 0 : SHIPPING_DISCOUNT (%)
        // Apply discount delivery by Promotion
        if (deliveryFee > 0 && easyOrderRedeemPromotionDTO.getLstPromotionDelivery() != null && easyOrderRedeemPromotionDTO.getLstPromotionDelivery().size() > 0 &&
                (!Constants.DELIVERY_PICK_AND_GO.equals(order.getDeliveryMethod())) && (StringUtils.isNotBlank(order.getAddress()))) {
            if (easyOrderRedeemPromotionDTO.getLstPromotionDelivery() != null && easyOrderRedeemPromotionDTO.getLstPromotionDelivery().size() > 0 &&
                    easyOrderRedeemPromotionDTO.getLstPromotionDelivery().get(0).getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT)) // Discount by Percent
                typeShipping = 1;
            //apply shipping promotion
            totalDeliveryDiscount = this.getDeliveryPriceDiscountByPromotion(easyOrderRedeemPromotionDTO, deliveryFee, customerEntity.getCustomerId(), 1);
            // deliveryPrice update by Promotion
            if (typeShipping == 0) { // SHIPPING_FIX_PRICE ($)
                double originalDelivery = deliveryFee;
                deliveryPrice = totalDeliveryDiscount;
                totalDeliveryDiscount = originalDelivery - totalDeliveryDiscount > 0 ? originalDelivery - totalDeliveryDiscount : totalDeliveryDiscount;
            } else // SHIPPING_DISCOUNT (%)
                deliveryPrice = deliveryFee - totalDeliveryDiscount > 0 ? deliveryFee - totalDeliveryDiscount : 0;

            easyOrderRedeemPromotionDTO.setDeliveryFee(deliveryFee);
            easyOrderRedeemPromotionDTO.setDeliveryDiscount(totalDeliveryDiscount);
        }
        orderOutletEntity.setDeliveryPrice(deliveryPrice);


        orderOutletEntity.setTotalPrice(easyOrderRedeemPromotionDTO.getTotalPrice() + deliveryPrice);
//        orderOutletEntity.setTotalPrice(easyOrderRedeemPromotionDTO.getDeliveryFee() == 0
//                ? easyOrderRedeemPromotionDTO.getTotalPrice() + deliveryPrice - easyOrderRedeemPromotionDTO.getDeliveryDiscount()
//                : easyOrderRedeemPromotionDTO.getTotalPrice() + easyOrderRedeemPromotionDTO.getDeliveryFee());
        orderOutletEntity.setSaving(easyOrderRedeemPromotionDTO.getTotalOriginalPrice() - easyOrderRedeemPromotionDTO.getTotalSalePrice());
        orderOutletEntity.setTotalStoreDiscountPrice(easyOrderRedeemPromotionDTO.getTotalStoreDiscount());
        orderOutletEntity.setTotalItem(easyOrderRedeemPromotionDTO.getTotalItems());
        orderOutletEntity.setTotalWeight(easyOrderRedeemPromotionDTO.getTotalWeight());

        //9. save promotion apply on Easyorder
        for (ReductionDTO outletPromotionDTO : easyOrderRedeemPromotionDTO.getOutletPromotionPass()) {
            OrderOutletPromotionEntity orderOutletPromotionEntity = new OrderOutletPromotionEntity();
            orderOutletPromotionEntity.setOutletPromotionId(outletPromotionDTO.getOutletPromotionId());
            orderOutletPromotionEntity.setOrderoutletId(orderOutletEntity.getOrderOutletID());

            // save detail Promotion Discount per Order
            try {
                com.fasterxml.jackson.databind.ObjectMapper oMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                String tmp = null;
                tmp = oMapper.writeValueAsString(outletPromotionDTO); // obj 2 json
                orderOutletPromotionEntity.setPromotionDiscount(tmp);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                System.out.println("<><><>PromotionDiscount : Can not convert reductionDTO to json !!!!");
            }

            orderOutletPromotionRepository.save(orderOutletPromotionEntity);
        }

        //10. update product order item
        List<ProductOrderItemEntity> productOrderItemEntities = new ArrayList<>();
        if (easyOrderRedeemPromotionDTO.getProducts() != null && easyOrderRedeemPromotionDTO.getProducts().size() > 0) {
            for (ProductOrderItemDTO dto : easyOrderRedeemPromotionDTO.getProducts()) {
                ProductOrderItemEntity productOrderItemEntity = ProductOrderItemUtils.DTO2entity(dto);
                productOrderItemEntity.setOrderOutletId(orderOutletEntity.getOrderOutletID());
                productOrderItemRepository.save(productOrderItemEntity);
                productOrderItemEntities.add(productOrderItemEntity);
            }
        }

        // Update Free-Product to OrderItem
        if (easyOrderRedeemPromotionDTO.getFreeProducts() != null && easyOrderRedeemPromotionDTO.getFreeProducts().size() > 0) {
            for (ProductOutletSkuDTO dto : easyOrderRedeemPromotionDTO.getFreeProducts()) {
                ProductOrderItemEntity productOrderItemEntity = new ProductOrderItemEntity();
                productOrderItemEntity.setPrice(0.0);
                productOrderItemEntity.setQuantity(1);
                productOrderItemEntity.setProductOutletSkuId(dto.getProductOutletSkuId());
                productOrderItemEntity.setOrderOutletId(orderOutletEntity.getOrderOutletID());
                productOrderItemRepository.save(productOrderItemEntity);
                productOrderItemEntities.add(productOrderItemEntity);
            }
        }

        // Update Free-Gift to OrderItem
        if (easyOrderRedeemPromotionDTO.getGifts() != null && easyOrderRedeemPromotionDTO.getGifts().size() > 0) {
            for (GiftDTO giftDTO : easyOrderRedeemPromotionDTO.getGifts()) {
                GiftEntity giftEntity = giftRepository.findById(giftDTO.getGiftId()).get();
                if (giftEntity == null) continue;
                ProductOrderItemEntity productOrderItemEntity = new ProductOrderItemEntity();
                productOrderItemEntity.setOrderOutletId(orderOutletEntity.getOrderOutletID());
                productOrderItemEntity.setGiftId(giftDTO.getGiftId());
                productOrderItemEntity.setPrice(0.0);
                productOrderItemEntity.setQuantity(1);

                Integer totalReceived = giftEntity.getTotalReceivedPromotion() == null ? 1 : giftEntity.getTotalReceivedPromotion() + 1;
                giftEntity.setTotalReceivedPromotion(totalReceived);
                if (giftEntity.getTotalReceivedPromotion() >= giftEntity.getQuantityPromotion())
                    giftEntity.setStatus(0);
//                        giftEntity.setQuantityPromotion(giftEntity.getQuantityPromotion() - 1);
                giftRepository.saveAndFlush(giftEntity);
                productOrderItemRepository.save(productOrderItemEntity);
                productOrderItemEntities.add(productOrderItemEntity);

            }
        }
        if (productOrderItemEntities != null && productOrderItemEntities.size() > 0) {
            orderOutletEntity.setProductOrderItemEntities(productOrderItemEntities);

            //create list products stock out (include free product promotion)
            for (ProductOrderItemEntity productOrderItemEntity : productOrderItemEntities) {
                if (productOrderItemEntity.getProductOutletSkuId() != null) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setProductOutletSkuId(productOrderItemEntity.getProductOutletSkuId());
                    productDTO.setPrice(productOrderItemEntity.getPrice());
                    productDTO.setQuantity(productOrderItemEntity.getQuantity());
                    productsStockOut.add(productDTO);
                }
            }
        }

        //11. stock out inventory
        //check is allow inventory
        if (this.isAllowStorage(orderOutletEntity.getOutlet().getOutletId())) {
            WareHouseEntity wareHouseEntity = new WareHouseEntity();
            if (order.getWarehouseId() == null) {
                wareHouseEntity = warehouseRepository.findByOutletOutletIdAndIsDefaultIsTrue(orderOutletEntity.getOutlet().getOutletId());                  //stock out from default warehouse
            } else {
                wareHouseEntity.setWareHouseId(order.getWarehouseId());
            }
            if (wareHouseEntity != null && wareHouseEntity.getWareHouseId() != null) {
                addStockOut(productsStockOut, orderOutletEntity, wareHouseEntity.getWareHouseId());

                //update modifidate for sync offline
                orderService.updateSkuAfterStockOut(productsStockOut);
            }
        }

        //8. update debt, point
        // NOTE: don't update debt, point for not member
        if (loyaltyMemberEntity != null && Constants.LOYALTY_MEMBERSHIP_MEMBER.equals(loyaltyMemberEntity.getStatus())) {

            //8.1 update point
            Integer loyaltyPoint = calculateLoyaltyPoint4EasyOrder(orderOutletEntity, loyaltyMemberEntity, order.getProducts());
            orderOutletEntity.setLoyaltyPoint(loyaltyPoint);

            //8.1.1 update loyalty member when customer have loyalty point
            if (loyaltyPoint != null && loyaltyPoint > 0) {
                loyaltyMemberEntity.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
                Integer monthExpired = outletEntity.getLoyaltyPointExpiredMonth();
                loyaltyMemberEntity.setPointExpiredDate(CommonBeanUtils.calculatorExpiredFromNowWithMonthInput(monthExpired != null ? monthExpired : 0));
            }

            //8.1.2 update point when order "COMPLETED"  (submit order from POS/VANSALE APP is auto completed)
            if (Constants.SOURCE_POS_ONLINE.equals(order.getSource()) || Constants.SOURCE_POS.equals(order.getSource()) || Constants.SOURCE_SMART_VANSALE.equals((order.getSource()))) {
                try {
                    loyaltyService.updatePointWhenSuccessOrder(orderOutletEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 8.2 update credit
            if (order.isCredit()) {
                orderOutletEntity.setDebt(order.getDebt());
                if (Constants.SOURCE_POS.equals(order.getSource()) || Constants.SOURCE_SMART_VANSALE.equals(order.getSource()) || Constants.SOURCE_POS_ONLINE.equals(order.getSource())) {

                    Double creditLimit = loyaltyMemberEntity.getCreditLimit() != null ? loyaltyMemberEntity.getCreditLimit() : 0D;
                    Double paymentDueAmount = loyaltyMemberEntity.getTotalDebt() != null ? loyaltyMemberEntity.getTotalDebt() : 0D;
                    Integer creditTerms = loyaltyMemberEntity.getCreditTerms() != null ? loyaltyMemberEntity.getCreditTerms() : 0;

                    Double totalDebt = order.getDebt() + paymentDueAmount;
                    Double creditBalance = creditLimit - totalDebt;

                    loyaltyMemberEntity.setTotalDebt(totalDebt);

                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    CustomerDebtHistoryEntity historyEntity = new CustomerDebtHistoryEntity();
                    historyEntity.setOrderOutlet(orderOutletEntity);
                    historyEntity.setLoyaltyMember(loyaltyMemberEntity);
                    historyEntity.setAmount(orderOutletEntity.getDebt());
                    historyEntity.setType(CoreConstants.DEBT_TYPE.DEBT_CREDIT.getType());
                    historyEntity.setCreditBalance(creditBalance);
                    historyEntity.setPaymentDueAmount(totalDebt);
                    historyEntity.setCreatedDate(now);
                    historyEntity.setStatus(1);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(now);
                    cal.add(Calendar.DAY_OF_WEEK, creditTerms);
                    Timestamp newPaymentDueDateForOrder = new Timestamp(cal.getTime().getTime());
                    historyEntity.setPaymentDueDate(newPaymentDueDateForOrder);
                    loyaltyMemberEntity.setPaymentDueDate(newPaymentDueDateForOrder);

                    if (orderOutletEntity.getOutlet().getShopman() != null) {
                        UserEntity loginUser = new UserEntity();
                        loginUser.setUserId(orderOutletEntity.getOutlet().getShopman().getUserId());
                        historyEntity.setCreatedBy(loginUser);
                    }
                    loyaltyMemberEntity.setUpdatedDate(now);
                    customerDebHistoryRepository.save(historyEntity);
                }
                loyaltyMemberRepository.save(loyaltyMemberEntity);
            }
        }

        orderOutletEntity = orderOutletRepository.saveAndFlush(orderOutletEntity);

        // Next Purchase :
        for (ReductionDTO reductionDTO : promotionPerOrderDTO.getReductionDTOS()) {
            if (reductionDTO.getPromotionType() == PromotionType.NEXTPURCHASE.getNo()) {
                System.out.println(" ~~~~~  EasyOrder with : " + orderOutletEntity.getOrderOutletID() + "  " + reductionDTO.getCodeNextPurchase());
                CustomerRewardEntity customerRewardEntity = customerRewardRepository.getCustomerRewardByCode(reductionDTO.getCodeNextPurchase());
                customerRewardEntity.setOrderOutletId(orderOutletEntity.getOrderOutletID());
                customerRewardRepository.saveAndFlush(customerRewardEntity);
            }
        }

        // 12. add gift promotion to customer reward


        // 13. send mail for easy order
        if (order.getSource().equals(Constants.SOURCE_EASY_ORDER)) {
            this.sendEmail(orderOutletEntity, order.getEmail()).getBody();
        }

        //14. send notification when submit from POS (online/offline)
        if (Constants.SOURCE_POS.equals(order.getSource()) || Constants.SOURCE_POS_ONLINE.equals(order.getSource())) {

            //14.1 Get template notify
            NotifyTemplateEntity notifyTemplateEntity = notifyTemplateRepositoty.findByCode(CoreConstants.NOTIFY_TEMPLATE.TEMPLATE_OUTLET_NEW_ORDER.getName());

            OutletEmployeeEntity outletEmployeeEntity = null;

            //14.2 Get salesman
            if (order.getSalesManUserId() != null) {
                outletEmployeeEntity = outletEmployeeRepository.findByUserUserId(order.getSalesManUserId());
            }

            OrderInformationMobileDTO orderInformationMobileDTO = new OrderInformationMobileDTO();

            //14.3 set language template notification
            List<String> listLang = new ArrayList<>();
            listLang.add("vi");
            listLang.add("cn");
            listLang.add("ma");
            listLang.add("id");
            listLang.add("en");

            //14.4 Send notification to outlet
            orderService.sendNotification2Outlet(orderOutletEntity, customerEntity, outletEntity, notifyTemplateEntity, listLang);

            //14.5 Send notification to buyer
            orderService.sendNotification2Shopper(orderOutletEntity, outletEntity);
        }

        status = new StatusResponse(Constants.ORDER_VALID);
        status.setId(orderOutletEntity.getOrderOutletID());
        return status;
    }

    private double updatePriceForProducts(List<ProductDTO> products, long customerId, long outletId) {
        double totalStoreDiscount = 0;
        for (ProductDTO dto : products) {
            List<LoyaltyMemberEntity> lstLoyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletOutletIdAndActive(customerId, outletId, 1);
            if (lstLoyaltyMemberEntity != null && lstLoyaltyMemberEntity.size() > 0) {
                List<Long> lstPricingEntity = pricingRepository.findPricingId(lstLoyaltyMemberEntity.get(0).getCustomerGroup() != null ? lstLoyaltyMemberEntity.get(0).getCustomerGroup().getCustomerGroupId() : 0);
                Long pricingId = lstPricingEntity != null && lstPricingEntity.size() > 0 ? lstPricingEntity.get(0) : null;
                List<PricingSkuEntity> lstPricingSkuEntity = pricingSkuRepository.findByPricingIdAndProductOutletSkuId(pricingId != null ? pricingId : 0, dto.getProductOutletSkuId());
                double salePrice = productOutletSkuRepository.findByProductOutletSkuId(dto.getProductOutletSkuId()).getSalePrice(); // original Price : salePrice
                double pricingDiscountSku = lstPricingSkuEntity != null && lstPricingSkuEntity.size() > 0 && lstPricingSkuEntity.get(0).getSalePrice() != null ? lstPricingSkuEntity.get(0).getSalePrice() : 0.0;
                dto.setPrice(pricingDiscountSku > 0 ? pricingDiscountSku : salePrice);
                totalStoreDiscount += dto.getQuantity() * (pricingDiscountSku > 0 ? salePrice - pricingDiscountSku : 0);
            }
            if (dto.getPrice() == null) {
                Double salePrice = productOutletSkuRepository.findByProductOutletSkuId(dto.getProductOutletSkuId()).getSalePrice(); // original Price : salePrice
                dto.setPrice(salePrice);
            }
        }
        return totalStoreDiscount;
    }

    public OrderOutletDTO getOrderInfoById(Long orderId) {
        OrderOutletDTO rs;
        Optional<OrderOutletEntity> orderOutletEntity = orderOutletRepository.findById(orderId);
        if (orderOutletEntity.isPresent()) {
            rs = EasyOrderSumitBeanUtils.entity2DTO(orderOutletEntity.get());
            OutletEntity outlet = orderOutletEntity.get().getOutlet();
            OutletDTO outletDTO = EasyOrderBeanUtils.entity2DTO(outlet);
            outletDTO.setCurrency(countryRepository.findById(outletDTO.getCountryId()).get().getCurrency());
            rs.setOutlet(outletDTO);
            List<ProductOrderItemEntity> entities = productOrderItemRepository.findByOrderOutletId(orderId);
            List<ProductOrderItemDTO> dtos = new ArrayList<>();
            for (ProductOrderItemEntity entity : entities) {
                ProductOrderItemDTO dto = OrderItemUtils.entity2DTO(entity);
                if (entity.getProductOutletSkuId() != null) {
                    ProductOutletSkuEntity productOutletSkuEntity = productOutletSkuRepository.findByProductOutletSkuId(entity.getProductOutletSkuId());
                    dto.setProductOutletSku(ProductOutletSkuBeanUtils.entity2DTO(productOutletSkuEntity));
                    ProductOutletEntity productOutletEntity = productOutletSkuEntity.getProductOutlet();
                    dto.setName(productOutletEntity.getName());
                    dto.setCode(productOutletEntity.getCode());
                } else {
                    if (entity.getGiftId() != null) {
                        Optional<GiftEntity> giftEntity = giftRepository.findById(entity.getGiftId());
                        if (giftEntity.isPresent()) {
                            dto.setName(giftEntity.get().getName());
                            dto.setPrice(0.0);
                            dto.setSalePrice(0.0);
                            dto.setTotalAmount(0.0);
                        }
                    }
                }


                dto.setQuantity(entity.getQuantity());
                dtos.add(dto);
            }
            rs.setProducts(dtos);
            return rs;
        } else {
            return null;
        }
    }

    private ResponseEntity<Boolean> sendEmail(OrderOutletEntity dto, String email) {
        try {
            SendMailDTO sendMailDTO = new SendMailDTO();
            sendMailDTO.setMailTemplate("SUBMIT_ORDER");
            String[] recipents = {email};
            sendMailDTO.setRecipients(recipents);
            sendMailDTO.setSendNow(true);
            Map<String, Object> sender = new HashMap<>();
            sender.put("customerName", dto.getOrderInformation().getReceiverName());
            sender.put("hotline", "19001811");
            sender.put("orderId", dto.getCode());

            String detailOrderUrl = env.getProperty("easy.order.detail.url", "");
            String base64OrderId = new String(Base64.encodeBase64(String.valueOf(dto.getOrderOutletID()).getBytes()));
            sender.put("link", detailOrderUrl + base64OrderId);
//            sender.put("link", "http://localhost:8080/order/easy/getOrder?id=" + dto.getOrderOutletID());
            sendMailDTO.setSender(sender);
            emailRepository.sendMail(sendMailDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }


    public EasyOrderRedeemPromotionDTO calculatePriceForEasyOrder(double storeDiscountPerOrder, PromotionPerOrderDTO promotionPerOrderDTO, EasyOrderSubmitDTO easyOrderSubmitDTO, OrderOutletEntity orderOutletEntity) {
        EasyOrderRedeemPromotionDTO resultDTO = new EasyOrderRedeemPromotionDTO();
        int totalItems = 0;
        double totalOriginalPriceOrder = 0D;
        double totalSalePriceOrder = 0D;
        double totalPrice = 0D;
        double deliveryDiscount = 0D;
        double totalWeight = 0D;
        double totalStoreDiscount = 0;  // $ Discount by POS per Order

//        ReductionDTO promotionDelivery = null;
        List<ReductionDTO> promotionsPass = new ArrayList<>();
        List<ReductionDTO> lstPromotionDelivery = new ArrayList<>();                   //promotion delivery
        List<GiftEntity> giftEntities = new ArrayList<>();                            //gift
        List<ProductOutletSkuEntity> productEntities = new ArrayList<>();             //free product
        List<ProductOutletSkuMobileDTO> productsFixPrice = new ArrayList<>();         //products have fix price
        List<ProductOutletSkuDTO> freeProducts = new ArrayList<>();             // free Products get by Promotion
        List<ProductOrderItemDTO> productOrderItemDTOS = new ArrayList<>();
        ReductionDTO easyOrderDiscount = null;

        int typeShipping = 0; // default  0 :corresponding for SHIPPING_FIX_PRICE ($)  ||  != 0 : SHIPPING_DISCOUNT (%)
        // 1. get information promotions
        for (ReductionDTO reductionDTO : promotionPerOrderDTO.getReductionDTOS()) {
            //just for test
            System.out.println("Type :" + reductionDTO.getDiscountType());
            System.out.println("Value Discount: " + reductionDTO.getValueDiscount());

            //promotion PERCENT_OFF || AMOUNT OFF
            if (DiscountType.PERCENT_OFF.equals(reductionDTO.getDiscountType()) || DiscountType.AMOUNT_OFF.equals(reductionDTO.getDiscountType())) {
                promotionsPass.add(reductionDTO);
            }

            // EasyOrder Promotion
            if (reductionDTO.getPromotionType().equals(PromotionType.EASYORDER.getNo())) {
                easyOrderDiscount = reductionDTO;
            }

            // Get Promotion Delivery
            if ((reductionDTO.getPromotionType() == 3) &&
                    (reductionDTO.getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT) ||
                            reductionDTO.getDiscountType().equals(DiscountType.SHIPPING_FIX_PRICE))) {
                if (reductionDTO.getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT)) {  // Discount by Percent
                    typeShipping += 1;
                    lstPromotionDelivery.add(reductionDTO);
                } else if (typeShipping == 0) {  // Discount by Fix-Price
                    lstPromotionDelivery.add(reductionDTO);     // only get One Promotion Discount for Shipping-Fix-Price
                }
//                promotionDelivery = reductionDTO;
            }

            //Promotion FIX PRICE ( buy product with promotion price)
            if (reductionDTO.getDiscountType().equals(DiscountType.FIX_PRICE)) {
                ProductOutletSkuMobileDTO productFixPrice = new ProductOutletSkuMobileDTO();
                productFixPrice.setProductOutletSkuId(reductionDTO.getDiscountForTarget());
                productFixPrice.setPromotionPrice(reductionDTO.getValueDiscount());
                productFixPrice.setOutletPromotion(reductionDTO);
                productsFixPrice.add(productFixPrice);
            }

            //Promotion Buy X Get Gift
            if ((reductionDTO.getDiscountType().equals(DiscountType.GIFT))) {
                GiftEntity giftEntity = giftRepository.findById(reductionDTO.getDiscountForTarget()).get();
                giftEntities.add(giftEntity);
                promotionsPass.add(reductionDTO);
            }

            //Promotion Buy X Get Product
            if ((reductionDTO.getDiscountType().equals((DiscountType.PRODUCT)))) {
                ProductOutletSkuEntity productOutletSkuEntity = productOutletSkuRepository.findByProductOutletSkuId(reductionDTO.getDiscountForTarget());
                productEntities.add(productOutletSkuEntity);
                promotionsPass.add(reductionDTO);
            }
        }


        // Customer Group check
        Long customerId = customerRepository.findByUserIdUserId(easyOrderSubmitDTO.getUserId()).get(0).getCustomerId();
        Long pricingId = 0L;
        if (customerId != null) {
            List<LoyaltyMemberEntity> lstLoyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletOutletIdAndActive(customerId, easyOrderSubmitDTO.getOutletId(), 1);
            if (lstLoyaltyMemberEntity != null && lstLoyaltyMemberEntity.size() > 0) {
                List<Long> lstPricingEntity = pricingRepository.findPricingId(lstLoyaltyMemberEntity.get(0).getCustomerGroup() != null ? lstLoyaltyMemberEntity.get(0).getCustomerGroup().getCustomerGroupId() : 0);
                pricingId = lstPricingEntity != null && lstPricingEntity.size() > 0 ? lstPricingEntity.get(0) : null;
            }
        }

        double groupPriceSkuDiscount = 0.0D;
        // 2. Calculate price base on order items
        for (ProductDTO productDTO : easyOrderSubmitDTO.getProducts()) {

            double totalSalePriceOfProduct = 0;
            double skuFixPrice = 0; //apply promotion price if product in promotion FIX PRICE
            int quantityFixPrice = 0;
            Double salePriceSku = null;
            double groupPrice = -1;

            ProductOutletSkuEntity productOutletSkuEntity = productOutletSkuRepository.findByProductOutletSkuId(productDTO.getProductOutletSkuId());
            if (productOutletSkuEntity != null) {
                totalOriginalPriceOrder += (productOutletSkuEntity.getSalePrice() * productDTO.getQuantity());
                totalSalePriceOrder += (productDTO.getPrice() * productDTO.getQuantity());
                totalSalePriceOfProduct = (productDTO.getPrice() * productDTO.getQuantity());   // Depend on Edit price from POS
                totalWeight += ((productOutletSkuEntity.getWeight() == null ? 0 : productOutletSkuEntity.getWeight()) * productDTO.getQuantity());

                //get sale price by check customerGroup
                if (customerId != null) {
                    List<PricingSkuEntity> lstPricingSkuEntity = pricingSkuRepository.findByPricingIdAndProductOutletSkuId(pricingId != null ? pricingId : 0, productDTO.getProductOutletSkuId());
                    groupPrice = lstPricingSkuEntity != null && lstPricingSkuEntity.size() > 0 && lstPricingSkuEntity.get(0).getSalePrice() != null ? lstPricingSkuEntity.get(0).getSalePrice() : -1;
//                    salePriceSku = lstPricingSkuEntity != null && lstPricingSkuEntity.size() > 0 && lstPricingSkuEntity.get(0).getSalePrice() != null ? lstPricingSkuEntity.get(0).getSalePrice() : shoppingCartEntity.getProductOutletSku().getSalePrice();
                }

                // check edit-price from POS
                if (easyOrderSubmitDTO.getSource().equals(Constants.SOURCE_POS) || easyOrderSubmitDTO.getSource().equals(Constants.SOURCE_POS_ONLINE)) {
                    if (!productDTO.getPrice().equals(productOutletSkuEntity.getSalePrice())) { // PriceSKU was changed
//                        if (groupPrice >= 0 && !productDTO.getPrice().equals(groupPrice)){ // PriceSKU was changed by GroupPrice
//                        } else { //  PriceSKU was edited by human
//                        }
                        if (productDTO.getPrice() < productOutletSkuEntity.getSalePrice()) {
                            productDTO.setPriceDiscount(productOutletSkuEntity.getSalePrice() - productDTO.getPrice()); // storeDiscount update
                            totalStoreDiscount += productDTO.getPriceDiscount() * productDTO.getQuantity();
                        }
                    }
                } else { // EasyOrder
                    if (productDTO.getPrice() < productOutletSkuEntity.getSalePrice()) { // groupPrice
                        productDTO.setPriceDiscount(productOutletSkuEntity.getSalePrice() - productDTO.getPrice()); // storeDiscount update
                        totalStoreDiscount += productDTO.getPriceDiscount() * productDTO.getQuantity();
                    }
                }

//                // 6789667896update groupPrice to calculate TotalStoreDiscount
//                groupPriceSkuDiscount = shoppingCartEntity.getProductOutletSku().getSalePrice() - salePriceSku > 0 ? shoppingCartEntity.getProductOutletSku().getSalePrice() - salePriceSku : 0;


                if (productsFixPrice.size() > 0) {
                    //sort promotion price ASC
                    productsFixPrice = productsFixPrice.stream().sorted(Comparator.comparingDouble(ProductOutletSkuMobileDTO::getPromotionPrice)).collect(Collectors.toList());
                    skuFixPrice = productsFixPrice.get(0).getPromotionPrice();
                    for (ProductOutletSkuMobileDTO dto : productsFixPrice) {
                        if (productDTO.getQuantity() > quantityFixPrice && dto.getProductOutletSkuId().equals(productOutletSkuEntity.getProductOutletSkuId())) {
                            quantityFixPrice++;
                            //add promotion passed
                            promotionsPass.add(dto.getOutletPromotion());
                        }
                    }
                    skuFixPrice = skuFixPrice * quantityFixPrice;
                }

                ProductOrderItemEntity productOrderItemEntity = new ProductOrderItemEntity();
                productOrderItemEntity.setProductOutletSkuId(productOutletSkuEntity.getProductOutletSkuId());
                productOrderItemEntity.setQuantity(productDTO.getQuantity());
                productOrderItemEntity.setPrice(productDTO.getPrice()); // GroupPrice || salePrice || Edit-Price by POS
//                productOrderItemEntity.setPriceDiscount(productOutletSkuEntity.getOriginalPrice() - productDTO.getPrice());
                productOrderItemEntity.setPriceDiscount(0.0);
                productOrderItemEntity.setWeight(productOutletSkuEntity.getWeight());


                //Total item in order
                totalItems += productDTO.getQuantity();

                ///recalculate total promotion discount in case FIX PRICE
                if (skuFixPrice != 0 && quantityFixPrice != 0) {
                    promotionPerOrderDTO.setTotalDiscountValue(promotionPerOrderDTO.getTotalDiscountValue() + (totalSalePriceOfProduct - ((productDTO.getPrice() * (productDTO.getQuantity() - quantityFixPrice)) + skuFixPrice)));
//                    promotionPerOrderDTO.setTotalDiscountValue(promotionPerOrderDTO.getTotalDiscountValue() + (productOutletSkuEntity.getSalePrice() * quantityFixPrice - skuFixPrice));
                }

//                //in case : PROMOTION PRODUCT FIX PRICE
//                if (skuFixPrice != 0 && quantityFixPrice != 0) {
//                    // cal total amount not include quantity sku have fix price + total amount sku have fix price
////                productOrderItemDTO.setTotalAmount((shoppingCartEntity.getProductOutletSku().getSalePrice() * (shoppingCartEntity.getQuantity() - quantityFixPrice)) + skuFixPrice);
//                    productOrderItemDTO.setTotalAmount(shoppingCartEntity.getProductOutletSku().getSalePrice() * shoppingCartEntity.getQuantity());
////                promotionPerOrderDTO.setTotalDiscountValue(promotionPerOrderDTO.getTotalDiscountValue() + (shoppingCartEntity.getProductOutletSku().getSalePrice() * shoppingCartEntity.getQuantity()) - productOrderItemDTO.getTotalAmount());
//                    promotionPerOrderDTO.setTotalDiscountValue(promotionPerOrderDTO.getTotalDiscountValue() + ((shoppingCartEntity.getProductOutletSku().getSalePrice() * quantityFixPrice - skuFixPrice)));
//                } else {
//                    //salePrice * quantity
////                productOrderItemDTO.setTotalAmount(salePriceSku * shoppingCartEntity.getQuantity());
//                    productOrderItemDTO.setTotalAmount(shoppingCartEntity.getProductOutletSku().getSalePrice() * shoppingCartEntity.getQuantity());
//                }

                productOrderItemDTOS.add(ProductOrderItemUtils.entity2DTO(productOrderItemEntity));

            }
        }

        //3. Apply discount delivery
//        if (promotionDelivery != null && (!Constants.DELIVERY_PICK_AND_GO.equals(easyOrderSubmitDTO.getDeliveryMethod())) && (StringUtils.isNotBlank(easyOrderSubmitDTO.getAddress()))) {
//            if (promotionDelivery.getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT)) {  // Discount by Percent
//                Double deliveryFee = easyOrderSubmitDTO.getDeliveryFee() != null ? easyOrderSubmitDTO.getDeliveryFee() : 0;
//                deliveryDiscount = deliveryFee * promotionDelivery.getValueDiscount() / 100;
//            } else { // Shipping fixed price
//                resultDTO.setDeliveryFee(promotionDelivery.getValueDiscount());
//            }
//        }

        //4. add free product
        for (ProductOutletSkuEntity entity : productEntities) {
            ProductOrderItemEntity freeProduct = new ProductOrderItemEntity();
            ProductOutletSkuEntity product = productOutletSkuRepository.findByProductOutletSkuId(entity.getProductOutletSkuId());
            freeProduct.setProductOutletSkuId(product.getProductOutletSkuId());
            freeProduct.setQuantity(1);
            freeProduct.setPrice(0.0);
            totalItems += 1;
            //add free product to order
//            productOrderItemDTOS.add(ProductOrderItemUtils.entity2DTO(freeProduct));
            //show free product
            freeProducts.add(ProductOutletSkuBeanUtils.entity2DTO(product));
        }

        // add gift to ProductOrderItem
        if (giftEntities.size() > 0 || giftEntities != null) {
            ProductOrderItemEntity freeGift;
            for (GiftEntity giftEntity : giftEntities) {
                freeGift = new ProductOrderItemEntity();
                freeGift.setGiftId(giftEntity.getGiftId());
                freeGift.setQuantity(1);
                freeGift.setPrice(0.0);
                //add free Gift to order
//                productOrderItemDTOS.add(ProductOrderItemUtils.entity2DTO(freeGift));
//                totalItems += 1;
//                Integer totalReceived = giftEntity.getTotalReceived() == null ? 1 : giftEntity.getTotalReceived() + 1;
//                giftEntity.setTotalReceived(totalReceived);
////                        giftEntity.setQuantityPromotion(giftEntity.getQuantityPromotion() - 1);
//                giftRepository.saveAndFlush(giftEntity);
            }
        }

        Double totalPromotionDiscountPrice = promotionPerOrderDTO.getTotalDiscountValue();
        if (totalPromotionDiscountPrice >= totalSalePriceOrder) {    // Cover case Discount by Promotion less SalePrice
            totalPromotionDiscountPrice = totalSalePriceOrder;
        }

        //5. EasyOrder Discount
        if (easyOrderDiscount != null) {
            double totalEasyOrderDiscount = this.getDiscountValuePromotion(easyOrderDiscount, totalSalePriceOrder);
            totalPromotionDiscountPrice += totalEasyOrderDiscount > 0 ? totalEasyOrderDiscount : 0D;
        }

        //6. Calculate final total price
        double totalDiscount = 0;    // total final Discount Per Order
        // update totalPrice (final Paid)
        if (easyOrderSubmitDTO.getSource().equals(Constants.SOURCE_POS) || easyOrderSubmitDTO.getSource().equals(Constants.SOURCE_POS_ONLINE)) { // POS submit
            totalDiscount = totalPromotionDiscountPrice + storeDiscountPerOrder;
            totalPrice = (totalSalePriceOrder - totalDiscount) > 0 ? totalSalePriceOrder - totalDiscount : 0;
            totalStoreDiscount += storeDiscountPerOrder;
        } else { // EasyOrder or Vansale
            totalDiscount = totalPromotionDiscountPrice + totalStoreDiscount;
            totalPrice = (totalOriginalPriceOrder - totalDiscount) > 0 ? totalOriginalPriceOrder - totalDiscount : 0;
        }

        resultDTO.setTotalWeight(totalWeight);
        resultDTO.setTotalItems(totalItems);
        resultDTO.setTotalOriginalPrice(totalOriginalPriceOrder);
        resultDTO.setTotalStoreDiscount(totalStoreDiscount);
        resultDTO.setTotalSalePrice(totalSalePriceOrder);
        resultDTO.setTotalPrice(totalPrice);
        resultDTO.setDeliveryDiscount(deliveryDiscount);
        resultDTO.setTotalPromotionDiscountPrice(totalPromotionDiscountPrice + deliveryDiscount);
        resultDTO.setGifts(GiftUtils.entity2DTO(giftEntities));
        resultDTO.setProducts(productOrderItemDTOS);
        resultDTO.setOutletPromotionPass(promotionsPass);
        resultDTO.setFreeProducts(freeProducts);
        resultDTO.setProductsFixPrice(productsFixPrice);
        resultDTO.setLstPromotionDelivery(lstPromotionDelivery);
        return resultDTO;
    }

    public Map<String, Object> checkoutEasyOrder(EasyOrderSubmitDTO easyOrderSubmitDTO) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> rs = new HashMap<>();
        OutletEntity outletEntity = outletRepository.findById(easyOrderSubmitDTO.getOutletId()).get();
        //Validate information
        if (StringUtils.isNotBlank(easyOrderSubmitDTO.getPhone()) && StringUtils.isNotBlank(easyOrderSubmitDTO.getPrefixPhoneNumber())) {
            //Validate information
            PhoneDTO phoneDTO = CommonUtils.parsePhoneNumber(easyOrderSubmitDTO.getPhone(), easyOrderSubmitDTO.getPrefixPhoneNumber());
            if (phoneDTO == null) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_PHONE.name());
            } else if (StringUtils.isBlank(easyOrderSubmitDTO.getName())) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_FULLNAME_EMPTY.name());
            }
//            else if (!CommonUtils.isValidEmail(easyOrderSubmitDTO.getEmail())) {
//                throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_EMAIL.name());
//            }
            //get User
            if (phoneDTO != null) {
//                if (!accountRepository.isUserExist(phoneDTO.getFullNumber(), "").getBody()) {
//                    easyOrderSubmitDTO.setUserId(accountRepository.getDefaultUser().getBody().getUserId());
//                } else {
                List<UserEntity> userEntities = usersRepository.findByUserNameContains(phoneDTO.getFullNumber());

                //check exits user in system
                if (userEntities != null && userEntities.size() > 0) {
                    //exits user
                    if (userEntities.size() > 1) {
                        for (int i = 0; i < userEntities.size(); i++) {
                            //choose user have role shopper when user have two account for outlet and shopper
                            if (userEntities.get(i).getUserGroup().getCode().equals("SHOPPER")) {
                                easyOrderSubmitDTO.setUserId(userEntities.get(i).getUserId());
                            }
                        }
                    } else {
                        easyOrderSubmitDTO.setUserId(userEntities.get(0).getUserId());
                    }
                } else {
                    //create user to get userId
                    CustomerDTO customerDTO = new CustomerDTO();
                    customerDTO.setOutletId(easyOrderSubmitDTO.getOutletId());
                    customerDTO.setAddress(easyOrderSubmitDTO.getAddress());
                    UserDTO userDTO = new UserDTO();
                    userDTO.setFullPhoneNumber(phoneDTO.getFullNumber());
                    userDTO.setFullName(easyOrderSubmitDTO.getName());
                    userDTO.setEmail(easyOrderSubmitDTO.getEmail());
                    userDTO.setCountryId(outletEntity.getCountryId());
                    customerDTO.setUser(userDTO);
                    try {
                        ResponseEntity<CustomerDTO> res = accountRepository.getOrCreateCustomer(customerDTO);
                        CustomerDTO currentCustomer = res.getBody();
                        if (currentCustomer != null && currentCustomer.getCustomerId() != null) {
                            easyOrderSubmitDTO.setUserId(currentCustomer.getUser().getUserId());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        Long customerId = customerRepository.findByUserIdUserId(easyOrderSubmitDTO.getUserId()).get(0).getCustomerId();
        double totalStoreDiscount = updatePriceForProducts(easyOrderSubmitDTO.getProducts(), customerId, outletEntity.getOutletId());

        if (!(easyOrderSubmitDTO.getUserId() == null)) {
            //Calculate price
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(easyOrderSubmitDTO));
            } catch (Exception ex) {
            }

            //Calculate delivery fee
            Double deliveryFee = calculateDeliveryFee(easyOrderSubmitDTO);
            easyOrderSubmitDTO.setDeliveryFee(deliveryFee);
            easyOrderSubmitDTO.setFromEasyOrder(true);

            PromotionPerOrderDTO promotionPerOrderDTO = new PromotionPerOrderDTO();

            try {
                //list promotion of product
                promotionPerOrderDTO = promotionRepository.getPromotionDiscountPriceByEasyOrder(easyOrderSubmitDTO); //call Promotion Service .DB
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            EasyOrderRedeemPromotionDTO easyOrderRedeemPromotionDTO = this.calculatePriceForEasyOrder(totalStoreDiscount, promotionPerOrderDTO, easyOrderSubmitDTO, null);

            double deliveryPrice = deliveryFee;
            double totalDeliveryDiscount = 0.0;
            int typeShipping = 0; // default  0 :corresponding for SHIPPING_FIX_PRICE ($)  ||  != 0 : SHIPPING_DISCOUNT (%)
            // Apply discount delivery by Promotion
            if (deliveryFee > 0 && easyOrderRedeemPromotionDTO.getLstPromotionDelivery() != null && easyOrderRedeemPromotionDTO.getLstPromotionDelivery().size() > 0 &&
                    (!Constants.DELIVERY_PICK_AND_GO.equals(easyOrderSubmitDTO.getDeliveryMethod())) && (StringUtils.isNotBlank(easyOrderSubmitDTO.getAddress()))) {
                if (easyOrderRedeemPromotionDTO.getLstPromotionDelivery() != null && easyOrderRedeemPromotionDTO.getLstPromotionDelivery().size() > 0 &&
                        easyOrderRedeemPromotionDTO.getLstPromotionDelivery().get(0).getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT)) // Discount by Percent
                    typeShipping = 1;
                //apply shipping promotion
                totalDeliveryDiscount = this.getDeliveryPriceDiscountByPromotion(easyOrderRedeemPromotionDTO, deliveryFee, customerId, 0);
                // deliveryPrice update by Promotion
                if (typeShipping == 0) { // SHIPPING_FIX_PRICE ($)
                    double originalDelivery = deliveryFee;
                    deliveryPrice = totalDeliveryDiscount;
                    totalDeliveryDiscount = originalDelivery - totalDeliveryDiscount > 0 ? originalDelivery - totalDeliveryDiscount : totalDeliveryDiscount;
                } else // SHIPPING_DISCOUNT (%)
                    deliveryPrice = deliveryFee - totalDeliveryDiscount > 0 ? deliveryFee - totalDeliveryDiscount : 0;

                easyOrderRedeemPromotionDTO.setDeliveryFee(deliveryFee);
                easyOrderRedeemPromotionDTO.setDeliveryDiscount(totalDeliveryDiscount);
            }
            easyOrderRedeemPromotionDTO.setLstPromotionDelivery(null); // reset

            easyOrderRedeemPromotionDTO.setFinalTotalPrice(easyOrderRedeemPromotionDTO.getTotalPrice() + deliveryPrice);
//            easyOrderRedeemPromotionDTO.setFinalTotalPrice(easyOrderRedeemPromotionDTO.getDeliveryFee() == 0 ?
//                    easyOrderRedeemPromotionDTO.getTotalPrice() + deliveryFee - easyOrderRedeemPromotionDTO.getDeliveryDiscount() :
//                    easyOrderRedeemPromotionDTO.getTotalPrice() + easyOrderRedeemPromotionDTO.getDeliveryFee());
            rs = mapper.convertValue(easyOrderRedeemPromotionDTO, Map.class);

            //Calculate delivery fee after discount
            rs.put("deliveryPrice", deliveryPrice); // fix price OR discount

            // Check EasyOrderPromotionCode is Invalid
            rs.put("invalidCode", promotionPerOrderDTO.getInvalidCode() != null && promotionPerOrderDTO.getInvalidCode());
//            List<CustomerEntity> customerEntities = customerRepository.findByUserIdUserId(easyOrderSubmitDTO.getUserId());
//            rs.put("customerId", customerEntities!= null && customerEntities.size() > 0 ? customerEntities.get(0) : null);

        }
        return rs;
    }

    private Double calculateDeliveryFee(EasyOrderSubmitDTO orderDTO) {
        Double deliveryFee = 0D;
        if (orderDTO.getDeliveryMethod().equals(Constants.DELIVERY_SHIP_TO_HOME_EXPRESS) && StringUtils.isNotBlank(orderDTO.getAddress())) {
            try {
                List<Double> location = GoogleLocationUtil.getCoordinate(orderDTO.getAddress());
                BigDecimal latitude = BigDecimal.valueOf(location.get(0));
                BigDecimal longitude = BigDecimal.valueOf(location.get(1));
                OutletEntity outletEntity = outletRepository.findById(orderDTO.getOutletId()).get();
                QuotesPlatformDTO quotesPlatformDTO = new QuotesPlatformDTO();
                //quotesPlatformDTO.setCustomerId(null);
                quotesPlatformDTO.setOutletId(orderDTO.getOutletId());
                quotesPlatformDTO.setLatitude(latitude.doubleValue());
                quotesPlatformDTO.setLongitude(longitude.doubleValue());
                quotesPlatformDTO.setSrcLatitude(outletEntity.getLatitude().doubleValue());
                quotesPlatformDTO.setSrcLongitude(outletEntity.getLongitude().doubleValue());
                quotesPlatformDTO.setAddress(orderDTO.getAddress());
                //quotesPlatformDTO.setPackages();
                ResponseDTO<QuotesResponseDTO> obj = deliveryRepository.getQuotes(quotesPlatformDTO);
                deliveryFee = Double.parseDouble(obj.getBody().getQuotes().get(0).getAmount().toString());
                //shipDate = obj.getBody().getQuotes().get(0).getEstimatedTimeline().getDropoff();
                //orderOutletEntity.setShipDate(shipDate);
            } catch (Exception e) {
                //throw new BadRequestException(e.getMessage());
                e.getStackTrace();
            }
        } else if (!Constants.DELIVERY_PICK_AND_GO.equals(orderDTO.getDeliveryMethod()) && StringUtils.isNotBlank(orderDTO.getAddress())) {
            OutletEntity outlet = outletRepository.findById(orderDTO.getOutletId()).get();
            List<DeliveryServiceEntity> entities = deliveryServiceRepository.findByOutletId(outlet.getOutletId());
            deliveryFee = DeliveryUtils.calculateDeliveryPrice(outlet, orderDTO.getAddress(), orderDTO.getDeliveryMethod(), entities);
        }
        return deliveryFee;
    }

    private List<OutletPromotionDTO> getOutletPromotionById(List<Long> ids) {
        List<OutletPromotionEntity> outletPromotionEntities = new ArrayList<>();
        for (Long id : ids) {
            OutletPromotionEntity outletPromotionEntity = outletPromotionRepository.findById(id).get();
            outletPromotionEntities.add(outletPromotionEntity);
        }
        return OutletPromotionUtils.entity2dto(outletPromotionEntities);
    }

    public Map<String, Object> calculateTemporary(EasyOrderSubmitDTO easyOrderSubmitDTO) {
        Map<String, Object> rs = new HashMap<>();
//        double totalSalePrice = 0;
        double totalOriginalPrice = 0;  // total Original Price for Order = get sale Price SKU
        for (ProductDTO dto : easyOrderSubmitDTO.getProducts()) {
            ProductOutletSkuEntity productOutletSkuEntity = productOutletSkuRepository.findByProductOutletSkuId(dto.getProductOutletSkuId());
            if (productOutletSkuEntity != null) {
                totalOriginalPrice += (productOutletSkuEntity.getSalePrice() * dto.getQuantity());
            }
//            totalSalePrice += (dto.getPrice() * dto.getQuantity());
        }
//        rs.put("totalSalePrice", totalSalePrice);
        rs.put("totalOriginalPrice", NumberUtils.calculate(totalOriginalPrice));
        return rs;
    }

    public Integer calculateLoyaltyPoint4EasyOrder(OrderOutletEntity orderOutletEntity, LoyaltyMemberEntity loyaltyMemberEntity, List<ProductDTO> productDTOS) {
        Long outletId = orderOutletEntity.getOutlet().getOutletId();
        Long customerId = orderOutletEntity.getCustomerId();
        Double price = orderOutletEntity.getTotalPrice() - orderOutletEntity.getDeliveryPrice();
        String orderOutletCode = orderOutletEntity.getCode();
        Integer loyaltyPoint = 0;
        Integer totalPlay;
        Integer totalPlayOrder;

        //1. from order amount
        List<LoyaltyEventOrderAmountEntity> lstLoyaltyEventOrderAmount = loyaltyEventOrderAmountRepository.findByOrderAmount(orderOutletEntity.getOutlet().getOutletId(), price, new Timestamp(System.currentTimeMillis()));

        LoyaltyEventOrderAmountEntity eventOrderAmount = LoyaltyOutletEventBeanUtils.selectOneEventOrderAmount(lstLoyaltyEventOrderAmount);
        if (eventOrderAmount != null && eventOrderAmount.getLoyaltyEventOrderAmountId() != null) {
            Integer totalPlayInOrder = 0;
            totalPlay = loyaltyPointHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(customerId, eventOrderAmount.getLoyaltyOutletEvent().getLoyaltyOutletEventId());
            totalPlayOrder = loyaltyOrderHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(customerId, eventOrderAmount.getLoyaltyOutletEvent().getLoyaltyOutletEventId());
            totalPlay += (totalPlayOrder + 1); // including this order

            if (eventOrderAmount.getLoyaltyOutletEvent().getMaxPlay() == null || totalPlay <= eventOrderAmount.getLoyaltyOutletEvent().getMaxPlay()) {
                if (LoyaltyOutletEventBeanUtils.checkCustomerGroupApproveLoyalty(eventOrderAmount.getLoyaltyOutletEvent(), loyaltyMemberEntity)) {

                    totalPlayInOrder = eventOrderAmount.getLoyaltyOutletEvent().getMaxPlayInOrder();
                    Integer point = eventOrderAmount.getLoyaltyOutletEvent().getPoint();

                    // increase by value
                    Double minAmount = eventOrderAmount.getOrderAmount();
                    Double p = (price / minAmount);
                    if (totalPlayInOrder != null) {
                        if (p.intValue() > totalPlayInOrder) {
                            p = Double.valueOf(totalPlayInOrder);
                        }
                    }
                    point = p.intValue() * point;

                    loyaltyPoint += point;

                    saveLoyaltyOrderHistory(eventOrderAmount.getLoyaltyOutletEvent(), orderOutletCode, point, customerId);
                }
            }
        }

        //2.from order product
        List<Long> productOutletSkuIds = new ArrayList<>();
        for (ProductDTO dto : productDTOS) {
            productOutletSkuIds.add(dto.getProductOutletSkuId());
        }
        List<LoyaltyOutletEventEntity> eventOrderProducts = this.getEventByProductOutletSkuIds(productOutletSkuIds);

        LoyaltyOutletEventEntity loyaltyOutletEventEntity = LoyaltyOutletEventBeanUtils.selectOneEventOrderProduct(eventOrderProducts);

        if (loyaltyOutletEventEntity != null && loyaltyOutletEventEntity.getLoyaltyOutletEventId() != null) {

            loyaltyOutletEventEntity = loyaltyOutletEventRepository.getOne(loyaltyOutletEventEntity.getLoyaltyOutletEventId());

            Integer totalPlayInOrder = 0;
            totalPlay = loyaltyPointHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(customerId, loyaltyOutletEventEntity.getLoyaltyOutletEventId());
            Integer totalPlayOrderHistory = loyaltyOrderHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(customerId, loyaltyOutletEventEntity.getLoyaltyOutletEventId());
            totalPlay += (totalPlayOrderHistory + 1); // including this order

            if (loyaltyOutletEventEntity.getMaxPlay() == null || (totalPlay <= loyaltyOutletEventEntity.getMaxPlay() && totalPlayInOrder <= loyaltyOutletEventEntity.getMaxPlayInOrder())) {
                if (LoyaltyOutletEventBeanUtils.checkCustomerGroupApproveLoyalty(loyaltyOutletEventEntity, loyaltyMemberEntity)) {

                    Map<Long, Integer> mapItems = new HashMap<>();
                    for (ProductOrderItemEntity orderItemEntity : orderOutletEntity.getProductOrderItemEntities()) {
                        if (orderItemEntity != null && orderItemEntity.getProductOutletSkuId() != null) {
                            mapItems.put(orderItemEntity.getProductOutletSkuId(), orderItemEntity.getQuantity());
                        }
                    }

                    Map<Long, Integer> mapQuantity = this.getQuantitySkuApprovedOnLoyalty(loyaltyOutletEventEntity.getLoyaltyOutletEventId(), mapItems);

                    Set<Long> skuIds = mapQuantity.keySet();
                    for (Long skuId : skuIds) {
                        totalPlayInOrder += mapQuantity.get(skuId);  // total = each quantity of product in cart item + number product in program
                    }

                    if (loyaltyOutletEventEntity.getMaxPlayInOrder() != null && totalPlayInOrder > loyaltyOutletEventEntity.getMaxPlayInOrder()) {
                        totalPlayInOrder = loyaltyOutletEventEntity.getMaxPlayInOrder();
                    }

                    if (loyaltyOutletEventEntity.getMaxPlayInOrder() == null || totalPlayInOrder > 0) {
                        loyaltyPoint += (loyaltyOutletEventEntity.getPoint() * totalPlayInOrder);
                        saveLoyaltyOrderHistory(loyaltyOutletEventEntity, orderOutletCode, (loyaltyOutletEventEntity.getPoint() * totalPlayInOrder), customerId);
                    }
                }
            }
        }

        //3. from first order
        if (customerId != null) {
            List<LoyaltyEventFirstOrderEntity> eventsFirstOrder = loyaltyEventFirstOrderRepository.findByLoyaltyOutletEventOutletIdAndLoyaltyOutletEventStatus(outletId,new Timestamp(System.currentTimeMillis()));

            LoyaltyOutletEventEntity eventFirstOrder = LoyaltyOutletEventBeanUtils.selectOneEventFirstOrder(eventsFirstOrder);

            if (eventFirstOrder != null && eventFirstOrder.getLoyaltyOutletEventId() != null) {
                // count total order of customer
                Integer totalOrder = orderOutletRepository.countByCustomerId(customerId) == null ? 0 : orderOutletRepository.countByCustomerId(customerId);
                if (eventFirstOrder.getMaxPlay() == null || totalOrder <= eventFirstOrder.getMaxPlay()) { // old order + a new order => total Order
                    if (LoyaltyOutletEventBeanUtils.checkCustomerGroupApproveLoyalty(eventFirstOrder, loyaltyMemberEntity)) {
                        Integer point = eventFirstOrder.getPoint();
                        loyaltyPoint += point;

                        saveLoyaltyOrderHistory(eventFirstOrder, orderOutletCode, point, customerId);
                    }
                }
            }
        }

        return loyaltyPoint;
    }

    private void saveLoyaltyOrderHistory(LoyaltyOutletEventEntity outletEvent, String orderOutletCode, Integer point, Long customerId) throws DuplicateKeyException {
        LoyaltyOrderHistoryEntity loyaltyOrderHistoryEntity = new LoyaltyOrderHistoryEntity();
        loyaltyOrderHistoryEntity.setLoyaltyOutletEventId(outletEvent.getLoyaltyOutletEventId());
        loyaltyOrderHistoryEntity.setOrderOutletCode(orderOutletCode);
        loyaltyOrderHistoryEntity.setPoint(point);
        loyaltyOrderHistoryEntity.setCustomerId(customerId);
        loyaltyOrderHistoryRepository.save(loyaltyOrderHistoryEntity);
    }

    private Map<Long, Integer> getQuantitySkuApprovedOnLoyalty(Long loyaltyOutletEventId, Map<Long, Integer> cartItems) {
        Map<Long, Integer> result = new LinkedHashMap<>();

        List<Object> skuIdInEvent = loyaltyOutletEventRepository.findCorrespondingSkuJoinInEventOrder(loyaltyOutletEventId);

        for (Object skuId : skuIdInEvent) {
            for (Long item : cartItems.keySet()) {
                if (Long.valueOf(skuId.toString()).equals(item)) {
                    result.put(Long.valueOf(skuId.toString()), cartItems.get(item));
                    break;
                }
            }
        }

        return result;
    }

    private List<LoyaltyOutletEventEntity> getEventByProductOutletSkuIds(List<Long> productOutletSkuIds) {
        List<Object[]> results = loyaltyOutletEventRepository.getEventByProductOutletSkuIds(productOutletSkuIds, new Timestamp(System.currentTimeMillis()));
        if (results != null && results.size() > 0) {
            List<LoyaltyOutletEventEntity> loyaltyOutletEventEntities = new ArrayList<>();
            for (Object[] arr : results) {
                LoyaltyOutletEventEntity loyaltyOutletEventEntity = new LoyaltyOutletEventEntity();
                loyaltyOutletEventEntity.setLoyaltyOutletEventId(((BigInteger) arr[0]).longValue());
                loyaltyOutletEventEntity.setLoyaltyMasterEventId(((BigInteger) arr[1]).longValue());
                loyaltyOutletEventEntity.setOutletId(((BigInteger) arr[2]).longValue());
                loyaltyOutletEventEntity.setStatus(arr[3].toString());
                loyaltyOutletEventEntity.setPoint((Integer) arr[4]);
                loyaltyOutletEventEntity.setName(arr[5].toString());
                loyaltyOutletEventEntity.setMaxPlay(arr[6] != null ? (Integer) arr[6] : null);
                loyaltyOutletEventEntity.setStartDate(arr[8] != null ? (Timestamp) arr[8] : null);
                loyaltyOutletEventEntity.setEndDate(arr[9] != null ? (Timestamp) arr[9] : null);
                loyaltyOutletEventEntities.add(loyaltyOutletEventEntity);
            }
            return loyaltyOutletEventEntities;
        }
        return null;
    }

    public List<StatusResponse> syncOrders(List<EasyOrderSubmitDTO> dtos) {
        List<StatusResponse> rs = new ArrayList<>();
        for (EasyOrderSubmitDTO dto : dtos) {
            try {
                rs.add(submitOrder(dto));
            } catch (Exception ex) {
                rs.add(new StatusResponse(ex.getMessage()));
                ex.printStackTrace();
            }
        }
        return rs;
    }

    public boolean addStockOut(List<ProductDTO> products, OrderOutletEntity orderOutletEntity, Long warehouseId) {
        // stockout from default storage
        if (!isAllowStorage(orderOutletEntity.getOutlet().getOutletId())) {
            System.out.println("Inventory Off");
            return true;
        }
        StockOutEntity stockOutEntity = new StockOutEntity();
        stockOutEntity.setCode("SO" + System.currentTimeMillis());
        stockOutEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        stockOutEntity.setOperationType("ORDER_SUBMIT");
        stockOutEntity.setOrderOutletId(orderOutletEntity.getOrderOutletID());
        stockOutEntity.setOutletId(orderOutletEntity.getOutlet().getOutletId());
        stockOutEntity.setStatus(1);
        stockOutEntity.setStokerId(orderOutletEntity.getOutlet().getShopman().getUserId());
        stockOutEntity.setWarehouseId(warehouseId);

        stockOutEntity = stockOutRepository.save(stockOutEntity);

        int totalStockItem = 0;
        double totalStockPrice = 0;

        for (ProductDTO product : products) {
            ProductOutletSkuEntity productOutletSkuEntity = productOutletSkuRepository.findByProductOutletSkuId(product.getProductOutletSkuId());
            ProductOutletStorageEntity productOutletStorageEntity = productOutletStorageRepository.findByWareHouseIdAnAndProductOutletSkuId(stockOutEntity.getWarehouseId(), productOutletSkuEntity.getProductOutletSkuId());
            if (productOutletStorageEntity == null || countProductNonExpire(productOutletStorageEntity) < product.getQuantity()) {
                throw new BadRequestException("product.is.out.of.stock");
            }
            totalStockItem += product.getQuantity();
            totalStockPrice += product.getQuantity() * productOutletSkuEntity.getOriginalPrice();

            productOutletStorageEntity.setQuantity(productOutletStorageEntity.getQuantity() - product.getQuantity());

            int quantity = product.getQuantity();

            while (quantity > 0) {
                int tmp = quantity;
                ProductOutletStorageDetailEntity minExp = null;
                for (ProductOutletStorageDetailEntity productOutletStorageDetail : productOutletStorageEntity.getProductOutletStorageDetails()) {
                    if (productOutletStorageDetail.getExpireDate().getTime() > System.currentTimeMillis() && productOutletStorageDetail.getQuantity() > 0) {
                        if (minExp == null || minExp.getExpireDate().getTime() > productOutletStorageDetail.getExpireDate().getTime()) {
                            minExp = productOutletStorageDetail;
                        }
                    }
                }
                if (minExp == null) break;
                if (minExp.getQuantity() <= 0) {
                    productOutletStorageEntity.getProductOutletStorageDetails().remove(minExp);
                    continue;
                }
                if (quantity <= minExp.getQuantity()) {
                    minExp.setQuantity(minExp.getQuantity() - quantity);
                    quantity = 0;
                } else {
                    productOutletStorageEntity.getProductOutletStorageDetails().remove(minExp);
                    quantity -= minExp.getQuantity();
                    minExp.setQuantity(0);
                }
                if (tmp == quantity) break;
                StockOutDetailEntity stockOutDetailEntity = new StockOutDetailEntity();
                stockOutDetailEntity.setQuantity(tmp - quantity);
                stockOutDetailEntity.setPrice(productOutletSkuEntity.getOriginalPrice());
                stockOutDetailEntity.setProductOutletSkuId(productOutletSkuEntity.getProductOutletSkuId());
                stockOutDetailEntity.setProductOutletStorageId(productOutletStorageEntity.getProductOutletStorageId());
                stockOutDetailEntity.setStockOutId(stockOutEntity.getStockOutId());
                stockOutDetailEntity.setExpireDate(minExp.getExpireDate());

                stockOutDetailRepository.save(stockOutDetailEntity);
                System.out.println("Stock out: " + stockOutDetailEntity.getProductOutletSkuId());
            }
            productOutletStorageRepository.save(productOutletStorageEntity);
        }
        stockOutEntity.setTotalItem(totalStockItem);
        stockOutEntity.setTotalValue(totalStockPrice);
        stockOutRepository.save(stockOutEntity);
        return true;
    }

    public boolean addStockOut(List<ProductDTO> products, OrderOutletEntity orderOutletEntity) {
        if (!isAllowStorage(orderOutletEntity.getOutlet().getOutletId())) {
            System.out.println("Inventory Off");
            return true;
        }
        return addStockOut(products, orderOutletEntity, warehouseRepository.findByOutletOutletIdAndIsDefaultIsTrue(orderOutletEntity.getOutlet().getOutletId()).getWareHouseId());
    }

    private StatusResponse isOrderValid(EasyOrderSubmitDTO order) {
        StatusResponse status;
        // 1. check address
        if (order.getDeliveryMethod().equals(CoreConstants.DELIVERY_PAY_N_GET_CODE)) {
            if (StringUtils.isBlank(order.getAddress())) {
                return new StatusResponse("receiver.address.not.found");
            } else {
                try {
                    List<Double> coordinates = GoogleLocationUtil.getCoordinate(order.getAddress());
                    if (coordinates.size() != 2 || coordinates.get(0) == null || coordinates.get(1) == null) {
                        return new StatusResponse("receiver.address.not.valid");
                    }
                } catch (Exception ex) {
                    return new StatusResponse("receiver.address.not.valid");
                }
            }
        }
        //2. check customer
        status = isCustomerValid(order.getUserId(), order.getOutletId());
        if (!status.getCode().equals(Constants.ORDER_VALID)) {
            return status;
        }
        //3. check product
        List<ProductDTO> products = order.getProducts();
        for (ProductDTO product : products) {
            status = isProductValid(product, order.getSource());
            if (!status.getCode().equals(Constants.ORDER_VALID)) {
                return status;
            }
        }
        return new StatusResponse(Constants.ORDER_VALID);
    }

    public boolean isAllowStorage(Long outletId) {
        try {
            OutletEntity entity = outletRepository.findById(outletId).get();
            return entity.getEnableInventory();
        } catch (Exception ex) {
            return false;
        }
    }

    private StatusResponse isProductValid(ProductDTO product) {
        return isProductValid(product, null);
    }

    private StatusResponse isProductValid(ProductDTO product, String source) {
        try {
            ProductOutletSkuEntity sku = productOutletSkuRepository.findById(product.getProductOutletSkuId()).get();
            if (isAllowStorage(sku.getProductOutlet().getOutlet().getOutletId())) {
                List<ProductOutletStorageEntity> productOutletStorageEntities = productOutletStorageRepository.findByProductOutletSkuProductOutletSkuId(sku.getProductOutletSkuId());
                ProductOutletStorageEntity productOutletStorageEntity = productOutletStorageEntities.stream().filter(entity -> entity.getWareHouse().getIsDefault()).collect(Collectors.toList()).get(0);
                if (product.getQuantity() > countProductNonExpire(productOutletStorageEntity)) {
                    return new StatusResponse("product.is.out.of.stock", sku.getProductOutlet().getName() + "(" + sku.getTitle() + ")");
                }
            }
            if (!(sku.getStatus().equals(CoreConstants.ACTIVE_STATUS) && sku.getProductOutlet().getStatus().equals(CoreConstants.ACTIVE_STATUS))) {
                return new StatusResponse("product.is.inactive", sku.getProductOutlet().getName() + "(" + sku.getTitle() + ")");
            }
            if (!(Constants.SOURCE_POS_ONLINE.contains(source)) && !checkSellingOnlineMode(sku)) {
                return new StatusResponse(ErrorCodeMap.PRODUCT_NOT_SALE_ONLINE_MODE.name(), sku.getProductOutlet().getName() + "(" + sku.getTitle() + ")");
            }
        } catch (Exception ex) {
            return new StatusResponse("product.not.found");
        }
        return new StatusResponse(Constants.ORDER_VALID);
    }

    private StatusResponse isCustomerValid(Long userId, Long outletId) {
        try {
            CustomerEntity customer = customerRepository.findByUserIdUserId(userId).get(0);
            LoyaltyMemberEntity memberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletOutletId(customer.getCustomerId(), outletId);
            if (memberEntity != null) {
                try {
                    if (!(memberEntity.getActive() == CoreConstants.LOYALTY_MEMBER_STATUS.ACTIVE.getType())) {
                        return new StatusResponse("customer.is.inactive");
                    }
                } catch (Exception ex) {
                    return new StatusResponse("customer.not.found");
                }
            }
        } catch (Exception ex) {
            return new StatusResponse("customer.not.found");
        }

        return new StatusResponse(Constants.ORDER_VALID);
    }

    private Boolean checkSellingOnlineMode(ProductOutletSkuEntity sku) {
        ProductOutletEntity product = sku.getProductOutlet();
        return outletSaleChannelRepository.findSaleChannelByCodeAndOutletId(CoreConstants.SALE_CHANEL.ONLINE.getName(), product.getOutlet().getOutletId(), product.getProductOutletId()) != null;
    }

    public int countProductNonExpire(ProductOutletStorageEntity entity) {
        int rs = 0;
        for (ProductOutletStorageDetailEntity productOutletStorageDetail : entity.getProductOutletStorageDetails()) {
            rs += productOutletStorageDetail.getExpireDate().getTime() > System.currentTimeMillis() ? productOutletStorageDetail.getQuantity() : 0;
        }
        return rs;
    }

    public Integer getQuantityProductSkuAvailability(long productSkuId) {
        int quantity = 0;
        try {
            ProductOutletSkuEntity sku = productOutletSkuRepository.findById(productSkuId).get();
            if (isAllowStorage(sku.getProductOutlet().getOutlet().getOutletId())) {
                List<ProductOutletStorageEntity> productOutletStorageEntities = productOutletStorageRepository.findByProductOutletSkuProductOutletSkuId(sku.getProductOutletSkuId());
                ProductOutletStorageEntity productOutletStorageEntity = productOutletStorageEntities.stream().filter(entity -> entity.getWareHouse().getIsDefault()).collect(Collectors.toList()).get(0);
                quantity = countProductNonExpire(productOutletStorageEntity);
            } else {
                return Integer.MAX_VALUE;   // Off Inventory => All Products are available
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return quantity;
    }

    public Map<String, Object> getPaymentMethods(Long outletId, Locale locale) {
        Map<String, Object> map = new HashMap<>();
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Map<String, Object> result = new HashMap<>();
            List<PaymentMethodDTO> newPaymentMethodList;
            List<PaymentMethodDTO> paymentMethodList = paymentMethodRepository.findByOutletId(outletId).stream().map(entity -> {
                return mapper.convertValue(entity, PaymentMethodDTO.class);
            }).filter(dto -> {
                return !dto.getCode().equals("GATEWAY") && !dto.getCode().equals("CREDIT") && !dto.getCode().equals("POINT");
            }).collect(Collectors.toList());

            newPaymentMethodList = deliveryService.convertPaymentReadBundleFile(paymentMethodList, locale);

            // workaround remove item to hide setting by country
            OutletEntity outletEntity = outletRepository.findById(outletId).get();
            if (outletEntity != null) {
                Integer canDisplay = deliveryService.getStatus("GRAB_PAYMENT", outletEntity.getCountryId());
                Integer canDisplayCredit = deliveryService.getStatus("CREDIT", outletEntity.getCountryId());
                Integer canDisplayBank = deliveryService.getStatus("BANK_TRANSFER", outletEntity.getCountryId());

                // hide payemnt setting by country
                Iterator<PaymentMethodDTO> paymentMethodDTOIterator = newPaymentMethodList.iterator();
                while (paymentMethodDTOIterator.hasNext()) {
                    PaymentMethodDTO e = paymentMethodDTOIterator.next();
                    if ((canDisplay == 0 && e.getCode().equalsIgnoreCase(Payment.PAYMENT_METHOD_GRABPAY.getValue()))
                            || (canDisplayCredit == 0 && e.getCode().equalsIgnoreCase(Payment.PAYMENT_METHOD_CREDIT.getValue()))
                            || (canDisplayBank == 0 && e.getCode().equalsIgnoreCase(Payment.PAYMENT_METHOD_BANK_TRANSFER.getValue()))) {
                        paymentMethodDTOIterator.remove();
                    }
                }
            }

            result.put("paymentData", newPaymentMethodList);
            map.put("status", ApiStatusCode.SUCCESS.getValue());
            map.put("message", ApiStatusCode.SUCCESS.getKey());
            map.put("result", result);
        } catch (Exception e) {
            map.put("status", ApiStatusCode.NOT_FOUND.getValue());
            map.put("message", ApiStatusCode.NOT_FOUND.getKey());
            e.printStackTrace();
        }
        return map;
    }

    public Map<String, Object> checkAvailableProducts(List<ProductDTO> productDTOS) {
        Map<String, Object> map = new HashMap<>();
        boolean isOutOfStock = false;
        List<ProductDTO> skuOutOfStock = new ArrayList<>();
        // Checking Out Of Stock
        for (ProductDTO productDTO : productDTOS) {
            Integer quantityProductSkuAvailability = this.getQuantityProductSkuAvailability(productDTO.getProductOutletSkuId());
            if (productDTO.getQuantity() > quantityProductSkuAvailability) {
                skuOutOfStock.add(new ProductDTO(productDTO.getProductOutletSkuId(), quantityProductSkuAvailability)); // quantityProductSkuAvailability < 0 : Out of Stock in warehouse
            }
        }
        if (skuOutOfStock.size() > 0) isOutOfStock = true;
        map.put("isOutOfStock", isOutOfStock);
        map.put("skuOutOfStock", skuOutOfStock);
        return map;
    }

    @Autowired
    private ProductOutletStorageDetailRepository productOutletStorageDetailRepository;
    @Autowired
    private OutletSaleChannelRepository outletSaleChannelRepository;
    @Autowired
    private AppSettingRepository appSettingRepository;
    @Autowired
    private ProductOutletStorageRepository productOutletStorageRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private StockOutRepository stockOutRepository;
    @Autowired
    private StockOutDetailRepository stockOutDetailRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EasyOrderRepository easyOrderRepository;
    @Autowired
    private OrderOutletRepository orderOutletRepository;
    @Autowired
    private LoyaltyMemberRepository loyaltyMemberRepository;
    @Autowired
    private ProductOrderItemRepository productOrderItemRepository;
    @Autowired
    private ProductOutletSkuRepository productOutletSkuRepository;
    @Autowired
    private LoyaltyOutletEventRepository loyaltyOutletEventRepository;
    @Autowired
    private LoyaltyOrderHistoryRepository loyaltyOrderHistoryRepository;
    @Autowired
    private LoyaltyPointHistoryRepository loyaltyPointHistoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private OutletRepository outletRepository;
    @Autowired
    private DeliveryServiceRepository deliveryServiceRepository;
    @Autowired
    private Environment env;
    @Autowired
    private EasyOrderItemRepository easyOrderItemRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private GiftRepository giftRepository;
    @Autowired
    private PricingSkuRepository pricingSkuRepository;
    @Autowired
    private OutletPromotionRepository outletPromotionRepository;
    @Autowired
    private CustomerRewardOrderItemRepository customerRewardOrderItemRepository;
    @Autowired
    private CustomerRewardRepository customerRewardRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private LoyaltyEventOrderAmountRepository loyaltyEventOrderAmountRepository;
    @Autowired
    private LoyaltyEventFirstOrderRepository loyaltyEventFirstOrderRepository;
    @Autowired
    private PricingRepository pricingRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderService orderService;
}
