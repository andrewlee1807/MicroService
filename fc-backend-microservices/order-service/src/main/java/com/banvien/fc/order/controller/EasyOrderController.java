package com.banvien.fc.order.controller;


import com.banvien.fc.common.enums.ErrorCodeMap;
import com.banvien.fc.common.rest.errors.BadRequestException;
import com.banvien.fc.common.util.CommonUtils;
import com.banvien.fc.common.util.Constants;
import com.banvien.fc.order.dto.*;
import com.banvien.fc.order.dto.rules.DiscountDTO;
import com.banvien.fc.order.repository.OutletRepository;
import com.banvien.fc.order.repository.PromotionRepository;
import com.banvien.fc.order.repository.ShoppingCartRepository;
import com.banvien.fc.order.service.EasyOrderService;
import com.banvien.fc.order.service.OrderService;
import com.banvien.fc.order.status.StatusResponse;
import com.banvien.fc.order.utils.OutletUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/order/easy")
public class EasyOrderController extends ApplicationObjectSupport {
    private final Logger logger = LogManager.getLogger(EasyOrderController.class);

    @Autowired
    private EasyOrderService easyOrderService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private Environment env;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private OutletRepository outletRepository;

    @GetMapping("/detail")
    public ResponseEntity<Map<String, Object>> getDetailOneEasyOrder(@RequestParam(value = "id") Long id,
//                                                                     @RequestParam(value = "size", defaultValue = "8") int size,    // Each Page/EasyOrder get 5 products + 3 collections
                                                                     @PageableDefault Pageable pageable,
                                                                     HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>();
        List<ProductDTO> productDtosToGetPromotion = new ArrayList<>();
        Long outletId = 0L;
        if (id == null || id <= 0) {
            map.put("status", HttpStatus.BAD_REQUEST.value());
            map.put("message", HttpStatus.BAD_REQUEST.value());
            map.put("result", null);

            return ResponseEntity.badRequest().body(map);
        }

        try {
//            Pageable pageable = PageRequest.of(0, size);
            EasyOrderDTO easyOrderDTO = easyOrderService.findById(id, pageable);
            if (easyOrderDTO == null) {
                map.put("status", HttpStatus.BAD_REQUEST.value());
                map.put("message", Objects.requireNonNull(getMessageSourceAccessor()).getMessage("errors.notfound",
                        new Object[]{this.getMessageSourceAccessor().getMessage("order.title", httpServletRequest.getLocale())},
                        "Order_not_found", httpServletRequest.getLocale()));
                map.put("result", null);
            } else {
                outletId = easyOrderDTO.getOutletDTO().getOutletId();
                map.put("status", HttpStatus.OK.value());
                map.put("message", "success");
//                map.put("totalElements", easyOrderDTO.getTotalElement() + totalElement);
                map.put("totalPages", easyOrderDTO.getTotalPage());
                map.put("totalElements", easyOrderDTO.getTotalElement());

                String urlImageServer = env.getProperty("url.image.server", "");
                Map<String, Object> mapResult = new HashMap<>();
                easyOrderDTO.getOutletDTO().setImage(CommonUtils.getBase64EncodedImage(urlImageServer + easyOrderDTO.getOutletDTO().getImage()));
                mapResult.put("outletDto", easyOrderDTO.getOutletDTO());
                mapResult.put("image", urlImageServer + easyOrderDTO.getPcImage());
                mapResult.put("mobileImage", urlImageServer + easyOrderDTO.getMobileImage());
                if (CollectionUtils.isNotEmpty(easyOrderDTO.getEasyOrderItemDTOS())) {
                    List<ProductOutletSkuDTO> lstSkuDTOS = new LinkedList<>();
                    for (EasyOrderItemDTO easyOrderItemDTO : easyOrderDTO.getEasyOrderItemDTOS()) {
                        if (easyOrderItemDTO.getProductOutletSkuDTO().getImage() == null) {
                            easyOrderItemDTO.getProductOutletSkuDTO().setImage("/images/default_product.png");
                        }
                        easyOrderItemDTO.getProductOutletSkuDTO().setImage(CommonUtils.getBase64EncodedImage(urlImageServer + easyOrderItemDTO.getProductOutletSkuDTO().getImage()));
                        if (easyOrderItemDTO.getProductOutletSkuDTO().getImage() != null && easyOrderItemDTO.getProductOutletSkuDTO().getImage().contains("/images/default_product.png")) {
                            easyOrderItemDTO.getProductOutletSkuDTO().setImage(easyOrderItemDTO.getProductOutletSkuDTO().getImage().replace("/repository", ""));
                        }

                        // Checking Out Of Stock to view
                        easyOrderItemDTO.getProductOutletSkuDTO().setOutOfStock(true);
                        Integer checkQuantity = easyOrderService.getQuantityProductSkuAvailability(easyOrderItemDTO.getProductOutletSkuDTO().getProductOutletSkuId());
                        if (checkQuantity > 0) {
                            easyOrderItemDTO.getProductOutletSkuDTO().setOutOfStock(false);
                        }


                        lstSkuDTOS.add(easyOrderItemDTO.getProductOutletSkuDTO());
                    }
                    mapResult.put("lstProductSkuDTOS", lstSkuDTOS);

                    //Create list product fake to get all promotion list in easy order
                    for (ProductOutletSkuDTO dto : lstSkuDTOS) {
                        ProductDTO productDTO = new ProductDTO();
                        productDTO.setProductOutletSkuId(dto.getProductOutletSkuId());
                        productDTO.setQuantity(1000);       //hardcode quantity
                        productDTO.setPrice(dto.getSalePrice());
                        productDtosToGetPromotion.add(productDTO);
                    }
                }
                EasyOrderSubmitDTO easyOrderSubmitToGetAllPromotion = new EasyOrderSubmitDTO();
                easyOrderSubmitToGetAllPromotion.setOutletId(outletId);
                easyOrderSubmitToGetAllPromotion.setEasyOrderId(id);
                easyOrderSubmitToGetAllPromotion.setProducts(productDtosToGetPromotion);
                //Get list promotion
                List<OutletPromotionDTO> lstPromotion = new ArrayList<>();
                try {
                    //list promotion of product
                    lstPromotion = promotionRepository.getPromotionByEasyOrder(easyOrderSubmitToGetAllPromotion);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

//                mapResult.put("lstCollection", collectionDTOS);
                mapResult.put("outletPromotion", lstPromotion);
                List<DiscountDTO> lstDiscount = new ArrayList<>();
                if (easyOrderDTO.getEasyOrderCode() != null) {  // EasyOrder Code Promotion attached
                    DiscountDTO discountDTO = promotionRepository.getPromotionByEasyOrderCode(easyOrderDTO.getEasyOrderCode());
                    if (discountDTO != null) {
                        lstDiscount.add(discountDTO);
                        mapResult.put("easyOrderPromotion", lstDiscount);
                    } else {
                        mapResult.put("easyOrderPromotion", lstDiscount);
                    }
                } else {
                    mapResult.put("easyOrderPromotion", null);
                }

                map.put("result", mapResult);
            }
        } catch (Exception e) {
            logger.error("_getDetailOneEasyOrder_ " + e.getMessage(), e);
            map.put("status", HttpStatus.BAD_REQUEST.value());
            map.put("message", HttpStatus.BAD_REQUEST.value());
            map.put("result", null);
        }
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping("/view/counter")
    public ResponseEntity<Map<String, Object>> updateTotalViewer(@RequestParam Long easyOrderId) {
        try {
            easyOrderService.updateTotalViewer(easyOrderId);
            return new ResponseEntity(new HashMap<>(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(new HashMap<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getOrder")
    public ResponseEntity<Map<String, Object>> getOrder(@RequestParam("id") String base64OrderId) throws Exception {
        String strOrderId = new String(Base64.decodeBase64(base64OrderId), StandardCharsets.UTF_8);
        String urlImageServer = env.getProperty("url.image.server");
        Map<String, Object> rs = new HashMap<>();
        OrderOutletDTO dto = easyOrderService.getOrderInfoById(new Long(strOrderId));
        Map<String, Object> sellerInfo = new HashMap<>();
        sellerInfo.put("sellerName", dto.getOutlet().getName());
        sellerInfo.put("sellerAddress", dto.getOutlet().getAddress());
        sellerInfo.put("sellerContact", dto.getOutlet().getPhoneNumber());
        sellerInfo.put("sellerEmail", OutletUtil.entity2DTO(outletRepository.findById(dto.getOutlet().getOutletId()).get()).getShopman().getEmail());
        sellerInfo.put("invoiceNo", dto.getCode());
        sellerInfo.put("invoiceDate", dto.getDateOrdered());
        sellerInfo.put("status", dto.getStatus());
        rs.put("sellerInfo", sellerInfo);

        List<Map<String, Object>> products = new ArrayList<>();
        double totalGross = 0;
        double totalDiscount = 0;
        double totalPriceProduct = 0;
        for (ProductOrderItemDTO item : dto.getProducts()) {
            Map<String, Object> product = new HashMap<>();
            product.put("productCode", item.getCode());
            product.put("productName", item.getName());
            if (item.getProductOutletSku() != null) product.put("skuName", item.getProductOutletSku().getTitle());
            else if (item.getName() != null) product.put("skuName", item.getName());
            product.put("quantity", item.getQuantity());
            product.put("perUnit", item.getSalePrice());

            //calculate gross(price * quantity)
            double totalPrice = item.getPrice() * item.getQuantity();   // contain PricingGR
            totalPriceProduct += totalPrice;
            product.put("total", totalPrice);
            products.add(product);
        }
        rs.put("products", products);
        rs.put("totalOriginalPrice", dto.getTotalOriginalPrice());
        rs.put("totalPromotionDiscountPrice", dto.getTotalPromotionDiscountPrice());
        rs.put("totalStoreDiscount", dto.getTotalStoreDiscountPrice());
        rs.put("deliveryFee", dto.getDeliveryPrice());
        rs.put("grandTotal", dto.getAmount());

        Map<String, Object> receiveInfo = new HashMap<>();

        //Buyer Infomation
        receiveInfo.put("receiverName", dto.getOrderInformation().getReceiverName());
        receiveInfo.put("phone", dto.getOrderInformation().getReceiverPhone());
        receiveInfo.put("address", dto.getOrderInformation().getReceiverAddress());
        receiveInfo.put("paymentMethod", dto.getOrderInformation().getPaymentMethod());
        receiveInfo.put("deliveryMethod", dto.getOrderInformation().getDeliveryMethod());
        receiveInfo.put("deliveryDate", dto.getShipDate());
        receiveInfo.put("note", dto.getNote());
        rs.put("receiveInfo", receiveInfo);

        dto.getOutlet().setImage(CommonUtils.getBase64EncodedImage(urlImageServer + dto.getOutlet().getImage()));
        rs.put("outlet", dto.getOutlet());
        return ResponseEntity.ok(rs);
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitOrder(@RequestBody EasyOrderSubmitDTO easyOrderSubmitDTO) {
        easyOrderSubmitDTO.setSource(Constants.SOURCE_EASY_ORDER);
        Map<String, Object> rs = new HashMap<>();
        StatusResponse status = easyOrderService.submitEasyOrder(easyOrderSubmitDTO);
        if (!Constants.ORDER_VALID.equals(status.getCode())) {
            System.out.println(status.getCode());
            System.out.println(status.getValue());
            throw new BadRequestException(status.getCode());
        } else {
            rs.put("orderId", status.getId());
            if (status.getId() != null)
                rs.put("hashId", new String(Base64.encodeBase64(status.getId().toString().getBytes())));
            rs.put("status", HttpStatus.OK.value());
            rs.put("message", "success");
            rs.put("authenUrl", status.getAuthenUrl());
            if (status.getId() == null)
                rs.put("orderCode", status.getOrderCode());
        }
//        Map<String, Object> rs = easyOrderService.submitEasyOrders(easyOrderSubmitDTO);
        return ResponseEntity.ok(rs);
    }

    @PostMapping("/grabPay/status")
    public ResponseEntity<Map<String, Object>> checkGrabPay(@RequestBody String body) {
        String orderCode = null;
        try {
            JSONObject json = new JSONObject(body);
            orderCode = json.getString("orderCode");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new BadRequestException(ErrorCodeMap.FAILURE_NULL_ORDERCODE.name());
        }
        Map<String, Object> rs = new HashMap<>();
        StatusResponse status = easyOrderService.checkGrabPayStatus(orderCode);
//        rs.put("orderId", status.getId());
        if (status.getId() != null)
            rs.put("hashId", new String(Base64.encodeBase64(status.getId().toString().getBytes())));
        rs.put("status", HttpStatus.OK.value());
        rs.put("message", status.getValue());
//        rs.put("authenUrl", status.getAuthenUrl());
        return ResponseEntity.ok(rs);
    }

    @PostMapping("/grabPay/paid")
    public ResponseEntity<Map<String, Object>> saveOrderAfterPaid(@RequestBody String body) {
        String orderCode = null;
        String txId = null;
        String token = null;
        String paymentStatus = null;
        try {
            JSONObject json = new JSONObject(body);
            orderCode = json.getString("orderCode");
            txId = json.getString("txId");
            token = json.getString("token");
            paymentStatus = json.getString("paymentStatus");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new BadRequestException(ErrorCodeMap.FAILURE_NULL_GRAB_PAID_SAVE.name());
        }
        Map<String, Object> rs = new HashMap<>();
        StatusResponse status = easyOrderService.saveOrderAfterPaidByGrab(orderCode, txId, token, paymentStatus);
        rs.put("status", HttpStatus.OK.value());
        rs.put("message", status.getValue());
        return ResponseEntity.ok(rs);
    }

    @GetMapping("/totalQuantity/{customerId}/{outletId}/{numberOfDay}")
    public Integer getTotalQuantityByUserAndOutlet(@PathVariable("customerId") Long customerId, @PathVariable("outletId") Long outletId, @PathVariable("numberOfDay") int numberOfDay) {
        return orderService.getTotalQuantityByUserAndOutlet(customerId, outletId, numberOfDay);
    }

    @GetMapping("/totalAmount/{customerId}/{outletId}/{numberOfDay}")
    public Double getTotalAmountByUserAndOutlet(@PathVariable("customerId") Long customerId, @PathVariable("outletId") Long outletId, @PathVariable("numberOfDay") int numberOfDay) {
        return orderService.getTotalAmountByUserAndOutlet(customerId, outletId, numberOfDay);
    }

    @GetMapping("/firstOrder/{customerId}/{outletId}")
    public Boolean userFirstTimeOrderInOutlet(@PathVariable("customerId") Long customerId, @PathVariable("outletId") Long outletId) {
        return orderService.userFirstTimeOrderInOutlet(customerId, outletId);
    }

    @GetMapping("/orders/{customerId}/{outletId}")
    public List<OrderOutletDTO> getOrderOutletByCustomerAndOutlet(@PathVariable("customerId") Long customerId, @PathVariable("outletId") Long outletId) {
        return orderService.getOrderOutletByCustomerAndOutlet(customerId, outletId);
    }

    @PostMapping("/service/shoppingCart/submit")
    public ResponseEntity<SubmitOrderResponseDTO> submitOrder(@RequestBody OrderInformationMobileDTO dto, @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(orderService.submitOrder4Mobile(dto, userId));
    }


    @PostMapping("/shoppingcart/updates")
    public ResponseEntity<Map<String, Object>> updateShoppingCart(@RequestBody ShoppingCartRequestBodyDTO dto) {
        try {
            for (SubmitShoppingCartDTO temp : dto.getItems()) {
                shoppingCartRepository.updateShopping(temp.getShoppingCartId(), dto.getTransactionID());
            }
            return new ResponseEntity(new HashMap<>(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(new HashMap<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Map<String, String>> getOrderByOrderId(@RequestParam("orderId") Long orderId) {
        Map<String, String> result = orderService.getOrderById(orderId);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * update to refund Grabpay
     *
     * @return
     */
    @RequestMapping("/update/grabtransaction")
    public ResponseEntity<Integer> updateGrabTransaction(@RequestParam("grabTxId") String grabTxId, @RequestParam("token") String token, @RequestParam("orderOutletId") Long orderOutletId) {
        try {
            return ResponseEntity.ok(orderService.updateGrabTransaction(grabTxId, token, orderOutletId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity checkoutEasyOrder(@RequestBody EasyOrderSubmitDTO easyOrderSubmitDTO) {
        easyOrderSubmitDTO.setSource(Constants.SOURCE_EASY_ORDER);
        return ResponseEntity.ok(easyOrderService.checkoutEasyOrder(easyOrderSubmitDTO));
    }

    @PostMapping("/calTemp")
    public ResponseEntity calculatePriceTemp(@RequestBody EasyOrderSubmitDTO easyOrderSubmitDTO) {
        return ResponseEntity.ok(easyOrderService.calculateTemporary(easyOrderSubmitDTO));
    }

    @GetMapping("/payments")
    public ResponseEntity getPaymentMethods(@RequestParam(value = "outletId") Long id,  HttpServletRequest request){
        return ResponseEntity.ok(easyOrderService.getPaymentMethods(id, request.getLocale()));
    }

    @PostMapping("/warehouse")
    public ResponseEntity checkAvailableProducts(@RequestBody List<ProductDTO> productDTOS){
        return ResponseEntity.ok(easyOrderService.checkAvailableProducts(productDTOS));
    }

    /**
     * update to create orderoutlet when paying by Grabpay on easyorder page
     *
     * @return
     */
    @RequestMapping("/update/paymentstatus")
    public ResponseEntity<Integer> updateGrabPaymentStatus(@RequestParam("paymentStatus") String paymentStatus, @RequestParam("orderOutletCode") String orderOutletCode) {
        try {
            return ResponseEntity.ok(orderService.updateGrabPaymentStatus(paymentStatus, orderOutletCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
