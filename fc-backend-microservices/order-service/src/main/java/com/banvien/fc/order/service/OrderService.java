package com.banvien.fc.order.service;

import com.banvien.fc.common.enums.ErrorCodeMap;
import com.banvien.fc.common.rest.errors.BadRequestException;
import com.banvien.fc.common.util.CommonUtils;
import com.banvien.fc.common.util.Constants;
import com.banvien.fc.order.dto.*;
import com.banvien.fc.order.dto.delivery.grabexpress.QuotesPlatformDTO;
import com.banvien.fc.order.dto.delivery.grabexpress.QuotesResponseDTO;
import com.banvien.fc.order.dto.mobile.*;
import com.banvien.fc.order.dto.rules.Enum.DiscountType;
import com.banvien.fc.order.dto.rules.Enum.PromotionType;
import com.banvien.fc.order.dto.rules.PromotionRuleDTO;
import com.banvien.fc.order.entity.*;
import com.banvien.fc.order.repository.*;
import com.banvien.fc.order.service.mapper.OrderOutletMapper;
import com.banvien.fc.order.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.tools.rmi.ObjectNotFoundException;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderOutletRepository orderOutletRepository;
    @Autowired
    private OrderOutletMapper orderOutletMapper;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private PricingRepository pricingRepository;
    @Autowired
    private PricingSkuRepository pricingSkuRepository;
    @Autowired
    private OrderInformationRepository orderInformationRepository;
    @Autowired
    private LoyaltyEventOrderAmountRepository loyaltyEventOrderAmountRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LoyaltyMemberRepository loyaltyMemberRepository;
    @Autowired
    private ProductOrderItemRepository productOrderItemRepository;
    @Autowired
    private LoyaltyOrderHistoryRepository loyaltyOrderHistoryRepository;
    @Autowired
    private LoyaltyPointHistoryRepository loyaltyPointHistoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OutletRepository outletRepository;
    @Autowired
    private DeliveryServiceRepository deliveryServiceRepository;
    @Autowired
    private LoyaltyEventFirstOrderRepository loyaltyEventFirstOrderRepository;
    @Autowired
    private LoyaltyOutletEventRepository loyaltyOutletEventRepository;
    @Autowired
    private ProductOutletSkuRepository productOutletSkuRepository;
    @Autowired
    private LoyaltyMasterEventRepository loyaltyMasterEventRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private OutletPromotionCustomerRepository outletPromotionCustomerRepository;
    @Autowired
    private NotifyTemplateRepositoty notifyTemplateRepositoty;
    @Autowired
    private UserDeviceRepository userDeviceRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private OrderOutletPromotionRepository orderOutletPromotionRepository;
    @Autowired
    private CustomerRewardOrderItemRepository customerRewardOrderItemRepository;
    @Autowired
    private CustomerRewardRepository customerRewardRepository;
    @Autowired
    private OutletPromotionRepository outletPromotionRepository;
    @Autowired
    private GiftRepository giftRepository;
    @Autowired
    private LoyaltyOutletEventRepository loyaltyOutletEvenRepository;
    @Autowired
    private LoyaltyEvenWelcomeRepository loyaltyEvenWelcomeRepository;
    @Autowired
    private LoyaltyCustomerTargetRepository loyaltyCustomerTargetRepository;
    @Autowired
    private CustomerGroupRepository customerGroupRepository;
    @Autowired
    private ProductOutletRepository productOutletRepository;
    @Autowired
    private CustomerAddressRepository customerAddressRepository;
    @Autowired
    private ProductOutletStorageRepository productOutletStorageRepository;
    @Autowired
    private EasyOrderService easyOrderService;
    @Autowired
    private OrderTemporaryRepository orderTemporaryRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private LoyaltyService loyaltyService;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private SalesFlowService salesFlowService;
