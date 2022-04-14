package com.banvien.fc.catalog.service;

import com.banvien.fc.catalog.dto.*;
import com.banvien.fc.catalog.entity.*;
import com.banvien.fc.catalog.repository.*;
import com.banvien.fc.common.enums.Delivery;
import com.banvien.fc.common.util.CommonUtils;
import com.banvien.fc.common.util.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
public class SalesFlowService {
    public JSONObject sendRequest(String uri, Map<String, Object> params) {
        try {
            String host = env.getProperty("salesflow.host", "https://integration.salesflo.com");
            Map<String, String> header = new HashMap<>();
            header.put("Content-Type", "application/x-www-form-urlencoded");
            header.put("AccessKey", env.getProperty("salesflow.AccessKey", "MTYwNTgxNjgzN19SWkR5T05pNmJwaXlqRmtvWDJraGVRPT0="));
            header.put("SecretKey", env.getProperty("salesflow.SecretKey", "UlpEeU9OaTZicGl5akZrb1gya2hlUT09"));
            header.put("X-API-KEY", env.getProperty("salesflow.X-API-KEY", "vOHr2X7EDe6QM3HYj6nj42zwhw1jTpFE1Tc9aGKr"));
            header.put("CompanyCode", env.getProperty("salesflow.CompanyCode", "efoods4"));
            HttpResponse<String> response = CommonUtils.sendPostRequest(host, uri, header, params);
            if (response != null) {
                SalesFlowHistoryEntity historyEntity = new SalesFlowHistoryEntity();
                historyEntity.setUri(uri);
                historyEntity.setStatus(response.getStatus());
                JSONObject masterData = null;
                if (StringUtils.isNotBlank(response.getBody())) {
                    masterData = new JSONObject(response.getBody());
                    if ("SUCCESS".equals(masterData.get("response"))) {
                        historyEntity.setResponse("{\"response\":\"SUCCESS\"}");
                    } else {
                        historyEntity.setResponse(response.getBody());
                    }
                } else {
                    historyEntity.setResponse("");
                }
                historyEntity.setSendTime(new Timestamp(System.currentTimeMillis()));
                ObjectMapper mapper = new ObjectMapper();
                historyEntity.setRequestBody(mapper.writeValueAsString(params));
                salesFlowHistoryRepository.save(historyEntity);
                return masterData;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateDistributorMasterData() {
        Map<String, Object> params = new HashMap<>();
        params.put("type","Distributors");
        JSONObject masterData = sendRequest("/master-data", params);
        if (masterData != null) {
            try {
                List<SaleChannelEntity> saleChannels = salesChannelRepository.findAll();
                if ("SUCCESS".equals(masterData.get("response"))) {
                    ObjectMapper mapper = new ObjectMapper();
                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                    CountryEntity country = countryRepository.findByCode("PK");
                    if (!masterData.isNull("data")) {
                        List<RawDistributorDTO> rawDistributorDTOS = mapper.readValue(masterData.get("data").toString(), new TypeReference<List<RawDistributorDTO>>(){});
//                        List<RawDistributorDTO> rawDistributorDTOS = rawDistributorDTOSS.subList(0, rawDistributorDTOSS.size() / 2);
                        Map<String, RawDistributorDTO> distributorCodeMap = new HashMap<>();
                        rawDistributorDTOS.forEach(item->{
                            if (StringUtils.isNotBlank(item.getDistributorCode())) {
                                distributorCodeMap.put(item.getDistributorCode(), item);
                            }
                            rawDistributorRepository.save(convertRawDistributor2Entity(item, currentTime));
                        });
                        if (!distributorCodeMap.isEmpty()) {
                            outletRepository.deActiveOutletNotInCode(country.getCountryId(), distributorCodeMap.keySet());
                            List<OutletEntity> currentOutlets = outletRepository.findByCodeListInAndCountryId(distributorCodeMap.keySet(), country.getCountryId());
                            for(OutletEntity outlet : currentOutlets) {
                                RawDistributorDTO dto = distributorCodeMap.get(outlet.getCode());
                                outlet.setName(dto.getDistributorName());
                                outlet.setPhoneNumber(dto.getDistrbutorPhone());
                                outlet.setAddress(dto.getDistributorAddress());
                                outlet.setModifiedDate(currentTime);
                                if (dto.getLongtitude() != null && dto.getLatitude() != null) {
                                    outlet.setLongitude(BigDecimal.valueOf(dto.getLongtitude()));
                                    outlet.setLatitude(BigDecimal.valueOf(dto.getLatitude()));
                                }
                                outlet.setStatus(dto.getStatus() == null || dto.getStatus() == 1);
                                outletRepository.save(outlet);
                                List<DeliveryServiceEntity> deliveryServices = deliveryServiceRepository.findByOutlet(outlet.getOutletId());
                                if (CollectionUtils.isEmpty(deliveryServices)) {
                                    DeliveryServiceEntity defaultDeliveryService = new DeliveryServiceEntity();
                                    defaultDeliveryService.setOutlet(outlet);
                                    defaultDeliveryService.setGroupCode(Delivery.PICK_N_GO.getValue());
                                    defaultDeliveryService.setCode(Delivery.PICK_N_GO.getValue());
                                    defaultDeliveryService.setTitle(Constants.DELIVERY_PICK_N_GO_TITLE);
                                    defaultDeliveryService.setPrice(0D);
                                    defaultDeliveryService.setStatus(true);
                                    defaultDeliveryService.setDistance(0D);
                                    defaultDeliveryService.setOrderBy(0);
                                    deliveryServiceRepository.save(defaultDeliveryService);
                                }
                                distributorCodeMap.remove(outlet.getCode());
                            }
                            for (RawDistributorDTO dto : distributorCodeMap.values()) {
                                UserDTO wholesalerDto = new UserDTO();
                                wholesalerDto.setFullPhoneNumber(dto.getDistrbutorPhone());
                                wholesalerDto.setFullName(dto.getDistributorName());
                                wholesalerDto.setCountryId(country.getCountryId());
                                wholesalerDto.setCode(dto.getDistributorCode());
                                ResponseEntity<UserDTO> wholesaler = accountRepository.addOrUpdateUser(wholesalerDto, country.getCountryId());
                                if (wholesaler.hasBody()) {
                                    OutletEntity outlet = new OutletEntity();
                                    outlet.setName(dto.getDistributorName());
                                    outlet.setPhoneNumber(dto.getDistrbutorPhone());
                                    outlet.setAddress(dto.getDistributorAddress());
                                    outlet.setModifiedDate(currentTime);
                                    outlet.setLongitude(BigDecimal.valueOf(dto.getLongtitude()));
                                    outlet.setLatitude(BigDecimal.valueOf(dto.getLatitude()));
                                    outlet.setStatus(dto.getStatus() == null || dto.getStatus() == 1);
                                    outlet.setShopManId(wholesaler.getBody().getUserId());
                                    outlet.setCountryId(country.getCountryId());
                                    if (StringUtils.isNotBlank(dto.getDistributorCode())) {
                                        outlet.setCode(dto.getDistributorCode());
                                    } else {
                                        Object maxNum = outletRepository.findMaxWSCode(country.getCountryId());
                                        outlet.setCode(String.format(country.getCode() + "WS%05d", (maxNum != null ? Integer.parseInt(maxNum.toString()) : 0) + 1));
                                    }
                                    outlet.setCreatedby(1L);
                                    outlet.setCreatedDate(currentTime);
                                    outletRepository.save(outlet);
                                    for (SaleChannelEntity saleChannel : saleChannels) {
                                        OutletSaleChannelEntity outletSaleChannel = new OutletSaleChannelEntity();
                                        outletSaleChannel.setOutlet(outlet);
                                        outletSaleChannel.setSaleChannel(saleChannel);
                                        outletSaleChannel.setStatus(1);
                                        outletSaleChannelRepository.save(outletSaleChannel);
                                    }
                                    DeliveryServiceEntity defaultDeliveryService = new DeliveryServiceEntity();
                                    defaultDeliveryService.setOutlet(outlet);
                                    defaultDeliveryService.setGroupCode(Delivery.PICK_N_GO.getValue());
                                    defaultDeliveryService.setCode(Delivery.PICK_N_GO.getValue());
                                    defaultDeliveryService.setTitle(Constants.DELIVERY_PICK_N_GO_TITLE);
                                    defaultDeliveryService.setPrice(0D);
                                    defaultDeliveryService.setStatus(true);
                                    defaultDeliveryService.setDistance(0D);
                                    defaultDeliveryService.setOrderBy(0);
                                    deliveryServiceRepository.save(defaultDeliveryService);
                                }
                            }
                            outletRepository.flush();
                            outletSaleChannelRepository.flush();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void updateStoreMasterData() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("type","Stores");
        JSONObject masterData = sendRequest("/master-data", params);
        if (masterData != null) {
            if ("SUCCESS".equals(masterData.get("response"))) {
                if (!masterData.isNull("data")) {
                    CountryEntity country = countryRepository.findByCode("PK");
                    Map<String, String> bodyContent = new HashMap<>();
                    bodyContent.put("data", masterData.getString("data"));
                    try {
                        accountRepository.updateSalesFlowCustomers(bodyContent, country.getCountryId());
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
    }

    public void updateProductMasterData() {
        Map<String, Object> params = new HashMap<>();
        params.put("type","Products");
        JSONObject masterData = sendRequest("/master-data", params);
        if (masterData != null) {
            try {
                if ("SUCCESS".equals(masterData.get("response"))) {
                    Map<String, CatGroupEntity> catGroupMap = new HashMap<>();
                    Map<String, BrandEntity> brandMap = new HashMap<>();
                    ObjectMapper mapper = new ObjectMapper();
                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                    CountryEntity country = countryRepository.findByCode("PK");
                    if (!masterData.isNull("Brands")) {
                        List<RawBrandDTO> rawBrandDTOS = mapper.readValue(masterData.get("Brands").toString(), new TypeReference<List<RawBrandDTO>>(){});
                        if (CollectionUtils.isNotEmpty(rawBrandDTOS)) {
                            updateBrandData(rawBrandDTOS, country, currentTime, brandMap);
                        }
                    }
                    if (!masterData.isNull("Categories")) {
                        List<RawCategoryDTO> saleFloCatGroups = mapper.readValue(masterData.get("Categories").toString(), new TypeReference<List<RawCategoryDTO>>(){});
                        if (CollectionUtils.isNotEmpty(saleFloCatGroups)) {
                            updateCatGroupData(saleFloCatGroups, country, currentTime, catGroupMap);
                        }
                    }
                    if (!masterData.isNull("Products")) {
                        List<RawProductDTO> rawProductDTOS = mapper.readValue(masterData.get("Products").toString(), new TypeReference<List<RawProductDTO>>(){});
                        if (CollectionUtils.isNotEmpty(rawProductDTOS)) {
//                            List<RawProductDTO> rawProductDTOS = rawProductDTOSS.subList(0, rawProductDTOSS.size() / 2);
                            updateProductData(rawProductDTOS, country, currentTime, brandMap, catGroupMap);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void updateBrandData(List<RawBrandDTO> rawBrandDTOS, CountryEntity country, Timestamp currentTime, Map<String, BrandEntity> brandMap) {
        Map<String, RawBrandDTO> priorityBrandMap = new HashMap<>();
        rawBrandDTOS.forEach(item->{
            if (StringUtils.isNotBlank(item.getBrandName())) {
                priorityBrandMap.put(item.getBrandName(), item);
            }
            rawBrandRepository.save(new RawBrandEntity(item.getBrandName(), item.getBrandPriority(), currentTime));
        });
//      brandRepository.deleteBrandNotInNames(country.getCountryId(), priorityBrandMap.keySet());
        if (!priorityBrandMap.isEmpty()) {
            List<BrandEntity> brandEntities = brandRepository.findByNameListAndCountryId(priorityBrandMap.keySet(), country.getCountryId());
            for (BrandEntity brandItem : brandEntities ) {
                brandItem.setTop(priorityBrandMap.get(brandItem.getName()).getBrandPriority());
                brandItem.setModifiedDate(currentTime);
                brandRepository.save(brandItem);
                brandMap.put(brandItem.getName(), brandItem);
                priorityBrandMap.remove(brandItem.getName());
            }
            for (RawBrandDTO item : priorityBrandMap.values()) {
                BrandEntity newBrand = new BrandEntity();
                newBrand.setTop(item.getBrandPriority());
                newBrand.setName(item.getBrandName());
                newBrand.setCountryId(country.getCountryId());
                newBrand.setCreatedDate(currentTime);
                newBrand.setImage("");
                brandRepository.save(newBrand);
                brandMap.put(newBrand.getName(), newBrand);
            }
            brandRepository.flush();
        }
    }

    void updateCatGroupData(List<RawCategoryDTO> saleFloCatGroups, CountryEntity country, Timestamp currentTime, Map<String, CatGroupEntity> catGroupMap) {
        Map<String, RawCategoryDTO> catGroupDTOMap = new HashMap<>();
        saleFloCatGroups.forEach(item->{
            if (StringUtils.isNotBlank(item.getCategoryCode())) {
                catGroupDTOMap.put(item.getCategoryCode(), item);
            }
            rawCategoryRepository.save(new RawCategoryEntity(item.getCategoryCode(), item.getCategoryName(), item.getDescription(), currentTime));
        });
        if (!catGroupDTOMap.isEmpty()) {
            categoryRepository.deActiveCategoryNotInCodes(country.getCountryId(), catGroupDTOMap.keySet());
            List<CatGroupEntity> categoryList = categoryRepository.findByCodeListAndCountryId(catGroupDTOMap.keySet(), country.getCountryId());
            List<OutletEntity> outletList = outletRepository.findByCountryId(country.getCountryId());
            List<SaleChannelEntity> saleChannels = salesChannelRepository.findAll();
            for (CatGroupEntity catGroupEntity : categoryList) {
                RawCategoryDTO targetItem = catGroupDTOMap.get(catGroupEntity.getCode());
                catGroupEntity.setName(targetItem.getCategoryName());
                catGroupEntity.setShortDescription(targetItem.getDescription());
                catGroupEntity.setModifiedDate(currentTime);
                catGroupEntity.setActive(true);
                categoryRepository.save(catGroupEntity);
                catGroupMap.put(catGroupEntity.getCode(), catGroupEntity);
                catGroupDTOMap.remove(catGroupEntity.getCode());
                for (OutletEntity outlet : outletList) {
                    List<CatGroupOutletEntity> catGroupOutlets = catGroupOutletRepository.findByCatGroupAndOutlet(catGroupEntity.getCatgroupId(), outlet.getOutletId());
                    if (CollectionUtils.isEmpty(catGroupOutlets)) {
                        CatGroupOutletEntity catGroupOutlet = new CatGroupOutletEntity();
                        catGroupOutlet.setCatGroup(catGroupEntity);
                        catGroupOutlet.setOutlet(outlet);
                        catGroupOutlet.setCreatedDate(currentTime);
                        catGroupOutlet.setCreatedBy(1L);
                        catGroupOutlet.setDisplayOrder(1);
                        catGroupOutletRepository.save(catGroupOutlet);
                        for (SaleChannelEntity saleChannel : saleChannels) {
                            CatgroupOutletSaleChannelEntity catGroupOutletSaleChannel = new CatgroupOutletSaleChannelEntity(catGroupOutlet.getCatGroupOutletId(), saleChannel.getSaleChannelId());
                            catGroupOutletSaleChannelRepository.save(catGroupOutletSaleChannel);
                        }
                    }
                }
            }
            for (RawCategoryDTO item : catGroupDTOMap.values()) {
                CatGroupEntity catGroupEntity = new CatGroupEntity();
                catGroupEntity.setCountryId(country.getCountryId());
                catGroupEntity.setName(item.getCategoryName());
                catGroupEntity.setCode(item.getCategoryCode());
                catGroupEntity.setShortDescription(item.getDescription());
                catGroupEntity.setCreatedDate(currentTime);
                catGroupEntity.setActive(true);
                categoryRepository.save(catGroupEntity);
                catGroupMap.put(catGroupEntity.getCode(), catGroupEntity);

                for (OutletEntity outlet : outletList) {
                    CatGroupOutletEntity catGroupOutlet = new CatGroupOutletEntity();
                    catGroupOutlet.setCatGroup(catGroupEntity);
                    catGroupOutlet.setOutlet(outlet);
                    catGroupOutlet.setCreatedDate(currentTime);
                    catGroupOutlet.setCreatedBy(1L);
                    catGroupOutlet.setDisplayOrder(1);
                    catGroupOutletRepository.save(catGroupOutlet);
                    for (SaleChannelEntity saleChannel : saleChannels) {
                        CatgroupOutletSaleChannelEntity catGroupOutletSaleChannel = new CatgroupOutletSaleChannelEntity(catGroupOutlet.getCatGroupOutletId(), saleChannel.getSaleChannelId());
                        catGroupOutletSaleChannelRepository.save(catGroupOutletSaleChannel);
                    }
                }
            }
            categoryRepository.flush();
        }
    }

    void updateProductData(List<RawProductDTO> rawProductDTOS, CountryEntity country, Timestamp currentTime, Map<String, BrandEntity> brandMap,  Map<String, CatGroupEntity> catGroupMap) {
        Map<String, RawProductDTO> productDTOMap = new HashMap<>();
        Map<String, ProductOutletEntity> productOutletMap = new HashMap<>();
        rawProductDTOS.forEach(item->{
            if (StringUtils.isNotBlank(item.getProductCode())) {
                productDTOMap.put(item.getProductCode(), item);
            }
            rawProductRepository.save(convertRawProduct2Entity(item, currentTime));
        });
        productRepository.deActiveProductByCountryId(country.getCountryId(), currentTime);
        productOutletRepository.deActiveProductOutletByCountryId(country.getCountryId(), currentTime);

        List<ProductOutletEntity> productOutlets = productOutletRepository.findByOutletCountry(country.getCountryId());
        productOutlets.forEach(i->productOutletMap.put(String.format("%s_%s", i.getCode(), i.getOutletId().toString()), i));

        List<OutletEntity> outletList = outletRepository.findByCountryId(country.getCountryId());
        //TODO:
        List<ProductEntity> productList = productRepository.findByCodeList(productDTOMap.keySet());

        for (ProductEntity oldProduct : productList) {
            if (productDTOMap.containsKey(oldProduct.getCode())) {
                RawProductDTO rawProductDTO = productDTOMap.get(oldProduct.getCode());
                oldProduct.setName(rawProductDTO.getProductName());
                oldProduct.setModifiedDate(currentTime);
                oldProduct.setActive(rawProductDTO.getStatus() == null || rawProductDTO.getStatus() == 1);
                if (catGroupMap.containsKey(rawProductDTO.getCategoryCode())) {
                    oldProduct.setCatGroupId(catGroupMap.get(rawProductDTO.getCategoryCode()).getCatgroupId());
                }
                if (brandMap.containsKey(rawProductDTO.getBrandName())) {
                    oldProduct.setBrandId(brandMap.get(rawProductDTO.getBrandName()).getBrandId());
                }
                Set<ProductSkuEntity> productSkus = oldProduct.getProductSkus();
                if (CollectionUtils.isEmpty(productSkus)) {
                    addProductSkus(oldProduct, rawProductDTO);
                } if (productSkus.size() != 2) {
                    productSkuRepository.deleteAll(productSkus);
                    addProductSkus(oldProduct, rawProductDTO);
                } else {
                    boolean isFirst = true;
                    for (ProductSkuEntity productSku : productSkus) {
                        if (isFirst) {
                            productSku.setTitle("UNIT (1 PACK)");
                            productSku.setSalePrice(rawProductDTO.getSalesPrice());
                            productSku.setOriginalPrice(rawProductDTO.getCostPrice());
                            productSku.setDisplayOrder(0);
                            productSku.setSkuCode(rawProductDTO.getProductCode()+"_1");
                            isFirst = false;
                        } else {
                            productSku.setTitle(String.format("CARTON (%s PACK)", rawProductDTO.getUnitsInCarton()));
                            productSku.setDisplayOrder(1);
                            productSku.setSalePrice(rawProductDTO.getPricePerCarton());
                            productSku.setOriginalPrice(rawProductDTO.getPricePerCarton());
                            productSku.setSkuCode(rawProductDTO.getProductCode()+"_2");
                        }
                        productSku.setWeight(0D);
                        productSku.setUnit(rawProductDTO.getUnitOfMeasure());
                        productSkuRepository.save(productSku);
                    }
                }

                for (OutletEntity outlet : outletList) {
                    if (productOutletMap.containsKey(String.format("%s_%s", oldProduct.getCode(), outlet.getOutletId().toString()))) {
                        ProductOutletEntity productOutlet = productOutletMap.get(String.format("%s_%s", oldProduct.getCode(), outlet.getOutletId().toString()));
                        productOutlet.setName(oldProduct.getName());
                        productOutlet.setStatus(rawProductDTO.getStatus());
                        productOutlet.setModifiedDate(currentTime);
                        productOutletRepository.save(productOutlet);
                        Set<ProductOutletSkuEntity> productOutletSkus = productOutlet.getProductOutletSkus();
                        if (CollectionUtils.isEmpty(productOutletSkus)) {
                            addProductOutletSkus(productOutlet, rawProductDTO);
                        } if (productOutletSkus.size() != 2) {
                            productOutletSkuRepository.deleteAll(productOutletSkus);
                            addProductOutletSkus(productOutlet, rawProductDTO);
                        } else {
                            boolean isFirst = true;
                            for (ProductOutletSkuEntity productOutletSku : productOutletSkus) {
                                if (isFirst) {
                                    productOutletSku.setTitle("UNIT (1 PACK)");
                                    productOutletSku.setSalePrice(rawProductDTO.getSalesPrice());
                                    productOutletSku.setOriginalPrice(rawProductDTO.getCostPrice());
                                    productOutletSku.setDisplayOrder(0);
                                    productOutletSku.setSkuCode(rawProductDTO.getProductCode()+"_1");
                                    isFirst = false;

                                } else {
                                    productOutletSku.setTitle(String.format("CARTON (%s PACK)", rawProductDTO.getUnitsInCarton()));
                                    productOutletSku.setDisplayOrder(1);
                                    productOutletSku.setSalePrice(rawProductDTO.getPricePerCarton());
                                    productOutletSku.setOriginalPrice(rawProductDTO.getPricePerCarton());
                                    productOutletSku.setSkuCode(rawProductDTO.getProductCode()+"_2");
                                }
                                productOutletSku.setStatus(rawProductDTO.getStatus());
                                productOutletSku.setWeight(0D);
                                productOutletSku.setUnit(rawProductDTO.getUnitOfMeasure());
                                productOutletSkuRepository.save(productOutletSku);
                            }
                        }
                        List<ProductOutletSaleChannelEntity> productOutletSaleChannels = productOutletSaleChannelRepository.findByProductOutlet(productOutlet.getProductOutletId());
                        if (CollectionUtils.isEmpty(productOutletSaleChannels)) {
                            OutletSaleChannelEntity outletSaleChannel = outletSaleChannelRepository.findByOutletAndSaleChannel(outlet.getOutletId(), "ONLINE");
                            ProductOutletSaleChannelEntity productOutletSaleChannel = new ProductOutletSaleChannelEntity(productOutlet, outletSaleChannel);
                            productOutletSaleChannelRepository.save(productOutletSaleChannel);
                        }

                    } else {
                        addProductOutletAndOutletSkus(oldProduct, outlet, rawProductDTO, currentTime);
                    }
                }
                productDTOMap.remove(oldProduct.getCode());
            }
        }
        productRepository.saveAll(productList);

        for (RawProductDTO item : productDTOMap.values()) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setCatGroupId(country.getCountryId());
            productEntity.setName(item.getProductName());
            productEntity.setCode(item.getProductCode());
            productEntity.setCreatedDate(currentTime);
            productEntity.setActive(item.getStatus() == null || item.getStatus() == 1);
            //default admin id
            productEntity.setCreatedBy(1);
            if (catGroupMap.containsKey(item.getCategoryCode())) {
                productEntity.setCatGroupId(catGroupMap.get(item.getCategoryCode()).getCatgroupId());
            }
            if (brandMap.containsKey(item.getBrandName())) {
                productEntity.setBrandId(brandMap.get(item.getBrandName()).getBrandId());
            }
            productRepository.save(productEntity);
            productList.add(productEntity);
            addProductSkus(productEntity, item);
            for (OutletEntity outlet : outletList) {
                addProductOutletAndOutletSkus(productEntity, outlet, item, currentTime);
            }
        }
    }

    private void addProductOutletAndOutletSkus(ProductEntity productEntity, OutletEntity outlet, RawProductDTO rawProductDTO ,Timestamp currentTime){
        ProductOutletEntity productOutlet = new ProductOutletEntity();
        productOutlet.setCode(productEntity.getCode());
        productOutlet.setName(productEntity.getName());
        productOutlet.setOutletId(outlet.getOutletId());
        productOutlet.setProductId(productEntity.getProductId());
        productOutlet.setStatus(rawProductDTO.getStatus());
        productOutlet.setCreatedDate(currentTime);
        productOutlet.setTotalView(0);
        productOutlet.setCreatedBy(1);
        productOutletRepository.save(productOutlet);
        OutletSaleChannelEntity outletSaleChannel = outletSaleChannelRepository.findByOutletAndSaleChannel(outlet.getOutletId(), "ONLINE");
        ProductOutletSaleChannelEntity productOutletSaleChannel = new ProductOutletSaleChannelEntity(productOutlet, outletSaleChannel);
        productOutletSaleChannelRepository.save(productOutletSaleChannel);
        addProductOutletSkus(productOutlet, rawProductDTO);
    }
    private void addProductOutletSkus(ProductOutletEntity productOutlet, RawProductDTO rawProductDTO){
        ProductOutletSkuEntity unitSku = new ProductOutletSkuEntity();
        ProductOutletSkuEntity cartonSku = new ProductOutletSkuEntity();

        unitSku.setProductOutletId(productOutlet.getProductOutletId());
        unitSku.setTitle("UNIT (1 PACK)");
        unitSku.setDisplayOrder(0);
        unitSku.setSalePrice(rawProductDTO.getSalesPrice());
        unitSku.setOriginalPrice(rawProductDTO.getCostPrice());
        unitSku.setStatus(rawProductDTO.getStatus());
        unitSku.setSkuCode(rawProductDTO.getProductCode()+"_1");
        unitSku.setWeight(0D);
        unitSku.setUnit(rawProductDTO.getUnitOfMeasure());

        cartonSku.setProductOutletId(productOutlet.getProductOutletId());
        cartonSku.setTitle(String.format("CARTON (%s PACK)", rawProductDTO.getUnitsInCarton()));
        cartonSku.setDisplayOrder(1);
        cartonSku.setSalePrice(rawProductDTO.getPricePerCarton());
        cartonSku.setOriginalPrice(rawProductDTO.getPricePerCarton());
        cartonSku.setStatus(rawProductDTO.getStatus());
        cartonSku.setSkuCode(rawProductDTO.getProductCode()+"_2");
        cartonSku.setWeight(0D);
        cartonSku.setUnit(rawProductDTO.getUnitOfMeasure());
        productOutletSkuRepository.saveAll(Arrays.asList(unitSku, cartonSku));
    }

    private void addProductSkus(ProductEntity product, RawProductDTO rawProductDTO){
        ProductSkuEntity unitSku = new ProductSkuEntity();
        ProductSkuEntity cartonSku = new ProductSkuEntity();

        unitSku.setProduct(product);
        unitSku.setTitle("UNIT (1 PACK)");
        unitSku.setDisplayOrder(0);
        unitSku.setSalePrice(rawProductDTO.getSalesPrice());
        unitSku.setOriginalPrice(rawProductDTO.getCostPrice());
        unitSku.setSkuCode(rawProductDTO.getProductCode()+"_1");
        unitSku.setWeight(0D);
        unitSku.setUnit(rawProductDTO.getUnitOfMeasure());

        cartonSku.setProduct(product);
        cartonSku.setTitle(String.format("CARTON (%s PACK)", rawProductDTO.getUnitsInCarton()));
        cartonSku.setDisplayOrder(1);
        cartonSku.setSalePrice(rawProductDTO.getPricePerCarton());
        cartonSku.setOriginalPrice(rawProductDTO.getPricePerCarton());
        cartonSku.setSkuCode(rawProductDTO.getProductCode()+"_2");
        cartonSku.setWeight(0D);
        cartonSku.setUnit(rawProductDTO.getUnitOfMeasure());
        productSkuRepository.saveAll(Arrays.asList(unitSku, cartonSku));
    }


    private RawProductEntity convertRawProduct2Entity (RawProductDTO rawProductDTO, Timestamp currentTime) {
        RawProductEntity rawProduct = new RawProductEntity();
        rawProduct.setBrandName(rawProductDTO.getBrandName());
        rawProduct.setBarCode(rawProductDTO.getBarCode());
        rawProduct.setCategoryCode(rawProductDTO.getCategoryCode());
        rawProduct.setCategoryName(rawProductDTO.getCategoryName());
        rawProduct.setCostPrice(rawProductDTO.getCostPrice());
        rawProduct.setSalesPrice(rawProductDTO.getSalesPrice());
        rawProduct.setPricePerCarton(rawProductDTO.getPricePerCarton());
        rawProduct.setStatus(rawProductDTO.getStatus());
        rawProduct.setUnitOfMeasure(rawProductDTO.getUnitOfMeasure());
        rawProduct.setUnitsInCarton(rawProductDTO.getUnitsInCarton());
        rawProduct.setUnitsInVirtualPack(rawProductDTO.getUnitsInVirtualPack());
        rawProduct.setCreatedDate(currentTime);
        rawProduct.setProductCode(rawProductDTO.getProductCode());
        rawProduct.setProductName(rawProductDTO.getProductName());
        return rawProduct;
    }

    private RawDistributorEntity convertRawDistributor2Entity (RawDistributorDTO rawDistributorDTO, Timestamp currentTime) {
        RawDistributorEntity rawDistributor = new RawDistributorEntity();
        rawDistributor.setDistributorAddress(rawDistributorDTO.getDistributorAddress());
        rawDistributor.setDistributorCode(rawDistributorDTO.getDistributorCode());
        rawDistributor.setDistributorName(rawDistributorDTO.getDistributorName());
        rawDistributor.setDistributorPhone(rawDistributorDTO.getDistrbutorPhone());
        rawDistributor.setLatitude(rawDistributorDTO.getLatitude());
        rawDistributor.setLongitude(rawDistributorDTO.getLongtitude());
        rawDistributor.setStatus(rawDistributorDTO.getStatus());
        rawDistributor.setCreatedDate(currentTime);
        return rawDistributor;
    }

    @Autowired
    private Environment env;
    @Autowired
    private SalesFlowHistoryRepository salesFlowHistoryRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductOutletRepository productOutletRepository;
    @Autowired
    private ProductOutletSkuRepository productOutletSkuRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OutletRepository outletRepository;
    @Autowired
    private RawCategoryRepository rawCategoryRepository;
    @Autowired
    private RawBrandRepository rawBrandRepository;
    @Autowired
    private RawProductRepository rawProductRepository;
    @Autowired
    private RawDistributorRepository rawDistributorRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SalesChannelRepository salesChannelRepository;
    @Autowired
    private CatGroupOutletRepository catGroupOutletRepository;
    @Autowired
    private OutletSaleChannelRepository outletSaleChannelRepository;
    @Autowired
    private ProductOutletSaleChannelRepository productOutletSaleChannelRepository;
    @Autowired
    private CatGroupOutletSaleChannelRepository catGroupOutletSaleChannelRepository;
    @Autowired
    private ProductSkuRepository productSkuRepository;
    @Autowired
    private DeliveryServiceRepository deliveryServiceRepository;
}
