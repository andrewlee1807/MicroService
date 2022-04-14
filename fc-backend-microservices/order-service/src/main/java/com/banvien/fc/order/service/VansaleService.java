package com.banvien.fc.order.service;

import com.banvien.fc.common.util.Constants;
import com.banvien.fc.order.dto.*;
import com.banvien.fc.order.dto.vansale.ShoppingCartVansaleDTO;
import com.banvien.fc.order.entity.*;
import com.banvien.fc.order.repository.*;
import com.banvien.fc.order.utils.ProductOrderItemUtils;
import com.banvien.fc.order.utils.RandomTokenBeanUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VansaleService {

    @Autowired
    private EasyOrderService easyOrderService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductOutletSkuRepository productOutletSkuRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private OrderOutletRepository orderOutletRepository;
    @Autowired
    private ProductOrderItemRepository productOrderItemRepository;
    @Autowired
    private OutletRepository outletRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderOutletPromotionRepository orderOutletPromotionRepository;


    @Transactional
    public List<SubmitOrderResponseDTO> submitOrders4Quicksell(List<ShoppingCartVansaleDTO> shoppingCartVansaleDTOS, Long vansalesUserId) {
        List<SubmitOrderResponseDTO> rs = new ArrayList<>();
        for (ShoppingCartVansaleDTO dto : shoppingCartVansaleDTOS) {
            try {
                dto.setVansalesUserId(vansalesUserId);
                rs.add(submitOrder4QuickSellOfVansaleApp(dto));
            } catch (Exception ex) {
                rs.add(new SubmitOrderResponseDTO(Boolean.FALSE, Status.BAD_REQUEST.getReasonPhrase(), ex.getMessage()));
            }
        }
        return rs;
    }

    @Transactional
    public SubmitOrderResponseDTO submitOrder4QuickSellOfVansaleApp(ShoppingCartVansaleDTO shoppingCartVansaleDTO) {
        SubmitOrderResponseDTO rs = new SubmitOrderResponseDTO();
        OrderOutletEntity orderOutletEntity = new OrderOutletEntity();
        try {
            WareHouseEntity wareHouseEntity = warehouseRepository.findById(shoppingCartVansaleDTO.getWarehouseId()).get();

            EasyOrderSubmitDTO easyOrderSubmitDTO = new EasyOrderSubmitDTO();

            easyOrderSubmitDTO.setOutletId(wareHouseEntity.getOutlet().getOutletId());
            //set default userId for anonymous user
            easyOrderSubmitDTO.setUserId(accountRepository.getDefaultUser(wareHouseEntity.getOutlet().getCountryId()).getBody().getUserId());
            //prepare data for get promotion list
            List<ProductDTO> productDTOS = shoppingCartVansaleDTO.getProducts().stream().map(x -> {
                ProductDTO productDTO = new ProductDTO();
                ProductOutletSkuEntity entity = productOutletSkuRepository.findById(x.getProductOutletSkuId()).get();
                productDTO.setQuantity(x.getQuantity());
                productDTO.setProductOutletSkuId(x.getProductOutletSkuId());
                productDTO.setPrice(entity.getSalePrice() == null || entity.getSalePrice() == 0.0 ? entity.getOriginalPrice() : entity.getSalePrice());
                return productDTO;
            }).collect(Collectors.toList());
            easyOrderSubmitDTO.setProducts(productDTOS);
            easyOrderSubmitDTO.setSource(Constants.SOURCE_SMART_VANSALE);
            easyOrderSubmitDTO.setPaymentMethod(shoppingCartVansaleDTO.getPaymentMethod());

            //save order first
            orderOutletEntity = this.saveOrderToDb(easyOrderSubmitDTO);

            EasyOrderRedeemPromotionDTO easyOrderRedeemPromotionDTO = easyOrderService.calculatePriceForEasyOrder(0, new PromotionPerOrderDTO(), easyOrderSubmitDTO, orderOutletEntity);

            //set all type price for entity
            orderOutletEntity.setTotalPromotionDiscountPrice(easyOrderRedeemPromotionDTO.getTotalPromotionDiscountPrice());
            orderOutletEntity.setTotalOriginalPrice(easyOrderRedeemPromotionDTO.getTotalOriginalPrice());
            orderOutletEntity.setTotalPrice(easyOrderRedeemPromotionDTO.getTotalPrice());
            orderOutletEntity.setSaving(easyOrderRedeemPromotionDTO.getTotalOriginalPrice() - easyOrderRedeemPromotionDTO.getTotalSalePrice());
            orderOutletEntity.setTotalStoreDiscountPrice(easyOrderRedeemPromotionDTO.getTotalStoreDiscount());
            orderOutletEntity.setTotalItem(easyOrderRedeemPromotionDTO.getTotalItems());

            //save promotionfor order outlet
            this.saveOrderOutletPromotion(easyOrderRedeemPromotionDTO.getOutletPromotionPass().stream().map(dto -> {
                Long id = dto.getOutletPromotionId();
                return id;
            }).collect(Collectors.toList()), orderOutletEntity);

            //order by vansale
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(shoppingCartVansaleDTO.getVansalesUserId());
            orderOutletEntity.setReferedBy(userEntity);

            orderOutletEntity = orderOutletRepository.save(orderOutletEntity);

            System.out.println("======= Stock out =======");

            //stock out product
            if (easyOrderService.addStockOut(productDTOS, orderOutletEntity, shoppingCartVansaleDTO.getWarehouseId())){

                //update modifidate for sync offline
                orderService.updateSkuAfterStockOut(productDTOS);
            };


            //update DB products
            if (easyOrderRedeemPromotionDTO.getProducts() != null && easyOrderRedeemPromotionDTO.getProducts().size() > 0) {
                for (ProductOrderItemDTO dto : easyOrderRedeemPromotionDTO.getProducts()) {
                    ProductOrderItemEntity productOrderItemEntity = ProductOrderItemUtils.DTO2entity(dto);
                    productOrderItemEntity.setOrderOutletId(orderOutletEntity.getOrderOutletID());
                    productOrderItemRepository.save(productOrderItemEntity);
                }
            }

        } catch (Exception ex) {
            rs.setSuccess(Boolean.FALSE);
            rs.setDescription(ex.getMessage());
            return rs;
        }
        rs.setOrderId(orderOutletEntity.getOrderOutletID());
        rs.setResponseMessageKey(Status.OK.getReasonPhrase());
        rs.setSuccess(Boolean.TRUE);
        System.out.println("----------/ Submit order for Quick Sell successfull /----------");
        return rs;
    }

    public Map<String, Object> checkoutShoppingcartVansale(EasyOrderSubmitDTO easyOrderSubmitDTO) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> rs = new HashMap<>();
        try {

            //get saleprice for sku
            List<ProductDTO> productDTOS = easyOrderSubmitDTO.getProducts().stream().map(dto -> {
                ProductDTO productDTO = new ProductDTO();
                ProductOutletSkuEntity entity = productOutletSkuRepository.findById(dto.getProductOutletSkuId()).get();
                productDTO.setProductOutletSkuId(entity.getProductOutletSkuId());
                productDTO.setQuantity(dto.getQuantity());
                productDTO.setPrice(entity.getSalePrice());
                return productDTO;
            }).collect(Collectors.toList());
            easyOrderSubmitDTO.setProducts(productDTOS);

            //calculate price
            EasyOrderRedeemPromotionDTO easyOrderRedeemPromotionDTO = easyOrderService.calculatePriceForEasyOrder(0, new PromotionPerOrderDTO(), easyOrderSubmitDTO, null);
            easyOrderRedeemPromotionDTO.setFinalTotalPrice(easyOrderRedeemPromotionDTO.getTotalPrice() - easyOrderRedeemPromotionDTO.getDeliveryDiscount());
            rs = mapper.convertValue(easyOrderRedeemPromotionDTO, Map.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    private OrderOutletEntity saveOrderToDb(EasyOrderSubmitDTO easyOrderSubmitDTO) {
        OrderOutletEntity orderOutletEntity = new OrderOutletEntity();
        OutletEntity outletEntity = outletRepository.findById(easyOrderSubmitDTO.getOutletId()).get();
        CustomerEntity customerEntity = customerRepository.findByUserIdUserId(accountRepository.getDefaultUser(outletEntity.getCountryId()).getBody().getUserId()).get(0);
        orderOutletEntity.setCustomerId(customerEntity.getCustomerId());
        orderOutletEntity.setOutlet(outletEntity);
        orderOutletEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        orderOutletEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        orderOutletEntity.setCode(RandomTokenBeanUtil.generateOrderCode());
        orderOutletEntity.setSaleChanel("ONLINE");
        orderOutletEntity.setStatus("SUCCESS");
        orderOutletEntity.setFromsource(Constants.SOURCE_SMART_VANSALE);
        orderOutletEntity.setNote(easyOrderSubmitDTO.getNote());

        //save Order Information (use default customer)
        if (easyOrderSubmitDTO.getPaymentMethod() != null) {
            easyOrderSubmitDTO.setName(customerEntity.getUserId().getFullName());
            easyOrderSubmitDTO.setPhone(customerEntity.getUserId().getPhoneNumber());
            easyOrderSubmitDTO.setAddress(customerEntity.getAddress());
            easyOrderSubmitDTO.setDeliveryMethod(Constants.DELIVERY_PICK_AND_GO);
            String paymentMethod = Constants.QUICKSELL_PAYMENT_CASH.equals(easyOrderSubmitDTO.getPaymentMethod()) ? Constants.PAYMENT_COD : Constants.PAYMENT_GATEWAY;
            easyOrderSubmitDTO.setPaymentMethod(paymentMethod);

            //save Order Information
            OrderInformationEntity orderInformationEntity = this.setOrderInfomation(easyOrderSubmitDTO);
            orderOutletEntity.setOrderInformation(orderInformationEntity);
        }

        //Hardcode for store in db first
        orderOutletEntity.setLoyaltyPoint(0);
        orderOutletEntity.setTotalItem(0);
        orderOutletEntity.setTotalOriginalPrice(0.0);
        orderOutletEntity.setDeliveryPrice(0.0);
        orderOutletEntity.setTotalStoreDiscountPrice(0.0);
        orderOutletEntity.setTotalPromotionDiscountPrice(0.0);
        orderOutletEntity.setTotalWeight(0.0);

        return orderOutletRepository.save(orderOutletEntity);
    }


    private OrderInformationEntity setOrderInfomation(EasyOrderSubmitDTO easyOrderSubmitDTO) {
        OrderInformationEntity orderInformationEntity = new OrderInformationEntity();
        orderInformationEntity.setReceiverName(easyOrderSubmitDTO.getName());
        orderInformationEntity.setReceiverPhone(easyOrderSubmitDTO.getPhone());
        orderInformationEntity.setPaymentMethod(easyOrderSubmitDTO.getPaymentMethod());
        orderInformationEntity.setDeliveryMethod(easyOrderSubmitDTO.getDeliveryMethod());
        orderInformationEntity.setReceiverAddress(easyOrderSubmitDTO.getAddress());
        return orderInformationEntity;
    }


    private PromotionPerOrderDTO getPromotionBySKUForQuickSellOfVansaleApp(EasyOrderSubmitDTO easyOrderSubmitDTO) {
        OutletEntity outletEntity = warehouseRepository.findById(easyOrderSubmitDTO.getWarehouseId()).get().getOutlet();    // get Country
        easyOrderSubmitDTO.setOutletId(outletEntity.getOutletId());

        //using default user to get promotion for anonymous
        easyOrderSubmitDTO.setUserId(accountRepository.getDefaultUser(outletEntity.getCountryId()).getBody().getUserId());  // add countryId to get Default

        //Get price for one unit
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (ProductDTO productDTO : easyOrderSubmitDTO.getProducts()) {
            ProductOutletSkuEntity entity = productOutletSkuRepository.findById(productDTO.getProductOutletSkuId()).get();
            ProductDTO productDTOInShoppingCart = new ProductDTO();
            productDTOInShoppingCart.setProductOutletSkuId(entity.getProductOutletSkuId());
            productDTOInShoppingCart.setPrice(entity.getSalePrice());
            productDTOInShoppingCart.setQuantity(productDTO.getQuantity());
            productDTOS.add(productDTOInShoppingCart);
        }

        easyOrderSubmitDTO.setProducts(productDTOS);

        //get promotion
        return promotionRepository.getPromotionDiscountPriceByEasyOrder(easyOrderSubmitDTO); //call Promotion Service .DB
    }

    public void saveOrderOutletPromotion(List<Long> promotionIdPassed, OrderOutletEntity orderOutletEntity) {
        for (Long id : promotionIdPassed) {
            OrderOutletPromotionEntity orderOutletPromotionEntity = new OrderOutletPromotionEntity();
            orderOutletPromotionEntity.setOutletPromotionId(id);
            orderOutletPromotionEntity.setOrderoutletId(orderOutletEntity.getOrderOutletID());
            orderOutletPromotionRepository.save(orderOutletPromotionEntity);
        }
    }
}