//    @Autowired
//    private OrderServiceRepository orderServiceRepository;

    private List<OrderOutletEntity> getOrderOutletByUserAndOutletFarDay(Long customerId, Long outletId, int numberOfDay) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - (long) 86400000 * numberOfDay);
        return orderOutletRepository.getOrderOutletByCustomerAndOutletFarDay(customerId, outletId, timestamp);
    }

    public List<OrderOutletDTO> getOrderOutletByCustomerAndOutlet(Long customerId, Long outletId) {
        List<OrderOutletEntity> orderOutletEntities = orderOutletRepository.getOrderOutletByCustomerAndOutlet(customerId, outletId);
        List<OrderOutletDTO> orderOutletDTOS = orderOutletMapper.toDto(orderOutletEntities);
        return orderOutletDTOS;
    }

    public Integer getTotalQuantityByUserAndOutlet(Long customerId, Long outletId, int numberOfDay) {
        int totalQuantity = 0;
        List<OrderOutletEntity> orderOutletEntities = getOrderOutletByUserAndOutletFarDay(customerId, outletId, numberOfDay);
        if (!orderOutletEntities.isEmpty()) {
            totalQuantity = orderOutletEntities.size();
        }
        return totalQuantity;
    }

    public Double getTotalAmountByUserAndOutlet(Long customerId, Long outletId, int numberOfDay) {
        double totalAmount = 0;
        List<OrderOutletEntity> orderOutletEntities = getOrderOutletByUserAndOutletFarDay(customerId, outletId, numberOfDay);
        if (!orderOutletEntities.isEmpty()) {
            for (OrderOutletEntity orderOutletEntity : orderOutletEntities) {
                totalAmount += orderOutletEntity.getTotalPrice();
            }
        }
        return totalAmount;
    }

    public boolean userFirstTimeOrderInOutlet(Long customerId, Long outletId) {
        return !(orderOutletRepository.existsByOutletIdAndCustomerId(customerId, outletId));
        // True : User has ever Order in this Outlet
        // False : User has less 1 order in this Outlet
    }

    @Transactional
    public SubmitOrderResponseDTO submitOrder4Mobile(OrderInformationMobileDTO orderInformationMobileDTO, Long userId) {
        List<ProductOrderItemEntity> orderItemEntities = new ArrayList<>();
        PromotionPerOrderDTO promotionPerOrderDTO = new PromotionPerOrderDTO();
        SubmitOrderResponseDTO result = new SubmitOrderResponseDTO();
        DiscountProductOrderAndDeliveryDTO discountProductOrderAndDeliveryDTO = new DiscountProductOrderAndDeliveryDTO();
        Integer totalItems = 0;
        Double salePriceSku = 0.0D;
        double totalPrice = 0;  // final Price =  Amount
        double totalSalesPrice = 0; // totalSalePriceSku after applied GroupPrice & Fix-Price (Promotion Discount)
//        double totalPromotionDiscount = 0;
//        double totalDiscountValue = 0;
        Double deliveryPrice = 0D;

        List<CustomerEntity> lstCustomerEntity = new ArrayList<>();
        //Seller order on behalf buyer
        if (orderInformationMobileDTO.getCustomerId() != null) {
            CustomerEntity customerEntity = customerRepository.findById(orderInformationMobileDTO.getCustomerId()).get();
            userId = customerEntity.getUserId().getUserId();
            lstCustomerEntity.add(customerEntity);
        } else {
            //Get customerId from userId
            lstCustomerEntity = customerRepository.findByUserIdUserId(userId);
            if (lstCustomerEntity == null || lstCustomerEntity.size() <= 0) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_LOGON_ID.name());
            }
        }
        //Just for test
        System.out.println("CustomerId: " + lstCustomerEntity.get(0).getCustomerId());
        //set custormerId
        orderInformationMobileDTO.setCustomerId(lstCustomerEntity.get(0).getCustomerId());

        //Set fullname prepare for send notification
        String fullName = lstCustomerEntity.get(0).getUserId().getFullName();

        //Get shopping cart
        List<ShoppingCartEntity> shoppingCartEntities = new ArrayList<>();
        if (orderInformationMobileDTO.getSalesManUserId() != null) {
            //For salesman
            shoppingCartEntities = shoppingCartRepository.findShoppingCartForSalesman(orderInformationMobileDTO.getCustomerId(), orderInformationMobileDTO.getSalesManUserId(), orderInformationMobileDTO.getOutletId());
        } else {
            //For customer
            shoppingCartEntities = shoppingCartRepository.findShoppingCartForCustomer(orderInformationMobileDTO.getCustomerId(), orderInformationMobileDTO.getOutletId());
        }

        OutletEntity outletEntity = outletRepository.getOne(orderInformationMobileDTO.getOutletId());
        if ("PK".equals(countryRepository.getOne(outletEntity.getCountryId()).getCode())) {
            try {
                List<ProductDTO> lstProductDTO = new ArrayList<>();
                for (ShoppingCartEntity shoppingCartEntity : shoppingCartEntities) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setProductOutletSkuId(shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());
                    productDTO.setQuantity(shoppingCartEntity.getQuantity());

                    lstProductDTO.add(productDTO);
                }
                orderInformationMobileDTO.setOrderOutletCode(salesFlowService.createOrder(String.valueOf(System.currentTimeMillis()), outletEntity.getCode(), customerRepository.findByCustomerId(orderInformationMobileDTO.getCustomerId()).getUserId().getCode() , lstProductDTO));
            } catch (Exception e) {
                throw new BadRequestException("Can't not create order from saleflo");
            }
        }

        Long pricingId = 0L;
        //Get pricing for calculate saleprice
        List<LoyaltyMemberEntity> lstLoyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletOutletIdAndActive(orderInformationMobileDTO.getCustomerId(), orderInformationMobileDTO.getOutletId(), 1);
        if (lstLoyaltyMemberEntity != null && lstLoyaltyMemberEntity.size() > 0) {
            List<Long> lstPricingEntity = pricingRepository.findPricingId(lstLoyaltyMemberEntity.get(0).getCustomerGroup() != null ? lstLoyaltyMemberEntity.get(0).getCustomerGroup().getCustomerGroupId() : 0);
            pricingId = lstPricingEntity != null && lstPricingEntity.size() > 0 ? lstPricingEntity.get(0) : null;
        }
        if (shoppingCartEntities != null && shoppingCartEntities.size() > 0) {
            //create new easysubmitorder to get list promotion of product
            EasyOrderSubmitDTO easyOrderSubmitDTO = new EasyOrderSubmitDTO();
            easyOrderSubmitDTO.setOutletId(orderInformationMobileDTO.getOutletId());
            easyOrderSubmitDTO.setUserId(userId);
            List<ProductDTO> lstProductDTO = new ArrayList<>();
            //set product on order to list

            //just for test
            System.out.println("======Submit Shopping cart=======");
            for (ShoppingCartEntity shoppingCartEntity : shoppingCartEntities) {
                ProductDTO productDTO = new ProductDTO();
                //Get price from customergroup if customer is member of outlet
                List<PricingSkuEntity> lstPricingSkuEntity = pricingSkuRepository.findByPricingIdAndProductOutletSkuId(pricingId != null ? pricingId : 0, shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());
                salePriceSku = lstPricingSkuEntity != null && lstPricingSkuEntity.size() > 0 && lstPricingSkuEntity.get(0).getSalePrice() != null ? lstPricingSkuEntity.get(0).getSalePrice() : shoppingCartEntity.getProductOutletSku().getSalePrice();
                productDTO.setProductOutletSkuId(shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());
                productDTO.setPrice(salePriceSku);
                productDTO.setQuantity(shoppingCartEntity.getQuantity());

                //just for test
                System.out.println("Product SKU ID: " + shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());
                System.out.println("Quantity: " + shoppingCartEntity.getQuantity());
                System.out.println("Price: " + salePriceSku);

                lstProductDTO.add(productDTO);
            }
            System.out.println("=====================================");
            System.out.println();
            //Apply prmotion code for order
            if (orderInformationMobileDTO.getPromotionCode() != null) {
                easyOrderSubmitDTO.setPromotionCode(orderInformationMobileDTO.getPromotionCode());
            }
            easyOrderSubmitDTO.setProducts(lstProductDTO);
            easyOrderSubmitDTO.setAction(1); //1: update db table (outletpromotioncustomer,orderoutletpromotion) default: no update db
            try {
                //list promotion of product
                promotionPerOrderDTO = promotionRepository.getPromotionDiscountPriceByEasyOrder(easyOrderSubmitDTO); //call Promotion Service . action 1: store DB  . 0: view Promotions without update DB
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Andrew check todo
            for (ReductionDTO reductionDTO : promotionPerOrderDTO.getReductionDTOS()) {
                System.out.println(" ~~~~~ Order with : " + reductionDTO.getCodeNextPurchase());
            }

            discountProductOrderAndDeliveryDTO = this.getDetailDiscountProductOrderAndDelivery(promotionPerOrderDTO, shoppingCartEntities, pricingId);

            //get total discount promotion price
//            totalDiscountValue = promotionPerOrderDTO.getTotalDiscountValue();

            for (ProductOrderItemDTO ProductOrderItemDTO : discountProductOrderAndDeliveryDTO.getProductOrderItems()) {
                //Calculate total
                totalSalesPrice += ProductOrderItemDTO.getTotalAmount();    // totalSalePriceSku after applied GroupPrice & Fix-Price (Promotion Discount) => totalPrice Products customer has to pay without PromotionDiscount
                totalItems += ProductOrderItemDTO.getQuantity();
                orderItemEntities.add(ProductOrderItemUtils.DTO2entity(ProductOrderItemDTO));
            }
//            totalPromotionDiscount = promotionPerOrderDTO.getTotalDiscountValue();  // include Promotion discount, fixPrice
            totalPrice = totalSalesPrice - promotionPerOrderDTO.getTotalDiscountValue() > 0 ? totalSalesPrice - promotionPerOrderDTO.getTotalDiscountValue() : 0;


            //add free product to ProductOrderItem
            if (discountProductOrderAndDeliveryDTO.getListFreeProductToChoose().size() > 0 || discountProductOrderAndDeliveryDTO.getListFreeProductToChoose() != null) {
                ProductOrderItemEntity freeProduct;
                for (GiftPromotionDTO giftPromotionDTO : discountProductOrderAndDeliveryDTO.getListFreeProductToChoose()) {
                    freeProduct = new ProductOrderItemEntity();
                    ProductOutletSkuEntity product = productOutletSkuRepository.findByProductOutletSkuId(Long.valueOf(giftPromotionDTO.getProductOutletSkuId()));
                    freeProduct.setProductOutletSkuId(product.getProductOutletSkuId());
                    freeProduct.setQuantity(1);
                    freeProduct.setPrice(0.0);
                    orderItemEntities.add(freeProduct);
                    totalItems += 1;
                }
            }

//            add gift to ProductOrderItem
            if (discountProductOrderAndDeliveryDTO.getGiftPromotions().size() > 0 || discountProductOrderAndDeliveryDTO.getGiftPromotions() != null) {
                ProductOrderItemEntity freeGift;
                for (GiftPromotionDTO giftPromotionDTO : discountProductOrderAndDeliveryDTO.getGiftPromotions()) {
                    freeGift = new ProductOrderItemEntity();
                    GiftEntity giftEntity = giftRepository.getGiftForPromotionApplied(giftPromotionDTO.getId(), new Timestamp(System.currentTimeMillis()));
                    if (giftEntity != null) {
                        freeGift.setGiftId(giftEntity.getGiftId());
                        freeGift.setQuantity(1);
                        freeGift.setPrice(0.0);
                        orderItemEntities.add(freeGift);
                        totalItems += 1;
                        Integer totalReceived = giftEntity.getTotalReceivedPromotion() == null ? 1 : giftEntity.getTotalReceivedPromotion() + 1;
                        giftEntity.setTotalReceivedPromotion(totalReceived);
                        if (giftEntity.getTotalReceivedPromotion() >= giftEntity.getQuantityPromotion())
                            giftEntity.setStatus(0);
//                        giftEntity.setQuantityPromotion(giftEntity.getQuantityPromotion() - 1);
                        giftRepository.saveAndFlush(giftEntity);
                    }
                }
            }
        }

        //Calculate delivery price (not calculate for vansaleapp)
        if (StringUtils.isNotBlank(orderInformationMobileDTO.getReceiverAddress()) && !Constants.SOURCE_SMART_VANSALE.equals(orderInformationMobileDTO.getSource())) {
            Double totalDeliveryDiscount = 0.0;
            Double deliveryDistance = 0.0;
            Timestamp shipDate;
            Optional<OutletEntity> outlet = outletRepository.findById(orderInformationMobileDTO.getOutletId());
            deliveryDistance = DeliveryUtils.calculateDeliveryDistance(outlet.get(), orderInformationMobileDTO.getReceiverAddress(), orderInformationMobileDTO.getReceiverLng(), orderInformationMobileDTO.getReceiverLat());
            if (StringUtils.isNotBlank(orderInformationMobileDTO.getDeliveryMethod())) {
                try {
                    //when user choose GRAB_EXPRESS
                    if (orderInformationMobileDTO.getDeliveryMethod().equals(Constants.DELIVERY_SHIP_TO_HOME_EXPRESS)) {
                        QuotesPlatformDTO quotesPlatformDTO = new QuotesPlatformDTO();
                        quotesPlatformDTO.setCustomerId(orderInformationMobileDTO.getCustomerId());
                        quotesPlatformDTO.setOutletId(orderInformationMobileDTO.getOutletId());
                        quotesPlatformDTO.setLatitude(Double.parseDouble(orderInformationMobileDTO.getReceiverLat().toString()));
                        quotesPlatformDTO.setLongitude(Double.parseDouble(orderInformationMobileDTO.getReceiverLng().toString()));
                        quotesPlatformDTO.setAddress(orderInformationMobileDTO.getReceiverAddress());
                        ResponseDTO<QuotesResponseDTO> obj = deliveryRepository.getQuotes(quotesPlatformDTO);
                        deliveryPrice = Double.parseDouble(obj.getBody().getQuotes().get(0).getAmount().toString());
                        shipDate = obj.getBody().getQuotes().get(0).getEstimatedTimeline().getDropoff();
                        orderInformationMobileDTO.setDeliveryDate(shipDate);
                    } else {
                        // Get delivery price
                        List<DeliveryServiceEntity> entities = deliveryServiceRepository.findByOutletIdAndCodeOrderByDistanceAscOrderByAsc(orderInformationMobileDTO.getOutletId(), orderInformationMobileDTO.getDeliveryMethod());
                        deliveryPrice = DeliveryServiceUtils.calculateDeliveryPrice(entities, deliveryDistance);
                    }
                } catch (Exception ex) {
                    throw ex;
                }
            }

            if (deliveryPrice > 0 && discountProductOrderAndDeliveryDTO.getDeliveryDiscount() != null && discountProductOrderAndDeliveryDTO.getDeliveryDiscount().size() > 0) {
                int typeShipping = 0; // default  0 :corresponding for SHIPPING_FIX_PRICE ($)  ||  != 0 : SHIPPING_DISCOUNT (%)
                if (discountProductOrderAndDeliveryDTO.getDeliveryDiscount() != null && discountProductOrderAndDeliveryDTO.getDeliveryDiscount().size() > 0 &&
                        discountProductOrderAndDeliveryDTO.getDeliveryDiscount().get(0).getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT)) // Discount by Percent
                    typeShipping = 1;
                //apply shipping promotion
                totalDeliveryDiscount = this.getDeliveryPriceDiscountByPromotion(discountProductOrderAndDeliveryDTO, deliveryPrice, lstCustomerEntity.get(0).getCustomerId(), 1);
                // deliveryPrice update by Promotion
                if (typeShipping == 0) { // SHIPPING_FIX_PRICE ($)
                    double originalDelivery = deliveryPrice;
                    deliveryPrice = totalDeliveryDiscount;
                    totalDeliveryDiscount = originalDelivery - totalDeliveryDiscount > 0 ? originalDelivery - totalDeliveryDiscount : totalDeliveryDiscount;
                } else // SHIPPING_DISCOUNT (%)
                    deliveryPrice = deliveryPrice - totalDeliveryDiscount > 0 ? deliveryPrice - totalDeliveryDiscount : 0;
//                    promotionPerOrderDTO.setTotalDiscountValue(promotionPerOrderDTO.getTotalDiscountValue() + totalDeliveryDiscount);
            }
        }
        //Check status of credit
        result = checkCreditValidation(result, orderInformationMobileDTO, totalPrice, lstLoyaltyMemberEntity);
        if ((orderInformationMobileDTO.getConfirmed() != null && orderInformationMobileDTO.getConfirmed()) || result.getSuccess()) {
            Long orderId = saveOrderOutlet(orderInformationMobileDTO, orderItemEntities, totalItems, totalPrice, totalSalesPrice, discountProductOrderAndDeliveryDTO.getTotalOriginalPrice(), discountProductOrderAndDeliveryDTO.getTotalStoreDiscount(), discountProductOrderAndDeliveryDTO.getTotalWeight(), promotionPerOrderDTO.getTotalDiscountValue(), discountProductOrderAndDeliveryDTO.getShoppingCartIds(), discountProductOrderAndDeliveryDTO.getLstOutletPromotion(), deliveryPrice, orderInformationMobileDTO.getCustomerRewards(), shoppingCartEntities, orderInformationMobileDTO.getCurrency(), fullName);

            // Save orderId to CustomerReward in Next Purchase Promotion
            for (ReductionDTO reductionDTO : promotionPerOrderDTO.getReductionDTOS()) {
                if (reductionDTO.getPromotionType() == PromotionType.NEXTPURCHASE.getNo()) {
                    CustomerRewardEntity customerRewardEntity = customerRewardRepository.getCustomerRewardByCode(reductionDTO.getCodeNextPurchase());
                    System.out.println(" ~~~~~ Order with : " + orderId + "  " + reductionDTO.getCodeNextPurchase());
                    customerRewardEntity.setOrderOutletId(orderId);
                    customerRewardRepository.saveAndFlush(customerRewardEntity);
                }
            }
            result.setOrderId(orderId);
            result.setResponseMessageKey(Status.OK.getReasonPhrase());
            result.setSuccess(true);
            System.out.println("Submit order successful!");
        }
        return result;
    }

    private SubmitOrderResponseDTO checkCreditValidation(SubmitOrderResponseDTO responseDTO, OrderInformationMobileDTO orderInformationMobileDTO, Double newOrderValue, List<LoyaltyMemberEntity> lstLoyaltyMemberEntity) {
        responseDTO.setSuccess(true);
        if (lstLoyaltyMemberEntity != null && lstLoyaltyMemberEntity.size() > 0) {
            // This order is selected paid by credit
            if (orderInformationMobileDTO.getPayment() != null && orderInformationMobileDTO.getPayment().equals(CoreConstants.PAYMENT_METHOD_CREDIT)) {
                // Check if customer has credit`
                responseDTO = ShoppingCartBeanUtils.messageWarningApproveCredit(responseDTO, lstLoyaltyMemberEntity.get(0), newOrderValue);
            }
        }
        return responseDTO;
    }

    public Long saveOrderOutlet(OrderInformationMobileDTO orderInformationMobileDTO, List<ProductOrderItemEntity> orderItemEntities, Integer totalItem, Double totalPrice, Double totalSalePrice, Double totalOriginalPrice, Double totalStoreDiscount, Double totalWeight, Double totalPromotionDiscount,
                                List<Long> shoppingCartIds, List<ReductionDTO> outletPromotions, Double deliveryPrice, List<Long> customerRewards, List<ShoppingCartEntity> shoppingCartEntities, String currency, String fullName) {
        Optional<OutletEntity> outletEntity = outletRepository.findById(orderInformationMobileDTO.getOutletId());
        Optional<CustomerEntity> customerEntity = customerRepository.findById(orderInformationMobileDTO.getCustomerId());
        NotifyTemplateEntity notifyTemplateEntity = notifyTemplateRepositoty.findByCode(CoreConstants.NOTIFY_TEMPLATE.TEMPLATE_OUTLET_NEW_ORDER.getName());

        // save order information
        OrderInformationEntity orderInformationEntity = OrderInformationbeanUtils.dto2Entity(orderInformationMobileDTO);
        orderInformationEntity = orderInformationRepository.save(orderInformationEntity);

        // save order outlet
        OrderOutletEntity orderOutletEntity = savingOrderOutletEntity(orderInformationMobileDTO, orderInformationEntity, outletPromotions, totalItem, totalPrice, totalSalePrice, totalOriginalPrice != null ? totalOriginalPrice : 0, totalStoreDiscount, totalPromotionDiscount, deliveryPrice, shoppingCartEntities, totalWeight);

        // save order items
        if (orderItemEntities.size() > 0) {
            orderOutletEntity.setProductOrderItemEntities(orderItemEntities);
        }
        if (orderOutletEntity.getProductOrderItemEntities() != null && orderOutletEntity.getProductOrderItemEntities().size() > 0) {
            for (ProductOrderItemEntity orderItemEntity : orderOutletEntity.getProductOrderItemEntities()) {
                orderItemEntity.setOrderOutletId(orderOutletEntity.getOrderOutletID());
                productOrderItemRepository.save(orderItemEntity);
            }
        }

        //check is allow inventory
        if (easyOrderService.isAllowStorage(orderOutletEntity.getOutlet().getOutletId())) {
            //stock out product from (default warehouse or warehouse (vansale,promotion,...)
            this.stockOutProduct(orderItemEntities, orderOutletEntity, orderInformationMobileDTO.getWarehouseId() == null ? warehouseRepository.findByOutletOutletIdAndIsDefaultIsTrue(orderOutletEntity.getOutlet().getOutletId()).getWareHouseId() : orderInformationMobileDTO.getWarehouseId());
        }

        //Create member for outlet
        List<LoyaltyMemberEntity> lstLoyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletOutletIdAndActive(orderInformationMobileDTO.getCustomerId(), orderInformationMobileDTO.getOutletId(), 1);
        LoyaltyMemberEntity loyaltyMemberEntity = new LoyaltyMemberEntity();
        if (lstLoyaltyMemberEntity == null || lstLoyaltyMemberEntity.size() == 0) {
            String lastCode = loyaltyMemberRepository.findLastCustomerCode(outletEntity.get().getOutletId());
            //loyaltyMemberEntity.setCustomerCode(CustomerBeanUtil.autoCreateCustomerCode(lastCode));
            loyaltyMemberEntity.setCustomerId(customerEntity.get().getCustomerId());
            loyaltyMemberEntity.setReferedby(customerEntity.get().getUserId());
            loyaltyMemberEntity.setPoint(0);
            loyaltyMemberEntity.setJoinDate(new Timestamp(System.currentTimeMillis()));
            loyaltyMemberEntity.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            loyaltyMemberEntity.setStatus(CoreConstants.LOYALTY_MEMBERSHIP.MEMBER.getStatus());
            loyaltyMemberEntity.setTerms(Boolean.FALSE);
            loyaltyMemberEntity.setActive(1);
            loyaltyMemberEntity.setOutlet(outletEntity.get());
            loyaltyMemberEntity = loyaltyMemberRepository.saveAndFlush(loyaltyMemberEntity);
            welcomingGift(loyaltyMemberEntity.getLoyaltyMemberId());
        } else {
            loyaltyMemberEntity = lstLoyaltyMemberEntity.get(0);
        }

        // Calculate loyalty point and save history for customer
        savingOrderLoyalty(orderOutletEntity, outletEntity.get(), orderInformationMobileDTO, orderOutletEntity.getTotalPrice(), loyaltyMemberEntity);

        // save reward gift in order
        if (customerRewards != null && customerRewards.size() > 0) {
            // save customer reward gift
            customerRewardOrderItemRepository.addToOrder(customerRewards, orderOutletEntity.getOrderOutletID());

            // update customerRewards  to redeemed
            customerRewardRepository.redeemRewards(customerRewards, new Timestamp(System.currentTimeMillis()));

            //update total item
            Integer totalItems = orderOutletEntity.getTotalItem() + customerRewards.size();     //include customer reward
            orderOutletEntity.setTotalItem(totalItems);
        }
        if (shoppingCartIds != null && shoppingCartIds.size() > 0) {
            for (Long id : shoppingCartIds) {
                shoppingCartRepository.deleteById(id);
            }
        }

        //update loyalty point when order success (VANSALE APP)
        if (Constants.SOURCE_SMART_VANSALE.equals(orderInformationMobileDTO.getSource())) {
            try {
                loyaltyService.updatePointWhenSuccessOrder(orderOutletEntity);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Send notification
        sendNotification2ShopperAndRetailerAfterOrderCreatedSuccess(currency, loyaltyMemberEntity.getSalesMan(), orderInformationMobileDTO, customerEntity.get(), outletEntity.get(), orderOutletEntity, notifyTemplateEntity, fullName);
        return orderOutletEntity.getOrderOutletID();
    }

    public void welcomingGift(long loyaltyMemberId) {
        LoyaltyMemberEntity loyaltyMemberEntity = loyaltyMemberRepository.findById(loyaltyMemberId).get();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<LoyaltyOutletEventEntity> evens = loyaltyOutletEvenRepository.findByOutletIdAndStatusAndStartDateBeforeAndEndDateAfter(
                loyaltyMemberEntity.getOutlet().getOutletId().longValue(), Constants.LOYALTY_MEMBERSHIP_ACTIVE_STATUS, now, now);
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
                        (loyaltyMemberEntity.getCustomerGroup() == null ||
                                targetGroups.stream().filter(x -> x.getCustomerGroupId() == loyaltyMemberEntity.getCustomerGroup().getCustomerGroupId()).count() == 0))) {
                    long totalPlay = loyaltyPointHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(loyaltyMemberEntity.getCustomerId(), welcomeEven.getLoyaltyOutletEventId());
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
        history.setOutletid(loyaltyMemberEntity.getOutlet().getOutletId());
        history.setLoyaltyOutletEventId(loyaltyOutletEventEntity.getLoyaltyOutletEventId());
        history.setPoint(point);
        history.setCompletionDate(new Timestamp(System.currentTimeMillis()));
        history.setCustomerId(loyaltyMemberEntity.getCustomerId());
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


    private OrderOutletEntity savingOrderOutletEntity(OrderInformationMobileDTO orderInformation, OrderInformationEntity orderInformationEntity, List<ReductionDTO> outletPromotions, Integer totalItem, Double totalPrice, Double totalSalePrice, Double totalOriginalPrice, Double totalStoreDiscount, Double totalPromotionDiscount, Double deliveryPrice, List<ShoppingCartEntity> shoppingCartEntities, Double totalWeight) throws DuplicateKeyException {
        OutletEntity outletEntity = new OutletEntity();
        outletEntity = outletRepository.findById(orderInformation.getOutletId()).get();
//        outletEntity.setOutletId(orderInformation.getOutletId());
        OrderOutletEntity orderOutletEntity = new OrderOutletEntity();

        orderOutletEntity.setOutlet(outletEntity);
        orderOutletEntity.setNote(orderInformation.getNote());
        orderOutletEntity.setCustomerId(orderInformation.getCustomerId());
        orderOutletEntity.setSaving(0d);
        try {
            if (orderInformation.getReceiveStartTime() != null && orderInformation.getReceiveEndTime() != null) {
                orderOutletEntity.setReceiveStartTime(new Timestamp(orderInformation.getReceiveStartTime()));
                orderOutletEntity.setReceiveEndTime(new Timestamp(orderInformation.getReceiveEndTime()));
                orderOutletEntity.setShipDate(new Timestamp(orderInformation.getReceiveStartTime()));
            }
        } catch (Exception ex) {
        }

//        Double totalStoreDiscountPrice = totalOriginalPrice - totalSalePrice > 0 ? totalOriginalPrice - totalSalePrice : 0D;

//        double totalStoreDiscountPrice = 0.0; // = salePrice - groupPrice

        Double savingPrice = totalOriginalPrice - totalPrice > 0 ? totalOriginalPrice - totalPrice : 0D;

        // collection discount
//        Double totalCollectionDiscountPrice = 0D;
        //Double totalCollectionDiscountPrice = calculateCollectionDiscount(orderInformation.getOutletId(), shoppingCartEntities);
//        totalPrice -= totalCollectionDiscountPrice;

        orderOutletEntity.setLoyaltyPoint(0);
        orderOutletEntity.setTotalItem(totalItem);
        orderOutletEntity.setTotalPrice(totalOriginalPrice - totalPromotionDiscount - totalStoreDiscount + deliveryPrice > 0 ? totalOriginalPrice - totalPromotionDiscount - totalStoreDiscount + deliveryPrice : 0);
        orderOutletEntity.setTotalOriginalPrice(totalOriginalPrice);
        orderOutletEntity.setTotalPromotionDiscountPrice(totalPromotionDiscount);
//        orderOutletEntity.setTotalStoreDiscountPrice(totalStoreDiscountPrice + totalCollectionDiscountPrice);
        orderOutletEntity.setTotalStoreDiscountPrice(totalStoreDiscount);
        orderOutletEntity.setSaving(savingPrice);
        orderOutletEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        orderOutletEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        orderOutletEntity.setCode(orderInformation.getOrderOutletCode() == null ? RandomTokenBeanUtil.generateOrderCode() : orderInformation.getOrderOutletCode());
        orderOutletEntity.setTotalWeight(totalWeight == null ? 0 : totalWeight);

        //set status is "SUCESS" when order submitted by vansale
        orderOutletEntity.setStatus(Constants.SOURCE_SMART_VANSALE.equals(orderInformation.getSource()) ? CoreConstants.OrderStatusCode.SUCCESS.toString() : CoreConstants.OrderStatusCode.WAITING_FOR_CONFIRMATION.toString());

        orderOutletEntity.setDeliveryPrice(deliveryPrice);
        orderOutletEntity.setShipDate(orderInformation.getDeliveryDate());
        orderOutletEntity.setGrabTxId(orderInformation.getGrabTxId());
        orderOutletEntity.setGrabGroupTxId(orderInformation.getGrabGroupTxId());
        orderOutletEntity.setToken(orderInformation.getToken());
        orderOutletEntity.setShipDate(orderInformation.getDeliveryDate());
        orderOutletEntity.setFromsource(orderInformation.getSource());

        if (orderInformationEntity.getPaymentMethod().equals(CoreConstants.PAYMENT_METHOD_CREDIT)) {
            orderOutletEntity.setDebt(orderOutletEntity.getTotalPrice());
        }
        // Set sales man
        if (orderInformation.getSalesManUserId() != null) {
            UserEntity salesMan = new UserEntity();
            salesMan.setUserId(orderInformation.getSalesManUserId());
            orderOutletEntity.setReferedBy(salesMan);
        }

        orderOutletEntity.setOrderInformation(orderInformationEntity);

        //search salechannel ONLINE of outlet , if not existed, create new
        orderOutletEntity.setSaleChanel(CoreConstants.SALE_CHANEL.ONLINE.getName());

        orderOutletEntity = orderOutletRepository.save(orderOutletEntity);

        //save promotion apply on order
        if (outletPromotions != null) {
            for (ReductionDTO reductionDTO : outletPromotions) {
                OrderOutletPromotionEntity orderOutletPromotionEntity = new OrderOutletPromotionEntity();
                orderOutletPromotionEntity.setOutletPromotionId(reductionDTO.getOutletPromotionId());
                orderOutletPromotionEntity.setOrderoutletId(orderOutletEntity.getOrderOutletID());

                // save detail Promotion Discount per Order
                try {
                    ObjectMapper oMapper = new ObjectMapper();
                    String tmp = null;
                    tmp = oMapper.writeValueAsString(reductionDTO); // obj 2 json
                    orderOutletPromotionEntity.setPromotionDiscount(tmp);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    System.out.println("<><><>PromotionDiscount : Can not convert reductionDTO to json !!!!");
                }
                orderOutletPromotionRepository.save(orderOutletPromotionEntity);
            }
        }

        return orderOutletEntity;
    }

    private void savingOrderLoyalty(OrderOutletEntity orderOutletEntity, OutletEntity outletEntity, OrderInformationMobileDTO orderInformation, Double totalPrice, LoyaltyMemberEntity loyaltyMemberEntity) throws DuplicateKeyException {
        Double loyaltyRate = outletEntity.getLoyaltyRate() != null ? outletEntity.getLoyaltyRate() : 0D;
        if (loyaltyRate.isNaN()) {
            loyaltyRate = 0D;
        }
        Double totalFinalPrice = totalPrice;    //total price + delivery fee

        // calculate loyalty point and save history for customer
        Integer loyaltyPoint = 0;
        if (orderOutletEntity.getProductOrderItemEntities() != null && orderOutletEntity.getProductOrderItemEntities().size() > 0) {
            loyaltyPoint = loyaltyService.calculateLoyaltyPoint(orderOutletEntity, outletEntity.getOutletId(), orderInformation.getCustomerId(), loyaltyMemberEntity);
            orderOutletEntity.setLoyaltyPoint(loyaltyPoint);
        }

        // check customer has used loyalty point for pay invoice
        Integer usedPoint = orderInformation.getUsedPoint();
        if (usedPoint != null && usedPoint > 0 && loyaltyRate > 0) {
            Double amountRedeemed = 0d;
            if (loyaltyMemberEntity != null && loyaltyMemberEntity.getLoyaltyMemberId() != null) {
                Integer remainingPoint = loyaltyMemberEntity.getPoint() - usedPoint;
                loyaltyMemberEntity.setPoint(remainingPoint);
                loyaltyMemberEntity.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
                Integer monthExpired = outletEntity.getLoyaltyPointExpiredMonth();
                loyaltyMemberEntity.setPointExpiredDate(CommonBeanUtils.calculatorExpiredFromNowWithMonthInput(monthExpired != null ? monthExpired : 0));
                loyaltyMemberRepository.save(loyaltyMemberEntity);
            }

            orderOutletEntity.setPointRedeemed(usedPoint);

            //1. remaining total price after use point for purchase
            if (orderInformation.getPointRate() != null) {
                amountRedeemed = orderInformation.getUsedPoint() / orderInformation.getPointRate();
                totalFinalPrice = totalFinalPrice - amountRedeemed;
            }
            orderOutletEntity.setTotalPrice(totalFinalPrice <= 0 ? 0 : totalFinalPrice);

            // exchange money
            orderOutletEntity.setAmountRedeemed(amountRedeemed);

            //2. update debt after use point if payment method credit
            if (orderInformation.getPayment().equals(CoreConstants.PAYMENT_METHOD_CREDIT)) {
                orderOutletEntity.setDebt(totalPrice);
            }

            //3. save history used point for purchase
            loyaltyService.saveHistoryUsedPoint(orderInformation.getOutletId(), orderInformation.getCustomerId(), usedPoint, orderOutletEntity.getCode());
        }
    }

    private void getSatisfiedPromotions(List<ReductionDTO> lstPromotion, List<ReductionDTO> promotionsPass, List<GiftPromotionDTO> giftPromotions,
                                        List<GiftPromotionDTO> listFreeProductToChoose, List<ReductionDTO> promotionDelivery,
                                        List<ProductOutletSkuMobileDTO> productsFixPrice) {
        for (ReductionDTO reductionDTO : lstPromotion) {
            //just for test
            System.out.println("Type :" + reductionDTO.getDiscountType());
            System.out.println("Value Discount: " + reductionDTO.getValueDiscount());

            if (DiscountType.PERCENT_OFF.equals(reductionDTO.getDiscountType()) || DiscountType.AMOUNT_OFF.equals(reductionDTO.getDiscountType())) {
                promotionsPass.add(reductionDTO);
            }

            // Get Promotion Delivery
            if ((reductionDTO.getPromotionType() == 3) &&
                    (reductionDTO.getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT) ||
                            reductionDTO.getDiscountType().equals(DiscountType.SHIPPING_FIX_PRICE))) {
                promotionDelivery.add(reductionDTO);
//                break;
            }

            //Promotion FIX PRICE ( buy product with promotion price)
            if (reductionDTO.getDiscountType().equals(DiscountType.FIX_PRICE)) {
//                productsHavePromotionPrice.put(reductionDTO.getDiscountForTarget(), reductionDTO.getValueDiscount());
                //show product fix price on mobile app
                ProductOutletSkuMobileDTO productFixPrice = new ProductOutletSkuMobileDTO();
                productFixPrice.setProductOutletSkuId(reductionDTO.getDiscountForTarget());
                productFixPrice.setPromotionPrice(reductionDTO.getValueDiscount());
                productFixPrice.setOutletPromotion(reductionDTO);
                productsFixPrice.add(productFixPrice);
            }
            //Promotion Buy X Get Gift
            if ((reductionDTO.getDiscountType().equals(DiscountType.GIFT))) {
                giftPromotions.add(GiftUtils.changeGiftEntity2GiftDto(giftRepository.findById(reductionDTO.getDiscountForTarget()).get()));
                //get promotionid
                promotionsPass.add(reductionDTO);
            }

            //Promotion Buy X Get Product
            if ((reductionDTO.getDiscountType().equals((DiscountType.PRODUCT)))) {
                listFreeProductToChoose.add(GiftUtils.changeGiftEntity2GiftProductDto(productOutletSkuRepository.findByProductOutletSkuId(reductionDTO.getDiscountForTarget())));
                //get promotionid
                promotionsPass.add(reductionDTO);
            }
        }
    }

    // reduce on : PERCENT_OFF and AMOUNT_OFF and promotionType in {0,1,2,3}
    public DiscountProductOrderAndDeliveryDTO getDetailDiscountProductOrderAndDelivery(PromotionPerOrderDTO promotionPerOrderDTO, List<ShoppingCartEntity> lstShoppingCart, Long pricingId) {
        Double deliveryDiscount = 0.0;
        Double totalOriginalPrice = 0.0;    // = Sum_all_Sku(salePriceSku * quantity)
        Double totalWeight = 0.0;
        List<Long> shoppingCartIds = new ArrayList<>();
        List<ProductOrderItemDTO> productOrderItems = new ArrayList<>();
        List<ReductionDTO> promotionsPass = new ArrayList<>();
        List<GiftPromotionDTO> giftPromotions = new ArrayList<>();                      //gifts
        List<GiftPromotionDTO> listFreeProductToChoose = new ArrayList<>();             //free product
        List<ReductionDTO> promotionDelivery = new ArrayList<>();                       //promotion delivery
        List<ProductOutletSkuMobileDTO> productsFixPrice = new ArrayList<>();           //products have fix price
        double totalStoreDiscount = 0.0;             // totalStoreDiscountPrice = totalSalePrice - totalGroupPrice

        //just for test
        System.out.println("======== List promotion =========");
        if (!(promotionPerOrderDTO.getReductionDTOS() == null)) {
            this.getSatisfiedPromotions(promotionPerOrderDTO.getReductionDTOS(), promotionsPass, giftPromotions, listFreeProductToChoose,
                    promotionDelivery, productsFixPrice);
            System.out.println("=====================================");
            System.out.println();

        }
        Double salePriceSku = 0.0D;
        double groupPriceSkuDiscount = 0.0D;
        for (ShoppingCartEntity shoppingCartEntity : lstShoppingCart) {
            List<Long> outletPromotionIdPass = new ArrayList<>();
            ProductOrderItemEntity productOrderItemEntity = new ProductOrderItemEntity();
            productOrderItemEntity.setProductOutletSkuId(shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());

            //get sale price by check customergroup
            List<PricingSkuEntity> lstPricingSkuEntity = pricingSkuRepository.findByPricingIdAndProductOutletSkuId(pricingId != null ? pricingId : 0, shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());
            salePriceSku = lstPricingSkuEntity != null && lstPricingSkuEntity.size() > 0 && lstPricingSkuEntity.get(0).getSalePrice() != null ? lstPricingSkuEntity.get(0).getSalePrice() : shoppingCartEntity.getProductOutletSku().getSalePrice();

            // update groupPrice to calculate TotalStoreDiscount
            groupPriceSkuDiscount = shoppingCartEntity.getProductOutletSku().getSalePrice() - salePriceSku > 0 ? shoppingCartEntity.getProductOutletSku().getSalePrice() - salePriceSku : 0;

            //apply promotion price if product in promotion FIX PRICE
            double skuFixPrice = 0;
            int quantityFixPrice = 0;
            if (productsFixPrice.size() > 0) {
                productsFixPrice = productsFixPrice.stream().sorted(Comparator.comparingDouble(ProductOutletSkuMobileDTO::getPromotionPrice)).collect(Collectors.toList());
                skuFixPrice = productsFixPrice.get(0).getPromotionPrice();
                for (ProductOutletSkuMobileDTO dto : productsFixPrice) {
                    if (shoppingCartEntity.getQuantity() > quantityFixPrice) {
                        if (dto.getProductOutletSkuId().equals(shoppingCartEntity.getProductOutletSku().getProductOutletSkuId())) {
                            quantityFixPrice++;
                            //add promotionId passed to save db
                            promotionsPass.add(dto.getOutletPromotion());
                        }
                    }
                }
                skuFixPrice = skuFixPrice * quantityFixPrice;
            }


            productOrderItemEntity.setPrice(salePriceSku);
            // saving Product Item with salePrice
//            productOrderItemEntity.setPrice(shoppingCartEntity.getProductOutletSku().getSalePrice());

            productOrderItemEntity.setQuantity(shoppingCartEntity.getQuantity());

            //price item after apply promotion for one unit
            productOrderItemEntity.setPriceDiscount(0.0);
            productOrderItemEntity.setWeight(shoppingCartEntity.getProductOutletSku().getWeight() != null ? shoppingCartEntity.getProductOutletSku().getWeight() : 0);
            totalOriginalPrice += (shoppingCartEntity.getProductOutletSku().getSalePrice() * shoppingCartEntity.getQuantity());
            totalWeight += shoppingCartEntity.getQuantity() * (shoppingCartEntity.getProductOutletSku().getWeight() != null ? shoppingCartEntity.getProductOutletSku().getWeight() : 0);


            //set data into DTO to prepare calculate price
            ProductOrderItemDTO productOrderItemDTO = ProductOrderItemUtils.entity2DTO(productOrderItemEntity);
            productOrderItemDTO.setShoppingCartId(shoppingCartEntity.getShoppingCartId());
            productOrderItemDTO.setDevice(shoppingCartEntity.getDevice());
            productOrderItemDTO.setProductOutletId(shoppingCartEntity.getProductOutletSku().getProductOutlet().getProductOutletId());
            productOrderItemDTO.setProductOutletName(shoppingCartEntity.getProductOutletSku().getProductOutlet().getName());
            productOrderItemDTO.setOutletPromotionId(outletPromotionIdPass);
            productOrderItemDTO.setProductOutletSku(ProductOutletSkuBeanUtils.entity2DTO(shoppingCartEntity.getProductOutletSku()));

            //in case : PROMOTION PRODUCT FIX PRICE
            if (skuFixPrice != 0 && quantityFixPrice != 0) {
                // cal total amount not include quantity sku have fix price + total amount sku have fix price
//                productOrderItemDTO.setTotalAmount((shoppingCartEntity.getProductOutletSku().getSalePrice() * (shoppingCartEntity.getQuantity() - quantityFixPrice)) + skuFixPrice);
                productOrderItemDTO.setTotalAmount(shoppingCartEntity.getProductOutletSku().getSalePrice() * shoppingCartEntity.getQuantity());
//                promotionPerOrderDTO.setTotalDiscountValue(promotionPerOrderDTO.getTotalDiscountValue() + (shoppingCartEntity.getProductOutletSku().getSalePrice() * shoppingCartEntity.getQuantity()) - productOrderItemDTO.getTotalAmount());
                promotionPerOrderDTO.setTotalDiscountValue(promotionPerOrderDTO.getTotalDiscountValue() + ((shoppingCartEntity.getProductOutletSku().getSalePrice() * quantityFixPrice - skuFixPrice)));
            } else {
                //salePrice * quantity
//                productOrderItemDTO.setTotalAmount(salePriceSku * shoppingCartEntity.getQuantity());
                productOrderItemDTO.setTotalAmount(shoppingCartEntity.getProductOutletSku().getSalePrice() * shoppingCartEntity.getQuantity());
            }
            // totalStoreDiscount = (totalSalePrice - totalGroupPrice) * (totalQuantity - totalQuantityDiscountByFixPrice)
            productOrderItemDTO.setPriceDiscount(groupPriceSkuDiscount * (shoppingCartEntity.getQuantity() - quantityFixPrice));
            totalStoreDiscount += groupPriceSkuDiscount * (shoppingCartEntity.getQuantity() - quantityFixPrice);    // quantityFixPrice updated discount by PromotionDiscount
            productOrderItems.add(productOrderItemDTO);
            shoppingCartIds.add(shoppingCartEntity.getShoppingCartId());
        }

        return new DiscountProductOrderAndDeliveryDTO(promotionDelivery, productOrderItems, totalWeight, totalOriginalPrice, shoppingCartIds, promotionsPass, giftPromotions, listFreeProductToChoose, productsFixPrice, totalStoreDiscount);
    }

    public void sendNotification2ShopperAndRetailerAfterOrderCreatedSuccess(String currency, OutletEmployeeEntity salesMan, OrderInformationMobileDTO orderInformation, CustomerEntity customerEntity, OutletEntity outletEntity, OrderOutletEntity orderOutletEntity, NotifyTemplateEntity notifyTemplateEntity, String fullname) {
        try {
            if (orderInformation.getOrderOnBeHalf() != null && orderInformation.getOrderOnBeHalf()) { // salesman help to order for customer
                // Notification for Shopper
                Map<String, Object> notificationMessageParamsShopper = new HashMap<>();
                notificationMessageParamsShopper.put("ORDERCODE", orderOutletEntity.getCode());
                notificationMessageParamsShopper.put("TOTALPRICE", PriceBeanUtils.formatPrice(orderOutletEntity.getTotalPrice()));
                notificationMessageParamsShopper.put("CUSTOMER", customerEntity.getUserId().getFullName());
                notificationMessageParamsShopper.put("CURRENCY", currency);
                notificationMessageParamsShopper.put("SALESMAN", ((salesMan != null && salesMan.getUser() != null) ? salesMan.getUser().getFullName() : outletEntity.getShopman().getFullName()));

                Optional<CustomerEntity> cusEntity = customerRepository.findById(orderOutletEntity.getCustomerId());

                List<String> listLang = userDeviceRepository.findDistinctByUserId(cusEntity.get().getUserId().getUserId());

                for (int i = 0; i < listLang.size(); i++) {
                    String lang = listLang.get(i) == null ? CoreConstants.ENGLISH : listLang.get(i);

                    NotificationEntity notificationEntityShopper = buildNotificationFromTemplate4Shopper(cusEntity.get(), CoreConstants.NOTIFY_TEMPLATE.OUTLET_HELP_ORDER.getName(), notificationMessageParamsShopper, orderOutletEntity.getOrderOutletID(), orderOutletEntity, lang);
                    if (notificationEntityShopper != null) {
                        notificationRepository.save(notificationEntityShopper);
                    }
                }
            } else {
                // Notification for Shopper
                Map<String, Object> notificationMessageParamsShopper = new HashMap<>();
                notificationMessageParamsShopper.put("ORDERCODE", orderOutletEntity.getCode());
                notificationMessageParamsShopper.put("OUTLET", outletEntity.getName());
                notificationMessageParamsShopper.put("TOTALITEM", orderOutletEntity.getTotalItem());
                notificationMessageParamsShopper.put("TOTALPRICE", PriceBeanUtils.formatPrice(orderOutletEntity.getTotalPrice()));
                Optional<CustomerEntity> cusEntity = customerRepository.findById(orderOutletEntity.getCustomerId());

                List<String> listLang = userDeviceRepository.findDistinctByUserId(cusEntity.get().getUserId().getUserId());

                for (int i = 0; i < listLang.size(); i++) {
                    String lang = listLang.get(i) == null ? CoreConstants.ENGLISH : listLang.get(i);
                    NotificationEntity notificationEntityShopper = buildNotificationFromTemplate4Shopper(cusEntity.get(), CoreConstants.NOTIFY_TEMPLATE.TEMPLATE_ORDER_STATUS.getName(), notificationMessageParamsShopper, orderOutletEntity.getOrderOutletID(), orderOutletEntity, lang);
                    if (notificationEntityShopper != null) {
                        notificationRepository.save(notificationEntityShopper);
                    }
                }
            }

            //sent notify to salesman manage
            List<LoyaltyMemberEntity> loyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletOutletIdAndActive(orderOutletEntity.getCustomerId(), orderOutletEntity.getOutlet().getOutletId(), 1);
            if (loyaltyMemberEntity != null && loyaltyMemberEntity.get(0).getSalesMan() != null && loyaltyMemberEntity.get(0).getSalesMan().getUser() != null) {
                Map<String, Object> notificationMessageParamsSalesman = new HashMap<>();
                notificationMessageParamsSalesman.put("ORDERCODE", orderOutletEntity.getCode());
                Optional<CustomerEntity> cusEntity = customerRepository.findById(loyaltyMemberEntity.get(0).getCustomerId());
                notificationMessageParamsSalesman.put("CUSTOMERNAME", cusEntity.get().getUserId().getFullName());
                notificationMessageParamsSalesman.put("TOTALPRICE", PriceBeanUtils.formatPrice(orderOutletEntity.getTotalPrice()));

                List<String> listLang = userDeviceRepository.findDistinctByUserId(cusEntity.get().getUserId().getUserId());

                for (int i = 0; i < listLang.size(); i++) {
                    String lang = listLang.get(i) == null ? CoreConstants.ENGLISH : listLang.get(i);

                    NotificationEntity notificationEntitySalesMan = buildNotificationFromTemplate4Salesman(loyaltyMemberEntity.get(0).getSalesMan().getUser().getUserId(), CoreConstants.NOTIFY_TEMPLATE.OUTLET_SALESMAN_NEW_ORDER.getName(), notificationMessageParamsSalesman, orderOutletEntity.getOrderOutletID(), orderOutletEntity, lang);
                    if (notificationEntitySalesMan != null) {
                        notificationRepository.save(notificationEntitySalesMan);
                    }
                }
            }

            // Notification for Outlets
            if (orderOutletEntity.getOutlet() != null && orderOutletEntity.getOutlet().getOutletId() != null) {
                List<String> listLang = new ArrayList<>();
                listLang.add("vi");
                listLang.add("cn");
                listLang.add("ma");
                listLang.add("id");
                listLang.add("en");
                sendNotification2Outlet(orderOutletEntity, customerEntity, outletEntity, notifyTemplateEntity, listLang);

//                Map<String, Object> notificationMessageParamsOutlet = new HashMap<>();
//                notificationMessageParamsOutlet.put("ORDERCODE", orderOutletEntity.getCode());
//                notificationMessageParamsOutlet.put("CUSTOMERNAME", customerEntity.getUserId().getUserName());
//                notificationMessageParamsOutlet.put("TOTALPRICE", PriceBeanUtils.formatPrice(orderOutletEntity.getTotalPrice()));
//
//                List<String> listLang = userDeviceRepository.findDistinctByUserId(outletEntity.getShopman().getUserId());
//                for (int i = 0; i < listLang.size(); i++) {
//                    String lang = listLang.get(i) == null ? CoreConstants.ENGLISH : listLang.get(i);
//                    NotificationEntity notificationEntityOutlet = buildNotificationFromTemplate4Retailer(outletEntity.getShopman().getUserId(), notifyTemplateEntity, notificationMessageParamsOutlet, orderOutletEntity.getOrderOutletID(), orderOutletEntity, lang);
//                    if (notificationEntityOutlet != null) {
//                        notificationRepository.save(notificationEntityOutlet);
//                    }
//                }

                // salesman help to order for customer
                if (orderInformation.getSalesManUserId() != null) {
                    // Notification for Shopper
                    Map<String, Object> notificationMessageParamsShopper = new HashMap<>();
                    notificationMessageParamsShopper.put("ORDERCODE", orderOutletEntity.getCode());
                    notificationMessageParamsShopper.put("TOTALPRICE", PriceBeanUtils.formatPrice(orderOutletEntity.getTotalPrice()));
                    notificationMessageParamsShopper.put("CUSTOMER", customerEntity.getUserId().getUserName());
                    notificationMessageParamsShopper.put("CURRENCY", currency);

                    List<String> listLangSalema4Cus = userDeviceRepository.findDistinctByUserId(orderInformation.getSalesManUserId());

                    for (int i = 0; i < listLangSalema4Cus.size(); i++) {
                        String lang = listLangSalema4Cus.get(i) == null ? CoreConstants.ENGLISH : listLangSalema4Cus.get(i);

                        NotificationEntity notificationEntitySalesMan = buildNotificationFromTemplate4Salesman(orderInformation.getSalesManUserId(), CoreConstants.NOTIFY_TEMPLATE.OUTLET_SALESMAN_HELP_ORDER.getName(), notificationMessageParamsShopper, orderOutletEntity.getOrderOutletID(), orderOutletEntity, lang);
                        if (notificationEntitySalesMan != null) {
                            notificationRepository.save(notificationEntitySalesMan);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private NotificationEntity buildNotificationFromTemplate4Shopper(CustomerEntity customerEntity, String notificationTemplateCode, Map<String, Object> messageParams, Long targetId, OrderOutletEntity orderOutletEntity, String lang) {
        try {
//            CustomerDTO customerDTO = customerManagementLocalBean.findById(customerId);
            NotifyTemplateEntity notifyTemplateEntity = notifyTemplateRepositoty.findByCode(notificationTemplateCode);

            //get message with language
            NotifyTemplateDTO notifyTemplateDTO = NotifyTemplateBeanUtil.entity2DTO(notifyTemplateEntity);
            String messageSent = MessageBeanUtil.getContentFromVelocityTemplate(notifyTemplateDTO.getContentWithLanguage(lang), messageParams);

            NotificationEntity notificationEntity = new NotificationEntity();
            notificationEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            notificationEntity.setNotifyTemplateId(notifyTemplateEntity.getNotifyTemplateId());
            notificationEntity.setMessage(messageSent);
            notificationEntity.setStatus(CoreConstants.NOTIFY_STATUS.WAITING.getStatus());
            notificationEntity.setType(CoreConstants.NOTIFICATION_TYPE);
            notificationEntity.setScheduledDate(notificationEntity.getCreatedDate());
            notificationEntity.setUserId(customerEntity.getUserId().getUserId());
            notificationEntity.setSentDate(notificationEntity.getCreatedDate());
            notificationEntity.setOutlet(orderOutletEntity.getOutlet().getOutletId());
            notificationEntity.setTargetType(CoreConstants.ORDER_TYPE_NOTIFICATION);
            notificationEntity.setTargetId(targetId);
            notificationEntity.setTitle(notifyTemplateEntity.getTitle());
            notificationEntity.setSendByManual(Boolean.FALSE);
            notificationEntity.setLang(lang);
            return notificationEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private NotificationEntity buildNotificationFromTemplate4Salesman(Long salesManUserId, String notificationTemplateCode, Map<String, Object> messageParams, Long targetId, OrderOutletEntity orderOutletEntity, String lang) {
        try {
            NotifyTemplateEntity notifyTemplateEntity = notifyTemplateRepositoty.findByCode(notificationTemplateCode);
            NotifyTemplateDTO notifyTemplateDTO = NotifyTemplateBeanUtil.entity2DTO(notifyTemplateEntity);
            String messageSent = MessageBeanUtil.getContentFromVelocityTemplate(notifyTemplateDTO.getContentWithLanguage(lang), messageParams);
            NotificationEntity notificationEntity = new NotificationEntity();
            notificationEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            notificationEntity.setNotifyTemplateId(notifyTemplateEntity.getNotifyTemplateId());
            notificationEntity.setMessage(messageSent);
            notificationEntity.setStatus(CoreConstants.NOTIFY_STATUS.WAITING.getStatus());
            notificationEntity.setType(CoreConstants.NOTIFICATION_TYPE);
            notificationEntity.setScheduledDate(notificationEntity.getCreatedDate());
            notificationEntity.setUserId(salesManUserId);
            notificationEntity.setSentDate(notificationEntity.getCreatedDate());
            notificationEntity.setOutlet(orderOutletEntity.getOutlet().getOutletId());
            notificationEntity.setTargetType(CoreConstants.ORDER_TYPE_NOTIFICATION);
            notificationEntity.setTargetId(targetId);
            notificationEntity.setTitle(notifyTemplateEntity.getTitle());
            notificationEntity.setSendByManual(Boolean.FALSE);
            notificationEntity.setLang(lang);
            return notificationEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private NotificationEntity buildNotificationFromTemplate4Retailer(Long shopmanId, NotifyTemplateEntity notifyTemplateEntity, Map<String, Object> messageParams, Long targetId, OrderOutletEntity orderOutletEntity, String lang) {
        NotifyTemplateDTO notifyTemplateDTO = NotifyTemplateBeanUtil.entity2DTO(notifyTemplateEntity);
        String messageSent = MessageBeanUtil.getContentFromVelocityTemplate(notifyTemplateDTO.getContentWithLanguage(lang), messageParams);
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        notificationEntity.setNotifyTemplateId(notifyTemplateEntity.getNotifyTemplateId());
        notificationEntity.setMessage(messageSent);
        notificationEntity.setStatus(CoreConstants.NOTIFY_STATUS.WAITING.getStatus());
        notificationEntity.setType(CoreConstants.NOTIFICATION_TYPE);
        notificationEntity.setScheduledDate(notificationEntity.getCreatedDate());
        notificationEntity.setSentDate(notificationEntity.getCreatedDate());
        notificationEntity.setUserId(shopmanId);
        notificationEntity.setOutlet(orderOutletEntity.getOutlet().getOutletId());
        notificationEntity.setTargetType(CoreConstants.ORDER_TYPE_NOTIFICATION);
        notificationEntity.setTargetId(targetId);
        notificationEntity.setSendByManual(Boolean.FALSE);
        notificationEntity.setTitle(notifyTemplateEntity.getTitle());
        notificationEntity.setLang(lang);
        return notificationEntity;
    }


    public Object[] listCartItemsInfor(CartInfoMobileDTO dto) throws Exception {
        Object[] objects = new Object[3];
        Integer totalItems = 0;
        Integer totalReward = 0;
        Double deliveryPrice = 0D;
        Double salePriceSku = 0.0D;
        Double totalPrice = 0.0D;
        Long shipDate = null;
        List<ShoppingCartSummaryMobileDTO> result = new ArrayList<>();
        List<ShoppingCartItemMobileDTO> itemMobiles = new ArrayList<>();
        DeliveryValue4MobileDTO deliveryValue4MobileDTO = new DeliveryValue4MobileDTO();

        List<CustomerEntity> lstCustomerEntity = new ArrayList<>();
        //Seller order on behalf buyer
        if (dto.getCustomerId() != null) {
            CustomerEntity customerEntity = customerRepository.findById(dto.getCustomerId()).get();
            lstCustomerEntity.add(customerEntity);
        } else {
            //Get customerId from userId
            lstCustomerEntity = customerRepository.findByUserIdUserId(dto.getUserId());
            if (lstCustomerEntity == null || lstCustomerEntity.size() == 0) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_LOGON_ID.name());
            }
        }
        dto.setCustomerId(lstCustomerEntity.get(0).getCustomerId());

        //Get shopping cart
        List<ShoppingCartEntity> shoppingCartEntities = new ArrayList<>();
        if (dto.getSalesManUserId() != null) {
            //For salesman
            shoppingCartEntities = shoppingCartRepository.findShoppingCartForSalesman(dto.getCustomerId(), dto.getSalesManUserId(), dto.getOutletId());
        } else {
            //For customer
            shoppingCartEntities = shoppingCartRepository.findShoppingCartForCustomer(dto.getCustomerId(), dto.getOutletId());
        }

        OutletEntity outletEntity = outletRepository.findById(dto.getOutletId()).get();
        Long pricingId = 0L;
        //Get pricing for calculate saleprice
        List<LoyaltyMemberEntity> lstLoyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletOutletIdAndActive(dto.getCustomerId(), dto.getOutletId(), 1);
        if (lstLoyaltyMemberEntity != null && lstLoyaltyMemberEntity.size() > 0) {
            List<Long> lstPricingEntity = pricingRepository.findPricingId(lstLoyaltyMemberEntity.get(0).getCustomerGroup() != null ? lstLoyaltyMemberEntity.get(0).getCustomerGroup().getCustomerGroupId() : 0);
            pricingId = lstPricingEntity != null && lstPricingEntity.size() > 0 ? lstPricingEntity.get(0) : null;
        }

        //create new easysubmitorder to get list promotion of product
        EasyOrderSubmitDTO easyOrderSubmitDTO = new EasyOrderSubmitDTO();
        easyOrderSubmitDTO.setOutletId(dto.getOutletId());
        easyOrderSubmitDTO.setUserId(dto.getUserId());
        List<ProductDTO> lstProductDTO = new ArrayList<>();

        //set product on order to list
        //just for test
        System.out.println("======Shopping cart(Checkout item in cart)=======");
        for (ShoppingCartEntity shoppingCartEntity : shoppingCartEntities) {
            ProductDTO productDTO = new ProductDTO();
            //Get price from customergroup if customer is member of outlet
            List<PricingSkuEntity> lstPricingSkuEntity = pricingSkuRepository.findByPricingIdAndProductOutletSkuId(pricingId != null ? pricingId : 0, shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());
            salePriceSku = lstPricingSkuEntity != null && lstPricingSkuEntity.size() > 0 && lstPricingSkuEntity.get(0).getSalePrice() != null ? lstPricingSkuEntity.get(0).getSalePrice() : shoppingCartEntity.getProductOutletSku().getSalePrice();
            productDTO.setProductOutletSkuId(shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());
            productDTO.setPrice(salePriceSku);
            productDTO.setQuantity(shoppingCartEntity.getQuantity());

            //just for test
            System.out.println("Product SKU ID: " + shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());
            System.out.println("Quantity: " + shoppingCartEntity.getQuantity());
            System.out.println("Price: " + salePriceSku);

            lstProductDTO.add(productDTO);
        }
        System.out.println("=====================================");
        System.out.println();

        //apply promotion code for order
        if (dto.getPromotionCode() != null) {
            easyOrderSubmitDTO.setPromotionCode(dto.getPromotionCode());
        }
        easyOrderSubmitDTO.setProducts(lstProductDTO);
        PromotionPerOrderDTO promotionPerOrderDTO = new PromotionPerOrderDTO();
        try {
            //list promotion of product
            promotionPerOrderDTO = promotionRepository.getPromotionDiscountPriceByEasyOrder(easyOrderSubmitDTO); //call Promotion Service . action 1: store DB  . 0: view Promotions without update DB
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        DiscountProductOrderAndDeliveryDTO discountProductOrderAndDeliveryDTO = this.getDetailDiscountProductOrderAndDelivery(promotionPerOrderDTO, shoppingCartEntities, pricingId);

        //Calculate price
        Map<Long, ShoppingCartMapperDTO> shoppingCartMapperDTOMap = convertListItem2DtoAndGroupByOutletId(discountProductOrderAndDeliveryDTO, promotionPerOrderDTO.getTotalDiscountValue(), dto.getWarehouseId());
        Map<Long, List<CustomerReward4MobileDTO>> customerRewardGifts = new HashMap<>();
        if (dto.getCustomerId() != null && dto.getOutletId() != null) {
            customerRewardGifts = generateCustomerReward4Mobile(dto.getCustomerId(), dto.getOutletId());
//            if (listOutletId  == null || listOutletId.size() == 0) {
//                listOutletId.addAll(customerRewardGifts.keySet());
//            }
        }

        //Calculate delivery price
        if (StringUtils.isNotBlank(dto.getShipToAddress())) {
            Double totalDeliveryDiscount = 0.0;
            Double deliveryDistance = 0.0;
            Optional<OutletEntity> outlet = outletRepository.findById(dto.getOutletId());
            deliveryDistance = DeliveryUtils.calculateDeliveryDistance(outlet.get(), dto.getShipToAddress(), dto.getLongitude(), dto.getLatitude());
            if (StringUtils.isNotBlank(dto.getDeliveryMethod())) {
                //when user choose GRAB_EXPRESS
                if (dto.getDeliveryMethod().equals(Constants.DELIVERY_SHIP_TO_HOME_EXPRESS)) {
                    try {
                        QuotesPlatformDTO quotesPlatformDTO = new QuotesPlatformDTO();
                        quotesPlatformDTO.setCustomerId(dto.getCustomerId());
                        quotesPlatformDTO.setOutletId(dto.getOutletId());
                        quotesPlatformDTO.setLatitude(Double.parseDouble(dto.getLatitude().toString()));
                        quotesPlatformDTO.setLongitude(Double.parseDouble(dto.getLongitude().toString()));
                        quotesPlatformDTO.setAddress(dto.getShipToAddress());
                        ResponseDTO<QuotesResponseDTO> obj = deliveryRepository.getQuotes(quotesPlatformDTO);
                        if (obj == null || obj.getStatus() != 200)
                            throw new Exception(obj.getMessage());
                        deliveryPrice = Double.parseDouble(obj.getBody().getQuotes().get(0).getAmount().toString());
                        shipDate = obj.getBody().getQuotes().get(0).getEstimatedTimeline().getDropoff().getTime();
                    } catch (Exception ex) {
                        throw ex;
                    }
                } else {
                    // Get delivery price
                    List<DeliveryServiceEntity> entities = deliveryServiceRepository.findByOutletIdAndCodeOrderByDistanceAscOrderByAsc(dto.getOutletId(), dto.getDeliveryMethod());
                    deliveryPrice = DeliveryServiceUtils.calculateDeliveryPrice(entities, deliveryDistance);
                }
            }
            deliveryValue4MobileDTO.setOriginalShippingFee(deliveryPrice);
            int typeShipping = 0; // default  0 :corresponding for SHIPPING_FIX_PRICE ($)  ||  != 0 : SHIPPING_DISCOUNT (%)//cover case Redemp > 2

            if (discountProductOrderAndDeliveryDTO.getDeliveryDiscount() != null && discountProductOrderAndDeliveryDTO.getDeliveryDiscount().size() > 0 &&
                    discountProductOrderAndDeliveryDTO.getDeliveryDiscount().get(0).getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT)) // Discount by Percent
                typeShipping = 1;
            //apply shipping promotion
            totalDeliveryDiscount = this.getDeliveryPriceDiscountByPromotion(discountProductOrderAndDeliveryDTO, deliveryPrice, lstCustomerEntity.get(0).getCustomerId(), 0);

//            //Apply shipping promotion
//            for (ReductionDTO reductionDTO : discountProductOrderAndDeliveryDTO.getDeliveryDiscount()) {
//                if (reductionDTO.getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT)) {  // Discount by Percent
//                    totalDeliveryDiscount += deliveryValue4MobileDTO.getOriginalShippingFee() * reductionDTO.getValueDiscount() / 100;
//                    reductionDTO.setValueDiscount(deliveryValue4MobileDTO.getOriginalShippingFee() * reductionDTO.getValueDiscount() / 100);
//                    typeShipping += 1;
//                } else if (typeShipping == 0) {
//                    totalDeliveryDiscount = reductionDTO.getValueDiscount();      //Shipping fee fix price
//                    reductionDTO.setValueDiscount(totalDeliveryDiscount);
//                }
//                discountProductOrderAndDeliveryDTO.getLstOutletPromotion().add(reductionDTO);
//                if (typeShipping == 0 || deliveryPrice - totalDeliveryDiscount <= 0) break; // choose only one for fix-price discount delivery or discount price than delivery fee
//            }
            if (discountProductOrderAndDeliveryDTO.getDeliveryDiscount() != null && discountProductOrderAndDeliveryDTO.getDeliveryDiscount().size() > 0) {
                if (typeShipping == 0) { // SHIPPING_FIX_PRICE ($)
                    deliveryPrice = totalDeliveryDiscount;
                    totalDeliveryDiscount = deliveryValue4MobileDTO.getOriginalShippingFee() - totalDeliveryDiscount > 0 ? deliveryValue4MobileDTO.getOriginalShippingFee() - totalDeliveryDiscount : totalDeliveryDiscount;
                } else // SHIPPING_DISCOUNT (%)
                    deliveryPrice = deliveryPrice - totalDeliveryDiscount > 0 ? deliveryPrice - totalDeliveryDiscount : 0;
            }
            deliveryValue4MobileDTO.setShippingDiscount(totalDeliveryDiscount);
            deliveryValue4MobileDTO.setShippingFee(deliveryPrice > 0 ? deliveryPrice : 0);
        }

        List<Long> outletIds = new ArrayList<>();
        outletIds.addAll(shoppingCartMapperDTOMap.keySet());
        List<Long> mergeOutletIds = new ArrayList<>();
        mergeOutletIds.addAll(customerRewardGifts.keySet());
        mergeOutletIds.removeAll(outletIds);
        outletIds.addAll(mergeOutletIds);

        for (Long outletId : outletIds) {
            ShoppingCartSummaryMobileDTO cartSummary = new ShoppingCartSummaryMobileDTO();
            ShoppingCartMapperDTO shoppingCartMapperDTO = shoppingCartMapperDTOMap.get(outletId);
            if (shoppingCartMapperDTO != null) {
                List<ShoppingCartItemMobileDTO> listProductItem = shoppingCartMapperDTO.getShoppingCartItems();
                cartSummary.setTotalItem(shoppingCartMapperDTO.getTotalItem());
                cartSummary.setProductItems(listProductItem);
                cartSummary.setTotalPrice(shoppingCartMapperDTO.getTotalPrice() > 0 ? shoppingCartMapperDTO.getTotalPrice() : 0);
                cartSummary.setTotalOriginalPrice(shoppingCartMapperDTO.getTotalOriginalPrice());
                cartSummary.setTotalStoreDiscountPrice(shoppingCartMapperDTO.getTotalStoreDiscountPrice());
                cartSummary.setTotalPromotionDiscountPrice(shoppingCartMapperDTO.getTotalPromotionDiscountPrice());
                Double totalSelling = shoppingCartMapperDTO.getTotalPrice() + shoppingCartMapperDTO.getTotalPromotionDiscountPrice() + shoppingCartMapperDTO.getTotalStoreDiscountPrice();
                cartSummary.setTotalSellingPrice(totalSelling);
            }
            totalPrice = cartSummary.getTotalPrice() != null ? cartSummary.getTotalPrice() : 0D;
            if (totalPrice.isNaN()) {
                totalPrice = 0D;
            }

            totalPrice = totalPrice + deliveryPrice;
            // total loyalty point use to pay bill
            if (dto.getTotalLoyaltyPoint() != null && dto.getTotalLoyaltyPoint() > 0) {
                totalPrice = (totalPrice - (dto.getTotalLoyaltyPoint() / dto.getPointRate())) > 0 ? (totalPrice - (dto.getTotalLoyaltyPoint() / dto.getPointRate())) : 0;
            }

            // debit infor
            LoyaltyMemberEntity loyaltyMemberEntity = new LoyaltyMemberEntity();
            List<LoyaltyMemberEntity> lstLoyalty = loyaltyMemberRepository.findByCustomerIdAndOutletOutletIdAndActive(dto.getCustomerId(), dto.getOutletId(), 1);
            if (!(lstLoyalty == null) && lstLoyalty.size() > 0) {
                loyaltyMemberEntity = lstLoyalty.get(0);
            }
            if (loyaltyMemberEntity != null && loyaltyMemberEntity.getLoyaltyMemberId() != null && loyaltyMemberEntity.getTerms()) {
                cartSummary.setCreditLimit(loyaltyMemberEntity.getCreditLimit());
                cartSummary.setCreditTerms(loyaltyMemberEntity.getCreditTerms());
                cartSummary.setPaymentDueDate(loyaltyMemberEntity.getPaymentDueDate());
                Double creditLimit = loyaltyMemberEntity.getCreditLimit() != null ? loyaltyMemberEntity.getCreditLimit() : 0D;
                Double totalDebt = loyaltyMemberEntity.getTotalDebt() != null ? loyaltyMemberEntity.getTotalDebt() : 0;
                cartSummary.setCreditBalance(creditLimit - totalDebt);
                cartSummary.setPaymentDueAmount(totalDebt + totalPrice);
                cartSummary.setTerms(Boolean.TRUE);
            } else {
                cartSummary.setTerms(Boolean.FALSE);
            }
            cartSummary.setOutletName(outletEntity.getName());
            cartSummary.setOutletAddress(outletEntity.getAddress());
            cartSummary.setOutletId(outletId);
            cartSummary.setCustomerRewards(customerRewardGifts.get(outletId));
            totalReward += (customerRewardGifts.size() > 0 ? customerRewardGifts.size() : 0);
            cartSummary.setTotalItem(((shoppingCartMapperDTO != null && shoppingCartMapperDTO.getTotalItem() != null) ? shoppingCartMapperDTO.getTotalItem() : 0) + totalReward);
            if (shoppingCartMapperDTO != null) {
                // point after apply promotion
                Double price4CalculatePoint = cartSummary.getTotalPrice();
                cartSummary.setLoyaltyPoint(calculateLoyaltyPoint4Cart(shoppingCartMapperDTO, outletId, dto.getCustomerId(), price4CalculatePoint, loyaltyMemberEntity));
            }

            totalItems += cartSummary.getTotalItem();
            cartSummary.setTotalPrice(totalPrice);
            cartSummary.setOutletPromotion((shoppingCartMapperDTO == null || shoppingCartMapperDTO.getPromotionActiveInCart() == null) ? null : shoppingCartMapperDTO.getPromotionActiveInCart());
            cartSummary.setShipDate(shipDate);
            cartSummary.setInvalidCode(promotionPerOrderDTO.getInvalidCode());
            result.add(cartSummary);
        }

        objects[0] = totalItems + totalReward;
        objects[1] = result;
        objects[2] = deliveryValue4MobileDTO;

        return objects;
    }

    private Map<Long, List<CustomerReward4MobileDTO>> generateCustomerReward4Mobile(Long customerId, Long outletId) {
        Map<Long, List<CustomerReward4MobileDTO>> mapListCustomerReward4MobileDTO = new HashMap<>();
        List<CustomerRewardEntity> originalResult = customerRewardRepository.findAllInCart(customerId, outletId);

        for (CustomerRewardEntity customerRewardEntity : originalResult) {
            if (customerRewardEntity.getGift() != null) {
                CustomerReward4MobileDTO mobileItem = new CustomerReward4MobileDTO();
                mobileItem.setCustomerRewardId(customerRewardEntity.getCustomerRewardId());
                mobileItem.setGiftName(customerRewardEntity.getGift().getName());
                mobileItem.setGiftImage(customerRewardEntity.getGift().getImage());
                OutletEntity outletEntity = customerRewardEntity.getOutlet();
                mobileItem.setOutletId(outletEntity.getOutletId());
                mobileItem.setOutletName(outletEntity.getName());
                mobileItem.setOutletAddress(outletEntity.getAddress());
                if (!mapListCustomerReward4MobileDTO.containsKey(outletEntity.getOutletId())) {
                    mapListCustomerReward4MobileDTO.put(outletEntity.getOutletId(), new ArrayList<CustomerReward4MobileDTO>());
                }
                mapListCustomerReward4MobileDTO.get(outletEntity.getOutletId()).add(mobileItem);
            }
        }

        return mapListCustomerReward4MobileDTO;
    }

    private Map<Long, ShoppingCartMapperDTO> convertListItem2DtoAndGroupByOutletId(DiscountProductOrderAndDeliveryDTO discountProductOrderAndDeliveryDTO, Double totalDiscountPromotionPrice, Long warehouseId) throws ObjectNotFoundException, JSONException {
        // OutletId, content cart
        Map<Long, ShoppingCartMapperDTO> mappingResult = new HashMap<>();
        Long outletIdHas = 0L;
        for (ProductOrderItemDTO productOrderItemDTO : discountProductOrderAndDeliveryDTO.getProductOrderItems()) {
            Long outletId = productOrderItemDTO.getProductOutletSku().getProductOutlet().getOutletId();

            //set to calculate totalprice
            outletIdHas = outletId;
            if (!mappingResult.containsKey(outletId)) {
                mappingResult.put(outletId, new ShoppingCartMapperDTO());
            }
            ShoppingCartMapperDTO mapperDTO = mappingResult.get(outletId);

            //show item in shopping cart
            ShoppingCartItemMobileDTO itemMobileDTO = new ShoppingCartItemMobileDTO();
            itemMobileDTO.setShoppingCartId(productOrderItemDTO.getShoppingCartId());
            itemMobileDTO.setProductOutletSkuId(productOrderItemDTO.getProductOutletSku().getProductOutletSkuId());
            itemMobileDTO.setProductOutletId(productOrderItemDTO.getProductOutletId());
            itemMobileDTO.setProductImage(productOrderItemDTO.getProductOutletSku().getImage());
            itemMobileDTO.setProductName(productOrderItemDTO.getProductOutletName());
            itemMobileDTO.setSkuName(productOrderItemDTO.getProductOutletSku().getTitle());
            itemMobileDTO.setDevice(productOrderItemDTO.getDevice());
            itemMobileDTO.setQuantity(productOrderItemDTO.getQuantity());

            //originalPrice = productoutletsku.salePrice || productoutletsku.originalPrice
            itemMobileDTO.setOriginalPrice(productOrderItemDTO.getProductOutletSku().getSalePrice() > productOrderItemDTO.getProductOutletSku().getOriginalPrice() ? productOrderItemDTO.getProductOutletSku().getSalePrice() : productOrderItemDTO.getProductOutletSku().getOriginalPrice());

            //get price by group price or sale price of outlet
//            itemMobileDTO.setSalePrice(productOrderItemDTO.getPrice());
            itemMobileDTO.setSalePrice(productOrderItemDTO.getProductOutletSku().getSalePrice()); // view salePrice of Product

            //price after apply promotion
            itemMobileDTO.setPromotionPrice(productOrderItemDTO.getPriceDiscount());

            //price promotion discount = salePrice - promotionPrice
            itemMobileDTO.setDiscountPromotionPrice(itemMobileDTO.getPromotionPrice() != null && itemMobileDTO.getPromotionPrice() > 0 ? itemMobileDTO.getSalePrice() - itemMobileDTO.getPromotionPrice() : 0);

            //total Amount of Product after apply promotion = salePrice || promotionPrice * quantity (not use)
//            itemMobileDTO.setTotalAmount(itemMobileDTO.getPromotionPrice() != null && itemMobileDTO.getPromotionPrice() > 0 ? itemMobileDTO.getPromotionPrice() * itemMobileDTO.getQuantity() : itemMobileDTO.getSalePrice() * itemMobileDTO.getQuantity());

            //total Amount of product = saleprice (or groupPrice) * quantity
            itemMobileDTO.setTotalAmount(productOrderItemDTO.getTotalAmount());

            //check quantity remain in warehouse
            itemMobileDTO.setAvailable(this.checkQuantityInWarehouse(outletId, itemMobileDTO.getQuantity(), itemMobileDTO.getProductOutletSkuId(), warehouseId));

            mapperDTO.getShoppingCartItems().add(itemMobileDTO);
            mapperDTO.setTotalItem(mapperDTO.getTotalItem() + productOrderItemDTO.getQuantity());
            mapperDTO.setTotalOriginalPrice(mapperDTO.getTotalOriginalPrice() + (itemMobileDTO.getOriginalPrice() * productOrderItemDTO.getQuantity()));
            // storeDiscount = salePrice - groupPrice
            mapperDTO.setTotalStoreDiscountPrice(discountProductOrderAndDeliveryDTO.getTotalStoreDiscount());

            //show promotion active in cart
            if (productOrderItemDTO.getOutletPromotionId() != null && productOrderItemDTO.getOutletPromotionId().size() > 0) {
                List<OutletPromotionMobileDTO> promotionMobileDTOS = new ArrayList<>();
                for (int i = 0; i < productOrderItemDTO.getOutletPromotionId().size(); i++) {
                    OutletPromotionEntity outletPromotionEntity = outletPromotionRepository.findById(productOrderItemDTO.getOutletPromotionId().get(i)).get();
                    //set data of oulet promotion
                    OutletPromotionMobileDTO outletPromotionMobileDTO = new OutletPromotionMobileDTO();
                    outletPromotionMobileDTO.setOutletPromotionId(outletPromotionEntity.getOutletPromotionId());
                    outletPromotionMobileDTO.setDescription(outletPromotionEntity.getDescription());
                    outletPromotionMobileDTO.setImage(outletPromotionEntity.getThumbnail());
                    outletPromotionMobileDTO.setLimitPlayer(outletPromotionEntity.getLimitPlayer());
                    outletPromotionMobileDTO.setTimesApplied(outletPromotionEntity.getTimesApplied());
                    outletPromotionMobileDTO.setMasterPromotionTitle(outletPromotionEntity.getTitle());
                    ObjectMapper mapper = new ObjectMapper();
                    JSONObject data = new JSONObject(outletPromotionEntity.getNewPromotionJson());
                    try {
                        PromotionRuleDTO ruleDTO = mapper.readValue(data.getJSONObject("promotionRule").toString(), PromotionRuleDTO.class);
                        // outletPromotionMobileDTO.setPromotionType(ruleDTO.getPromotionType()); //todo
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    outletPromotionMobileDTO.setRemainGift(outletPromotionEntity.getRemainGift());
                    outletPromotionMobileDTO.setTotalGift(outletPromotionEntity.getTotalGift());
                    promotionMobileDTOS.add(outletPromotionMobileDTO);
                }
                mapperDTO.getPromotionActiveInCart().addAll(promotionMobileDTOS);
            }


//            mapperDTO.setTotalPrice(mapperDTO.getTotalOriginalPrice() - mapperDTO.getTotalStoreDiscountPrice());
            mapperDTO.setTotalPrice(discountProductOrderAndDeliveryDTO.getTotalOriginalPrice() - discountProductOrderAndDeliveryDTO.getTotalStoreDiscount() - totalDiscountPromotionPrice);
        }
        //get by outletId
        ShoppingCartMapperDTO shoppingCartMapperDTO = mappingResult.get(outletIdHas);

        if (shoppingCartMapperDTO != null) {
            //set total discount promotion price
            shoppingCartMapperDTO.setTotalPromotionDiscountPrice(totalDiscountPromotionPrice == null ? 0.0 : totalDiscountPromotionPrice);

            //calculate totalprice had apply promotion (total all price - total price discount promotion)
//            shoppingCartMapperDTO.setTotalPrice(shoppingCartMapperDTO.getTotalPrice() - totalDiscountPromotionPrice);
            shoppingCartMapperDTO.setTotalPrice(shoppingCartMapperDTO.getTotalPrice());

            //add gift to shopping cart
            this.addGiftToCart(shoppingCartMapperDTO, discountProductOrderAndDeliveryDTO.getGiftPromotions());

            //add free product to shopping cart (BUY X GET FREE PRODUCT Y)
            this.addFreeProductToCart(shoppingCartMapperDTO, discountProductOrderAndDeliveryDTO.getListFreeProductToChoose());

        }
        return mappingResult;
    }

    private Integer calculateLoyaltyPoint4Cart(ShoppingCartMapperDTO shoppingCartMapperDTO, Long outletId, Long customerId, Double price, LoyaltyMemberEntity loyaltyMemberEntity) throws ObjectNotFoundException {
        List<Long> productOutletSkuIds = new ArrayList<>();
        for (ShoppingCartItemMobileDTO itemMobileDTO : shoppingCartMapperDTO.getShoppingCartItems()) {
            if (itemMobileDTO.getProductOutletId() != null) {
                productOutletSkuIds.add(itemMobileDTO.getProductOutletSkuId());
            }
        }
        Integer loyaltyPoint = 0;
        Integer totalPlay;
        Integer totalPlayOrder;
        Integer totalPlayInOrder = 0;

        //1. from order amount
        List<LoyaltyEventOrderAmountEntity> eventsOrderAmount = loyaltyEventOrderAmountRepository.findByOrderAmount(outletId, price, new Timestamp(System.currentTimeMillis()));

        LoyaltyEventOrderAmountEntity eventOrderAmount = LoyaltyOutletEventBeanUtils.selectOneEventOrderAmount(eventsOrderAmount);

        if (eventOrderAmount != null && eventOrderAmount.getLoyaltyOutletEvent() != null) {
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
                }
            }
        }

        //2. from order product
        List<LoyaltyOutletEventEntity> eventOrderProducts = loyaltyService.getEventByProductOutletSkuIds(productOutletSkuIds);

        // just select 1 event to calculate
        LoyaltyOutletEventEntity loyaltyOutletEventEntity = LoyaltyOutletEventBeanUtils.selectOneEventOrderProduct(eventOrderProducts);
        if (loyaltyOutletEventEntity != null && loyaltyOutletEventEntity.getLoyaltyOutletEventId() != null) {

            loyaltyOutletEventEntity = loyaltyOutletEventRepository.findById(loyaltyOutletEventEntity.getLoyaltyOutletEventId()).get();

            totalPlay = loyaltyPointHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(customerId, loyaltyOutletEventEntity.getLoyaltyOutletEventId());
            Integer totalPlayOrderHistory = loyaltyOrderHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(customerId, loyaltyOutletEventEntity.getLoyaltyOutletEventId());
            totalPlay += (totalPlayOrderHistory + 1); // including this order
            totalPlayInOrder = 0;

            if (loyaltyOutletEventEntity.getMaxPlay() == null || (totalPlay <= loyaltyOutletEventEntity.getMaxPlay() && totalPlayInOrder <= loyaltyOutletEventEntity.getMaxPlayInOrder())) {
                if (LoyaltyOutletEventBeanUtils.checkCustomerGroupApproveLoyalty(loyaltyOutletEventEntity, loyaltyMemberEntity)) {

                    Map<Long, Integer> mapItems = new HashMap<>();

                    for (ShoppingCartItemMobileDTO cartItem : shoppingCartMapperDTO.getShoppingCartItems()) {
                        mapItems.put(cartItem.getProductOutletSkuId(), cartItem.getQuantity());
                    }

                    Map<Long, Integer> mapQuantity = loyaltyService.getQuantitySkuApprovedOnLoyalty(loyaltyOutletEventEntity.getLoyaltyOutletEventId(), mapItems);

                    Set<Long> skuIds = mapQuantity.keySet();
                    for (Long skuId : skuIds) {
                        totalPlayInOrder += mapQuantity.get(skuId);  // total = each quantity of product in cart item + number product in program
                    }

                    if (loyaltyOutletEventEntity.getMaxPlayInOrder() != null && totalPlayInOrder > loyaltyOutletEventEntity.getMaxPlayInOrder()) {
                        totalPlayInOrder = loyaltyOutletEventEntity.getMaxPlayInOrder();
                    }

                    if (loyaltyOutletEventEntity.getMaxPlayInOrder() == null || totalPlayInOrder > 0) {
                        loyaltyPoint += (loyaltyOutletEventEntity.getPoint() * totalPlayInOrder);
                    }
                }
            }
        }

        //3. from first order
        if (customerId != null) {
            List<LoyaltyEventFirstOrderEntity> eventsFirstOrder = loyaltyEventFirstOrderRepository.findByLoyaltyOutletEventOutletIdAndLoyaltyOutletEventStatus(outletId, new Timestamp(System.currentTimeMillis()));

            LoyaltyOutletEventEntity eventFirstOrder = LoyaltyOutletEventBeanUtils.selectOneEventFirstOrder(eventsFirstOrder);

            if (eventFirstOrder != null && eventFirstOrder.getLoyaltyOutletEventId() != null) {
                // count total order of customer
                Integer totalOrder = orderOutletRepository.countByCustomerId(customerId) == null ? 0 : orderOutletRepository.countByCustomerId(customerId);
                if (eventFirstOrder.getMaxPlay() == null || totalOrder <= eventFirstOrder.getMaxPlay()) { // old order + a new order => total Order
                    if (LoyaltyOutletEventBeanUtils.checkCustomerGroupApproveLoyalty(eventFirstOrder, loyaltyMemberEntity)) {
                        Integer point = eventFirstOrder.getPoint();
                        loyaltyPoint += point;
                    }
                }
            }
        }
        return loyaltyPoint;
    }

    /**
     * @param orderId
     * @return
     */
    public Map<String, String> getOrderById(Long orderId) {
        Map<String, String> map = new HashMap<>();
        OrderOutletEntity orderOutletEntity = orderOutletRepository.findByOrderOutletID(orderId);
        map.put("transactionCode", orderOutletEntity.getCode());
        map.put("totalPrice", String.valueOf(orderOutletEntity.getTotalPrice()));
        return map;
    }

    /**
     * @param grabTxId
     * @param token
     * @param orderOutletId
     * @return
     * @throws Exception
     */

    public Integer updateGrabTransaction(String grabTxId, String token, Long orderOutletId) throws Exception {
        return orderOutletRepository.updateOrder(grabTxId, token, orderOutletId);
    }

    public List<ShoppingCartSummaryMobileDTO> cartSummaryItems(Long userId, Long outletIdInput, Long warehouseId, String device, Long salesManUserId) throws ObjectNotFoundException, JSONException {
        List<ShoppingCartSummaryMobileDTO> result = new ArrayList<>();
        Long customerId = null;
        Map<Long, List<ShoppingCartEntity>> mapperShoppingCartEntities = new HashMap<>();
        DiscountProductOrderAndDeliveryDTO discountProductOrderAndDeliveryDTO = new DiscountProductOrderAndDeliveryDTO();

        if (userId != null) {
            //Get customerId
            customerId = customerRepository.findByUserIdUserId(userId).get(0).getCustomerId();
        }

        List<ShoppingCartEntity> shoppingCartEntities = new ArrayList<>();
        if (salesManUserId != null) {
            shoppingCartEntities = shoppingCartRepository.findShoppingCartForSalesman(customerId, salesManUserId, outletIdInput != null ? outletIdInput : -1L);
        } else {
            shoppingCartEntities = shoppingCartRepository.findShoppingCartForCustomer(customerId, outletIdInput != null ? outletIdInput : -1L);
        }

//        if (shoppingCartEntities == null || shoppingCartEntities.size() == 0) {
//            return result;
//        }

        List<Long> listOutletId = new ArrayList<>();
        if (salesManUserId != null && shoppingCartEntities.size() > 0) {
            //sales man
            listOutletId.add(shoppingCartEntities.get(0).getProductOutletSku().getProductOutlet().getOutlet().getOutletId());
        } else if (customerId != null && shoppingCartEntities.size() > 0) {
            //buyer
            listOutletId = shoppingCartRepository.getListOutletIdFromShoppingCart(customerId != null ? customerId : -1L);
        }

        Map<Long, ShoppingCartMapperDTO> mapperDTOMap = new HashMap<>();

        Map<Long, List<CustomerReward4MobileDTO>> customerRewardGifts = new HashMap<>();

        if (customerId != null) {
            customerRewardGifts = generateCustomerReward4Mobile(customerId, -1L);
        }
        if (listOutletId.size() > 0) {
            for (Long outletId : listOutletId) {
                Long pricingId = 0L;
                //Get pricing for calculate saleprice
                List<LoyaltyMemberEntity> lstLoyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletOutletIdAndActive(customerId, outletId, 1);
                if (lstLoyaltyMemberEntity != null && lstLoyaltyMemberEntity.size() > 0) {
                    List<Long> lstPricingEntity = pricingRepository.findPricingId(lstLoyaltyMemberEntity.get(0).getCustomerGroup() != null ? lstLoyaltyMemberEntity.get(0).getCustomerGroup().getCustomerGroupId() : 0);
                    pricingId = lstPricingEntity != null && lstPricingEntity.size() > 0 ? lstPricingEntity.get(0) : null;
                }

                //create new easysubmitorder to get list promotion of product
                EasyOrderSubmitDTO easyOrderSubmitDTO = new EasyOrderSubmitDTO();
                easyOrderSubmitDTO.setUserId(userId);
                List<ProductDTO> lstProductDTO = new ArrayList<>();
                Double salePriceSku = 0.0D;
                //set product on order to list

                List<ShoppingCartEntity> lstShoppingCart = new ArrayList<>();

                //just for test
                System.out.println("======Shopping cart(List cart)=======");
                for (ShoppingCartEntity shoppingCartEntity : shoppingCartEntities) {
                    if (shoppingCartEntity.getProductOutletSku().getProductOutlet().getOutlet().getOutletId().equals(outletId)) {
                        ProductDTO productDTO = new ProductDTO();
                        //Get price from customergroup if customer is member of outlet
                        pricingId = pricingId != null ? pricingId : 0;
                        List<PricingSkuEntity> lstPricingSkuEntity = pricingSkuRepository.findByPricingIdAndProductOutletSkuId(pricingId, shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());
                        salePriceSku = lstPricingSkuEntity != null && lstPricingSkuEntity.size() > 0 && lstPricingSkuEntity.get(0).getSalePrice() != null ? lstPricingSkuEntity.get(0).getSalePrice() : shoppingCartEntity.getProductOutletSku().getSalePrice();
                        productDTO.setProductOutletSkuId(shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());
                        productDTO.setPrice(salePriceSku);
                        productDTO.setQuantity(shoppingCartEntity.getQuantity());

                        //just for test
                        System.out.println("Product SKU ID:" + shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());
                        System.out.println("Quantity:" + shoppingCartEntity.getQuantity());
                        System.out.println("Price:" + salePriceSku);

                        //check out of stock will not apply promotion
                        if (this.checkQuantityInWarehouse(shoppingCartEntity.getProductOutletSku().getProductOutlet().getOutlet().getOutletId(), shoppingCartEntity.getQuantity(), shoppingCartEntity.getProductOutletSku().getProductOutletSkuId(), warehouseId)) {
                            System.out.println("==> SKU Available");
                            lstProductDTO.add(productDTO);
                        }
                        lstShoppingCart.add(shoppingCartEntity);
                        mapperShoppingCartEntities.put(outletId, lstShoppingCart);
                    }
                }

                System.out.println("=====================================");
                System.out.println();

                easyOrderSubmitDTO.setProducts(lstProductDTO);
                easyOrderSubmitDTO.setOutletId(outletId);
                PromotionPerOrderDTO promotionPerOrderDTO = new PromotionPerOrderDTO();
                try {
                    //list promotion of product
                    promotionPerOrderDTO = promotionRepository.getPromotionDiscountPriceByEasyOrder(easyOrderSubmitDTO); //call Promotion Service . action 1: store DB  . 0: view Promotions without update DB
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //just for test //todo
                System.out.println("--------------- Total Discount Price :" + promotionPerOrderDTO.getTotalDiscountValue());

                discountProductOrderAndDeliveryDTO = this.getDetailDiscountProductOrderAndDelivery(promotionPerOrderDTO, mapperShoppingCartEntities.get(outletId), pricingId);

                // Outlet ID, content cart
                Map<Long, ShoppingCartMapperDTO> shoppingCartMapperDTOMap = convertListItem2DtoAndGroupByOutletId(discountProductOrderAndDeliveryDTO, promotionPerOrderDTO.getTotalDiscountValue(), warehouseId);
                mapperDTOMap.put(outletId, shoppingCartMapperDTOMap.get(outletId));
            }

        } else if (customerRewardGifts.size() == 0) {                     //case customer not have shoppingcart and customerreward
            return result;
        }

        //merge 2 list outletId without duplicate
        List<Long> outletIds = new ArrayList<>();
        List<Long> setOutletIds2 = new ArrayList<>();
        outletIds.addAll(mapperDTOMap.keySet());
        setOutletIds2.addAll(customerRewardGifts.keySet());
        setOutletIds2.removeAll(outletIds);
        outletIds.addAll(setOutletIds2);

        for (Long outletId : outletIds) {
            ShoppingCartSummaryMobileDTO cartSummary = new ShoppingCartSummaryMobileDTO();
            ShoppingCartMapperDTO shoppingCartMapperDTO = mapperDTOMap.get(outletId);
            Integer totalReward = 0;
            OutletEntity outletEntity = outletRepository.findById(outletId).get();
            if (shoppingCartMapperDTO != null) {
                List<ShoppingCartItemMobileDTO> listProductItem = shoppingCartMapperDTO.getShoppingCartItems();
                cartSummary.setOutletName(outletEntity.getName());
                cartSummary.setOutletAddress(outletEntity.getAddress());
                cartSummary.setOutletId(outletId);
                cartSummary.setProductItems(listProductItem);
                cartSummary.setTotalPrice(shoppingCartMapperDTO.getTotalPrice() > 0 ? shoppingCartMapperDTO.getTotalPrice() : 0);
                cartSummary.setTotalOriginalPrice(shoppingCartMapperDTO.getTotalOriginalPrice());
                cartSummary.setTotalStoreDiscountPrice(shoppingCartMapperDTO.getTotalStoreDiscountPrice() > 0 ? shoppingCartMapperDTO.getTotalStoreDiscountPrice() : 0);
                cartSummary.setTotalPromotionDiscountPrice(shoppingCartMapperDTO.getTotalPromotionDiscountPrice());
                Double totalSellingNoPromotion = shoppingCartMapperDTO.getTotalPrice() + cartSummary.getTotalPromotionDiscountPrice() + cartSummary.getTotalStoreDiscountPrice();
                cartSummary.setTotalSellingPrice(totalSellingNoPromotion);


                // Hot Deal
                List<ProductOutletEntity> hotDeals = productOutletRepository.findProductHotDeals(outletId, 1, true);
                List<ProductOutletMobileDTO> hotDealsMobile = new ArrayList<>();
                for (ProductOutletEntity hotDeal : hotDeals) {
                    hotDealsMobile.add(ProductOutletBeanUtil.entityToMobile(hotDeal, null, null));
                }

                //Add promoiton label
                try {
                    Set<Long> dataSet = new HashSet<>();
                    hotDealsMobile.forEach(dto -> {
                        dataSet.addAll(dto.getSkus().stream().map(ProductOutletSkuMobileDTO::getProductOutletSkuId).collect(Collectors.toList()));
                    });
                    List<Long> data = new ArrayList<>(dataSet);
                    JSONObject requestBody = new JSONObject();
                    requestBody.put("userId", userId);
                    requestBody.put("outletId", outletId);
                    requestBody.put("skuIds", data);
                    Map<Long, Boolean> dataMap = new HashMap<>();
                    boolean[] response = promotionRepository.getPromotionProduct(requestBody.toString()).getBody();
                    for (int i = 0; i < data.size(); i++) {
                        dataMap.put(data.get(i), response[i]);
                    }
                    hotDealsMobile.forEach(dto -> {
                        dto.getSkus().forEach(sku -> {
                            sku.setHasPromotion(dataMap.get(sku.getProductOutletSkuId()));
                        });
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                cartSummary.setHotDeals(hotDealsMobile);

                //show GUI product fix price
                if (discountProductOrderAndDeliveryDTO.getProductsFixPrice().size() > 0 && discountProductOrderAndDeliveryDTO.getProductsFixPrice() != null) {
                    Long productOutletIdTemp = 0L;
                    Set<ProductOutletMobileDTO> productFixPriceSet = new HashSet<>();
                    for (ProductOutletSkuMobileDTO dto : discountProductOrderAndDeliveryDTO.getProductsFixPrice()) {
                        ProductOutletMobileDTO productOutletMobileDTO = new ProductOutletMobileDTO();
                        List<ProductOutletSkuMobileDTO> rs = new ArrayList<>();
                        ProductOutletSkuEntity productOutletSkuEntity = productOutletSkuRepository.findById(dto.getProductOutletSkuId()).get();
                        ProductOutletSkuMobileDTO dto1 = ProductOutletBeanUtil.productOutletSkuSkuEntity2DTO(productOutletSkuEntity, null);
                        dto1.setHasPromotion(true);
                        dto1.setPromotionPrice(dto.getPromotionPrice());
                        rs.add(dto1);
                        productOutletMobileDTO.setProductOutletId(productOutletSkuEntity.getProductOutlet().getProductOutletId());
                        productOutletMobileDTO.setOutletId(productOutletSkuEntity.getProductOutlet().getOutlet().getOutletId());
                        productOutletMobileDTO.setCatGroupId(productOutletSkuEntity.getProductOutlet().getProduct().getCatGroup().getCatGroupId());
                        productOutletMobileDTO.setBrandId(productOutletSkuEntity.getProductOutlet().getProduct().getBrand().getBrandId());
                        productOutletMobileDTO.setProductName(productOutletSkuEntity.getProductOutlet().getName());
                        productOutletMobileDTO.setProductImage(productOutletSkuEntity.getProductOutlet().getThumbnail());
                        productOutletMobileDTO.setSkus(rs);
                        productFixPriceSet.add(productOutletMobileDTO);
                    }
                    List<ProductOutletMobileDTO> productsFixPrice = new ArrayList<>(productFixPriceSet); //convert set to list
                    cartSummary.setProductsFixPrice(productsFixPrice);
                }
            }

            // Get reward gift for customer
            List<CustomerReward4MobileDTO> customerReward4MobileDTOS = customerRewardGifts.get(outletId);
            if (customerId != null) {
                cartSummary.setCustomerRewards(customerReward4MobileDTOS);
                totalReward = customerReward4MobileDTOS != null ? customerReward4MobileDTOS.size() : 0;
                if (customerReward4MobileDTOS != null && customerReward4MobileDTOS.size() > 0 && cartSummary.getOutletId() == null) {
                    CustomerReward4MobileDTO customerReward4MobileDTO = customerReward4MobileDTOS.get(0);
                    cartSummary.setOutletId(customerReward4MobileDTO.getOutletId());
                    cartSummary.setOutletName(customerReward4MobileDTO.getOutletName());
                    cartSummary.setOutletAddress(customerReward4MobileDTO.getOutletAddress());
                }
            }

            cartSummary.setCustomerRewards(customerReward4MobileDTOS);
            cartSummary.setTotalItem(((shoppingCartMapperDTO != null && shoppingCartMapperDTO.getTotalItem() != null) ? shoppingCartMapperDTO.getTotalItem() : 0) + totalReward);
            result.add(cartSummary);
        }
        return result;
    }

    public Map<String, Object> findAddressDefault4Mobile(Long userId) {
        Map<String, Object> rs = new HashMap<>();
        if (userId == null) {
            return rs;
        }
        CustomerAddressMobileDTO customerAddressMobileDTO = new CustomerAddressMobileDTO();
        Long customerId = customerRepository.findByUserIdUserId(userId).get(0).getCustomerId();
        if (customerId == null) {
            return rs;
        }
        CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
        List<CustomerAddressEntity> customerAddressEntities;
        if (customerId != null) {
            customerAddressEntities = customerAddressRepository.findByCustomerCustomerIdAndIsDefaultIsTrue(customerId);
            if (customerAddressEntities.isEmpty()) {
                customerAddressEntities = customerAddressRepository.findByCustomerCustomerId(customerId);
            }
            if (!customerAddressEntities.isEmpty()) {
                customerAddressEntity = customerAddressEntities.get(0);
            }

            if (customerAddressEntity != null) {
                CustomerAddressDTO customerAddressDTO = CustomerAddressBeanUtil.Entity2DTO(customerAddressEntity);
                customerAddressMobileDTO = CustomerAddressBeanUtil.DTO2MobileDTO(customerAddressDTO);
            }
        }
        rs.put("address", customerAddressMobileDTO);
        rs.put("hasGift", this.checkAvailableGift(customerId));
        return rs;
    }

    private Boolean checkAvailableGift(Long customerId) {
        return customerRewardRepository.countAvailableGift(customerId) > 0;
    }

    private ShoppingCartMapperDTO addGiftToCart(ShoppingCartMapperDTO shoppingCartMapperDTO, List<GiftPromotionDTO> giftPromotions) {
        if (giftPromotions.size() > 0 || giftPromotions != null) {
            for (GiftPromotionDTO giftPromotionDTO : giftPromotions) {
                ShoppingCartItemMobileDTO itemMobileDTO = new ShoppingCartItemMobileDTO();
                itemMobileDTO.setShoppingCartId(null);
                itemMobileDTO.setProductImage(giftPromotionDTO.getImgUrl());
                itemMobileDTO.setProductName(giftPromotionDTO.getName());
                itemMobileDTO.setQuantity(1);
                itemMobileDTO.setSalePrice(0.0);
                shoppingCartMapperDTO.getShoppingCartItems().add(itemMobileDTO);
            }
        }
        return shoppingCartMapperDTO;
    }

    private ShoppingCartMapperDTO addFreeProductToCart(ShoppingCartMapperDTO shoppingCartMapperDTO, List<GiftPromotionDTO> productPromotionDTOS) {
        if (productPromotionDTOS.size() > 0 || productPromotionDTOS != null) {
            for (GiftPromotionDTO giftPromotionDTO : productPromotionDTOS) {
                ShoppingCartItemMobileDTO itemMobileDTO = new ShoppingCartItemMobileDTO();
                itemMobileDTO.setShoppingCartId(null);
                itemMobileDTO.setProductOutletSkuId(giftPromotionDTO.getProductOutletSkuId());
                itemMobileDTO.setProductImage(giftPromotionDTO.getImgUrl());
                itemMobileDTO.setProductName(giftPromotionDTO.getName());
                itemMobileDTO.setQuantity(1);
                itemMobileDTO.setSalePrice(0.0);
                shoppingCartMapperDTO.getShoppingCartItems().add(itemMobileDTO);
            }
        }
        return shoppingCartMapperDTO;
    }

    private boolean checkQuantityInWarehouse(Long outletId, Integer quantityTheory, Long skuId, Long warehouseId) {
        //check allow storage
        if (easyOrderService.isAllowStorage(outletId)) {
            //check quantity availabe in warehouse of outlet
            if (warehouseId == null) {
                WareHouseEntity wareHouseEntity = warehouseRepository.findByOutletOutletIdAndIsDefaultIsTrue(outletId);    // warehouse is null -> check product in warehouse default of outlet
                if (wareHouseEntity != null) {
                    warehouseId = wareHouseEntity.getWareHouseId();
                } else {
                    return false;
                }
            }
            ProductOutletStorageEntity productOutletStorageEntity = productOutletStorageRepository.findByWareHouseIdAnAndProductOutletSkuId(warehouseId, skuId);
            if (productOutletStorageEntity != null) {
                // out of stock
                //available
                return easyOrderService.countProductNonExpire(productOutletStorageEntity) - quantityTheory >= 0;
            } else {
                // out of stock
                return false;
            }
        } else {
            return true;
        }
    }

    @Transactional
    public OrderTemporaryEntity saveOrderTemporary(OrderInformationMobileDTO dto, Double amount) {
        OrderTemporaryEntity orderTemporaryEntity = new OrderTemporaryEntity();
        orderTemporaryEntity.setCustomerId(dto.getCustomerId());
        orderTemporaryEntity.setAmount(amount);
        orderTemporaryEntity.setOrderOutletCode(RandomTokenBeanUtil.generateOrderCode());
        orderTemporaryEntity.setStatus(CoreConstants.WAITING_FOR_CONFIRMATION);
        orderTemporaryEntity.setCreatedBy(dto.getCustomerId());
        orderTemporaryEntity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        orderTemporaryEntity.setPromotionCode(dto.getPromotionCode());
        orderTemporaryEntity.setReceiverName(dto.getReceiverName());
        orderTemporaryEntity.setReceiverPhone(dto.getReceiverPhone());
        orderTemporaryEntity.setReceiverAddress(dto.getReceiverAddress());
        orderTemporaryEntity.setReceiverLat(dto.getReceiverLat());
        orderTemporaryEntity.setReceiverLng(dto.getReceiverLng());
        orderTemporaryEntity.setDeliveryMethod(dto.getDeliveryMethod());
        orderTemporaryEntity.setSalesManUserId(dto.getSalesManUserId());
        orderTemporaryEntity.setUsedPoint(dto.getUsedPoint());
        orderTemporaryEntity.setAmountAfterUsePoint(dto.getAmountAfterUsePoint());
        orderTemporaryEntity.setPayment(dto.getPayment());
        orderTemporaryEntity.setNote(dto.getNote());
        orderTemporaryEntity.setDeliveryDate(dto.getDeliveryDate());
        orderTemporaryEntity.setGrabTxId(dto.getGrabTxId());
        orderTemporaryEntity.setGrabGroupTxId(dto.getGrabGroupTxId());
        orderTemporaryEntity.setToken(dto.getToken());
        orderTemporaryEntity.setOrderOnBeHalf(dto.getOrderOnBeHalf());
        orderTemporaryEntity.setConfirmed(dto.getConfirmed());
        orderTemporaryEntity.setState(dto.getState());
        orderTemporaryEntity.setOutletId(dto.getOutletId());
        orderTemporaryEntity.setFromSource(dto.getSource());

        return orderTemporaryEntity;
    }

    @Transactional
    public Map<String, Object> calculateTempPrice4GrabPay(OrderInformationMobileDTO dto) throws JSONException, ObjectNotFoundException {
        Map<String, Object> rs = new HashMap<>();
        //convert Dto to calculate price
        CartInfoMobileDTO cartInfoMobileDTO = new CartInfoMobileDTO();
        cartInfoMobileDTO.setCustomerId(dto.getCustomerId());
        cartInfoMobileDTO.setOutletId(dto.getOutletId());
        cartInfoMobileDTO.setDeliveryMethod(dto.getDeliveryMethod());
        cartInfoMobileDTO.setShipToAddress(dto.getReceiverAddress());
        cartInfoMobileDTO.setRemainingTotalPrice(dto.getAmountAfterUsePoint());
        cartInfoMobileDTO.setTotalLoyaltyPoint(dto.getUsedPoint());
        cartInfoMobileDTO.setLongitude(dto.getReceiverLng());
        cartInfoMobileDTO.setLatitude(dto.getReceiverLat());
        try {
            //calculate price
            Object[] obj = this.listCartItemsInfor(cartInfoMobileDTO);
            //insert temp record into table
            List<ShoppingCartSummaryMobileDTO> shoppingCartDTOs = (List<ShoppingCartSummaryMobileDTO>) obj[1];
            OrderTemporaryEntity orderTemporaryEntity = this.saveOrderTemporary(dto, shoppingCartDTOs.get(0).getTotalPrice());
            orderTemporaryEntity = orderTemporaryRepository.saveAndFlush(orderTemporaryEntity);

            rs.put("OrderOutletCode", orderTemporaryEntity.getOrderOutletCode());
            rs.put("Amount", orderTemporaryEntity.getAmount());
        } catch (Exception e) {
            rs.put("OrderOutletCode", null);
            rs.put("Amount", 0.0);
        }

        return rs;
    }

    @Transactional
    public SubmitOrderResponseDTO submitOrder4GrabPay(String state, String txId, String token) {
        SubmitOrderResponseDTO rs = new SubmitOrderResponseDTO();
        try {
            OrderTemporaryEntity orderTemporaryEntity = orderTemporaryRepository.findByState(state);
            OrderInformationMobileDTO dto = OrderTemporaryUtil.entity2OrderMobileDTO(orderTemporaryEntity);
            dto.setGrabTxId(txId);
            dto.setToken(token);
            dto.setSource(Constants.SOURCE_SMART_RETAIL);
            rs = this.submitOrder4Mobile(dto, null);
            //delete record
            orderTemporaryRepository.delete(orderTemporaryEntity);
            orderTemporaryRepository.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    @Transactional
    public void cancelPromotionInOrder(Long outletId) {
        // Cancel Product was awarded by Promotion
        OrderOutletEntity orderOutletEntity = orderOutletRepository.findByOrderOutletID(outletId);
        if (orderOutletEntity == null) return;

        for (ProductOrderItemEntity productOrderItemEntity : orderOutletEntity.getProductOrderItemEntities()) {
            // Cancel Product was awarded by Promotion : Price = 0 and priceDiscount = null
            if (productOrderItemEntity.getProductOutletSkuId() != null && productOrderItemEntity.getPrice() == 0.0 && productOrderItemEntity.getPriceDiscount() == null) {
                productOrderItemRepository.delete(productOrderItemEntity);
            }
            // Cancel Gift was awarded by Promotion
            if (productOrderItemEntity.getGiftId() != null) {
                // update Gift : Should be updated in PromotionService
//                Optional<GiftEntity> giftEntity = giftRepository.findById(productOrderItemEntity.getGiftId());
//                if (giftEntity.isPresent()) {
//                    giftEntity.get().setTotalReceivedPromotion(giftEntity.get().getTotalReceivedPromotion() != null && giftEntity.get().getTotalReceivedPromotion() > 1 ? giftEntity.get().getTotalReceivedPromotion() - 1 : null);
////                    giftEntity.get().setQuantityPromotion(giftEntity.get().getQuantityPromotion() != null ? giftEntity.get().getQuantityPromotion() + 1 : 1);
//                    giftRepository.saveAndFlush(giftEntity.get());
//                }
                productOrderItemRepository.delete(productOrderItemEntity);
            }
        }

        // Unsubscribe Promotion
        promotionRepository.restoreRemain2OutletPromotion(outletId, orderOutletEntity.getCustomerId());

        // Remove all Gifts or Free Products in Order : reserve to Edit Order
        orderOutletEntity.setProductOrderItemEntities(orderOutletEntity.getProductOrderItemEntities().stream().filter(productOrderItemEntity -> {
            return productOrderItemEntity.getGiftId() == null && (productOrderItemEntity.getProductOutletSkuId() != null && (productOrderItemEntity.getPrice() != 0.0 ||
                    (productOrderItemEntity.getPrice() == null && productOrderItemEntity.getPriceDiscount() != null)));
        }).collect(Collectors.toList()));


    }

    // reduce on : PERCENT_OFF and AMOUNT_OFF and promotionType in {0,1,2,3}
    public DiscountProductOrderAndDeliveryDTO getDetailDiscountProductOrderAndDeliveryAfterSubmitted(PromotionPerOrderDTO promotionPerOrderDTO, List<ProductDTO> lstProductDTO, Long pricingId) {
        double totalOriginalPrice = 0.0;
        double totalWeight = 0.0;
        List<Long> shoppingCartIds = new ArrayList<>();
        List<ProductOrderItemDTO> productOrderItems = new ArrayList<>();
        List<ReductionDTO> promotionsPass = new ArrayList<>();
        List<GiftPromotionDTO> giftPromotions = new ArrayList<>();               //gifts
        List<GiftPromotionDTO> listFreeProductToChoose = new ArrayList<>();     //free product
        List<ReductionDTO> promotionDelivery = new ArrayList<>();                            //promotion delivery
        List<ProductOutletSkuMobileDTO> productsFixPrice = new ArrayList<>();         //products have fix price
//        Map<Long, Double> productsHavePromotionPrice = new HashMap<>();
        List<ReductionDTO> lstPromotion = promotionPerOrderDTO.getReductionDTOS();
        double totalStoreDiscount = 0.0;             // totalStoreDiscountPrice = totalSalePrice - totalGroupPrice

        //just for test
        System.out.println("======== List promotion Reapplied : ");
        if (!(lstPromotion == null)) {
            this.getSatisfiedPromotions(lstPromotion, promotionsPass, giftPromotions, listFreeProductToChoose,
                    promotionDelivery, productsFixPrice);

        }
        Double salePriceSku = 0.0D;
        for (ProductDTO productDTO : lstProductDTO) {   // main Products ( not contain Free Product/Gift)
            Optional<ProductOutletSkuEntity> productOutletSkuEntity = productOutletSkuRepository.findById(productDTO.getProductOutletSkuId());
            if (!productOutletSkuEntity.isPresent()) {
                System.out.println("SKU not found when edited Order   !!! skuId = " + productDTO.getProductOutletSkuId());
                continue;
            }
            ProductOrderItemEntity productOrderItemEntity = new ProductOrderItemEntity();
            productOrderItemEntity.setProductOutletSkuId(productDTO.getProductOutletSkuId());

            //get sale price by check customergroup
            List<PricingSkuEntity> lstPricingSkuEntity = pricingSkuRepository.findByPricingIdAndProductOutletSkuId(pricingId != null ? pricingId : 0, productOutletSkuEntity.get().getProductOutletSkuId());
            salePriceSku = lstPricingSkuEntity != null && lstPricingSkuEntity.size() > 0 && lstPricingSkuEntity.get(0).getSalePrice() != null ? lstPricingSkuEntity.get(0).getSalePrice() : productOutletSkuEntity.get().getSalePrice();

            double groupPriceSkuDiscount = 0.0D;
            boolean editedPrice = false;
            if (!salePriceSku.equals(productDTO.getPrice())) { // New Price : edited Price
                salePriceSku = productDTO.getPrice();
                editedPrice = true;
                System.out.println("Updated New Price for SalePrice! " + salePriceSku);
            } else {
                // No Edit Price
                // update groupPrice to calculate TotalStoreDiscount
                groupPriceSkuDiscount = productOutletSkuEntity.get().getSalePrice() - salePriceSku > 0 ? productOutletSkuEntity.get().getSalePrice() - salePriceSku : 0;
            }

            //get sale price by check customerGroup || price after edited
//            salePriceSku = productDTO.getPrice();

//            groupPriceSkuDiscount = productDTO.getPriceDiscount();

            //apply promotion price if product in promotion FIX PRICE
            double skuFixPrice = 0;
            double totalSkuFixPrice = 0;
            int quantityFixPrice = 0;
            if (productsFixPrice.size() > 0) {
                productsFixPrice = productsFixPrice.stream().sorted(Comparator.comparingDouble(ProductOutletSkuMobileDTO::getPromotionPrice)).collect(Collectors.toList());
                skuFixPrice = productsFixPrice.get(0).getPromotionPrice();
                if (!editedPrice || skuFixPrice < salePriceSku) {   // Ignored Fix-Price Discount > salePrice(edited-Price)
                    for (ProductOutletSkuMobileDTO dto : productsFixPrice) {
                        if (productDTO.getQuantity() > quantityFixPrice) {
                            if (dto.getProductOutletSkuId().equals(productDTO.getProductOutletSkuId())) {
                                skuFixPrice = dto.getPromotionPrice();
                                quantityFixPrice++;
                                //add promotionId passed to save db
                                promotionsPass.add(dto.getOutletPromotion());
                            }
                        }
                    }
                    totalSkuFixPrice = skuFixPrice * quantityFixPrice;
                }
            }

//            productDTO.setPrice(salePriceSku);

//            productOrderItemEntity.setPrice(salePriceSku);
//            productOrderItemEntity.setQuantity(productDTO.getQuantity());

            //price item after apply promotion for one unit
//            productOrderItemEntity.setPriceDiscount(0.0);
//            productOrderItemEntity.setWeight(productOutletSkuEntity.get().getWeight() != null ? productOutletSkuEntity.get().getWeight() : 0);
//            totalOriginalPrice += (productOutletSkuEntity.get().getSalePrice() * productDTO.getQuantity());
            totalWeight += productDTO.getQuantity() * (productOutletSkuEntity.get().getWeight() != null ? productOutletSkuEntity.get().getWeight() : 0);

            //in case : PROMOTION PRODUCT FIX PRICE
            if (totalSkuFixPrice != 0 && quantityFixPrice != 0) {
                // cal total amount not include quantity sku have fix price + total amount sku have fix price
//                Double productPrice = (salePriceSku * (productDTO.getQuantity() - quantityFixPrice)) + skuFixPrice;
//                productDTO.setPrice(productPrice);
                promotionPerOrderDTO.setTotalDiscountValue(promotionPerOrderDTO.getTotalDiscountValue() + ((salePriceSku * quantityFixPrice - totalSkuFixPrice)));
            }

            // totalStoreDiscount = (totalSalePrice - totalGroupPrice) * (totalQuantity - totalQuantityDiscountByFixPrice)
            if (editedPrice) {  // This currently Product is no edited Price => Keep SalesPrice and update PriceDiscount by GroupPrice
                productDTO.setPriceDiscount(groupPriceSkuDiscount * (productDTO.getQuantity() - quantityFixPrice));
                totalStoreDiscount += groupPriceSkuDiscount * (productDTO.getQuantity() - quantityFixPrice);    // quantityFixPrice updated discount by PromotionDiscount
                totalOriginalPrice += salePriceSku * productDTO.getQuantity(); // update Sub-Price base on Edited Price
            } else {
                totalOriginalPrice += productOutletSkuEntity.get().getSalePrice() * productDTO.getQuantity(); // SalePrice for Sub-Price
            }
        }

        return new DiscountProductOrderAndDeliveryDTO(promotionDelivery, productOrderItems, totalWeight, totalOriginalPrice, shoppingCartIds, promotionsPass, giftPromotions, listFreeProductToChoose, productsFixPrice, totalStoreDiscount);
    }


    /* Create input with EasyOrderSubmitDTO format to apply Promotion */
    private void createInput4PromotionService(EasyOrderSubmitDTO easyOrderSubmitDTO, Long pricingId, Long outletId, Long customerId, List<ProductOrderItemEntity> productOrderItemEntities, List<ProductDTO> lstProductDTO, int action) {
        easyOrderSubmitDTO.setOutletId(outletId);
        CustomerEntity customerEntity = customerRepository.findByCustomerId(customerId);
        if (customerEntity == null) return;
        easyOrderSubmitDTO.setUserId(customerEntity.getUserId().getUserId());
        Double salePriceSku = 0.0D;
        //set product on order to list

        for (ProductOrderItemEntity productOrderItemEntity : productOrderItemEntities) {
            ProductDTO productDTO = new ProductDTO();
            // Get price from Customer Group if customer is member of outlet
            productDTO.setProductOutletSkuId(productOrderItemEntity.getProductOutletSkuId());
            Optional<ProductOutletSkuEntity> productOutletSkuEntity = productOutletSkuRepository.findById(productOrderItemEntity.getProductOutletSkuId());
            if (!productOutletSkuEntity.isPresent()) {
                System.out.println("Order after Edit : SKU not found !!! " + productOrderItemEntity.getProductOutletSkuId());
                continue;
            }
            // GroupPrice || SalePrice || EditedPrice :)))
            List<PricingSkuEntity> lstPricingSkuEntity = pricingSkuRepository.findByPricingIdAndProductOutletSkuId(pricingId != null ? pricingId : 0, productOrderItemEntity.getProductOutletSkuId());
            salePriceSku = lstPricingSkuEntity != null && lstPricingSkuEntity.size() > 0 && lstPricingSkuEntity.get(0).getSalePrice() != null ? lstPricingSkuEntity.get(0).getSalePrice() :
                    productOrderItemEntity.getPrice() != null ? productOrderItemEntity.getPrice() : productOutletSkuEntity.get().getSalePrice();

            // Get Price a Product base on productOrderItem's Price : This Price probably is edited or in Customer's groupPrice
            productDTO.setPrice(productOrderItemEntity.getPrice() != null ? productOrderItemEntity.getPrice() : productOutletSkuEntity.get().getSalePrice());
            productDTO.setQuantity(productOrderItemEntity.getQuantity());
            productDTO.setPriceDiscount(productOrderItemEntity.getPriceDiscount());     // ProductOrderItem's Price consist : SalePrice of Product || GroupPrice of Product || Edited Price of Product

            lstProductDTO.add(productDTO);
        }

        easyOrderSubmitDTO.setProducts(lstProductDTO);
        easyOrderSubmitDTO.setAction(action); //1: update db table (outletpromotioncustomer,orderoutletpromotion) default: no update db
    }

    private Long getPricingIdByCustomerGroup(Long customerId, Long outletId) {
        Long pricingId = null;
        List<LoyaltyMemberEntity> lstLoyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletOutletIdAndActive(customerId, outletId, 1);
        if (lstLoyaltyMemberEntity != null && lstLoyaltyMemberEntity.size() > 0) {
            List<Long> lstPricingEntity = pricingRepository.findPricingId(lstLoyaltyMemberEntity.get(0).getCustomerGroup() != null ? lstLoyaltyMemberEntity.get(0).getCustomerGroup().getCustomerGroupId() : 0);
            pricingId = lstPricingEntity != null && lstPricingEntity.size() > 0 ? lstPricingEntity.get(0) : null;
        }
        return pricingId;
    }

    private void updateOrderOutletEntity(OrderOutletEntity orderOutletEntity, Integer totalItem, Double totalPrice, Double totalOriginalPrice, Double totalStoreDiscount, Double totalPromotionDiscount) {
        Double savingPrice = totalOriginalPrice - totalPrice > 0 ? totalOriginalPrice - totalPrice : 0D;
        orderOutletEntity.setTotalItem(totalItem);
        orderOutletEntity.setTotalPrice(totalPrice + orderOutletEntity.getDeliveryPrice());
        orderOutletEntity.setTotalOriginalPrice(totalOriginalPrice);
        orderOutletEntity.setTotalPromotionDiscountPrice(totalPromotionDiscount);
        orderOutletEntity.setTotalStoreDiscountPrice(totalStoreDiscount);
        orderOutletEntity.setSaving(savingPrice);
        //orderOutletEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        orderOutletEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));

    }

    /* Calculate delivery price */
    private double calculateDelivery(OrderOutletEntity orderOutletEntity) {
        double deliveryPrice = 0.0;
        Double deliveryDistance = 0.0;
        Timestamp shipDate;
        OutletEntity outletEntity = orderOutletEntity.getOutlet();
        OrderInformationEntity orderInformationEntity = orderOutletEntity.getOrderInformation();
        if (!(orderInformationEntity.getReceiverAddress() != null && orderInformationEntity.getReceiverAddress().length() >= 3))
            return deliveryPrice;
        deliveryDistance = DeliveryUtils.calculateDeliveryDistance(outletEntity, orderInformationEntity.getReceiverAddress(), orderInformationEntity.getReceiverLng(), orderInformationEntity.getReceiverLat());
        if (orderInformationEntity.getDeliveryMethod() != null) {
            try {
                //when user choose GRAB_EXPRESS
                if (orderInformationEntity.getDeliveryMethod().equals(Constants.DELIVERY_SHIP_TO_HOME_EXPRESS)) {
                    QuotesPlatformDTO quotesPlatformDTO = new QuotesPlatformDTO();
                    quotesPlatformDTO.setCustomerId(orderOutletEntity.getCustomerId());
                    quotesPlatformDTO.setOutletId(outletEntity.getOutletId());
                    quotesPlatformDTO.setLatitude(Double.parseDouble(orderInformationEntity.getReceiverLat().toString()));
                    quotesPlatformDTO.setLongitude(Double.parseDouble(orderInformationEntity.getReceiverLng().toString()));
                    quotesPlatformDTO.setAddress(orderInformationEntity.getReceiverAddress());
                    ResponseDTO<QuotesResponseDTO> obj = deliveryRepository.getQuotes(quotesPlatformDTO);
                    deliveryPrice = Double.parseDouble(obj.getBody().getQuotes().get(0).getAmount().toString());
                    shipDate = obj.getBody().getQuotes().get(0).getEstimatedTimeline().getDropoff();
                    orderOutletEntity.setShipDate(shipDate);
                } else {
                    // Get delivery price
                    if (orderInformationEntity.getDeliveryMethod().equals(Constants.DELIVERY_SHIP_TO_HOME)) {
                        List<DeliveryServiceEntity> entities = deliveryServiceRepository.findByOutletIdAndCodeOrderByDistanceAscOrderByAsc(outletEntity.getOutletId(), orderInformationEntity.getDeliveryMethod());
                        deliveryPrice = DeliveryServiceUtils.calculateDeliveryPrice(entities, deliveryDistance);
                    }
                }
            } catch (Exception ex) {
                throw new BadRequestException(ErrorCodeMap.FAILURE_DELIVERY_MULTI_CITIES.name());
            }
        }
        return deliveryPrice;
    }

    /* Get total price discount for delivery by Promotions */
    private double getDeliveryPriceDiscountByPromotion(DiscountProductOrderAndDeliveryDTO discountProductOrderAndDeliveryDTO, double deliveryPrice, long customerId, int action) {
        int typeShipping = 0; // default  0 :corresponding for SHIPPING_FIX_PRICE ($)  ||  != 0 : SHIPPING_DISCOUNT (%)
        double totalDeliveryDiscount = 0.0;
        //apply shipping promotion
        for (ReductionDTO reductionDTO : discountProductOrderAndDeliveryDTO.getDeliveryDiscount()) {
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
                totalDeliveryDiscount += deliveryPrice * reductionDTO.getValueDiscount() / 100;
                reductionDTO.setValueDiscount(deliveryPrice * reductionDTO.getValueDiscount() / 100);
                typeShipping += 1;
                discountProductOrderAndDeliveryDTO.getLstOutletPromotion().add(reductionDTO);
            } else if (typeShipping == 0) {  // Discount by Fix-Price
                totalDeliveryDiscount = reductionDTO.getValueDiscount();
                reductionDTO.setValueDiscount(deliveryPrice - totalDeliveryDiscount > 0 ? deliveryPrice - totalDeliveryDiscount : totalDeliveryDiscount);
                discountProductOrderAndDeliveryDTO.getLstOutletPromotion().add(reductionDTO);
            }
            if (typeShipping == 0 || deliveryPrice - totalDeliveryDiscount <= 0)
                break; // choose only one for fix-price discount delivery or discount price than delivery fee
        }

        return totalDeliveryDiscount;
    }

    private void updateOrderAfterSubmit(OrderOutletEntity orderOutletEntity, CustomerEntity customerEntity, List<ProductOrderItemEntity> orderItemEntities,
                                        Integer totalItem, Double totalPrice, Double totalOriginalPrice, Double totalStoreDiscount,
                                        Double totalPromotionDiscount, List<ReductionDTO> outletPromotions) throws ObjectNotFoundException {
        OutletEntity outletEntity = orderOutletEntity.getOutlet();
        NotifyTemplateEntity notifyTemplateEntity = notifyTemplateRepositoty.findByCode(CoreConstants.NOTIFY_TEMPLATE.TEMPLATE_OUTLET_NEW_ORDER.getName());

        // save order information : no change

        // update orderOutlet
        updateOrderOutletEntity(orderOutletEntity, totalItem, totalPrice, totalOriginalPrice, totalStoreDiscount, totalPromotionDiscount);

        // save reward gift in order
        // update orderId to CustomerReward in Next Purchase Promotion
        for (ReductionDTO reductionDTO : outletPromotions) {
            if (reductionDTO.getPromotionType() == PromotionType.NEXTPURCHASE.getNo()) {
                CustomerRewardEntity customerRewardEntity = customerRewardRepository.getCustomerRewardByCode(reductionDTO.getCodeNextPurchase());
                System.out.println(" ~~~~~ Order with : " + orderOutletEntity.getOrderOutletID() + "  " + reductionDTO.getCodeNextPurchase());
                customerRewardEntity.setOrderOutletId(orderOutletEntity.getOrderOutletID());
                customerRewardRepository.saveAndFlush(customerRewardEntity);
            }
        }

        //save promotion apply on order
        for (ReductionDTO reductionDTO : outletPromotions) {
            OrderOutletPromotionEntity orderOutletPromotionEntity = new OrderOutletPromotionEntity();
            orderOutletPromotionEntity.setOutletPromotionId(reductionDTO.getOutletPromotionId());
            orderOutletPromotionEntity.setOrderoutletId(orderOutletEntity.getOrderOutletID());

            // save detail Promotion Discount per Order
            try {
                ObjectMapper oMapper = new ObjectMapper();
                String tmp = null;
                tmp = oMapper.writeValueAsString(reductionDTO); // obj 2 json
                orderOutletPromotionEntity.setPromotionDiscount(tmp);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                System.out.println("<><><>PromotionDiscount : Can not convert reductionDTO to json !!!!");
            }

            orderOutletPromotionRepository.saveAndFlush(orderOutletPromotionEntity);
        }
        // update orderItems and save FreeProducts/Gifts
        if (orderOutletEntity.getProductOrderItemEntities() != null && orderOutletEntity.getProductOrderItemEntities().size() > 0) {
            for (ProductOrderItemEntity orderItemEntity : orderOutletEntity.getProductOrderItemEntities()) {
                orderItemEntity.setOrderOutletId(orderOutletEntity.getOrderOutletID());
                productOrderItemRepository.save(orderItemEntity);
            }
        }

        //Create member for outlet
        List<LoyaltyMemberEntity> lstLoyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletOutletIdAndActive(customerEntity.getCustomerId(), orderOutletEntity.getOutlet().getOutletId(), 1);
        LoyaltyMemberEntity loyaltyMemberEntity = new LoyaltyMemberEntity();
        if (lstLoyaltyMemberEntity == null || lstLoyaltyMemberEntity.size() == 0) {
            String lastCode = loyaltyMemberRepository.findLastCustomerCode(outletEntity.getOutletId());
            //loyaltyMemberEntity.setCustomerCode(CustomerBeanUtil.autoCreateCustomerCode(lastCode));
            loyaltyMemberEntity.setCustomerId(customerEntity.getCustomerId());
            loyaltyMemberEntity.setReferedby(customerEntity.getUserId());
            loyaltyMemberEntity.setPoint(0);
            loyaltyMemberEntity.setJoinDate(new Timestamp(System.currentTimeMillis()));
            loyaltyMemberEntity.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            loyaltyMemberEntity.setStatus(CoreConstants.LOYALTY_MEMBERSHIP.MEMBER.getStatus());
            loyaltyMemberEntity.setTerms(Boolean.FALSE);
            loyaltyMemberEntity.setActive(1);
            loyaltyMemberEntity.setOutlet(outletEntity);
            loyaltyMemberEntity = loyaltyMemberRepository.saveAndFlush(loyaltyMemberEntity);
            welcomingGift(loyaltyMemberEntity.getLoyaltyMemberId());
        } else {
            loyaltyMemberEntity = lstLoyaltyMemberEntity.get(0);
        }

        // ReApply Used Point for Order
        if (orderOutletEntity.getPointRedeemed() != null && orderOutletEntity.getAmountRedeemed() != null) {
            loyaltyService.reApplyUsedPoint(orderOutletEntity);
        }

        //Customer Reward
        List<CustomerRewardOrderItemEntity> customerRewardOrderItemEntities = customerRewardOrderItemRepository.findByOrderOutletOrderOutletID(orderOutletEntity.getOrderOutletID());
        if (customerRewardOrderItemEntities.size() > 0 && customerRewardOrderItemEntities != null){
            Integer totalItems = orderOutletEntity.getTotalItem() + customerRewardOrderItemEntities.size();
            orderOutletEntity.setTotalItem(totalItems);
            orderOutletRepository.save(orderOutletEntity);
        }

        // Send notification
//        sendNotification2ShopperAndRetailerAfterOrderCreatedSuccess(currency, loyaltyMemberEntity.getSalesMan(), orderInformationMobileDTO, customerEntity.get(), outletEntity.get(), orderOutletEntity, notifyTemplateEntity, fullName);

    }

    @Transactional
    public void editOrderAfterSubmitted(Long orderOutletId) throws ObjectNotFoundException {
// Unsubscribe Promotion
        this.cancelPromotionInOrder(orderOutletId);

        OrderOutletEntity orderOutletEntity = orderOutletRepository.findByOrderOutletID(orderOutletId);
        if (orderOutletEntity == null) return;

        DiscountProductOrderAndDeliveryDTO discountProductOrderAndDeliveryDTO = new DiscountProductOrderAndDeliveryDTO();
        List<ProductOrderItemEntity> orderItemEntities = orderOutletEntity.getProductOrderItemEntities();
//        double totalDiscountValue = 0;
        Integer totalItems = 0;
        double totalPrice = 0;
        double totalSalesPrice = 0;
//        double totalPromotionDiscount = 0;


// Get FullName for send notification
        CustomerEntity customerEntity = customerRepository.findByCustomerId(orderOutletEntity.getCustomerId());
        String fullName = customerEntity.getUserId().getFullName();

// Get pricing for calculate saleprice
        Long pricingId = this.getPricingIdByCustomerGroup(orderOutletEntity.getCustomerId(), orderOutletEntity.getOutlet().getOutletId());

// Create Input to apply Promotions
        PromotionPerOrderDTO promotionPerOrderDTO = new PromotionPerOrderDTO();
        EasyOrderSubmitDTO easyOrderSubmitDTO = new EasyOrderSubmitDTO();
        List<ProductDTO> lstProductDTO = new ArrayList<>();


        this.createInput4PromotionService(easyOrderSubmitDTO, pricingId, orderOutletEntity.getOutlet().getOutletId(), orderOutletEntity.getCustomerId(), orderOutletEntity.getProductOrderItemEntities(), lstProductDTO, 1);
        try {
            //list promotion of products
            promotionPerOrderDTO = promotionRepository.getPromotionDiscountPriceByEasyOrder(easyOrderSubmitDTO); //call Promotion Service . action 1: store DB  . 0: view Promotions without update DB
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Get total discount promotion price
//        totalDiscountValue = promotionPerOrderDTO.getTotalDiscountValue();

        discountProductOrderAndDeliveryDTO = this.getDetailDiscountProductOrderAndDeliveryAfterSubmitted(promotionPerOrderDTO, lstProductDTO, pricingId);

        // Update Price for OrderItems
        for (int i = 0; i < orderItemEntities.size(); i++) {
            //Calculate Price
            if (orderItemEntities.get(i).getProductOutletSkuId().equals(lstProductDTO.get(i).getProductOutletSkuId())) {
                orderItemEntities.get(i).setPrice(lstProductDTO.get(i).getPrice());
                orderItemEntities.get(i).setPriceDiscount(lstProductDTO.get(i).getPriceDiscount());
            } else {
                for (int j = 0; j < orderItemEntities.size(); j++) {
                    if (orderItemEntities.get(i).getProductOutletSkuId().equals(lstProductDTO.get(j).getProductOutletSkuId())) {
                        orderItemEntities.get(i).setPrice(lstProductDTO.get(j).getPrice());
                        orderItemEntities.get(i).setPriceDiscount(lstProductDTO.get(i).getPriceDiscount());
                        break;
                    }
                }
            }
            Optional<ProductOutletSkuEntity> productOutletSkuEntity = productOutletSkuRepository.findById(orderItemEntities.get(i).getProductOutletSkuId());
            if (productOutletSkuEntity.isPresent()) {
                orderItemEntities.get(i).setWeight(productOutletSkuEntity.get().getWeight() != null ? productOutletSkuEntity.get().getWeight() : 0);
            } else orderItemEntities.get(i).setWeight(0.0);

            //Calculate total
            totalSalesPrice += (orderItemEntities.get(i).getPrice() * orderItemEntities.get(i).getQuantity());  // totalSalePriceSku after applied GroupPrice & Fix-Price (Promotion Discount) => totalPrice Products customer has to pay without PromotionDiscount
            totalItems += orderItemEntities.get(i).getQuantity();
        }

//        totalPromotionDiscount = promotionPerOrderDTO.getTotalDiscountValue();
        totalPrice = totalSalesPrice - promotionPerOrderDTO.getTotalDiscountValue() > 0 ? totalSalesPrice - promotionPerOrderDTO.getTotalDiscountValue() : 0;

//    Add to OrderItems: Add Free Products or Gifts when applied Promotions
//      add free product to shoppingcart
        if (discountProductOrderAndDeliveryDTO.getListFreeProductToChoose().size() > 0 || discountProductOrderAndDeliveryDTO.getListFreeProductToChoose() != null) {
            ProductOrderItemEntity freeProduct;
            for (GiftPromotionDTO giftPromotionDTO : discountProductOrderAndDeliveryDTO.getListFreeProductToChoose()) {
                freeProduct = new ProductOrderItemEntity();
                ProductOutletSkuEntity product = productOutletSkuRepository.findByProductOutletSkuId(Long.valueOf(giftPromotionDTO.getProductOutletSkuId()));
                freeProduct.setProductOutletSkuId(product.getProductOutletSkuId());
                freeProduct.setQuantity(1);
                freeProduct.setPrice(0.0);
                freeProduct.setWeight(product.getWeight());
                orderItemEntities.add(freeProduct);
                totalItems += 1;
            }
        }

//      add gift to ProductOrderItem
        if (discountProductOrderAndDeliveryDTO.getGiftPromotions().size() > 0 || discountProductOrderAndDeliveryDTO.getGiftPromotions() != null) {
            ProductOrderItemEntity freeGift;
            for (GiftPromotionDTO giftPromotionDTO : discountProductOrderAndDeliveryDTO.getGiftPromotions()) {
                freeGift = new ProductOrderItemEntity();
                GiftEntity giftEntity = giftRepository.getGiftForPromotionApplied(giftPromotionDTO.getId(), new Timestamp(System.currentTimeMillis()));
                if (giftEntity != null) {
                    freeGift.setGiftId(giftEntity.getGiftId());
                    freeGift.setQuantity(1);
                    freeGift.setPrice(0.0);
                    orderItemEntities.add(freeGift);
                    totalItems += 1;
                    Integer totalReceived = giftEntity.getTotalReceivedPromotion() == null ? 1 : giftEntity.getTotalReceivedPromotion() + 1;
                    giftEntity.setTotalReceivedPromotion(totalReceived);
                    if (giftEntity.getTotalReceivedPromotion() >= giftEntity.getQuantityPromotion())
                        giftEntity.setStatus(0);
//                        giftEntity.setQuantityPromotion(giftEntity.getQuantityPromotion() - 1);
                    giftRepository.saveAndFlush(giftEntity);
                }
            }
        }


        // update deliveryPrice
        Double deliveryPrice = this.calculateDelivery(orderOutletEntity);
        // apply shipping promotion
        if (deliveryPrice > 0 && discountProductOrderAndDeliveryDTO.getDeliveryDiscount() != null && discountProductOrderAndDeliveryDTO.getDeliveryDiscount().size() > 0) {
            Double deliveryDiscount = 0.0;
            int typeShipping = 0; // default  0 :corresponding for SHIPPING_FIX_PRICE ($)  ||  != 0 : SHIPPING_DISCOUNT (%)//cover case Redemp > 2
            if (discountProductOrderAndDeliveryDTO.getDeliveryDiscount().get(0).getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT)) // Discount by Percent
                typeShipping = 1;
            //apply shipping promotion
            deliveryDiscount = this.getDeliveryPriceDiscountByPromotion(discountProductOrderAndDeliveryDTO, deliveryPrice, customerEntity.getCustomerId(), 1);
            if (typeShipping == 0) { // SHIPPING_FIX_PRICE ($)
                double originalDelivery = deliveryPrice;
                deliveryPrice = deliveryDiscount;
                deliveryDiscount = originalDelivery - deliveryDiscount;
            } else  // SHIPPING_DISCOUNT (%)
                deliveryPrice = deliveryPrice - deliveryDiscount;
//            promotionPerOrderDTO.setTotalDiscountValue(promotionPerOrderDTO.getTotalDiscountValue() + deliveryDiscount);
//            deliveryPrice = originalDelivery - deliveryDiscount > 0 ? originalDelivery - deliveryDiscount : 0;
        }
        orderOutletEntity.setDeliveryPrice(deliveryPrice > 0 ? deliveryPrice : 0);


        updateOrderAfterSubmit(orderOutletEntity, customerEntity, orderItemEntities, totalItems, totalPrice, discountProductOrderAndDeliveryDTO.getTotalOriginalPrice(),
                discountProductOrderAndDeliveryDTO.getTotalStoreDiscount(), promotionPerOrderDTO.getTotalDiscountValue(), discountProductOrderAndDeliveryDTO.getLstOutletPromotion());
    }

    public List<ProductDTO> stockOutProduct(List<ProductOrderItemEntity> products, OrderOutletEntity orderOutletEntity, Long warehouseId) {
        List<ProductDTO> productsStockOut = new ArrayList<>();

        for (ProductOrderItemEntity productOrderItemEntity : products) {
            if (productOrderItemEntity.getProductOutletSkuId() != null && productOrderItemEntity.getQuantity() != null) {
                ProductDTO rs = new ProductDTO();
                rs.setQuantity(productOrderItemEntity.getQuantity());
                rs.setProductOutletSkuId(productOrderItemEntity.getProductOutletSkuId());
                productsStockOut.add(rs);
            }
        }
        //just for test
        if (easyOrderService.addStockOut(productsStockOut, orderOutletEntity, warehouseId)) {

            //update modifidate for sync offline
            this.updateSkuAfterStockOut(productsStockOut);
            System.out.println("======= Stock out product from " + warehouseId + " ======");
        }
        return productsStockOut;
    }

    /**
     * @param orderTemporaryId
     * @return
     * @throws Exception
     */
    public Map<String, Object> getAmountOrderTemporary(Long orderTemporaryId) throws Exception {
        Map<String, Object> rs = new HashMap<>();
        OrderTemporaryEntity orderTemporaryEntity = orderTemporaryRepository.getOne(orderTemporaryId);
        if (orderTemporaryEntity == null) {
            throw new Exception("Error: Cannot get OrderTemporaryOrder by ID");
        }
        rs.put("OrderOutletCode", orderTemporaryEntity.getOrderOutletCode());
        rs.put("Amount", orderTemporaryEntity.getAmount());
        rs.put("Outlet", orderTemporaryEntity.getOutletId());
        rs.put("Source", orderTemporaryEntity.getFromSource());
        return rs;
    }

    /**
     * @param paymentStatus
     * @param orderOutletCode
     * @return
     * @throws Exception
     */
    public Integer updateGrabPaymentStatus(String paymentStatus, String orderOutletCode) throws Exception {
        return orderTemporaryRepository.updateOrder(paymentStatus, orderOutletCode);
    }

    public void sendNotification2Outlet(OrderOutletEntity orderOutletEntity, CustomerEntity customerEntity, OutletEntity outletEntity, NotifyTemplateEntity notifyTemplateEntity, List<String> listLang) {
        Map<String, Object> notificationMessageParamsOutlet = new HashMap<>();
        notificationMessageParamsOutlet.put("ORDERCODE", orderOutletEntity.getCode());
        notificationMessageParamsOutlet.put("CUSTOMERNAME", customerEntity.getUserId().getUserName());
        notificationMessageParamsOutlet.put("TOTALPRICE", PriceBeanUtils.formatPrice(orderOutletEntity.getTotalPrice()));

        for (int i = 0; i < listLang.size(); i++) {
            String lang = listLang.get(i) == null ? CoreConstants.ENGLISH : listLang.get(i);
            NotificationEntity notificationEntityOutlet = buildNotificationFromTemplate4Retailer(outletEntity.getShopman().getUserId(), notifyTemplateEntity, notificationMessageParamsOutlet, orderOutletEntity.getOrderOutletID(), orderOutletEntity, lang);
            if (notificationEntityOutlet != null) {
                notificationRepository.save(notificationEntityOutlet);
            }
        }
    }

    public void sendNotification2Shopper(OrderOutletEntity orderOutletEntity, OutletEntity outletEntity) {
        // Notification for Shopper
        Map<String, Object> notificationMessageParamsShopper = new HashMap<>();
        notificationMessageParamsShopper.put("ORDERCODE", orderOutletEntity.getCode());
        notificationMessageParamsShopper.put("OUTLET", outletEntity.getName());
        notificationMessageParamsShopper.put("TOTALITEM", orderOutletEntity.getTotalItem());
        notificationMessageParamsShopper.put("TOTALPRICE", PriceBeanUtils.formatPrice(orderOutletEntity.getTotalPrice()));
        Optional<CustomerEntity> cusEntity = customerRepository.findById(orderOutletEntity.getCustomerId());

        List<String> listLang = userDeviceRepository.findDistinctByUserId(cusEntity.get().getUserId().getUserId());

        for (int i = 0; i < listLang.size(); i++) {
            String lang = listLang.get(i) == null ? CoreConstants.ENGLISH : listLang.get(i);
            NotificationEntity notificationEntityShopper = buildNotificationFromTemplate4Shopper(cusEntity.get(), CoreConstants.NOTIFY_TEMPLATE.TEMPLATE_ORDER_STATUS.getName(), notificationMessageParamsShopper, orderOutletEntity.getOrderOutletID(), orderOutletEntity, lang);
            if (notificationEntityShopper != null) {
                notificationRepository.save(notificationEntityShopper);
            }
        }
    }

    public void updateSkuAfterStockOut(List<ProductDTO> productDTOS) {
        for (ProductDTO productDTO : productDTOS) {
            ProductOutletSkuEntity entity = productOutletSkuRepository.findById(productDTO.getProductOutletSkuId()).get();
            entity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
            productOutletSkuRepository.save(entity);
        }
    }
}
