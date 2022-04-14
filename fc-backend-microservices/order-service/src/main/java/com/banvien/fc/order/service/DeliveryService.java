package com.banvien.fc.order.service;

import com.banvien.fc.common.util.GoogleLocationUtil;
import com.banvien.fc.order.dto.OutletDTO;
import com.banvien.fc.order.dto.ResponseDTO;
import com.banvien.fc.order.dto.ShoppingCartDTO;
import com.banvien.fc.order.dto.delivery.*;
import com.banvien.fc.order.dto.delivery.grabexpress.*;
import com.banvien.fc.order.dto.rules.Enum.ApiStatusCode;
import com.banvien.fc.order.dto.rules.Enum.Delivery;
import com.banvien.fc.order.entity.DeliveryServiceEntity;
import com.banvien.fc.order.entity.FeatureSettingCountryEntity;
import com.banvien.fc.order.entity.OutletEntity;
import com.banvien.fc.order.entity.ShoppingCartEntity;
import com.banvien.fc.order.repository.*;
import com.banvien.fc.order.utils.CoreConstants;
import com.banvien.fc.order.utils.DeliveryServiceUtils;
import com.banvien.fc.order.utils.OutletUtil;
import com.banvien.fc.order.utils.ShoppingCartBeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DeliveryService extends ApplicationObjectSupport {

    @Autowired
    private OutletRepository outletRepository;
    @Autowired
    private DeliveryServiceRepository deliveryServiceRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private AppSettingRepository appSettingRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private FeatureSettingCountryRepository featureSettingCountryRepository;

    public List<DeliveryServiceDTO> findAllForApiByOutletId(Long outletId, BigDecimal latitude, BigDecimal longitude) {
        Double distance = null;
        Optional<OutletEntity> outletEntity = outletRepository.findById(outletId);
        if (outletEntity.isPresent()) {
            if (latitude != null && longitude != null) {
                distance = GoogleLocationUtil.calculateDistanceFrom2Point(new LatLng(outletEntity.get().getLatitude().doubleValue(), outletEntity.get().getLongitude().doubleValue()), new LatLng(latitude.doubleValue(), longitude.doubleValue()));
//                if (distance == 0){
//                    throw new BadRequestException("");
//                }
            }
        }
        // get distinct delivery service. ex: grab, ahamove, ship_to_home, pick_n_go.
        List<DeliveryServiceEntity> deliveryDistinctEntities = deliveryServiceRepository.findByOutletIdOrderByOrderBy(outletId);

        // create new delivery service if not exists
        if (deliveryDistinctEntities.size() < (Delivery.values().length - 1)) {
            deliveryDistinctEntities = DeliveryServiceUtils.createDeliveryServiceDefault(deliveryServiceRepository, outletId, deliveryDistinctEntities);
        }

        List<DeliveryServiceDTO> deliveryServiceDTOList = new ArrayList<>();
        // calculate delivery price if distance not null.
        for (DeliveryServiceEntity entity : deliveryDistinctEntities) {
            if(!entity.getStatus()) continue;
            if (distance != null && (!CoreConstants.DELIVERY_PICK_N_GO.equals(entity.getCode()))) {
                List<DeliveryServiceEntity> deliveryServiceOrdered = deliveryServiceRepository.findByOutletIdAndCodeAndStatusOrderByOrderBy(outletId, entity.getCode(), true);
                entity = DeliveryServiceUtils.calculateDeliveryPrice(deliveryServiceOrdered, distance, entity);
            }
            DeliveryServiceDTO deliveryServiceDTO = DeliveryServiceUtils.entity2Dto(entity);
            deliveryServiceDTO.setDistanceService(entity.getDistance());
            deliveryServiceDTO.setDistance(distance != null ? distance : 0D);
            deliveryServiceDTOList.add(deliveryServiceDTO);
        }


        // workaround remove item to hide setting by country
        if(outletEntity != null) {
            Integer canDisplayGrabDelivery = getStatus("GRAB_DELIVERY", outletEntity.get().getCountryId());
            Integer canDisplayGetFromStore = getStatus("GET_FROM_STORE", outletEntity.get().getCountryId());

            // hide delivery setting by Country
            Iterator<DeliveryServiceDTO> deliveryServiceDTOIterator = deliveryServiceDTOList.iterator();
            while (deliveryServiceDTOIterator.hasNext()) {
                DeliveryServiceDTO e = deliveryServiceDTOIterator.next();
                if ((canDisplayGrabDelivery == 0 && e.getCode().equalsIgnoreCase(Delivery.SHIP_TO_HOME_EXPRESS.getValue()))
                        || (canDisplayGetFromStore == 0 && e.getCode().equalsIgnoreCase(Delivery.PICK_N_GO.getValue()))) {
                    deliveryServiceDTOIterator.remove();
                }
            }
        }

        // return list delivery service for api.
        return deliveryServiceDTOList;
    }

    /**
     * get settting by code and country
     * @param code
     * @param countryId
     * @return
     */
    public Integer getStatus(String code, Long countryId) {
        FeatureSettingCountryEntity featureSettingCountryEntities = featureSettingCountryRepository.findByCodeAndCountryId(code, countryId);
        if(featureSettingCountryEntities != null) {
            return featureSettingCountryEntities.getStatus();
        }
        return 0;
    }

    public Map<String, Object> getDeliveryAndPayment(EasyOrderRequestDTO easyOrderRequestDTO, Locale locale) {
        List<Double> location = GoogleLocationUtil.getCoordinate(easyOrderRequestDTO.getAddress());
        BigDecimal latitude = BigDecimal.valueOf(location.get(0));
        BigDecimal longitude = BigDecimal.valueOf(location.get(1));
        OutletEntity outletEntity = outletRepository.findById(easyOrderRequestDTO.getOutletId()).get();
        easyOrderRequestDTO.setDesLatitude(latitude);
        easyOrderRequestDTO.setDesLongitude(longitude);
        easyOrderRequestDTO.setSrcLatitude(outletEntity.getLatitude());
        easyOrderRequestDTO.setSrcLongitude(outletEntity.getLongitude());
        Map<String, Object> map = new HashMap<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> result = new HashMap<>();
            List<DeliveryDTO> deliveryDTOS;
//            List<PaymentMethodDTO> newPaymentMethodList;
            List<DeliveryServiceDTO> deliveryServiceDTOList = findAllForApiByOutletId(easyOrderRequestDTO.getOutletId(), latitude, longitude);
//            List<PaymentMethodDTO> paymentMethodList = paymentMethodRepository.findByOutletId(outletId).stream().map(entity -> {
//                return mapper.convertValue(entity, PaymentMethodDTO.class);
//            }).filter(dto -> {
//                return !dto.getCode().equals("GATEWAY") && !dto.getCode().equals("CREDIT") && !dto.getCode().equals("POINT");
//            }).collect(Collectors.toList());

            // convert DeliveryServiceDTO to DeliveryDTO
            deliveryDTOS = convert(deliveryServiceDTOList, locale, easyOrderRequestDTO);
//            newPaymentMethodList = convertPaymentReadBundleFile(paymentMethodList, locale);

            result.put("deliveryData", deliveryDTOS);
//            result.put("paymentData", newPaymentMethodList);
            result.put("constraintDistance", isConstraintDistance(CoreConstants.APP_SETTING.CONSTRAINT_DISTANCE.getValue(), easyOrderRequestDTO.getOutletId()));
            map.put("status", ApiStatusCode.SUCCESS.getValue());
            map.put("message", ApiStatusCode.SUCCESS.getKey());
            map.put("result", result);
        } catch (Exception e) {
            map.put("status", ApiStatusCode.NOT_FOUND.getValue());
            map.put("message", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    public List<PaymentMethodDTO> convertPaymentReadBundleFile(List<PaymentMethodDTO> paymentMethodList, Locale locale) {
        List<PaymentMethodDTO> newPaymentMethodList = new ArrayList<>();
        for (PaymentMethodDTO dto : paymentMethodList) {
            if (!dto.getStatus()) continue;
            if (locale != null) {
                dto.setTitle(this.getMessageSourceAccessor().getMessage(dto.getCode(), locale));
            } else {
                dto.setTitle(this.getMessageSourceAccessor().getMessage(dto.getCode()));
            }
            newPaymentMethodList.add(dto);
        }
        return newPaymentMethodList;
    }

    public List<DeliveryDTO> convert(List<DeliveryServiceDTO> deliveryServiceDTOList, Locale locale, EasyOrderRequestDTO easyOrderRequestDTO) {
        List<DeliveryDTO> deliveryDTOList = new ArrayList<>();
        Map<String, List<DeliveryServiceDTO>> deliveryMapByGroupCode = new LinkedHashMap<>();

        for (DeliveryServiceDTO deliveryServiceDTO : deliveryServiceDTOList) {
            if (!deliveryMapByGroupCode.containsKey(deliveryServiceDTO.getGroupCode())) {
                deliveryMapByGroupCode.put(deliveryServiceDTO.getGroupCode(), new ArrayList<DeliveryServiceDTO>());
            }
            deliveryMapByGroupCode.get(deliveryServiceDTO.getGroupCode()).add(deliveryServiceDTO);
        }
        Set<String> keysGroupCode = deliveryMapByGroupCode.keySet();
        for (String key : keysGroupCode) {
            List<DeliveryServiceDTO> deliveryServices = deliveryMapByGroupCode.get(key);
            List<ServiceItemDTO> serviceItems = new ArrayList<>();
            Map<String, List<DeliveryServiceDTO>> deliveryMapByCode = new LinkedHashMap<>();

            DeliveryDTO deliveryDTO = new DeliveryDTO();
            deliveryDTO.setCode(key);
            deliveryDTO.setTitle(this.getMessageSourceAccessor().getMessage(key, locale));
            for (DeliveryServiceDTO deliveryServiceDTO : deliveryServices) {
                if (!deliveryMapByCode.containsKey(deliveryServiceDTO.getCode())) {
                    deliveryMapByCode.put(deliveryServiceDTO.getCode(), new ArrayList<DeliveryServiceDTO>());
                }
                deliveryMapByCode.get(deliveryServiceDTO.getCode()).add(deliveryServiceDTO);
            }

            Set<String> keysCode = deliveryMapByCode.keySet();
            for (String keyCode : keysCode) {
                List<DeliveryServiceDTO> deliveryServiceDtoOnCode = deliveryMapByCode.get(keyCode);
                List<DeliveryPriceOnDistanceDTO> prices = new ArrayList<>();

                ServiceItemDTO serviceItem = new ServiceItemDTO();

                serviceItem.setCode(keyCode);
                serviceItem.setTitle(this.getMessageSourceAccessor().getMessage(keyCode, locale));
                serviceItem.setDeliveryServiceId(deliveryServiceDtoOnCode.get(0).getDeliveryServiceId());
                serviceItem.setStatus(deliveryServiceDtoOnCode.get(0).getStatus());
                serviceItem.setOrderBy(deliveryServiceDtoOnCode.get(0).getOrderBy());
                Double maxDistance = -1D;
                String mess = "";
                if("SHIP_TO_HOME_EXPRESS".equalsIgnoreCase(keyCode)) {
                    QuotesPlatformDTO quotesPlatformDTO = new QuotesPlatformDTO();
                    //quotesPlatformDTO.setCustomerId(null);
                    quotesPlatformDTO.setOutletId(easyOrderRequestDTO.getOutletId());
                    quotesPlatformDTO.setLatitude(easyOrderRequestDTO.getDesLatitude().doubleValue());
                    quotesPlatformDTO.setLongitude(easyOrderRequestDTO.getDesLongitude().doubleValue());
                    quotesPlatformDTO.setSrcLatitude(easyOrderRequestDTO.getSrcLatitude().doubleValue());
                    quotesPlatformDTO.setSrcLongitude(easyOrderRequestDTO.getSrcLongitude().doubleValue());
                    quotesPlatformDTO.setAddress(easyOrderRequestDTO.getAddress());
                    //quotesPlatformDTO.setPackages();
                    ResponseDTO<QuotesResponseDTO> obj = deliveryRepository.getQuotes(quotesPlatformDTO);
                    DeliveryPriceOnDistanceDTO priceOnDistanceDTO = new DeliveryPriceOnDistanceDTO();
                    if(obj.getStatus() == 200) {
                        priceOnDistanceDTO.setDeliveryServiceId(deliveryServiceDtoOnCode.get(0).getDeliveryServiceId());
                        priceOnDistanceDTO.setPrice(obj.getBody().getQuotes().get(0).getAmount().toString());
                        //priceOnDistanceDTO.setDistance(obj.getBody().getBody().getQuotes().get(0).getDistance().toString());
                    }
                    else {
                        mess = obj.getMessage();
                    }
                    prices.add(priceOnDistanceDTO);
                } else {
                    for (DeliveryServiceDTO deliveryServiceDTO : deliveryServiceDtoOnCode) {
                        DeliveryPriceOnDistanceDTO priceOnDistanceDTO = new DeliveryPriceOnDistanceDTO();
                        priceOnDistanceDTO.setDeliveryServiceId(deliveryServiceDTO.getDeliveryServiceId());
                        priceOnDistanceDTO.setDistance(deliveryServiceDTO.getDistance() != null ? deliveryServiceDTO.getDistance().toString() : "0");
                        if (deliveryServiceDTO.getDistanceService() != null) {
                            if (Double.valueOf(deliveryServiceDTO.getDistanceService()) > maxDistance && deliveryServiceDTO.getDistance() != null) {
                                maxDistance = Double.valueOf(deliveryServiceDTO.getDistanceService());
                            }
                        }
                        priceOnDistanceDTO.setPrice(deliveryServiceDTO.getPrice() != null ? deliveryServiceDTO.getPrice().toString() : "0");
                        prices.add(priceOnDistanceDTO);
                    }
                }
                serviceItem.setMessage(mess);
                serviceItem.setMaxDistance(maxDistance);
                serviceItem.setPrices(prices);
                serviceItem.setEstimateDays(deliveryServiceDtoOnCode.get(0).getEstimateDays());
                serviceItems.add(serviceItem);
            }
            deliveryDTO.setService(serviceItems);
            deliveryDTOList.add(deliveryDTO);
        }
        return deliveryDTOList;
    }

    public Boolean isConstraintDistance(String key, Long outletId) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            AppSettingDTO appSettingDTO = mapper.convertValue(appSettingRepository.findByKeyAndOutletIdAndStatus(key, outletId, true), AppSettingDTO.class);
            return appSettingDTO.getValue().equalsIgnoreCase("true");
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Find Shopping cart items for mobile call quotes API (Grab Express)
     *
     * @param customerId
     * @param device
     * @param outletId
     * @return
     */
    public List<ShoppingCartDTO> findCartItemsByCustomer(Long customerId, String device, Long outletId) throws Exception {
        List<ShoppingCartEntity> lstShoppingCart = shoppingCartRepository.findShoppingCartForCustomer(customerId != null ? customerId : -1L,outletId != null ? outletId : -1L);
        return ShoppingCartBeanUtils.entities2Dtos(lstShoppingCart);
    }

    /**
     * get Address of Outlet (Grab express)
     *
     * @param Id
     * @return
     */
    public OutletDTO findOutletById(Long Id) {
        OutletEntity outletEntity = outletRepository.getOne(Id);
        return OutletUtil.entity2DTO(outletEntity);
    }
}
