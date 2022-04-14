package com.banvien.fc.promotion.service;

import com.banvien.fc.common.util.Constants;
import com.banvien.fc.promotion.dto.*;
import com.banvien.fc.promotion.dto.rules.Enum.PromotionType;
import com.banvien.fc.promotion.dto.vansale.QuickSellCategoryDTO;
import com.banvien.fc.promotion.dto.vansale.QuickSellPromotionDTO;
import com.banvien.fc.promotion.dto.vansale.SubCatDTO;
import com.banvien.fc.promotion.dto.vansale.VansaleProductDTO;
import com.banvien.fc.promotion.entity.*;
import com.banvien.fc.promotion.repository.*;
import com.banvien.fc.promotion.utils.ProductOutletSkuBeanUtils;
import com.banvien.fc.promotion.utils.PromotionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VansaleService {

    @Autowired
    private OutletEmployeeRepository outletEmployeeRepository;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private OutletPromotionRepository outletPromotionRepository;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SyncVersionTrackingRepository syncVersionTrackingRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoyaltyMemberRepository loyaltyMemberRepository;
    @Autowired
    private CustomerGroupRepository customerGroupRepository;

    public List<ProductOutletSkuDTO> getAllProducts4QuickSell(Long warehouseId) {
        //get all product in warehouse vans sale have available
        return ProductOutletSkuBeanUtils.entity2DTO(promotionService.getProductsAvailableVansale(warehouseId, null));
    }

    public List<ProductOutletSkuDTO> getAllProductHavePromotion4QuickSell(Long warehouseId) {
        QuickSellPromotionDTO quickSellPromotionDTO = promotionService.getPromotionDataForQuickSell(warehouseId, null);
        return quickSellPromotionDTO.getProductsWithPromotion();
    }

    public List<OutletPromotionDTO> getAllPromotion4Vansale(Long warehouseId) {
        QuickSellPromotionDTO quickSellPromotionDTO = promotionService.getPromotionDataForQuickSell(warehouseId, null);
        return quickSellPromotionDTO.getHotPromotions();
    }

    public QuickSellCategoryDTO getAllCatAndSubcat(Long warehouseId) {
        QuickSellCategoryDTO quickSellCategoryDTO = promotionService.getProductDataForQuickSell(warehouseId, null);
        return quickSellCategoryDTO;
    }

    public VansaleProductDTO getProductsByCategory(Long warehouseId, Long limit) {
        VansaleProductDTO vansaleProductDTO = new VansaleProductDTO();
        Set<ViewItemDTO> category = new HashSet<>();
        List<SubCatDTO> subCatDTOs = new ArrayList<>();
        List<ProductOutletSkuEntity> productOutletSkuEntities = promotionService.getProductsAvailableVansale(warehouseId, null);
        for (ProductOutletSkuEntity entity : productOutletSkuEntities) {
            ProductDTO productDTO = catalogRepository.getProductByProductOutletSkuId(entity.getProductOutletSkuId());
            if (!Objects.isNull(productDTO)) {
                CatGroupEntity catGroupEntity = categoryRepository.findByCatGroupId(productDTO.getCatGroupId());
                ViewItemDTO viewItemDTO = new ViewItemDTO();

                //subcategory
                if (!Objects.isNull(catGroupEntity.getParent())) {
                    SubCatDTO subCatDTO = new SubCatDTO();
                    subCatDTO.setCatGroupId(catGroupEntity.getCatGroupId());
                    subCatDTO.setCode(catGroupEntity.getCode());
                    subCatDTO.setImage(catGroupEntity.getImage());
                    subCatDTO.setName(catGroupEntity.getName());

                    //add subcat
                    if (subCatDTOs.size() > 0) {
                        //check exits subcat in list
                        if (subCatDTOs.contains(subCatDTO)) {
                            //if exits : add product into list of subcat
                            subCatDTOs.get(subCatDTOs.indexOf(subCatDTO)).getProducts().add(ProductOutletSkuBeanUtils.entity2DTO(entity));
                        } else {
                            //if not exits : add new subcat with product
                            subCatDTO.getProducts().add(ProductOutletSkuBeanUtils.entity2DTO(entity));
                            subCatDTOs.add(subCatDTO);
                        }
                    } else {
                        subCatDTO.getProducts().add(ProductOutletSkuBeanUtils.entity2DTO(entity));
                        subCatDTOs.add(subCatDTO);
                    }
                    //add category
                    viewItemDTO.setName(catGroupEntity.getParent().getName());
                    viewItemDTO.setId(catGroupEntity.getParent().getCatGroupId());
                    viewItemDTO.setImage(catGroupEntity.getParent().getImage());
                } else {
                    //add category
                    viewItemDTO.setName(catGroupEntity.getName());
                    viewItemDTO.setId(catGroupEntity.getCatGroupId());
                    viewItemDTO.setImage(catGroupEntity.getImage());
                }
                category.add(viewItemDTO);
            }
        }
        vansaleProductDTO.setSubCat(subCatDTOs.stream().limit(limit == null ? Long.MAX_VALUE : limit).collect(Collectors.toList()));
        vansaleProductDTO.setCategory(new ArrayList<>(category).stream().limit(limit == null ? Long.MAX_VALUE : limit).collect(Collectors.toList()));
        return vansaleProductDTO;
    }

    /**
     * SYNC PROMOTION FOR VANSALE APP OFFLINE MODE
     *
     * @param userId
     * @param token
     * @param pageable
     * @param warehouseId
     * @return
     */
    public Page syncPromotionInWareHouse(Long userId, String token, Pageable pageable, Long warehouseId) {
        Long outletId = outletEmployeeRepository.findByUserId(userId).getOutletId();
        Timestamp time;
        try {
            time = syncVersionTrackingRepository.findByUserIdAndTypeSyncAndToken(userId, Constants.SYNC_PROMOTION_VANSALE, token).getSyncDate();
        } catch (Exception ex) {
            time = new Timestamp(0);
        }

        //list promotion sync (list all by outlet)
        List<OutletPromotionEntity> outletPromotionEntities = outletPromotionRepository.findAllByOutletOutletIdAndModifiedDateAfterAndEasyOrderCodeIsNullAndPenetrationType(outletId, time, 0);

        //filter promotion in warehouse
        List<OutletPromotionDTO> promotionAvailableInWarehouse = this.promotionsAvailableInWareHouse(outletPromotionEntities, warehouseId, outletId);
        return this.customPagination(promotionAvailableInWarehouse, pageable);
    }

    public Page customPagination(List lstObjects, Pageable pageable) {
        Page result;
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), lstObjects.size());
        try {
            result = new PageImpl(lstObjects.subList(start, end), pageable, lstObjects.size());
        } catch (Exception ex) {
            return new PageImpl<>(new ArrayList<>());
        }
        return result;
    }


    /**
     * PROMOTIONS OF PRODUCT IN WAREHOUSE
     *
     * @param outletPromotionEntities
     * @param warehouseId
     * @return
     */
    public List<OutletPromotionDTO> promotionsAvailableInWareHouse(List<OutletPromotionEntity> outletPromotionEntities, Long warehouseId, Long outletId) {
        List<OutletPromotionDTO> promotionsOfOutlet = new ArrayList<>();       //promotion of product in warehouse

        //get list product in warehouse vans sale have available
        List<ProductOutletSkuEntity> productAvailable = promotionService.getProductsAvailableVansale(warehouseId, null);

        //checkTypePromotion before filter (not filter Order,Shipping Discount)
        for (OutletPromotionEntity outletPromotionEntity : outletPromotionEntities) {
            try {
                JSONObject jsonObject = new JSONObject(outletPromotionEntity.getNewPromotionJson());

                //check applid is All customer for vansale app
                JSONObject rs = (JSONObject) jsonObject.get("promotionRule");

                if ((Boolean) rs.get("isAll") == null || (Boolean) rs.get("isAll") == false) {
                    continue;                   //not applied for all customer
                }

                int typePromotion = jsonObject.getInt("type");
                //order,shipping promotion
                if (typePromotion == PromotionType.SHIPPING.getNo()) {
                    //return opder/shipping
                    promotionsOfOutlet.add(PromotionUtils.entity2DtoForVansales(outletPromotionEntity, outletPromotionEntity.getMaxPerPromotion() - outletPromotionEntity.getRemainGift()));
                }
            } catch (Exception ex) {
            }
        }

        //get list promotion with type
        List<OutletPromotionDTO> outletPromotionDTOS = promotionService.filterTypePromotion(outletPromotionEntities, Constants.SOURCE_SMART_VANSALE);

        //id promotion
        Set<Long> idPromotion = new HashSet<>();
        for (OutletPromotionDTO dto : outletPromotionDTOS)

            for (ProductOutletSkuEntity entity : productAvailable) {
                ProductEntity productEntity = productRepository.findProductsInWarehouseVanSale(outletId, entity.getProductOutletSkuId());

                //CATEGORY promotion , DISCOUNT FOR NEXT PURCHASE
                if (dto.getTypePromotion().equals(PromotionType.CATEGORY.getNo()) || dto.getTypePromotion().equals(PromotionType.NEXTPURCHASE.getNo())) {
                    if (productEntity.getCatGroup().getCatGroupId().equals(dto.getIdCondition())) {
                        idPromotion.addAll(dto.getOutletPromotionIds());
                    }
                }

                //BRAND promotion
                if (dto.getTypePromotion().equals(PromotionType.BRAND.getNo())) {
                    if (productEntity.getBrand().getBrandId().equals(dto.getIdCondition())) {
                        idPromotion.addAll(dto.getOutletPromotionIds());
                    }
                }

                //PRODUCT promotion
                if (dto.getTypePromotion().equals(PromotionType.PRODUCT.getNo())) {
                    if (entity.getProductOutletSkuId().equals(dto.getIdCondition())) {
                        idPromotion.addAll(dto.getOutletPromotionIds());
                    }
                }
            }
        //get information of promotion
        for (Long id : idPromotion) {
            OutletPromotionEntity outletPromotionEntity = outletPromotionRepository.findById(id).get();
            int quantityHasApplied = outletPromotionEntity.getMaxPerPromotion() - outletPromotionEntity.getRemainGift();          //quantity has applied
            promotionsOfOutlet.add(PromotionUtils.entity2DtoForVansales(outletPromotionEntity, quantityHasApplied));
        }
        return promotionsOfOutlet;
    }


    /**
     * GET ALL PROMOTION BY PRODUCT IN WAREHOUSE
     *
     * @param warehouseId
     * @param pageable
     * @return
     */
    public Page getPromotionsOfWarehouse(Long warehouseId, Long userId, Pageable pageable) {
        Long outletId = warehouseRepository.findById(warehouseId).get().getOutlet().getOutletId();

        //get all promotion is active of outlet
        List<OutletPromotionEntity> outletPromotionEntities = outletPromotionRepository.getAllOutLetPromotionByOutletId(outletId, new Timestamp(System.currentTimeMillis()));

        if (userId != null) {
            CustomerEnity customer = null;
            String customerGroupName = null;
            Long customerGroupId = null;
            customer = customerRepository.findByUserId(userId);
            if (customer != null) {
                customerGroupId = loyaltyMemberRepository.findByCustomerIdAndOutletId(customer.getCustomerId(), outletId).getCustomerGroupId();
            }
            if (customerGroupId != null) {
                customerGroupName = customerGroupRepository.getOne(customerGroupId).getGroupName();
            }
            CustomerEnity finalCustomer = customer;
            String finalCustomerGroupName = customerGroupName;
            outletPromotionEntities = outletPromotionEntities.stream().filter(entity -> {
                ObjectMapper mapper = new ObjectMapper();
                EnhancedPromotionDTO promotion = null;
                try {
                    promotion = mapper.readValue(entity.getNewPromotionJson(), EnhancedPromotionDTO.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return false;
                }
                if (finalCustomer != null) {
                    assert promotion != null;
                    return promotionService.canApply(finalCustomer, finalCustomerGroupName, outletId, promotion);
                } else {
                    return promotion.getPromotionRule().getIsAll();
                }
            }).collect(Collectors.toList());
        }

        //filter promotion by product in warehouse
        List<OutletPromotionDTO> outletPromotionDTOS = this.promotionsAvailableInWareHouse(outletPromotionEntities, warehouseId, outletId);

        return this.customPagination(outletPromotionDTOS, pageable);
    }
}
