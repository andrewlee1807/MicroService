package com.banvien.fc.promotion.service;

import com.banvien.fc.common.enums.PromotionProperty;
import com.banvien.fc.common.enums.SyncTracking;
import com.banvien.fc.common.rest.errors.BadRequestException;
import com.banvien.fc.common.util.CommonUtils;
import com.banvien.fc.common.util.Constants;
import com.banvien.fc.promotion.dto.*;
import com.banvien.fc.promotion.dto.mobile.ProductOutletMobileDTO;
import com.banvien.fc.promotion.dto.rules.*;
import com.banvien.fc.promotion.dto.rules.Enum.*;
import com.banvien.fc.promotion.dto.vansale.QuickSellCategoryDTO;
import com.banvien.fc.promotion.dto.vansale.QuickSellPromotionDTO;
import com.banvien.fc.promotion.entity.*;
import com.banvien.fc.promotion.repository.*;
import com.banvien.fc.promotion.service.mapper.CustomerGroupMapper;
import com.banvien.fc.promotion.service.mapper.OutletPromotionMapper;
import com.banvien.fc.promotion.utils.*;
import com.banvien.fc.promotion.utils.promotion.PromotionOverlapChecker;
import com.banvien.fc.promotion.utils.rule.RuleBuilder;
import com.banvien.fc.promotion.utils.rule.RuleProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PromotionService {
    @Autowired
    private OutletPromotionRepository outletPromotionRepository;
    @Autowired
    private ProductOutletRepository productOutletRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductOutletSkuRepository productOutletSkuRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PromotionCustomerTargetRepository promotionCustomerTargetRepository;
    @Autowired
    private CustomerGroupRepository customerGroupRepository;
    @Autowired
    private OutletPromotionCustomerRepository outletPromotionCustomerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private CustomerRewardRepository customerRewardRepository;
    @Autowired
    private OutletPromotionMapper outletPromotionMapper;
    @Autowired
    private CustomerGroupMapper customerGroupMapper;
    @Autowired
    private ProductPromotionBlockedRepository productPromotionBlockedRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private AdminPromotionRepository adminPromotionRepository;
    @Autowired
    private BlockProductRepository blockProductRepository;
    @Autowired
    private BlockProductAdminRepository blockProductAdminRepository;
    @Autowired
    private LoyaltyMemberRepository loyaltyMemberRepository;
    @Autowired
    private GiftRepository giftRepository;
    @Autowired
    private SyncVersionTrackingRepository syncVersionTrackingRepository;
    @Autowired
    private OutletEmployeeRepository outletEmployeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Environment env;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private OrderOutletPromotionRepository orderOutletPromotionRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ProductOutletStorageRepository productOutletStorageRepository;
    @Autowired
    private DataInActiveTrackingRepository dataInActiveTrackingRepository;
    @Autowired
    private EmailRepository emailRepository;
//    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionService.class);

    private Long copyPromotion(EnhancedPromotionDTO enhancedPromotionDTO) {
        try {
            Optional<OutletPromotionEntity> entityOptional = outletPromotionRepository.findById(enhancedPromotionDTO.getOutletPromotionId());
            if (entityOptional.isPresent()) {
                EnhancedPromotionDTO dto = getPromotionDetail(enhancedPromotionDTO.getOutletPromotionId());
                dto.setTitle(enhancedPromotionDTO.getTitle());
                dto.setStartDate(enhancedPromotionDTO.getStartDate());
                dto.setEndDate(enhancedPromotionDTO.getEndDate());
                dto.setNotifySendDate(enhancedPromotionDTO.getNotifySendDate());
                dto.setStatus(enhancedPromotionDTO.getStatus());

                try {
                    JSONObject jsonObject = new JSONObject(entityOptional.get().getNewPromotionJson());
                    dto.setType(jsonObject.getInt("type"));
                } catch (JSONException e) {
                    throw new BadRequestException("Can not found promotion type");
                }
                if (checkOverlap(dto, enhancedPromotionDTO.getOutletId()))
                    throw new BadRequestException("Outlet promotion is overlaps");

                OutletPromotionEntity entity = new OutletPromotionEntity();
                dto2Entity(entity, dto);

                entity.setNewPromotionJson(entityOptional.get().getNewPromotionJson());
                entity.setPromotionRule(entityOptional.get().getPromotionRule());
                entity.setPenetrationType(entityOptional.get().getPenetrationType());
                entity.setPenetrationValue(entityOptional.get().getPenetrationValue());
                entity.setApplyShopByAdmin(entityOptional.get().getApplyShopByAdmin());
                entity.setPromotionAdmin(entityOptional.get().getPromotionAdmin());
                entity.setNeverEnd(entityOptional.get().getNeverEnd());
                entity.setGroupCode(entityOptional.get().getGroupCode());

                entity = outletPromotionRepository.saveAndFlush(entity);
                this.saveCustomerTarget(entity, enhancedPromotionDTO.getCustomerGroupIds());

                if (entity.getGroupCode() != null) {
                    return Long.parseLong(entity.getGroupCode());
                } else {
                    return entity.getOutletPromotionId();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private void createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeHeaderLine(XSSFSheet sheet, XSSFWorkbook workbook, Long outletId) {
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(11);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        createCell(sheet, row, 0, "No", style);
        createCell(sheet, row, 1, "Category/Sub-Category", style);
        createCell(sheet, row, 2, "Sub-Category Code", style);
        createCell(sheet, row, 3, "Brand", style);
        createCell(sheet, row, 4, "Product Code", style);
        createCell(sheet, row, 5, "Product Name", style);
        if (outletId != null) {
            createCell(sheet, row, 6, "Unit of Measure (UOM)", style);
            createCell(sheet, row, 7, "Barcode", style);
            createCell(sheet, row, 8, "Cost Price", style);
            createCell(sheet, row, 9, "Sales price", style);
            createCell(sheet, row, 10, "Unit", style);
            createCell(sheet, row, 11, "Status", style);
            createCell(sheet, row, 12, "Fast Entry Code", style);
        } else {
            createCell(sheet, row, 6, "Status", style);
            createCell(sheet, row, 7, "Fast Entry Code", style);
        }

    }

    private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook, int rowCount, CellStyle style, XSSFFont font,
                                String code, String name, Boolean Status, CatGroupEntity catGroupEntity, BrandEntity brandEntity,
                                ProductOutletSkuEntity productOutletSkuEntity, Long outletId) {


        Row row = sheet.createRow(rowCount);
        int columnCount = 0;
        createCell(sheet, row, columnCount++, rowCount, style);
        if (catGroupEntity.getParent() != null) {
            createCell(sheet, row, columnCount++, catGroupEntity.getParent().getName() + ">>" + catGroupEntity.getName(), style);
        } else {
            createCell(sheet, row, columnCount++, catGroupEntity.getName(), style);
        }
        createCell(sheet, row, columnCount++, catGroupEntity.getCode(), style);
        createCell(sheet, row, columnCount++, brandEntity.getName(), style);
        createCell(sheet, row, columnCount++, code, style);
        createCell(sheet, row, columnCount++, name, style);
        if (outletId != null) {
            createCell(sheet, row, columnCount++, productOutletSkuEntity.getTitle(), style);
            createCell(sheet, row, columnCount++, productOutletSkuEntity.getBarcode(), style);
            createCell(sheet, row, columnCount++, productOutletSkuEntity.getOriginalPrice(), style);
            createCell(sheet, row, columnCount++, productOutletSkuEntity.getSalePrice().toString(), style);
            createCell(sheet, row, columnCount++, productOutletSkuEntity.getUnit(), style);
            if (productOutletSkuEntity.getStatus() == 1) createCell(sheet, row, columnCount++, "Active", style);
            else createCell(sheet, row, columnCount++, "Inactive", style);
            createCell(sheet, row, columnCount++, " ", style);
        } else {
            if (Status) createCell(sheet, row, columnCount++, "Active", style);
            else createCell(sheet, row, columnCount++, "Inactive", style);
            createCell(sheet, row, columnCount++, " ", style);
        }


    }

    private boolean checkPenetration(String penetrationValue, int penetrationType, List<OrderOutletDTO> orderOutletDTOs,
                                     long customerId, long outletId, Timestamp lastLogin) {
        try {
            if (lastLogin == null) lastLogin = new Timestamp(System.currentTimeMillis());
            boolean check = true;
//            JSONObject penetrationValueJS = new JSONObject(penetrationValue);
            switch (PenetrationType.getEnum(penetrationType)) {
                case CATEGORY: // hasn't bought Category this Outlet
                    long catId = Long.parseLong(penetrationValue);
//                    long catId = penetrationValueJS.getLong("penetrationValue");
                    for (OrderOutletDTO orderOutletDTO : orderOutletDTOs) {
                        if (check)
                            for (ProductOrderItemEntity productOrderItemEntity : orderOutletDTO.getProductOrderItemEntities()) {
                                ProductDTO productDTO = catalogRepository.getProductByProductOutletSkuId(productOrderItemEntity.getProductOutletSkuId());
                                if (productDTO == null || productDTO.getCatGroupId() == null) {
                                    System.out.println("!!!!!! Fail check Penetration CatGroup : " + productOrderItemEntity.getProductOutletSkuId());
                                    continue;
                                }
                                CatGroupEntity catGroupEntity = categoryRepository.findByCatGroupId(productDTO.getCatGroupId());
                                if (productDTO.getCatGroupId() == catId || (catGroupEntity.getParent() != null && catGroupEntity.getParent().getCatGroupId() == catId)) {
                                    check = false;
                                    break;
                                }
                            }
                    }
                    break;
                case BRAND:  // hasn't bought Brand this Outlet
                    long brandId = Long.parseLong(penetrationValue);
//                    long brandId = penetrationValueJS.getLong("penetrationValue");
                    for (OrderOutletDTO orderOutletDTO : orderOutletDTOs) {
                        if (check)
                            for (ProductOrderItemEntity productOrderItemEntity : orderOutletDTO.getProductOrderItemEntities()) {
                                ProductDTO productDTO = catalogRepository.getProductByProductOutletSkuId(productOrderItemEntity.getProductOutletSkuId());
                                if (productDTO.getBrandId() == brandId) {
                                    check = false;
                                    break;
                                }
                            }
                    }
                    break;
                case LAPSED_USER: // Lapsed Users / Buyers
//                    JSONObject penetrationValueJS = new JSONObject(penetrationValue);
                    JSONObject lapsedUserJS = new JSONObject(penetrationValue);
//                    JSONObject lapsedUserJS = penetrationValueJS.getJSONObject("lapsedUser");
                    int option = lapsedUserJS.getInt("option");
                    int numberOfDay = lapsedUserJS.getInt("numberOfDay");
                    double value;
                    switch (option) {
                        case 1:
                            // Order (not achieved)
                            int quantity = orderRepository.getTotalQuantityByUserAndOutlet(customerId, outletId, numberOfDay);
                            if (quantity >= lapsedUserJS.getDouble("value")) check = false;
                            break;
                        case 2:
                            // Sale (not achieved)
                            double amount = orderRepository.getTotalAmountByUserAndOutlet(customerId, outletId, numberOfDay);
                            if (amount > lapsedUserJS.getDouble("value")) check = false;
                            break;
                        case 3:
                            // Not login
                            int day = Math.round((System.currentTimeMillis() - lastLogin.getTime()) / 86400000);
                            if (day < numberOfDay) check = false;
                            break;
                    }
                    break;
            }


            return check;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*Initialise Input to Engine Promotion*/
    private RuleInputDTO createInputRule(List<ListProductDTO> listProductDTOS, long outletId) {
        double totalPrice = 0.0;
        int totalQuantity = 0;
        ArrayList<RuleProductDTO> products = new ArrayList<>();
        List<GroupTypeDTO> categoryGrs = new ArrayList<>();
        List<GroupTypeDTO> brandGrs = new ArrayList<>();
        List<ProductPromotionBlockedEntity> productPromotionBlockedEntities = productPromotionBlockedRepository.findByOutletIdOrOutletIdIsNull(outletId);
        Set<Long> productIdBlocked = new HashSet<>();   // List product ID was blocked in an Outlet
        for (ProductPromotionBlockedEntity productPromotionBlockedEntity : productPromotionBlockedEntities) {
            productIdBlocked.add(productPromotionBlockedEntity.getProductOutletSkuId());
        }

        for (ListProductDTO listProductDTO : listProductDTOS) {
            // Check ProductOutlet is Product Blocked
            Optional<ProductOutletSkuEntity> productOutletSkuEntity = productOutletSkuRepository.findById(listProductDTO.getProductOutletSkuId());
            if (productOutletSkuEntity.isPresent()) {
                if (productIdBlocked.contains(productOutletSkuEntity.get().getProductOutletSkuId())) continue;
            }
            ProductDTO productDTO = catalogRepository.getProductByProductOutletSkuId(listProductDTO.getProductOutletSkuId());
            // transfer product to JS
            RuleProductDTO product = new RuleProductDTO(listProductDTO.getProductOutletSkuId(),
                    listProductDTO.getPrice() * listProductDTO.getQuantity(), listProductDTO.getQuantity(),
                    productDTO.getCatGroupId(), productDTO.getBrandId());

            boolean flag = true;
            for (GroupTypeDTO categoryType : categoryGrs) {
                if (productDTO.getCatGroupId() == categoryType.getId()) {
                    categoryType.setAmount(categoryType.getAmount() + listProductDTO.getPrice());
                    flag = false;
                    break;
                }
            }
            if (flag) {
                categoryGrs.add(new GroupTypeDTO(productDTO.getCatGroupId(), listProductDTO.getQuantity(), listProductDTO.getPrice()));
            }
            flag = true;

            for (GroupTypeDTO brandType : brandGrs) {
                if (productDTO.getBrandId().equals(brandType.getId())) {
                    brandType.setAmount(brandType.getAmount() + listProductDTO.getPrice());
                    flag = false;
                    break;
                }
            }
            if (flag) {
                brandGrs.add(new GroupTypeDTO(productDTO.getBrandId(), listProductDTO.getQuantity(), listProductDTO.getPrice()));
            }

//            totalPrice += product.getPrice();
//            totalQuantity += product.getQuantity();
            products.add(product);
        }
        return new RuleInputDTO(products, false);
    }

    /* For self-promotion applied many time per Order*/
    private RuleInputDTO createSubInputRule(RuleInputDTO ruleInputDTO, DiscountDTO discountDTO, int operate, int typeThreshold) {
        List<RuleProductDTO> products = new ArrayList<>();
        Double amount = 0D;
        Integer quantity = 0;
        for (Long id : discountDTO.getProductIds()) {
            for (RuleProductDTO ruleProductDTO : ruleInputDTO.getProducts()) {
                if (id.equals(ruleProductDTO.getId()) && operate == Operation.AND.getNo()) { // in case AND
                    amount += ruleProductDTO.getAmount();
                    quantity += ruleProductDTO.getQuantity();
                }
                if (id.equals(ruleProductDTO.getId()) && operate == Operation.OR.getNo()) { // in case OR
                    if (typeThreshold == 1) {
                        ruleProductDTO.setQuantity(ruleProductDTO.getQuantity() - (int) discountDTO.getMinValue());
                    } else ruleProductDTO.setAmount(ruleProductDTO.getAmount() - discountDTO.getMinValue());
                }
            }
        }

        if (quantity == 0 && amount == 0) {  // Incase Max per Order > 1
            for (RuleProductDTO ruleProductDTO : ruleInputDTO.getProducts()) {
                amount += ruleProductDTO.getAmount();
                quantity += ruleProductDTO.getQuantity();
            }

        }
        if (operate == Operation.AND.getNo()) {
            if (typeThreshold == 1) // base on quantity
                quantity -= (int) discountDTO.getMinValue();
            else
                amount -= discountDTO.getMinValue();
            products.add(new RuleProductDTO(-1L, amount, quantity, -1L, -1L));
            return new RuleInputDTO(products, true);
        }
        return ruleInputDTO;
    }

    private Double getDiscountValuePromotion(ReductionDTO reductionDTO, HashMap<Long, Double> amountProducts, Double totalPriceOrder) {
        if (reductionDTO.getDiscountType().equals(DiscountType.PERCENT_OFF)) {
            if (reductionDTO.getPromotionType().equals(PromotionType.ORDER.getNo()) || reductionDTO.getPromotionType().equals(PromotionType.EASYORDER.getNo())) { // Order, Shipping Promotion: Discount for all Order
                return totalPriceOrder * reductionDTO.getValueDiscount() / 100;
            }
//            if (reductionDTO.getDiscountType().equals(DiscountType.PERCENT_OFF)) {
            // Discount for each Product
            Double totalPriceDiscount = 0.0;
            for (Long idProduct : reductionDTO.getDiscountByProducts())
                totalPriceDiscount += amountProducts.get(idProduct);
            return totalPriceDiscount * reductionDTO.getValueDiscount() / 100;
//            }
        }
        // else AMOUNT_OFF : using own reductionDTO.getValueDiscount() to return
        return reductionDTO.getValueDiscount();

    }

    /*Update information for a Promotion changing Status*/
    private void updatePromotionChangingStatus(OutletPromotionEntity entity, String content) {
        entity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        if (entity.getNotifySendDate() != null && entity.getStatus()) { // Ignore EasyOrder Promotion
            entity.setNotifySentDate(null);
            entity.setNotifySendDate(new Timestamp(System.currentTimeMillis()));
            entity.setNotifyContent(content);
        }
    }

    /*Update Status for a Promotion*/
    private void updateStatusOutletPromotion(OutletPromotionEntity outletPromotionEntity, boolean status) {
//        this.updatePromotionChangingStatus(outletPromotionEntity, "Change Status");
        outletPromotionEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        outletPromotionEntity.setStatus(status);
//        outletPromotionRepository.saveAndFlush(outletPromotionEntity);
        emailRepository.send(outletPromotionEntity.getOutletPromotionId());
        System.out.println("Inactive Promotion");
    }

    /*Update Status for a Promotion*/
    private void updateStatus4PromotionOutOfQuantity(long outletId) {
        List<OutletPromotionEntity> listOutletPromotionExpired = outletPromotionRepository.getByRemainAndActiveStatus(outletId);
        for (OutletPromotionEntity entity : listOutletPromotionExpired) {
            entity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
            entity.setStatus(false);
            outletPromotionRepository.saveAndFlush(entity);
        }
    }

    /*Update Status for Promotions are expired date to Inactive*/
    private void updateStatus4PromotionExpired(long outletId) {
        List<OutletPromotionEntity> listOutletPromotionExpired = outletPromotionRepository.getByExpiredDateAndActiveStatus(outletId, new Timestamp(System.currentTimeMillis()));
        for (OutletPromotionEntity entity : listOutletPromotionExpired) {
            String content = "Promotion is not enough quantity to remain";
            if (entity.getEndDate().compareTo(new Timestamp(System.currentTimeMillis())) < 0) {  // expired date
                entity.setStatus(false);
            }
            this.updatePromotionChangingStatus(entity, content);
            entity.setStatus(false);
            outletPromotionRepository.saveAndFlush(entity);
        }
    }

    /*Update Status for Promotions Admin are expired date to Inactive*/
    private void updateStatus4PromotionAdminExpired(Long countryId) {
        List<AdminPromotionEntity> listOutletPromotionAdminExpired = adminPromotionRepository.getByExpiredDateAndActiveStatus(countryId, new Timestamp(System.currentTimeMillis()));
        for (AdminPromotionEntity entity : listOutletPromotionAdminExpired) {
            entity.setStatus(false);
            adminPromotionRepository.saveAndFlush(entity);
        }
    }

    /*Check Overlap base on Rule of 2 Promotions*/
    private boolean checkOverlapPromotionByRule(PromotionRuleDTO ruleDTO1, PromotionRuleDTO ruleDTO2, long outletId,
                                                Timestamp promotion1Start, Timestamp promotion1End,
                                                Timestamp promotion2Start, Timestamp promotion2End) {
        boolean rs = false;
        rs = PromotionOverlapChecker.isOverlap(ruleDTO1, ruleDTO2, outletId);
        if (rs) {
            int cnt1 = promotion1Start.compareTo(promotion2Start);
            int cnt2 = promotion2Start.compareTo(promotion1End);

            int cnt4 = promotion2Start.compareTo(promotion1Start);
            int cnt5 = promotion1Start.compareTo(promotion2End);
            if ((cnt1 <= 0 && cnt2 <= 0) || (cnt4 <= 0 && cnt5 <= 0)) {
                return true;
            }
            rs = false;
        }
        return rs;
    }

    /*Check Customer Target/Group is accepted for Promotion*/
    private boolean checkCustomerTarget(Long customerId, Long outletPromotionId, JSONObject jsonObject, JSONObject jsonObjectRule,
                                        LoyaltyMemberEntity loyaltyMemberEntity, Boolean firstTimeOrder) {
        //Get list Target Type of Outlet allow from DB
        Set<Long> customerGroupIds = promotionCustomerTargetRepository.getListCustomerGroupIdByOutletId(outletPromotionId);
        List<Long> customerTargetIds = new ArrayList<>();
        try {
            JSONArray arr = jsonObject.getJSONArray("customerTargetIds");
            for (int i = 0; i < arr.length(); i++) {
                customerTargetIds.add(arr.getLong(i));
            }
        } catch (Exception ex) {
        }
        if (customerTargetIds.isEmpty()) {
            boolean isAll = false;
            try {
                if (jsonObjectRule.has("isAll")) isAll = jsonObjectRule.getString("isAll").equals("true");
                if (isAll) return true;
                if ((loyaltyMemberEntity == null || !(customerGroupIds.contains(loyaltyMemberEntity.getCustomerGroupId())))) {
                    if (jsonObjectRule.getString("firstOrder").equals("true")) {
                        // Check first time order
                        if (!firstTimeOrder)    // firstTimeOrder = True : Customer hasn't ever order in outlet
                            return false; //continue; // customer's group target is not in this promotion
                    } else {
                        return false; // customer's group target is not in this promotion
                    }
                }
            } catch (Exception ex) {
                return false;
            }
        } else {
            if (loyaltyMemberEntity == null) return false;// continue;
            return customerTargetIds.contains(customerRepository.findById(loyaltyMemberEntity.getCustomerId()).get().getUserId()); //continue;
        }
        return true;
    }

    /*Check Easy Order Promotion with easyOrderCode*/
    private Double getDiscountByEasyOrderPromotion(EasyOrderSubmitDTO easyOrderSubmitDTO, CustomerEnity customerEnity, List<ReductionDTO> reductionDTOS,
                                                   Double totalDiscount, HashMap<Long, Double> amountProducts, Double totalPriceOrder,
                                                   ActionType actionType) {
        OutletPromotionEntity outletPromotionEntity = outletPromotionRepository.getOutletPromotionByEasyOrderCode(easyOrderSubmitDTO.getEasyOrderCode(), new Timestamp(System.currentTimeMillis()));
        if (outletPromotionEntity == null) {
            return -1.0;
        }
        // Redemption per Customer
        OutletPromotionCustomerEntity outletPromotionCustomerEntity = null;
        outletPromotionCustomerEntity = outletPromotionCustomerRepository.findByCustomerIdAndOutletPromotionId(customerEnity.getCustomerId(), outletPromotionEntity.getOutletPromotionId());
        // Check redemption per customer
        if (outletPromotionCustomerEntity != null) {
            if (outletPromotionCustomerEntity.getQuantity() >= outletPromotionEntity.getMaxPerCustomer()) {
                return totalDiscount;
            }
        }

        JSONObject jsonObject = null;
        JSONObject jsonObjectRule = null;
        String baseOn = "";
        try {
            jsonObject = new JSONObject(outletPromotionEntity.getNewPromotionJson());
            String discountType = jsonObject.getJSONObject("promotionRule").getString("discountType");
            Double discountValue = 0.0;
//            List<Long> targetIds = new ArrayList<Long>();
            ReductionDTO reductionDTO = new ReductionDTO();
            reductionDTO.setPromotionType(jsonObject.getInt("type"));
            reductionDTO.setOutletPromotionId(outletPromotionEntity.getOutletPromotionId());
            reductionDTO.setValueDiscount(0.0);

            DiscountDTO discountDTO = new DiscountDTO();
            discountDTO.setDiscountType(discountType);
            reductionDTO.setDiscountType(discountDTO.getDiscountType());
            discountValue = jsonObject.getJSONObject("promotionRule").getJSONArray("discounts").getJSONObject(0).getDouble("discountValue");
            reductionDTO.setValueDiscount(discountValue);

//            if (discountDTO.getDiscountType().equals(DiscountType.GIFT) || discountDTO.getDiscountType().equals(DiscountType.PRODUCT) || discountDTO.getDiscountType().equals(DiscountType.FIX_PRICE)) {
//                targetIds.add(jsonObject.getJSONObject("promotionRule").getJSONArray("discounts").getJSONObject(0).getJSONArray("targetIds").getLong(0));
//                reductionDTO.setDiscountForTarget(targetIds.get(0));
//            }

//            totalDiscount += getDiscountValuePromotion(reductionDTO, amountProducts, totalPriceOrder);     // Always in type : AMOUNT_OFF and PERCENT || calculate EasyOrderPromotion Discount belong with OrderService
            reductionDTOS.add(reductionDTO);

            int remainGift = outletPromotionEntity.getRemainGift() - 1;
            if (ActionType.GET_TO_ORDER.equals(actionType)) {
                outletPromotionEntity.setRemainGift(remainGift);
                outletPromotionEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
                outletPromotionRepository.save(outletPromotionEntity);  // update quantity of the Promotion
                outletPromotionRepository.flush();

                // update OutletPromotionCustomer
                if (outletPromotionCustomerEntity == null) {
                    outletPromotionCustomerEntity =
                            new OutletPromotionCustomerEntity(customerEnity.getCustomerId(), outletPromotionEntity.getOutletPromotionId(), 1, new Timestamp(System.currentTimeMillis()));
                } else {
                    outletPromotionCustomerEntity.setQuantity(outletPromotionCustomerEntity.getQuantity() + 1);
                }
                outletPromotionCustomerRepository.save(outletPromotionCustomerEntity);
                outletPromotionCustomerRepository.flush();
            }
            System.out.println("---Applied EasyOrderCode to EasyOrder");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalDiscount;
    }

    /*Check quantity, expiration by Discount Product and Gift*/
    private int isRewardAvailable(DiscountDTO discountDTO, int quantity) {
        if (discountDTO.getDiscountType().equals(DiscountType.GIFT)) {
            Long countAvailableGift = catalogRepository.checkGiftForPromotionApplied(discountDTO.getTargetIds().get(0));
            if (countAvailableGift <= 0 || countAvailableGift == null) {    // unavailable Gift
                catalogRepository.changeStatus(discountDTO.getTargetIds().get(0));
                return 0; // inactived Gift
            }
            if (countAvailableGift < quantity) { // out of quantity
                return -1;   // out of quantity
            }
        }
        if (discountDTO.getDiscountType().equals(DiscountType.PRODUCT)) {
            Long countAvailableProduct = catalogRepository.getProductForPromotionApplied(discountDTO.getTargetIds().get(0));
            // expired or out of quantity
            if (countAvailableProduct == null || countAvailableProduct <= 0) return 0; // unavailable Product
            if (countAvailableProduct < quantity) return -1;  // out of quantity
        }
        return 1; // available
    }


    public Page<OutletPromotionDTO> getAllOutLetPromotion4WholeSaler(String programName, long outletId, Pageable pageable) {
        Page<OutletPromotionEntity> outletPromotionEntities = outletPromotionRepository.getAllOutLetPromotion4Wholesaler(programName, outletId, pageable);
        if (outletPromotionEntities == null) return null;
        Page<OutletPromotionDTO> outletPromotionDTOS = outletPromotionEntities.map(outletPromotionEntity -> {
            OutletPromotionDTO outletPromotionDTO = new OutletPromotionDTO();
            outletPromotionDTO.setOutletPromotionId(outletPromotionEntity.getOutletPromotionId());
            outletPromotionDTO.setTitle(outletPromotionEntity.getTitle());
            outletPromotionDTO.setStartDateLong(outletPromotionEntity.getStartDate().getTime());
            outletPromotionDTO.setEndDateLong(outletPromotionEntity.getEndDate().getTime());
            outletPromotionDTO.setDescription(outletPromotionEntity.getDescription());
            outletPromotionDTO.setStatus(outletPromotionEntity.getStatus());
            outletPromotionDTO.setDescription(outletPromotionEntity.getDescription());
            outletPromotionDTO.setThumbnail(outletPromotionEntity.getThumbnail());
            return outletPromotionDTO;
        });

        return outletPromotionDTOS;
    }

    public Page<OutletPromotionDTO> getAllOutLetPromotionByOutletId(Long outletId, Long userId, Pageable pageable) {
        Page<OutletPromotionEntity> entities = null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CustomerEnity customerEnity = null;
        List<OutletPromotionEntity> outletPromotionEntities = outletPromotionRepository.getAllOutLetPromotionByOutletId(outletId, timestamp);
        if (userId != null) {
            // Get Customer Group type to be Allow apply promotion
            customerEnity = customerRepository.findByUserId(userId);
            if (customerEnity != null) {
                LoyaltyMemberEntity loyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletId(customerEnity.getCustomerId(), outletId);
                Boolean firstTimeOrder = orderRepository.userFirstTimeOrderInOutlet(customerEnity.getCustomerId(), outletId);
//                List<OutletPromotionEntity> outletPromotionEntities = outletPromotionRepository.getAllOutLetPromotionByOutletId(outletId, timestamp);
                CustomerEnity finalCustomerEnity = customerEnity;
                List<OrderOutletDTO> orderOutletDTOs = orderRepository.getOrderOutletByCustomerAndOutlet(customerEnity.getCustomerId(), outletId);
                outletPromotionEntities = outletPromotionEntities.stream().filter(x -> {
                    try {
                        JSONObject jsonObject = new JSONObject(x.getNewPromotionJson());

                        boolean checkTarget = checkCustomerTarget(finalCustomerEnity.getCustomerId(), x.getOutletPromotionId(), jsonObject, jsonObject.getJSONObject("promotionRule"), loyaltyMemberEntity, firstTimeOrder);
                        if (!checkTarget) return false;   // customer's group target is not in this promotion

//                        if (jsonObject.getJSONObject("promotionRule").getString("isAll").equals("true")) {
//                            return true;
//                        }
                        // Check redemption per customer
                        OutletPromotionCustomerEntity outletPromotionCustomerEntity = outletPromotionCustomerRepository.findByCustomerIdAndOutletPromotionId(finalCustomerEnity.getCustomerId(), x.getOutletPromotionId());
                        if (outletPromotionCustomerEntity != null) {
                            if (outletPromotionCustomerEntity.getQuantity() >= x.getMaxPerCustomer())
                                return false;
                        }

                        //Get list Target Type of Outlet allow from DB
//                        Set<Long> customerGroupIds = promotionCustomerTargetRepository.getListCustomerGroupIdByOutletId(x.getOutletPromotionId());
//                        if (loyaltyMemberEntity == null || !(customerGroupIds.contains(loyaltyMemberEntity.getCustomerGroupId()))) {
//                            if (firstTimeOrder && jsonObject.getJSONObject("promotionRule").getString("firstOrder").equals("true")) {
//                                return true;
//                            }
//                        } else {
//                            return true;
//                        }

                        // Check penetration checked for each Promotion
                        if (x.getPenetrationType() > 0) {
                            Timestamp lastLogin = finalCustomerEnity.getBeforeLastLogin() != null ? finalCustomerEnity.getBeforeLastLogin() : finalCustomerEnity.getLastLogin();
                            boolean check = checkPenetration(x.getPenetrationValue(), x.getPenetrationType(),
                                    orderOutletDTOs, finalCustomerEnity.getCustomerId(), outletId, finalCustomerEnity.getBeforeLastLogin());
                            return check;
                        }

                        return true;
                    } catch (JSONException e) {
                        throw new BadRequestException("Can not bad promotion : NewJSONPromotion error -promotionRule- -firstOrder- at Promotion ID : " + x.getOutletPromotionId());
                    }
                }).collect(Collectors.toList());

            } else {
                return new PageImpl<>(new ArrayList<>());
            }
        }
//        if (entities == null) {
//            entities = outletPromotionRepository.getAllOutLetPromotionByOutletId(outletId, timestamp, pageable);
//        }


        // Filter show only Admin Promotion (Independent) Overlap with Outlet Promotion exist DUAL
        List<OutletPromotionEntity> outletPromotionEntitiesOverlapAdmin = new ArrayList<>();
        for (OutletPromotionEntity promotionAdminCreated : outletPromotionEntities) {
            if (promotionAdminCreated.getPriority() <= 1000) {  // focus on Promotion created by Admin
                break;
            }
            if (promotionAdminCreated.getIsCombined().equals(0)) { // focus on Promotion is Independent
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    JSONObject jsonObjectAdmin = new JSONObject(promotionAdminCreated.getNewPromotionJson());
                    PromotionRuleDTO ruleAdminDTO = mapper.readValue(jsonObjectAdmin.getJSONObject("promotionRule").toString(), PromotionRuleDTO.class);
                    if (ruleAdminDTO.getPromotionType() == PromotionType.ORDER || ruleAdminDTO.getPromotionType() == PromotionType.SHIPPING)
                        continue;
                    for (OutletPromotionEntity entity : outletPromotionEntities) {
                        if (entity.getPriority() <= 1000) {
                            boolean rs = false;
                            JSONObject jsonObject = new JSONObject(entity.getNewPromotionJson());
                            PromotionRuleDTO ruleDTO = mapper.readValue(jsonObject.getJSONObject("promotionRule").toString(), PromotionRuleDTO.class);
                            if (ruleDTO.getPromotionType() == PromotionType.ORDER || ruleDTO.getPromotionType() == PromotionType.SHIPPING)
                                continue;
                            rs = checkOverlapPromotionByRule(ruleAdminDTO, ruleDTO, outletId,
                                    promotionAdminCreated.getStartDate(), promotionAdminCreated.getEndDate(),
                                    entity.getStartDate(), entity.getEndDate());
                            if (rs) {
                                outletPromotionEntitiesOverlapAdmin.add(entity);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        for (OutletPromotionEntity entity : outletPromotionEntitiesOverlapAdmin) {
            outletPromotionEntities.remove(entity);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), outletPromotionEntities.size());
        try {
            entities = new PageImpl(outletPromotionEntities.subList(start, end), pageable, outletPromotionEntities.size());
        } catch (Exception ex) {
            return new PageImpl<>(new ArrayList<>());
        }

        Page<OutletPromotionDTO> outletPromotionDTOS = entities.map(outletPromotionEntity -> {
            OutletPromotionDTO outletPromotionDTO = new OutletPromotionDTO();
            outletPromotionDTO.setOutletPromotionId(outletPromotionEntity.getOutletPromotionId());
            outletPromotionDTO.setTitle(outletPromotionEntity.getTitle());
            outletPromotionDTO.setStartDateLong(outletPromotionEntity.getStartDate().getTime());
            outletPromotionDTO.setEndDateLong(outletPromotionEntity.getEndDate().getTime());
            outletPromotionDTO.setDescription(outletPromotionEntity.getDescription());
            outletPromotionDTO.setStatus(outletPromotionEntity.getStatus());
            outletPromotionDTO.setDescription(outletPromotionEntity.getDescription());
            outletPromotionDTO.setThumbnail(outletPromotionEntity.getThumbnail());
            return outletPromotionDTO;
        });

        return outletPromotionDTOS;
    }

    public Page getOutletPromotion(String programName, Integer stt, Timestamp startDate, Timestamp endDate, Long outletId, final Integer type, Pageable pageable) {
        startDate = startDate == null ? new Timestamp(0) : startDate;
        endDate = endDate == null ? new Timestamp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365 * 100) : endDate;
        programName = programName == null ? "" : programName;
        stt = stt == null ? -1 : stt;
        Boolean status = stt < 0 ? null : stt != 0;
        Page<OutletPromotionEntity> entities;

        updateStatus4PromotionExpired(outletId);
        updateStatus4PromotionOutOfQuantity(outletId);

        if (type == null || type == -1) {
            entities = outletPromotionRepository.getByTitleAndStatusAndStartEndDate(programName, startDate, endDate, outletId, status, pageable);
        } else {
            List<OutletPromotionEntity> outletPromotionEntities;
            if (status == null) {
                outletPromotionEntities = outletPromotionRepository.findAllByTitleContainsIgnoreCaseAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndOutletOutletId(programName, startDate, endDate, outletId);
            } else {
                outletPromotionEntities = outletPromotionRepository.findAllByTitleContainsIgnoreCaseAndStatusAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndOutletOutletId(programName, status, startDate, endDate, outletId);
            }
            outletPromotionEntities = outletPromotionEntities.stream().filter(x -> {
                try {
                    JSONObject jsonObject = new JSONObject(x.getNewPromotionJson());
                    return PromotionType.getEnum(jsonObject.getInt("type")) == PromotionType.getEnum(type);
                } catch (JSONException e) {
                    throw new BadRequestException("Can not found promotion type");
                }
            }).collect(Collectors.toList());
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), outletPromotionEntities.size());
            try {
                entities = new PageImpl(outletPromotionEntities.subList(start, end), pageable, outletPromotionEntities.size());
            } catch (Exception ex) {
                return new PageImpl<>(new ArrayList<>());
            }
        }

        return entities.map(entity -> {
            PromotionsDTO dto = PromotionUtils.from(entity);
            try {
                JSONObject jsonObject = new JSONObject(entity.getNewPromotionJson());
                dto.setType(PromotionType.getEnum(jsonObject.getInt("type")).name());
            } catch (JSONException e) {
                throw new BadRequestException("Can not found promotion type");
            }
            dto.setIsAdmin(entity.getGroupCode() != null);
            dto.setStartDate(entity.getStartDate().getTime());
            dto.setEndDate(entity.getEndDate().getTime());
            return dto;
        });
    }

    public Page<PromotionsDTO> getOutletPromotions4EasyOrder(Long outletId, Pageable pageable) {
        Page<OutletPromotionEntity> entities;
        List<OutletPromotionEntity> outletPromotionEntities;
        outletPromotionEntities = outletPromotionRepository.getAllOutLetPromotionByOutletIdOrderByPriority(outletId, new Timestamp(System.currentTimeMillis()));

        outletPromotionEntities = outletPromotionEntities.stream().filter(x -> {
            try {
                JSONObject jsonObject = new JSONObject(x.getNewPromotionJson());
                JSONObject jsonObjectRule = jsonObject.getJSONObject("promotionRule");
                boolean isAll = false;
                if (jsonObjectRule.has("isAll")) isAll = jsonObjectRule.getString("isAll").equals("true");
                return isAll;
            } catch (JSONException e) {
                throw new BadRequestException("Can not found promotion isAll");
            }
        }).collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), outletPromotionEntities.size());
        entities = new PageImpl(outletPromotionEntities.subList(start, end), pageable, outletPromotionEntities.size());

        Page<PromotionsDTO> promotionsDTOS = entities.map(outletPromotionEntity -> {
            PromotionsDTO promotionsDTO = PromotionUtils.from(outletPromotionEntity);
            try {
                JSONObject jsonObject = new JSONObject(outletPromotionEntity.getNewPromotionJson());
                promotionsDTO.setType(PromotionType.getEnum(jsonObject.getInt("type")).name());
            } catch (JSONException e) {
                throw new BadRequestException("Can not found promotion type");
            }
            promotionsDTO.setIsAdmin(outletPromotionEntity.getGroupCode() != null);
            return promotionsDTO;
        });
        return promotionsDTOS;
    }

    public Page<AdminPromotionDTO> getAdminPromotion(String programName, Integer stt, Timestamp startDate, Timestamp endDate, Integer type, Long countryId, Pageable pageable) {
        startDate = startDate == null ? new Timestamp(0) : startDate;
        endDate = endDate == null ? new Timestamp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365 * 100) : endDate;
        programName = programName == null ? "" : programName;
        stt = stt == null ? -1 : stt;
        Boolean status = stt < 0 ? null : stt != 0;
        Page<AdminPromotionEntity> entities;

        updateStatus4PromotionAdminExpired(countryId);

        if (type == null || type == -1) {
            entities = adminPromotionRepository.getByTitleAndStatusAndStartEndDate(programName, startDate, endDate, status, countryId, pageable);
        } else {
            List<AdminPromotionEntity> adminPromotionEntities;
            if (status == null) {
                adminPromotionEntities = adminPromotionRepository.findAllByNameContainsIgnoreCaseAndStartDateAfterAndEndDateBeforeAndCountryId(programName, startDate, endDate, countryId);
            } else {
                adminPromotionEntities = adminPromotionRepository.findAllByNameContainsIgnoreCaseAndStatusAndStartDateAfterAndEndDateBeforeAndCountryId(programName, status, startDate, endDate, countryId);
            }
            adminPromotionEntities = adminPromotionEntities.stream().filter(x -> {
                try {
                    JSONObject jsonObject = new JSONObject(x.getNewPromotionJson());
                    return PromotionType.getEnum(jsonObject.getInt("type")) == PromotionType.getEnum(type);
                } catch (JSONException e) {
                    throw new BadRequestException("Can not found promotion type");
                }
            }).collect(Collectors.toList());
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), adminPromotionEntities.size());
            entities = new PageImpl(adminPromotionEntities.subList(start, end), pageable, adminPromotionEntities.size());
        }

        Page<AdminPromotionDTO> dtos = entities.map(entity -> {
            AdminPromotionDTO dto = AdminPromotionUtils.entity2DTO(entity);
            OutletPromotionEntity outletPromotionEntity = outletPromotionRepository.findByGroupCode(entity.getGroupCode()).get(0);
            try {
                JSONObject jsonObject = new JSONObject(outletPromotionEntity.getNewPromotionJson());
                dto.setType(PromotionType.getEnum(jsonObject.getInt("type")).name());
            } catch (JSONException e) {
                throw new BadRequestException("Can not found promotion type");
            }
            dto.setStartDate(entity.getStartDate().getTime());
            dto.setEndDate(entity.getEndDate().getTime());
            return dto;
        });
        for (AdminPromotionDTO dto : dtos) {
            dto.setProgramName(dto.getName());
        }
        return dtos;
    }

    public List<OutletPromotionDTO> getPromotionsInOutlet(Long outletId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<OutletPromotionEntity> outletPromotionEntities = outletPromotionRepository.getAllOutLetPromotionByOutletIdOrderByPriority(outletId, timestamp);
        return this.filterTypePromotion(outletPromotionEntities, null);
    }

    public PromotionPerOrderDTO getPromotionDiscountByOrder(EasyOrderSubmitDTO easyOrderSubmitDTO, ActionType actionType) {
        if (easyOrderSubmitDTO.getOutletId() == null || easyOrderSubmitDTO.getUserId() == null || easyOrderSubmitDTO.getProducts() == null)
            return new PromotionPerOrderDTO();
        if (actionType.equals(ActionType.GET_TO_ORDER))
            System.out.println("$$$$$$ GET PROMOTION FOR SUBMIT ORDER $$$$$$");//            LOGGER.info("$$$$$$ GET PROMOTION FOR SUBMIT ORDER $$$$$$");
        else
            System.out.println("\\\\/ GET PROMOTION FOR SHOPPING CART \\\\/");//            LOGGER.info("\\/ GET PROMOTION FOR SHOPPING CART \\/"); //

        List<OrderOutletDTO> orderOutletDTOs = null;
        List<ReductionDTO> reductionDTOS = new ArrayList<>();
        HashMap<Long, Double> amountProducts = new HashMap<Long, Double>();
        Double totalDiscount = 0.0;
        Double totalPriceOrder = 0.0;

        // Get Block List Product in Outlet
        List<ProductPromotionBlockedEntity> productPromotionBlockedEntities = productPromotionBlockedRepository.findByOutletIdOrOutletIdIsNull(easyOrderSubmitDTO.getOutletId());
        Set<Long> productIdBlocked = new HashSet<>();   // List product ID was blocked in an Outlet
        for (ProductPromotionBlockedEntity productPromotionBlockedEntity : productPromotionBlockedEntities) {
            productIdBlocked.add(productPromotionBlockedEntity.getProductOutletSkuId());
        }
        // Get Block List Product by Admin

        // create RuleInputDTO
        RuleInputDTO ruleInputEasyOrder = createInputRule(easyOrderSubmitDTO.getProducts(), easyOrderSubmitDTO.getOutletId());

        // Set of Sku
        Set<Long> skuIds = new HashSet<>(); // Serve EasyOrder's Fix-Price Discount

        // Create a Amount Hash Map to easy get total discount
        for (RuleProductDTO product : ruleInputEasyOrder.getProducts()) {
            amountProducts.put(product.getId(), product.getAmount());
            totalPriceOrder += product.getAmount();
            skuIds.add(product.getId());
        }
//        LOGGER.info("TOTALPRICE ORDER :" + totalPriceOrder);
        // Get Customer Group type to be Allow apply promotion
        CustomerEnity customerEnity = null;
        LoyaltyMemberEntity loyaltyMemberEntity = null;
        Boolean firstTimeOrder = false;
        customerEnity = customerRepository.findByUserId(easyOrderSubmitDTO.getUserId());
        if (customerEnity != null)
            loyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletId(customerEnity.getCustomerId(), easyOrderSubmitDTO.getOutletId());
        else {
//            LOGGER.error("--- NO CUSTOMER BY USER ID : " + easyOrderSubmitDTO.getUserId());
            System.out.println("---- NO CUSTOMER BY USER ID : " + easyOrderSubmitDTO.getUserId());
            return new PromotionPerOrderDTO();
        }
        //        CustomerGroupCustomerEntity customerGroupCustomerEntity = customerGroupCustomerRepository.findByCustomerId(customerEnity.getCustomerId(), easyOrderSubmitDTO.getOutletId());
        firstTimeOrder = orderRepository.userFirstTimeOrderInOutlet(customerEnity.getCustomerId(), easyOrderSubmitDTO.getOutletId());


        // Independent and Combine check
        int highestPromotionProperty = PromotionProperty.COMBINE.getValue(); // Independent(0) Combine(1)

//        Long customerGrId = customerGroupCustomerEntity.getCustomerGroupEntity().getCustomerGroupId();
//        String customerGrName = customerGroupCustomerEntity.getCustomerGroupEntity().getGroupName();

        System.out.println("-BEGIN CHECKING PROMOTIONS ON OUTLET ID -" + easyOrderSubmitDTO.getOutletId());
//        LOGGER.info("BEGIN CHECKING PROMOTIONS ON OUTLET ID - " + easyOrderSubmitDTO.getOutletId());
        // Get All Promotion OrderObj By "priority" and "status is Active" and "remain > 0" and expiry date
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<OutletPromotionEntity> outletPromotionEntities = outletPromotionRepository.getAllOutLetPromotionByOutletIdOrderByPriority(easyOrderSubmitDTO.getOutletId(), timestamp);
        for (OutletPromotionEntity outletPromotionEntity : outletPromotionEntities) {
            if (reductionDTOS.size() == 0 || outletPromotionEntity.getIsCombined() == 1) {
                System.out.println("---Checking Promotion ID " + outletPromotionEntity.getOutletPromotionId());
//                LOGGER.info("-Checking Promotion - " + outletPromotionEntity.getOutletPromotionId());
                OutletPromotionCustomerEntity outletPromotionCustomerEntity = null;
                int customerPerPromotion = 0;
                outletPromotionCustomerEntity = outletPromotionCustomerRepository.findByCustomerIdAndOutletPromotionId(customerEnity.getCustomerId(), outletPromotionEntity.getOutletPromotionId());
                // Check redemption per customer
                if (outletPromotionCustomerEntity != null) {
                    customerPerPromotion = outletPromotionCustomerEntity.getQuantity();
                    if (outletPromotionCustomerEntity.getQuantity() >= outletPromotionEntity.getMaxPerCustomer())
                        continue;
                }

                JSONObject jsonObject = null;
                JSONObject jsonObjectRule = null;
                String baseOn = "";
                try {
                    jsonObject = new JSONObject(outletPromotionEntity.getNewPromotionJson());
                    jsonObjectRule = jsonObject.getJSONObject("promotionRule");

                    baseOn = jsonObjectRule.getString("baseOn");

                    boolean checkTarget = checkCustomerTarget(customerEnity.getCustomerId(), outletPromotionEntity.getOutletPromotionId(), jsonObject, jsonObjectRule, loyaltyMemberEntity, firstTimeOrder);

                    if (!checkTarget) {
                        System.out.println("------ Customer's group target is not in this promotion"); // View to test
                        continue;   // customer's group target is not in this promotion
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return new PromotionPerOrderDTO();
                }

//              Check penetration checked for each Promotion
                if (outletPromotionEntity.getPenetrationType() > 0) {
                    if (outletPromotionEntity.getPenetrationType() < 3 && orderOutletDTOs == null) {   // Query only one per outlet
                        orderOutletDTOs = orderRepository.getOrderOutletByCustomerAndOutlet(customerEnity.getCustomerId(), easyOrderSubmitDTO.getOutletId());
                    }
                    Timestamp lastLogin = customerEnity.getBeforeLastLogin() != null ? customerEnity.getBeforeLastLogin() : customerEnity.getLastLogin();
                    boolean check = checkPenetration(outletPromotionEntity.getPenetrationValue(), outletPromotionEntity.getPenetrationType(),
                            orderOutletDTOs, customerEnity.getCustomerId(), easyOrderSubmitDTO.getOutletId(), lastLogin);
                    if (!check) continue;
                }

//              Apply engine
                DiscountDTO discountDTO = new DiscountDTO();
                int remainGift = outletPromotionEntity.getRemainGift();
                try {
                    RuleInputDTO subRuleInput = ruleInputEasyOrder;
                    subRuleInput.setByPass(false);
                    int quantityItemFree = 1; // quantity Product/Gift Discount : A Promotion per Order
                    for (int item = 0; item < outletPromotionEntity.getMaxPerOrder(); item++) {         // Applied many time for a Promotion
                        if (jsonObject.getInt("type") == PromotionType.SHIPPING.getNo()) {
                            subRuleInput.setByPass(true);          // Promotion for Order/Shipping
                        }
                        discountDTO = RuleProcessor.getInstance().execute(outletPromotionEntity.getPromotionRule(), subRuleInput);
                        if (discountDTO != null) {


                            // EasyOrder : Check Product B was in EasyOrder
                            if (easyOrderSubmitDTO.getFromEasyOrder() != null && easyOrderSubmitDTO.getFromEasyOrder() && DiscountType.FIX_PRICE.equals(discountDTO.getDiscountType())) {
                                if (!skuIds.contains(discountDTO.getTargetIds().get(0))) continue;
                            }

                            // Get number of current FreeItem
                            if (discountDTO.getDiscountType().equals(DiscountType.GIFT) || discountDTO.getDiscountType().equals(DiscountType.PRODUCT)) {
                                if (reductionDTOS.size() > 0) {
                                    if ((reductionDTOS.get(reductionDTOS.size() - 1).getDiscountType().equals(DiscountType.GIFT) || reductionDTOS.get(reductionDTOS.size() - 1).getDiscountType().equals(DiscountType.PRODUCT)) &&
                                            discountDTO.getTargetIds().get(0).equals(reductionDTOS.get(reductionDTOS.size() - 1).getDiscountForTarget())) { // update quantity FreeItem to get available quantity
                                        quantityItemFree++;
                                    } else {
                                        quantityItemFree = 1;   // reset quantity to different FreeItem got
                                    }
                                }
                            }

//                            // check quantity by Discount Product and Gift is available
                            int checkReward = isRewardAvailable(discountDTO, quantityItemFree);
                            if (checkReward != 1) {
                                if (actionType.equals(ActionType.GET_TO_ORDER) || checkReward == 0)
                                    this.updateStatusOutletPromotion(outletPromotionEntity, false);
                                System.out.println("xxxxxThis Promotion " + outletPromotionEntity.getOutletPromotionId() + " is not available!");
                                break;
                            }

                            // Check Applied many time of this Promotion to create new sub-ruleInputEasyOrder
                            if (outletPromotionEntity.getMaxPerOrder() > 1 && item + 1 < outletPromotionEntity.getMaxPerOrder()) {
                                String operation = jsonObjectRule.getString("operation");
                                int operate;
                                if (operation.equals("AND")) {
                                    operate = Operation.AND.getNo();
                                    // Priority highest "Discount Value" in this Promotion
                                    if (item > 0 && discountDTO.getMinValue() < reductionDTOS.get(reductionDTOS.size() - 1).getMinValue()) {
                                        continue;
                                    }
                                } else operate = Operation.OR.getNo();
                                if (baseOn.equals("QUANTITY")) { // base on Quantity
                                    subRuleInput = createSubInputRule(subRuleInput, discountDTO, operate, 1);
                                } else { // base on Amount
                                    subRuleInput = createSubInputRule(subRuleInput, discountDTO, operate, 0);
                                }
                            }

                            // Update remain
                            remainGift -= 1;
//                            outletPromotionEntity.setRemainGift(outletPromotionEntity.getRemainGift() - 1);

                            // update OutletPromotionCustomer
                            if (outletPromotionCustomerEntity == null) {
                                customerPerPromotion = 1;
                                outletPromotionCustomerEntity =
                                        new OutletPromotionCustomerEntity(customerEnity.getCustomerId(), outletPromotionEntity.getOutletPromotionId(), 1, timestamp);
                            } else {
                                customerPerPromotion = customerPerPromotion + 1;
//                                outletPromotionCustomerEntity.setQuantity(outletPromotionCustomerEntity.getQuantity() + 1);
                            }

//                          if Promotion Property of this Promotions is Independent(0) IN THE HIGHEST "PRIORITY" : try to get more own Promotion then exit
                            if ((reductionDTOS.size() == 0 && outletPromotionEntity.getIsCombined() == 0)) {
                                highestPromotionProperty = PromotionProperty.INDEPENDENT.getValue();
                            }
                            ReductionDTO reductionDTO = new ReductionDTO();
//                          Promotion Next Purchase
                            System.out.println("------- Promotion Type :" + jsonObject.getInt("type"));
                            if (jsonObject.getInt("type") == PromotionType.NEXTPURCHASE.getNo()) {
                                Long productOutletSkuId = jsonObject.getLong("offerNextProductId");
                                int typeDiscount = 1;   // typeDiscount : PERCENT_OFF(0), PRICE_OFF(1)
                                if (discountDTO.getDiscountType().equals(DiscountType.PERCENT_OFF)) typeDiscount = 0;
//                                Long expiredDateNum = jsonObject.getLong("expiredDate");
                                Long expiredDateNum = jsonObjectRule.getLong("expiredDate");

                                if (ActionType.GET_TO_ORDER.equals(actionType)) {
                                    CustomerRewardEntity customerRewardEntity = new CustomerRewardEntity(customerEnity.getCustomerId(), 1, new Timestamp(System.currentTimeMillis()), false, null,
                                            CommonUtils.genCode(4), easyOrderSubmitDTO.getOutletId(), productOutletSkuId, typeDiscount, discountDTO.getDiscountValue(), new Timestamp(expiredDateNum), outletPromotionEntity.getOutletPromotionId());
                                    customerRewardRepository.save(customerRewardEntity);
//                                redeemCodeList.add(customerRewardEntity.getRedeemCode());
                                    reductionDTO.setCodeNextPurchase(customerRewardEntity.getRedeemCode());
                                    System.out.println("------ Get a Promotion Code ----------" + customerRewardEntity.getRedeemCode());
//                                    LOGGER.info("---Get a Promotion Code ");
                                }
//                            } else listDiscount.add(discountDTO);
                            }
                            System.out.println("----Applied " + outletPromotionEntity.getOutletPromotionId().toString());
//                            LOGGER.info("--Applied " + outletPromotionEntity.getOutletPromotionId());
                            reductionDTO.setMinValue(discountDTO.getMinValue());
                            reductionDTO.setPromotionType(jsonObject.getInt("type"));
                            reductionDTO.setOutletPromotionId(outletPromotionEntity.getOutletPromotionId());
                            if (discountDTO.getDiscountValue() > 0)
                                reductionDTO.setValueDiscount(discountDTO.getDiscountValue());
                            reductionDTO.setDiscountType(discountDTO.getDiscountType());

                            if (!discountDTO.getProductIds().isEmpty())
                                reductionDTO.setDiscountByProducts(discountDTO.getProductIds());
                            else if (reductionDTOS.size() > 0)
                                reductionDTO.setDiscountByProducts(reductionDTOS.get(reductionDTOS.size() - 1).getDiscountByProducts());

                            Double valueDiscount = null;
                            if (!discountDTO.getTargetIds().isEmpty()) {
                                reductionDTO.setDiscountForTarget(discountDTO.getTargetIds().get(0));
                            } else if (jsonObject.getInt("type") != PromotionType.NEXTPURCHASE.getNo() &&
                                    !reductionDTO.getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT) && !reductionDTO.getDiscountType().equals(DiscountType.SHIPPING_FIX_PRICE)
                            ) {
                                valueDiscount = getDiscountValuePromotion(reductionDTO, amountProducts, totalPriceOrder);
                                totalDiscount += valueDiscount;
                                reductionDTO.setValueDiscount(valueDiscount); // the material discount  $ or null : correspond with PERCENT/AMOUNT and OTHERS
                            }

                            reductionDTOS.add(reductionDTO);

                            if (remainGift == 0) break;  // out of Promotion
                            if (customerPerPromotion >= outletPromotionEntity.getMaxPerCustomer())
                                break; // out of redemption per customer

                            checkReward = isRewardAvailable(discountDTO, quantityItemFree + 1);
                            if (actionType.equals(ActionType.GET_TO_ORDER) && checkReward == -1) { // out of quantity
                                this.updateStatusOutletPromotion(outletPromotionEntity, false);
                                System.out.println("xxxxxThis Promotion " + outletPromotionEntity.getOutletPromotionId() + " is out of reward!");
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    // save and update Promotion&Customer but Ignore Shipping_discount
                    if (ActionType.GET_TO_ORDER.equals(actionType) && discountDTO!= null && !(discountDTO.getDiscountType().equals(DiscountType.SHIPPING_FIX_PRICE) || discountDTO.getDiscountType().equals(DiscountType.SHIPPING_DISCOUNT))) {
                        outletPromotionEntity.setRemainGift(remainGift);
                        outletPromotionEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
                        outletPromotionRepository.save(outletPromotionEntity);  // update quantity of the Promotion
                        outletPromotionRepository.flush();

                        // Save the Promotions are applied on Order and this Customer :
                        if (outletPromotionCustomerEntity != null) {
                            outletPromotionCustomerEntity.setQuantity(customerPerPromotion);
                            outletPromotionCustomerRepository.save(outletPromotionCustomerEntity);
                            outletPromotionCustomerRepository.flush();
                        }
                    }
                    if (highestPromotionProperty == PromotionProperty.INDEPENDENT.getValue())
                        break; // The best Promotion is required Independent

                } catch (Exception e) {
                    e.printStackTrace();
                    return new PromotionPerOrderDTO();
                }
//                if (highestPromotionProperty == PromotionProperty.INDEPENDENT.getValue()) break; // The best Promotion is required Independent
            }
        }
        // Updated table relation

        Boolean invalidCode = null;


//        LOGGER.info("$uccess with discount : " + totalDiscount + "   --> For CustomerID: " + customerEnity.getCustomerId());
        // Next Purchase Code (4 Characters) || Voucher Code (12 Characters)
        if (easyOrderSubmitDTO.getPromotionCode() != null && customerEnity != null) {
            DiscountDTO discountCodePromotion = this.appliedPromotionCode(ruleInputEasyOrder.getProducts(), easyOrderSubmitDTO.getPromotionCode(), customerEnity.getCustomerId(), actionType);
            if (discountCodePromotion != null) {
                int codeType = PromotionType.NEXTPURCHASE.getNo();  // Next Purchase Discount
                if (!(discountCodePromotion.getTargetIds() != null && discountCodePromotion.getTargetIds().size() > 0))
                    codeType = PromotionType.ORDER.getNo();         // Voucher Discount
                totalDiscount += getDiscountValuePromotion(new ReductionDTO(discountCodePromotion.getDiscountValue(), discountCodePromotion.getDiscountType(), discountCodePromotion.getTargetIds(), codeType), amountProducts, totalPriceOrder);
                System.out.println("~~Bonus Applied Promotion Code");
//                LOGGER.info("~~Bonus Applied Promotion Code");
            } else {
                System.out.println("!!! Invalid NextPurchase Code");
                invalidCode = true;
            }
        }

        // Easy Order Discount with Code
        if (easyOrderSubmitDTO.getEasyOrderCode() != null) {
            System.out.println("~~Admitted EasyOrderCode to EasyOrder");
            Double totalDiscountEasyOrderCode = this.getDiscountByEasyOrderPromotion(easyOrderSubmitDTO, customerEnity, reductionDTOS, totalDiscount,
                    amountProducts, totalPriceOrder, actionType);
            if (totalDiscountEasyOrderCode == -1.0) {// Invalid EasyOrderCode
                System.out.println("!!! Invalid EasyOrderCode");
                invalidCode = true;
            } else {
                totalDiscount = totalDiscountEasyOrderCode;
            }
        }
        System.out.println("$uccess with discount : " + totalDiscount + "   --> For CustomerID: " + customerEnity.getCustomerId());
        System.out.println();
        return new PromotionPerOrderDTO(reductionDTOS, totalDiscount, invalidCode);
    }

    public DiscountDTO appliedPromotionCode(List<RuleProductDTO> products, String promotionCode, long customerId, ActionType actionType) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CustomerRewardEntity customerRewardEntity = customerRewardRepository.getCustomerRewardByCode(promotionCode, timestamp, customerId);
        if (customerRewardEntity != null) {
            DiscountDTO discountDTO = new DiscountDTO();
            discountDTO.setDiscountValue(customerRewardEntity.getDiscountValue());
            if (customerRewardEntity.getDiscountType() == 0)
                discountDTO.setDiscountType(DiscountType.PERCENT_OFF);
            else
                discountDTO.setDiscountType(DiscountType.AMOUNT_OFF);

//      Next Purchase Code
            if (customerRewardEntity.getProductOutletSkuId() != null) {
                List<Long> ids = new ArrayList<>();
                ids.add(customerRewardEntity.getProductOutletSkuId());
                discountDTO.setTargetIds(ids);
                // Check product will be applied CodePromotion in current Order
                boolean isSkuNextPurchaseInOrder = false;
                for (RuleProductDTO product : products) {
                    if (product.getId().equals(customerRewardEntity.getProductOutletSkuId())) {
                        isSkuNextPurchaseInOrder = true;
                        System.out.println("----Apply Promotion Code : " + customerRewardEntity.getId());
//                    LOGGER.info("---Apply Promotion Code : " + customerRewardEntity.getId());
                        break;
                    }
                }
                if (!isSkuNextPurchaseInOrder) return null;

//                if (isSkuNextPurchaseInOrder) {
//                    if (ActionType.GET_TO_ORDER.equals(actionType)) {
//                        customerRewardEntity.setRedeem(true);
//                        customerRewardEntity.setRedeemDate(new Timestamp(System.currentTimeMillis()));
//                        customerRewardRepository.save(customerRewardEntity);
//                        customerRewardRepository.flush();
//                    }
//                    return discountDTO;
//                }
            }
//      Voucher Code
//            else {
//                return discountDTO;
//            }
            // update history was redeemed
            if (ActionType.GET_TO_ORDER.equals(actionType)) {
                customerRewardEntity.setRedeem(true);
                customerRewardEntity.setRedeemDate(new Timestamp(System.currentTimeMillis()));
                customerRewardRepository.save(customerRewardEntity);
                customerRewardRepository.flush();
            }
            return discountDTO;
        }
        return null;
    }

    /* Remove relation of Order and Promotion
    *  Update remain Promotion
    *  Update relation of Customer and Promotion */
    public void restoreRemain2OutletPromotion(Long orderOutletId, Long customerId) {
        System.out.println("========Restored Remain Promotion Order After Submitted======= OrderOutlet : " + orderOutletId);
        List<OrderOutletPromotionEntity> orderOutletPromotionEntities = orderOutletPromotionRepository.getOrderOutletPromotionByOrderOutletId(orderOutletId);
        for (OrderOutletPromotionEntity entity : orderOutletPromotionEntities) {
            Optional<OutletPromotionEntity> outletPromotionEntity = outletPromotionRepository.findById(entity.getOutletPromotionId());
            if (outletPromotionEntity.isPresent()) {
                // update history of customer and Promotion
                OutletPromotionCustomerEntity outletPromotionCustomerEntity = outletPromotionCustomerRepository.findByCustomerIdAndOutletPromotionId(customerId, outletPromotionEntity.get().getOutletPromotionId());
                if (outletPromotionCustomerEntity != null) {
                    if (outletPromotionCustomerEntity.getQuantity() > 1) {
                        outletPromotionCustomerEntity.setQuantity(outletPromotionCustomerEntity.getQuantity() - 1);
                    } else {
                        outletPromotionCustomerRepository.delete(outletPromotionCustomerEntity);
                    }
                }

                // update Reward from Promotion with Gift Discount
                DiscountDTO discountDTO = PromotionUtils.getDiscountDTOFromPromotion(outletPromotionEntity.get());
                if (discountDTO != null) {
                    if (discountDTO.getDiscountType().equals(DiscountType.GIFT)) {
                        Optional<GiftEntity> giftEntity = giftRepository.findById(discountDTO.getTargetIds().get(0));
                        if (giftEntity.isPresent()) {
                            if (giftEntity.get().getTotalReceivedPromotion() != null) {
//                                giftEntity.get().setTotalReceived(giftEntity.get().getTotalReceived() - 1);
                                giftEntity.get().setTotalReceivedPromotion(giftEntity.get().getTotalReceivedPromotion() != null && giftEntity.get().getTotalReceivedPromotion() > 1 ? giftEntity.get().getTotalReceivedPromotion() - 1 : null);
                                giftRepository.saveAndFlush(giftEntity.get());
                            }
                        }
                    } else {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(outletPromotionEntity.get().getNewPromotionJson());
                            if (jsonObject.getInt("type") == PromotionType.NEXTPURCHASE.getNo()) {
                                CustomerRewardEntity customerRewardEntity = customerRewardRepository.findByOrderOutletId(orderOutletId);
                                if (customerRewardEntity != null) {
                                    customerRewardRepository.delete(customerRewardEntity);
                                }
                            }
                        } catch (JSONException e) {
                            System.out.println("Can not parse Promotion Json to Object when cancel Promotion |||");
                            e.printStackTrace();
                        }
                    }
                }
                // update remain
                int remain = outletPromotionEntity.get().getRemainGift();
                if (remain == 0) outletPromotionEntity.get().setRemainGift(1);
                else outletPromotionEntity.get().setRemainGift(remain + 1);
                outletPromotionRepository.saveAndFlush(outletPromotionEntity.get());
                // delete connection between Order and Promotion
                orderOutletPromotionRepository.delete(entity);
            }
        }
        System.out.println("=== Unsubscribed Promotion ====  " + orderOutletId);
    }

    public String generateEasyOrderCode() {
        return CommonUtils.genCode(10);
    }

    public DiscountDTO getPromotionByEasyOrderCode(String easyOrderCode) {
        DiscountDTO discountDTO = new DiscountDTO();
        if (easyOrderCode == null) return discountDTO;
        OutletPromotionEntity outletPromotionEntity = outletPromotionRepository.getOutletPromotionByEasyOrderCode(easyOrderCode, new Timestamp(System.currentTimeMillis()));
        if (outletPromotionEntity == null) return null;
        JSONObject jsonObject = null;
        JSONObject jsonObjectRule = null;
        JSONObject discountsJS = null;
        try {
            jsonObject = new JSONObject(outletPromotionEntity.getNewPromotionJson());
            jsonObjectRule = jsonObject.getJSONObject("promotionRule");

            discountsJS = jsonObject.getJSONObject("promotionRule").getJSONArray("discounts").getJSONObject(0);

            ObjectMapper oMapper = new ObjectMapper();
            DiscountDTO dto = oMapper.readValue(discountsJS.toString(), DiscountDTO.class); // json 2 obj

            discountDTO.setDiscountType(dto.getDiscountType());
            discountDTO.setDiscountValue(dto.getDiscountValue());

//            if ( !discountDTO.getDiscountType().equals(DiscountType.PRODUCT) && !discountDTO.getDiscountType().equals(DiscountType.GIFT) ){
//
//            }

//            if ( discountDTO.getDiscountType().equals(DiscountType.PRODUCT) || discountDTO.getDiscountType().equals(DiscountType.FIX_PRICE) ){
//                discountDTO.setTargetName(catalogRepository.getProductByProductOutletSkuId(dto.getTargetIds().get(0)).getName());
//            }

//            if (discountDTO.getDiscountType().equals(DiscountType.GIFT) ){
//                discountDTO.setTargetName(catalogRepository.getGiftByGiftId(dto.getTargetIds().get(0)).getName());
//            }

            discountDTO.setEasyOrderCode(easyOrderCode);    // should be got from EasyOrderCode Column than NewJS

        } catch (Exception e) {
            e.printStackTrace();
        }

        return discountDTO;
    }

    public ByteArrayInputStream exportBlockProducts2Excel(Long outletId) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet;
        sheet = workbook.createSheet("Sheet1");

        writeHeaderLine(sheet, workbook, outletId);
        int rowCount = 0;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        style.setFont(font);

        List<ProductPromotionBlockedEntity> productPromotionBlockedEntities = productPromotionBlockedRepository.findByOutletId(outletId);
        for (ProductPromotionBlockedEntity productPromotionBlockedEntity : productPromotionBlockedEntities) {
            if (outletId != null) {
                Optional<ProductOutletSkuEntity> productOutletSkuEntity = productOutletSkuRepository.findById(productPromotionBlockedEntity.getProductOutletSkuId());
                if (!productOutletSkuEntity.isPresent()) continue;
                ProductOutletEntity productOutletEntity = productOutletRepository.findByProductOutletId(productOutletSkuEntity.get().getProductOutletId());
                ProductDTO productDTO = catalogRepository.getProductByProductOutletSkuId(productPromotionBlockedEntity.getProductOutletSkuId());
                CatGroupEntity catGroupEntity = categoryRepository.findByCatGroupId(productDTO.getCatGroupId());
                BrandEntity brandEntity = brandRepository.findByBrandId(productDTO.getBrandId());
                // Convert to Excel
                rowCount++;
                writeDataLines(sheet, workbook, rowCount, style, font, productOutletEntity.getCode(), productOutletEntity.getName(), null, catGroupEntity, brandEntity, productOutletSkuEntity.get(), outletId);
            } else {
                Optional<ProductEntity> productEntity = productRepository.findById(productPromotionBlockedEntity.getProductId());
                if (!productEntity.isPresent()) continue;
                CatGroupEntity catGroupEntity = productEntity.get().getCatGroup();
                BrandEntity brandEntity = productEntity.get().getBrand();
                rowCount++;
                writeDataLines(sheet, workbook, rowCount, style, font, productEntity.get().getCode(), productEntity.get().getName(), productEntity.get().getActive(), catGroupEntity, brandEntity, null, outletId);
            }

        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            workbook.write(out);
            workbook.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }

        // Test on Local
//        try (FileOutputStream outputStream = new FileOutputStream("D:\\JavaBooks.xlsx")) {
//            workbook.write(outputStream);
//            workbook.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public int updateAdminPromotionState(List<Long> ids, String st, String content) {
        int count = 0;
        for (Long id : ids) {
            Optional<AdminPromotionEntity> entity = adminPromotionRepository.findById(id);
            if (entity.isPresent()) {
                List<Long> outletIds = outletPromotionRepository.findByGroupCode(entity.get().getGroupCode()).stream().map(OutletPromotionEntity::getOutletPromotionId).collect(Collectors.toList());
                updatePromotionState(outletIds, st, content, true);
                boolean state = false;
                if (st.equals("active")) state = true;
                entity.get().setStatus(state);
                adminPromotionRepository.save(entity.get());
                count++;
            } else {
                throw new BadRequestException("Admin promotion not found");
            }
        }
        return count;
    }

    public int updatePromotionState(List<Long> outletPromotionIds, String st, String content, boolean isAdmin) {
        int count = 0;
        for (Long outletPromotionId : outletPromotionIds) {
            Optional<OutletPromotionEntity> entity = outletPromotionRepository.findById(outletPromotionId);
            if (entity.isPresent()) {
                if (!isAdmin && entity.get().getGroupCode() != null) continue;
                boolean state = false;
                if (st.equals("active")) state = true;
                if (entity.get().getStatus() == state) continue; // No change state Promotion

                // Check current Promotion is running
                if (state) {
                    int startCompare = entity.get().getStartDate().compareTo(new Timestamp(System.currentTimeMillis()));
                    int endCompare = entity.get().getEndDate().compareTo(new Timestamp(System.currentTimeMillis()));
                    if (startCompare <= 0 && endCompare >= 0)
                        continue;    // Not Allow to change Status to be Active when current Promotion running
                }
                entity.get().setStatus(state);
                entity.get().setModifiedDate(new Timestamp(System.currentTimeMillis()));
                if (!state && entity.get().getNotifySentDate() != null) { // if change state to Inactive when Promotion is sent notification
                    entity.get().setNotifySentDate(null);
                    entity.get().setNotifySendDate(new Timestamp(System.currentTimeMillis()));
                    entity.get().setNotifyContent(content);
                }
                // update status for OutletPromotion
                outletPromotionRepository.save(entity.get());

                // update DataInActiveTracking for Mobile Synchronization when inactive Promotion
                DataInActiveTrackingEntity dataInActiveTrackingEntity = dataInActiveTrackingRepository.getByOutletPromotionIdAndOutletId(entity.get().getOutletPromotionId(), entity.get().getOutlet().getOutletId());
                if (!state) {
                    if (dataInActiveTrackingEntity == null) {
                        dataInActiveTrackingEntity = new DataInActiveTrackingEntity(entity.get().getOutletPromotionId(), SyncTracking.SYNC_PROMOTION.getValue(), new Timestamp(System.currentTimeMillis()), entity.get().getOutlet().getOutletId());
                        dataInActiveTrackingRepository.save(dataInActiveTrackingEntity);
                    }
                } else {
                    if (dataInActiveTrackingEntity != null) {
                        dataInActiveTrackingRepository.delete(dataInActiveTrackingEntity);
                    }
                }
                count++;
            }
        }
        outletPromotionRepository.flush();
        dataInActiveTrackingRepository.flush();
        return count;
    }

    public Boolean checkPromotionsWereNotified(List<Long> outletPromotionIds, Long outletId) {
        if (outletId == null) {
            for (Long id : outletPromotionIds) {
                Optional<AdminPromotionEntity> adminPromotion = adminPromotionRepository.findById(id);
                if (adminPromotion.isPresent()) {
                    List<Long> outletIds = outletPromotionRepository.findByGroupCode(adminPromotion.get().getGroupCode()).stream().map(OutletPromotionEntity::getOutletPromotionId).collect(Collectors.toList());
                    for (Long id1 : outletIds) {
                        Optional<OutletPromotionEntity> entity = outletPromotionRepository.findById(id1);
                        if (entity.isPresent()) {
                            if (entity.get().getStatus() && entity.get().getNotifySentDate() != null) {
                                return true;
                            }
                            break; // all promotions created by Admin with the same NotifySendDate
                        }
                    }
                } else {
                    throw new BadRequestException("Admin promotion not found");
                }
            }
            return false;
        } else {
            for (Long id : outletPromotionIds) {
                Optional<OutletPromotionEntity> entity = outletPromotionRepository.findById(id);
                if (entity.isPresent()) {
                    if (entity.get().getStatus() && entity.get().getNotifySentDate() != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
//    public Page<ProductPromotionBlockDTO> getListProductBlock(Long outletId, String productCode, String productName, String category, Pageable pageable) {
//        Page<ProductPromotionBlockDTO> pgEntity = productPromotionBlockedRepository.findAllProductIsBlocked(pageable, outletId, productCode, productName, category);
//        return pgEntity;
//    }

    public List<OutletPromotionDTO> getPromotionDiscountByEasyOrder(EasyOrderSubmitDTO easyOrderSubmitDTO) {
        List<OutletPromotionDTO> outletPromotionList = new ArrayList<>();
        // create RuleInputDTO
        RuleInputDTO ruleInputEasyOrder = createInputRule(easyOrderSubmitDTO.getProducts(), easyOrderSubmitDTO.getOutletId());

        // Set of Sku
        Set<Long> skuIds = new HashSet<>();
        for (RuleProductDTO product : ruleInputEasyOrder.getProducts()) {
            skuIds.add(product.getId());
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // Get all Promotion in Outlet
        List<OutletPromotionEntity> outletPromotionEntities = outletPromotionRepository.getAllOutLetPromotionByOutletIdOrderByPriority(easyOrderSubmitDTO.getOutletId(), timestamp);

        // Get Block List Product in Outlet
        List<ProductPromotionBlockedEntity> productPromotionBlockedEntities = productPromotionBlockedRepository.findByOutletIdOrOutletIdIsNull(easyOrderSubmitDTO.getOutletId());
        Set<Long> productIdBlocked = new HashSet<>();   // List product ID was blocked in an Outlet
        for (ProductPromotionBlockedEntity productPromotionBlockedEntity : productPromotionBlockedEntities) {
            productIdBlocked.add(productPromotionBlockedEntity.getProductOutletSkuId());
        }
        // Get Block List Product by Admin


        for (OutletPromotionEntity outletPromotionEntity : outletPromotionEntities) {
            try {
                JSONObject jsonObject = new JSONObject(outletPromotionEntity.getNewPromotionJson());
                JSONObject jsonObjectRule = jsonObject.getJSONObject("promotionRule");
                boolean isAll = false;
                if (jsonObjectRule.has("isAll")) {
                    isAll = jsonObjectRule.getString("isAll").equals("true");
                }
                if (isAll) {
                    int pType = jsonObject.getInt("type");
                    if (PromotionType.SHIPPING.getNo() == pType) { // Order/shipping promotion
                        outletPromotionList.add(new OutletPromotionDTO(outletPromotionEntity.getOutletPromotionId(), outletPromotionEntity.getTitle(), outletPromotionEntity.getDescription()));
                    } else {
                        //  Apply engine
                        DiscountDTO discountDTO = RuleProcessor.getInstance().execute(outletPromotionEntity.getPromotionRule(), ruleInputEasyOrder);
                        if (discountDTO != null) {
                            if (DiscountType.FIX_PRICE.equals(discountDTO.getDiscountType())) {
                                if (!skuIds.contains(discountDTO.getTargetIds().get(0))) continue;
                            }
                            outletPromotionList.add(new OutletPromotionDTO(outletPromotionEntity.getOutletPromotionId(), outletPromotionEntity.getTitle(), outletPromotionEntity.getDescription()));
                        }
                    }
                }

            } catch (Exception e) {
                continue;
            }
        }
        return outletPromotionList;
    }

    public List<String> getAllEasyOrderCode(Long outletId) {
        List<String> easyOrderCodes = outletPromotionRepository.getAllEasyOrderCodeByOutletId(outletId, new Timestamp(System.currentTimeMillis()));
        return easyOrderCodes;
    }

    public boolean checkEasyOrderCode(Long outletId, String easyOrderCodeCheck) {
        List<String> easyOrderCodes = this.getAllEasyOrderCode(outletId);
        for (String easyOrderCode : easyOrderCodes) {
            if (easyOrderCode.equals(easyOrderCodeCheck)) {
                return false;
            }
        }
        return true;
    }

    public ResponseEntity<Map<String, Object>> addProductBlock(Long outletId, List<Long> ids) {
        Map<String, Object> rs = new HashMap<>();
        try {
            for (int i = 0; i < ids.size(); i++) {
                Long id = ids.get(i);
                ProductPromotionBlockedEntity productPromotionBlockedEntity = new ProductPromotionBlockedEntity();
                if (outletId == null) {
                    productPromotionBlockedEntity.setProductId(id);
                } else {
                    productPromotionBlockedEntity.setOutletId(outletId);
                    productPromotionBlockedEntity.setProductOutletSkuId(id);
                }
                //insert into table productpromotionblocked
                productPromotionBlockedRepository.save(productPromotionBlockedEntity);
            }
        } catch (Exception ex) {
            return new ResponseEntity(rs, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(rs, new HttpHeaders(), HttpStatus.OK);
    }

    public Map<String, Object> createOrUpdateOutletPromotion(EnhancedPromotionDTO enhancedPromotionDTO) {
        ObjectWriter ow = new ObjectMapper().writer();
        Map<String, Object> output = new HashMap<>();
//        enhancedPromotionDTO.getPromotionRule().setDiscountsByDiscountType();
        if (enhancedPromotionDTO.getOutletPromotionId() != null && enhancedPromotionDTO.getPromotionProperty() == null) {  // Copy Promotion
            Long idNew = this.copyPromotion(enhancedPromotionDTO);
            output.put("PromotionIdForOutlet", idNew);
            return output;
        }
        try {
            enhancedPromotionDTO.setType(enhancedPromotionDTO.getPromotionRule().getPromotionType().getNo());
            String json = ow.writeValueAsString(enhancedPromotionDTO);
            enhancedPromotionDTO.setNewPromotionJson(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Long idNew = savePromotion(enhancedPromotionDTO);
        output.put("PromotionIdForOutlet", idNew);
        return output;
    }

    private Long savePromotion(EnhancedPromotionDTO enhancedPromotionDTO) {
        PromotionRuleDTO promotionRuleDTO = enhancedPromotionDTO.getPromotionRule();
        enhancedPromotionDTO.setNeverEnd(!(enhancedPromotionDTO.getStartDate() != null & enhancedPromotionDTO.getEndDate() != null & enhancedPromotionDTO.getStartDate() < enhancedPromotionDTO.getEndDate()));
        OutletPromotionEntity entity = new OutletPromotionEntity();

        //check edit
        if (enhancedPromotionDTO.getOutletPromotionId() != null) {
            Optional<OutletPromotionEntity> otp = outletPromotionRepository.findById(enhancedPromotionDTO.getOutletPromotionId());
            if (otp.isPresent()) {
                entity = otp.get();
            }
        }

        entity = dto2Entity(entity, enhancedPromotionDTO);
        entity.setGroupCode(enhancedPromotionDTO.getPromotionAdmin() == null ? null : enhancedPromotionDTO.getPromotionAdmin().toString());
        OutletPromotionEntity notify;
        if (entity.getOutletPromotionId() == null) {
            entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            entity.setRemainGift(entity.getTotalGift());
        } else {
            notify = getOutletPromotionById(entity.getOutletPromotionId());
            entity.setCreatedDate(notify.getCreatedDate());
        }

        //handle promotion for NEXTPURCHASE
        if (enhancedPromotionDTO.getPromotionRule().getPromotionType().equals(PromotionType.NEXTPURCHASE)) {
            promotionRuleDTO.setPromotionType(PromotionType.CATEGORY);
        }

        entity.setModifiedDate(new Timestamp(System.currentTimeMillis()));

        if (PromotionType.EASYORDER.getNo() != enhancedPromotionDTO.getType()) {
            //create rule
            String rs = RuleBuilder.createRuleTest(promotionRuleDTO);
            entity.setPromotionRule(rs);
        }
        entity = outletPromotionRepository.save(entity);
        if (PromotionType.EASYORDER.getNo() != enhancedPromotionDTO.getType()) {
            //save customer target group (Gold/Silver/Normal)
            this.saveCustomerTarget(entity, enhancedPromotionDTO.getCustomerGroupIds());
        }
        if (entity.getGroupCode() != null) {
            return Long.parseLong(entity.getGroupCode());
        } else {
            return entity.getOutletPromotionId();
        }
    }

    public OutletPromotionEntity getOutletPromotionById(Long id) {
        Optional<OutletPromotionEntity> employee = outletPromotionRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            System.out.println("No promotion record exist for given id");
            return null;
        }
    }

    private void saveCustomerTarget(OutletPromotionEntity entity, String[] customerGroupIds) throws DuplicateKeyException {
        promotionCustomerTargetRepository.deleteAll(promotionCustomerTargetRepository.findByOutletPromotionId(entity.getOutletPromotionId()));
        if (customerGroupIds != null && customerGroupIds.length > 0) {
            for (int i = 0; i < customerGroupIds.length; i++) {
                List<Long> lstId = customerGroupRepository.findIdByOutletOutletIdAndGroupName(entity.getOutlet().getOutletId(), customerGroupIds[i]);
                if (lstId.size() == 0 || lstId == null) {
                    continue;
                }
                List<PromotionCustomerTargetEntity> lstCustomerGroup = promotionCustomerTargetRepository.findByOutletPromotionIdAndCustomerGroupId(entity.getOutletPromotionId(), lstId.get(0));
                if (lstCustomerGroup == null || lstCustomerGroup.size() <= 0) {
                    PromotionCustomerTargetEntity target = new PromotionCustomerTargetEntity();
                    target.setOutletPromotionId(entity.getOutletPromotionId());
                    target.setCustomerGroupId(lstId.get(0));
                    promotionCustomerTargetRepository.save(target);
                }
                promotionCustomerTargetRepository.flush();
            }
        }
    }

    public static OutletPromotionEntity dto2Entity(OutletPromotionEntity entity, EnhancedPromotionDTO enhancedPromotionDTO) {
        OutletEntity outlet = new OutletEntity();
        ObjectWriter ow = new ObjectMapper().writer();
        outlet.setOutletId(enhancedPromotionDTO.getOutletId());
        entity.setOutlet(outlet);
        entity.setStartDate(new Timestamp(enhancedPromotionDTO.getStartDate()));
        entity.setEndDate(new Timestamp(enhancedPromotionDTO.getEndDate()));
        entity.setContentXML(enhancedPromotionDTO.getContentXML());
        if (enhancedPromotionDTO.getStatus() == Boolean.TRUE) {
            entity.setStatus(Boolean.TRUE);
        } else {
            entity.setStatus(Boolean.FALSE);
        }
        entity.setDescription(enhancedPromotionDTO.getDescription());
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        entity.setTitle(enhancedPromotionDTO.getTitle());
        entity.setNotifyType(enhancedPromotionDTO.getNotifyType());
        entity.setNotifyContent(enhancedPromotionDTO.getNotifyContent());
        entity.setNotifySendDate(enhancedPromotionDTO.getNotifySendDate() != null ? new Timestamp(enhancedPromotionDTO.getNotifySendDate()) : null);
        entity.setNeverEnd(enhancedPromotionDTO.getNeverEnd() != null ? enhancedPromotionDTO.getNeverEnd() : Boolean.FALSE);
        entity.setMaxPerPromotion(enhancedPromotionDTO.getMaxPerPromotion());
        entity.setMaxPerCustomer(enhancedPromotionDTO.getMaxPerCustomer());
        entity.setMaxPerOrder(enhancedPromotionDTO.getMaxPerOrder());
        entity.setThumbnail(enhancedPromotionDTO.getThumbnail());
        entity.setPriority(enhancedPromotionDTO.getPriority());
        entity.setIsCombined(enhancedPromotionDTO.getPromotionProperty());
        entity.setPromotionAdmin(enhancedPromotionDTO.getPromotionAdmin());
        entity.setNewPromotionJson(enhancedPromotionDTO.getNewPromotionJson());
        // For Easy Order Promotion
        if (enhancedPromotionDTO.getPromotionRule().getEasyOrderCode() != null && enhancedPromotionDTO.getPromotionRule().getEasyOrderCode().length() == 10) {
            entity.setEasyOrderCode(enhancedPromotionDTO.getPromotionRule().getEasyOrderCode());
        }
        try {
            entity.setApplyShopByAdmin(ow.writeValueAsString(enhancedPromotionDTO.getApplyShopByAdmin()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (enhancedPromotionDTO.getCreatedBy() != null) {
            UserEntity createdBy = new UserEntity();
            createdBy.setUserId(enhancedPromotionDTO.getCreatedBy().getUserId());
            entity.setCreatedBy(createdBy);
        }
        if (enhancedPromotionDTO.getPenetration() != null) {
            entity.setPenetrationType(enhancedPromotionDTO.getPenetration().getPenetrationType());
            if (enhancedPromotionDTO.getPenetration().getPenetrationType() == 3) {
                String json = null;
                try {
                    json = ow.writeValueAsString(enhancedPromotionDTO.getPenetration().getLapsedUser());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                entity.setPenetrationValue(json);
            } else entity.setPenetrationValue(enhancedPromotionDTO.getPenetration().getPenetrationValue());
        } else {
            entity.setPenetrationType(0);
        }

        //Hardcode
        CommonTemplateEntity commonTemplate = new CommonTemplateEntity();
        commonTemplate.setCommonTemplateId(1L);
        entity.setCommonTemplate(commonTemplate);
        entity.setTotalGift(entity.getMaxPerPromotion());
        entity.setRemainGift(entity.getMaxPerPromotion());
        return entity;
    }

    public void deleteOutletPromotion(Long id) {
        Optional<OutletPromotionEntity> entity = outletPromotionRepository.findById(id);
        if (entity.isPresent()) {
            deleteOutletPromotion(entity.get());
        } else {
            throw new BadRequestException("Outlet promotion not found - id: " + id);
        }
    }

    public void deleteOutletPromotion(OutletPromotionEntity entity) {
        // Check notified Date
        if (entity.getNotifySendDate().before(new Timestamp(System.currentTimeMillis())))
            throw new BadRequestException("Outlet promotion can't be deleted!");
        promotionCustomerTargetRepository.deleteAll(promotionCustomerTargetRepository.findByOutletPromotionId(entity.getOutletPromotionId()));
        promotionCustomerTargetRepository.flush();

        // update DataInActiveTracking for Mobile Synchronization
        DataInActiveTrackingEntity dataInActiveTrackingEntity = dataInActiveTrackingRepository.getByOutletPromotionIdAndOutletId(entity.getOutletPromotionId(), entity.getOutlet().getOutletId());
        if (dataInActiveTrackingEntity == null) {
            dataInActiveTrackingEntity = new DataInActiveTrackingEntity(entity.getOutletPromotionId(), SyncTracking.SYNC_PROMOTION.getValue(), new Timestamp(System.currentTimeMillis()), entity.getOutlet().getOutletId());
            dataInActiveTrackingRepository.save(dataInActiveTrackingEntity);
        }

        outletPromotionRepository.delete(entity);
        outletPromotionRepository.flush();
    }

    public List<OutletPromotionEntity> paginationOutletPromotion(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<OutletPromotionEntity> pagedResult = outletPromotionRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<OutletPromotionEntity>();
        }
    }

    public EnhancedPromotionDTO getPromotionDetail(Long id) throws Exception {
        EnhancedPromotionDTO dto = new EnhancedPromotionDTO();
        Optional<OutletPromotionEntity> entity = outletPromotionRepository.findById(id);
        if (entity.isPresent()) {
            dto.setOutletId(entity.get().getOutlet().getOutletId());
            dto.setTitle(entity.get().getTitle());
            dto.setStartDate(entity.get().getStartDate().getTime());
            dto.setEndDate(entity.get().getEndDate().getTime());
            dto.setStatus(entity.get().getStatus());
            dto.setDescription(entity.get().getDescription());
            dto.setThumbnail(entity.get().getThumbnail());
            dto.setPromotionProperty(entity.get().getIsCombined());
            dto.setMaxPerPromotion(entity.get().getMaxPerPromotion());
            dto.setMaxPerCustomer(entity.get().getMaxPerCustomer());
            dto.setMaxPerOrder(entity.get().getMaxPerOrder());
            dto.setNotifyContent(entity.get().getNotifyContent());
            dto.setNotifySendDate(entity.get().getNotifySendDate() != null ? entity.get().getNotifySendDate().getTime() : null);
            dto.setTotalGift(entity.get().getTotalGift());
            dto.setRemains(entity.get().getRemainGift());
            if (entity.get().getPriority() != null && entity.get().getEasyOrderCode() == null) {    // Ignore EasyOrder Promotion
                if (entity.get().getPriority() > 1000) {
                    dto.setPriority(entity.get().getPriority() - 1000);
                } else {
                    dto.setPriority(entity.get().getPriority());
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            JSONObject data = new JSONObject(entity.get().getNewPromotionJson());
            PromotionRuleDTO ruleDTO = mapper.readValue(data.getJSONObject("promotionRule").toString(), PromotionRuleDTO.class);
            dto.setPromotionRule(ruleDTO);
            try {
                dto.setOfferNextProductId(data.getLong("offerNextProductId"));
            } catch (Exception ex) {
            }
            try {
                JSONArray arr = data.getJSONArray("customerTargetIds");
                List<Long> ids = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    ids.add(arr.getLong(i));
                }
                dto.setCustomerTargetIds(ids);
            } catch (Exception ex) {
            }
            PenetrationDTO penetrationDTO = new PenetrationDTO();
            if (entity.get().getPenetrationType() == 3) {
                penetrationDTO = mapper.readValue(data.getJSONObject("penetration").toString(), PenetrationDTO.class);
            } else {
                penetrationDTO.setPenetrationType(entity.get().getPenetrationType());
                penetrationDTO.setPenetrationValue(entity.get().getPenetrationValue());
            }
            dto.setPenetration(penetrationDTO);

            List<String> lstGroupCode = promotionCustomerTargetRepository.findGroupName(entity.get().getOutletPromotionId());
            if (lstGroupCode != null && lstGroupCode.size() > 0) {
                lstGroupCode.toArray();
                dto.setCustomerGroupIds(lstGroupCode.stream().toArray(String[]::new));
            } else {
                dto.setCustomerGroupIds(null);
            }
            List<CustomerGroupDTO> allCustomerGroups = new ArrayList<>();
            List<CustomerGroupEntity> groupList = customerGroupRepository.findIdByOutletOutletId(entity.get().getOutlet().getOutletId());
            for (CustomerGroupEntity customerGroup : groupList) {
                CustomerGroupDTO customerGroupDto = customerGroupMapper.toDto(customerGroup);
                allCustomerGroups.add(customerGroupDto);
            }
            dto.setAllCustomerGroups(allCustomerGroups);
        } else {
            throw new BadRequestException("Can not found promotion - id: " + id);
        }
        return dto;
    }

    public boolean checkOverlap(EnhancedPromotionDTO enhancedPromotionDTO, Long outletId) {
        boolean rs = false;
        if (PromotionType.getEnum(enhancedPromotionDTO.getType()) == PromotionType.ORDER || PromotionType.getEnum(enhancedPromotionDTO.getType()) == PromotionType.SHIPPING)
            return false;
        List<OutletPromotionEntity> entities = outletPromotionRepository.findByOutletOutletIdAndEndDateAfter(outletId, new Timestamp(System.currentTimeMillis()));
        for (OutletPromotionEntity entity : entities) {
            if (entity.getNewPromotionJson() != null) {
                if (enhancedPromotionDTO.getOutletPromotionId() != null)
                    if (entity.getOutletPromotionId().equals(enhancedPromotionDTO.getOutletPromotionId())) continue;
                ObjectMapper mapper = new ObjectMapper();
                try {
                    if (entity.getGroupCode() != null) {
                        continue;
                    }
                    JSONObject jsonObject = new JSONObject(entity.getNewPromotionJson());
                    PromotionRuleDTO ruleDTO = mapper.readValue(jsonObject.getJSONObject("promotionRule").toString(), PromotionRuleDTO.class);
                    if (ruleDTO.getPromotionType() == PromotionType.ORDER || ruleDTO.getPromotionType() == PromotionType.SHIPPING)
                        continue;
                    rs = checkOverlapPromotionByRule(enhancedPromotionDTO.getPromotionRule(), ruleDTO, outletId,
                            new Timestamp(enhancedPromotionDTO.getStartDate()), new Timestamp(enhancedPromotionDTO.getEndDate()),
                            entity.getStartDate(), entity.getEndDate());

                    if (rs) {
                        System.out.println("Overlap with " + entity.getOutletPromotionId());
                        return rs;
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
//                    throw new BadRequestException(ex.getMessage());
                }
            }
        }
        return false;
    }

    public List<OutletDTO> checkOverlap4Admin(EnhancedPromotionDTO enhancedPromotionDTO) {
        List<OutletDTO> outletDTOS = new ArrayList<>();
        List<ApplyShop> applyShops = enhancedPromotionDTO.getLstApplyShop();
        AdminPromotionEntity adminPromotionEntity = null;
        if (enhancedPromotionDTO.getPromotionAdmin() != null) {
            Optional<AdminPromotionEntity> entity = adminPromotionRepository.findById(enhancedPromotionDTO.getPromotionAdmin());
            if (entity.isPresent()) {
                adminPromotionEntity = entity.get();    // edit Admin Promotion
            }
        }
        for (ApplyShop applyShop : applyShops) {
            boolean rs = false;
            List<OutletPromotionEntity> outletPromotionEntities = outletPromotionRepository.getByOutletIdCreateByAdmin(applyShop.getOutletId(), new Timestamp(System.currentTimeMillis()));
            for (OutletPromotionEntity entity : outletPromotionEntities) {
                if (entity.getNewPromotionJson() != null) {
                    if (adminPromotionEntity != null)
                        if (adminPromotionEntity.getGroupCode().equals(entity.getGroupCode()))
                            continue;    // ignore current promotion is editing
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JSONObject jsonObject = new JSONObject(entity.getNewPromotionJson());
                        PromotionRuleDTO ruleDTO = mapper.readValue(jsonObject.getJSONObject("promotionRule").toString(), PromotionRuleDTO.class);
                        if (ruleDTO.getPromotionType() == PromotionType.ORDER || ruleDTO.getPromotionType() == PromotionType.SHIPPING)
                            continue;

                        rs = checkOverlapPromotionByRule(applyShop.getPromotionRule(), ruleDTO, applyShop.getOutletId(),
                                new Timestamp(enhancedPromotionDTO.getStartDate()), new Timestamp(enhancedPromotionDTO.getEndDate()),
                                entity.getStartDate(), entity.getEndDate());
                        if (rs) {
                            System.out.println("Admin Overlap with PromotionID : " + entity.getOutletPromotionId() + " | " + applyShop.getOutletName());
                            outletDTOS.add(new OutletDTO(applyShop.getOutletId(), applyShop.getOutletName()));
                            break;
                        }

//                        rs = PromotionOverlapChecker.isOverlap(applyShop.getPromotionRule(), ruleDTO, applyShop.getOutletId());
//                        if (rs) {
//                            int cnt1 = new Timestamp(enhancedPromotionDTO.getStartDate()).compareTo(entity.getStartDate());
//                            int cnt2 = entity.getStartDate().compareTo(new Timestamp(enhancedPromotionDTO.getEndDate()));
//                            int cnt4 = entity.getStartDate().compareTo(new Timestamp(enhancedPromotionDTO.getStartDate()));
//                            int cnt5 = new Timestamp(enhancedPromotionDTO.getStartDate()).compareTo(entity.getEndDate());
//                            if ((cnt1 <= 0 && cnt2 <= 0) || (cnt4 <= 0 && cnt5 <= 0)) {
//                                System.out.println("Admin Overlap with PromotionID : " + entity.getOutletPromotionId() + " | " + applyShop.getOutletName());
//                                outletDTOS.add(new OutletDTO(applyShop.getOutletId(), applyShop.getOutletName()));
//                                break;
//                            }
//                            rs = false;
//                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            if (!rs) {
                outletDTOS.add(new OutletDTO());
            }
        }
        return outletDTOS;
    }

    public List<ViewItemDTO> getItemForPromotionDetail(List<Long> items, PromotionType type) {
        return items.stream().map(item -> {
            ViewItemDTO viewItemDTO = new ViewItemDTO();
            switch (type) {
                case NEXTPURCHASE:
                case CATEGORY:
                    Optional<CatGroupEntity> catGroupEntity = categoryRepository.findById(item);
                    if (catGroupEntity.isPresent()) {
                        viewItemDTO.setId(catGroupEntity.get().getCatGroupId());
                        viewItemDTO.setName(catGroupEntity.get().getName());
                        if (catGroupEntity.get().getParent() != null) {
                            viewItemDTO.setParentId(catGroupEntity.get().getParent().getCatGroupId());
                        }
                        viewItemDTO.setImage(catGroupEntity.get().getImage());
                    }
                    break;
                case BRAND:
                    Optional<BrandEntity> brandEntity = brandRepository.findById(item);
                    if (brandEntity.isPresent()) {
                        viewItemDTO.setId(brandEntity.get().getBrandId());
                        viewItemDTO.setName(brandEntity.get().getName());
                        viewItemDTO.setImage(brandEntity.get().getImage());
                    }
                    break;
                case PRODUCT:
                    Optional<ProductOutletSkuEntity> productOutletSkuEntity = productOutletSkuRepository.findById(item);
                    if (productOutletSkuEntity.isPresent()) {
                        viewItemDTO.setId(productOutletSkuEntity.get().getProductOutletSkuId());
                        viewItemDTO.setName(productOutletRepository.findById(productOutletSkuEntity.get().getProductOutletId()).get().getName() + " | " + productOutletSkuEntity.get().getTitle());
                        viewItemDTO.setImage(productOutletSkuEntity.get().getImage());
                    }
                    break;
                default:
                    return null;
            }
            return viewItemDTO;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Map<String, Object> getPromotionDetailForAdmin(Long id) throws Exception {
        List<AdminPromotionResponseDTO> applyShops = new ArrayList();
        Map<String, Object> result = new HashMap<>();
        EnhancedPromotionDTO dto = new EnhancedPromotionDTO();
        Optional<AdminPromotionEntity> adminPromotion = adminPromotionRepository.findById(id);
        if (adminPromotion.isPresent()) {
            List<OutletPromotionEntity> outletPromotionEntities = outletPromotionRepository.findByGroupCode(adminPromotion.get().getGroupCode());
            if (outletPromotionEntities != null && outletPromotionEntities.size() > 0) {
                for (OutletPromotionEntity entity : outletPromotionEntities) {
                    dto.setTitle(entity.getTitle());
                    dto.setStartDate(entity.getStartDate().getTime());
                    dto.setEndDate(entity.getEndDate().getTime());
                    dto.setStatus(entity.getStatus());
                    dto.setDescription(entity.getDescription());
                    dto.setThumbnail(entity.getThumbnail());
                    dto.setPromotionProperty(entity.getIsCombined());
                    dto.setMaxPerCustomer(entity.getMaxPerCustomer());
                    dto.setMaxPerOrder(entity.getMaxPerOrder());
                    dto.setNotifyContent(entity.getNotifyContent());
                    dto.setNotifySendDate(entity.getNotifySendDate().getTime());
                    dto.setTotalGift(entity.getTotalGift());
                    dto.setRemains(entity.getRemainGift());
                    dto.setPromotionAdmin(entity.getPromotionAdmin());
                    ObjectMapper mapper = new ObjectMapper();
                    JSONObject dataPromotionJson = null;
                    JSONObject data = null;
                    try {
                        dataPromotionJson = new JSONObject(entity.getNewPromotionJson());
                        data = new JSONObject(entity.getApplyShopByAdmin());
                    } catch (Exception ex) {
                    }
                    ApplyShop applyShop = mapper.readValue(data.toString(), ApplyShop.class);
                    List lstItemCondition = new ArrayList();
                    List itemExcludes = new ArrayList();

                    AdminPromotionResponseDTO adminPromotionResponseDTO = new AdminPromotionResponseDTO();
                    adminPromotionResponseDTO.setOutletPromotionId(entity.getOutletPromotionId());
                    adminPromotionResponseDTO.setOutletId(entity.getOutlet().getOutletId());
                    adminPromotionResponseDTO.setOutletName(entity.getOutlet().getName());
                    adminPromotionResponseDTO.setMaxPerPromotion(entity.getMaxPerPromotion());
                    adminPromotionResponseDTO.setPromotionRule(applyShop.getPromotionRule());
                    adminPromotionResponseDTO.setCustomerTargets(getCustomerTarget(applyShop.getCustomerTargetIds()));
                    adminPromotionResponseDTO.setPriority(entity.getPriority() - 1000);
                    adminPromotionResponseDTO.setRemains(entity.getRemainGift());
                    if (applyShop.getPromotionRule().getPromotionType() != PromotionType.NEXTPURCHASE) {
                        adminPromotionResponseDTO.setLstItemDiscount(applyShop.getPromotionRule().getDiscounts().stream().map(this::findDiscountItemDetail).filter(Objects::nonNull).collect(Collectors.toList()));
                    } else {
                        List<ViewItemDTO> tmp = new ArrayList<>();
                        tmp.add(getProductView(applyShop.getOfferNextProductId()));
                        adminPromotionResponseDTO.setLstItemDiscount(tmp);
                    }

                    //get item when buy have apply promotion
                    if (applyShop.getPromotionRule().getIds() != null && applyShop.getPromotionRule().getIds().size() > 0) {
                        lstItemCondition = this.getItemForPromotionDetail(applyShop.getPromotionRule().getIds(), applyShop.getPromotionRule().getPromotionType());
                    }

                    adminPromotionResponseDTO.setLstItemCondition(lstItemCondition);

                    //get item when buy not have apply promotion
                    if (!(applyShop.getPromotionRule().getExcludeIds() == null)) {
                        itemExcludes = this.getItemForPromotionDetail(applyShop.getPromotionRule().getExcludeIds(), PromotionType.PRODUCT);
                    }
                    adminPromotionResponseDTO.setLstItemExcludes(itemExcludes);

                    List<String> lstGroupCode = promotionCustomerTargetRepository.findGroupName(entity.getOutletPromotionId());
                    if (lstGroupCode != null && lstGroupCode.size() > 0) {
                        lstGroupCode.toArray();
                        adminPromotionResponseDTO.setCustomerGroupIds(lstGroupCode.stream().toArray(String[]::new));
                    }
                    List<CustomerGroupDTO> allCustomerGroups = new ArrayList<>();
                    List<CustomerGroupEntity> groupList = customerGroupRepository.findIdByOutletOutletId(entity.getOutlet().getOutletId());
                    for (CustomerGroupEntity customerGroup : groupList) {
                        CustomerGroupDTO customerGroupDto = customerGroupMapper.toDto(customerGroup);
                        allCustomerGroups.add(customerGroupDto);
                    }
                    dto.setAllCustomerGroups(allCustomerGroups);

                    applyShops.add(adminPromotionResponseDTO);
                    PenetrationDTO penetrationDTO = new PenetrationDTO();
                    if (entity.getPenetrationType() == 3) {
                        penetrationDTO = mapper.readValue(dataPromotionJson.getJSONObject("penetration").toString(), PenetrationDTO.class);
                    } else {
                        penetrationDTO.setPenetrationType(entity.getPenetrationType());
                        penetrationDTO.setPenetrationValue(entity.getPenetrationValue());
                    }
                    dto.setPenetration(penetrationDTO);
                }
                setIntoMap(dto, applyShops, result);
            } else {
                throw new BadRequestException("Can not found admin promotion - id: " + id);
            }
            return result;
        } else {
            throw new BadRequestException("Can not found admin promotion - id: " + id);
        }
    }

    public Map<String, Object> findDiscountItemDetail(DiscountDTO dto) {
        Map<String, Object> rs = new HashMap<>();
        for (long target : dto.getTargetIds()) {
            switch (dto.getDiscountType()) {
                case GIFT:
                    GiftEntity giftEntity = giftRepository.findById(target).get();
                    rs.put("id", giftEntity.getGiftId());
                    rs.put("name", giftEntity.getName());
                    rs.put("image", giftEntity.getImage());
                    break;
                case PRODUCT:
                case FIX_PRICE:
                    ProductOutletSkuEntity skuEntity = productOutletSkuRepository.findById(target).get();
                    ProductOutletEntity productOutletEntity = productOutletRepository.findById(skuEntity.getProductOutletId()).get();
                    rs.put("id", skuEntity.getProductOutletSkuId());
                    rs.put("name", productOutletEntity.getName() + " | " + skuEntity.getTitle());
                    rs.put("image", skuEntity.getImage());
                    break;
            }
        }
        if (rs.isEmpty()) return null;
        return rs;
    }

    public Map<String, Object> setIntoMap(EnhancedPromotionDTO dto, List<AdminPromotionResponseDTO> lstApplyShop, Map<String, Object> result) {
        result.put("title", dto.getTitle());
        result.put("startDate", dto.getStartDate());
        result.put("endDate", dto.getEndDate());
        result.put("status", dto.getStatus());
        result.put("description", dto.getDescription());
        result.put("thumbnail", dto.getThumbnail());
        result.put("promotionProperty", dto.getPromotionProperty());
        result.put("lstApplyShop", lstApplyShop);
        result.put("maxPerCustomer", dto.getMaxPerCustomer());
        result.put("maxPerOrder", dto.getMaxPerOrder());
        result.put("priority", dto.getPriority());
        result.put("notifyContent", dto.getNotifyContent());
        result.put("notifySendDate", dto.getNotifySendDate());
        result.put("promotionRule", dto.getPromotionRule());
        if (dto.getPenetration().getPenetrationType() != 0) {
            result.put("penetration", dto.getPenetration());
        }
        //get item in case penetration
        List<Long> lstPenetrationValue = new ArrayList<>();
        if (dto.getPenetration() != null && dto.getPenetration().getPenetrationType() != 3 && dto.getPenetration().getPenetrationType() != 0) {
            lstPenetrationValue.add(Long.parseLong(dto.getPenetration().getPenetrationValue()));
            if (dto.getPenetration().getPenetrationType() == 1) {
                result.put("itemPenetration", this.getItemForPromotionDetail(lstPenetrationValue, PromotionType.CATEGORY));
            } else {
                result.put("itemPenetration", this.getItemForPromotionDetail(lstPenetrationValue, PromotionType.BRAND));
            }
        }
        return result;
    }

    public Map<String, Object> createOrUpdate4Admin(EnhancedPromotionDTO enhancedPromotionDTO, Long countryId) {
        ObjectWriter ow = new ObjectMapper().writer();
        ObjectMapper om = new ObjectMapper();
        Map<String, Object> output = new HashMap<>();
        try {
            //save admin promotion
            AdminPromotionEntity adminPromotionEntity = new AdminPromotionEntity();
            if (enhancedPromotionDTO.getPromotionAdmin() == null) {
                adminPromotionEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                Long promotionIdForAdmin = System.currentTimeMillis();
                adminPromotionEntity.setGroupCode(promotionIdForAdmin.toString());
            } else {
                adminPromotionEntity = adminPromotionRepository.findById(enhancedPromotionDTO.getPromotionAdmin()).get();
                //remove outlet promotion
                List<OutletPromotionEntity> entities = outletPromotionRepository.findByGroupCode(adminPromotionEntity.getGroupCode());
                for (OutletPromotionEntity entity : entities) {
                    promotionCustomerTargetRepository.deleteAll(promotionCustomerTargetRepository.findByOutletPromotionId(entity.getOutletPromotionId()));
                    promotionCustomerTargetRepository.flush();
                    outletPromotionRepository.delete(entity);
                    outletPromotionRepository.flush();
                }
                enhancedPromotionDTO.setType(new JSONObject(adminPromotionEntity.getNewPromotionJson()).getInt("type"));
            }
            adminPromotionEntity.setName(enhancedPromotionDTO.getTitle());
            adminPromotionEntity.setStartDate(new Timestamp(enhancedPromotionDTO.getStartDate()));
            adminPromotionEntity.setEndDate(new Timestamp(enhancedPromotionDTO.getEndDate()));
            adminPromotionEntity.setStatus(enhancedPromotionDTO.getStatus());
            adminPromotionEntity.setNewPromotionJson(ow.writeValueAsString(enhancedPromotionDTO));
            adminPromotionEntity.setNumberPromotion(1);      //TODO just for test
            adminPromotionEntity.setCountryId(countryId);
            adminPromotionEntity = adminPromotionRepository.save(adminPromotionEntity);

            //add outlet promotion
            List<Long> ids = new ArrayList<>();
            List<ApplyShop> applyShops = enhancedPromotionDTO.getLstApplyShop();
            for (ApplyShop applyShop : applyShops) {
                if (applyShop.getOutletPromotionId() == null) {
                    enhancedPromotionDTO.setOutletPromotionId(null);
                } else {
                    enhancedPromotionDTO.setOutletPromotionId(applyShop.getOutletPromotionId());
                }
                enhancedPromotionDTO.setCustomerTargetIds(applyShop.getCustomerTargetIds());
                enhancedPromotionDTO.setOutletId(applyShop.getOutletId());
                enhancedPromotionDTO.setCustomerGroupIds(applyShop.getCustomerGroupIds());
                enhancedPromotionDTO.setMaxPerPromotion(applyShop.getMaxPerPromotion());
                enhancedPromotionDTO.setPromotionRule(applyShop.getPromotionRule());
                enhancedPromotionDTO.setPromotionAdmin(Long.parseLong(adminPromotionEntity.getGroupCode()));
                enhancedPromotionDTO.setApplyShopByAdmin(applyShop);
                enhancedPromotionDTO.setPriority(applyShop.getPriority() + 1000);
                enhancedPromotionDTO.setPromotionRule(applyShop.getPromotionRule());
                enhancedPromotionDTO.setOfferNextProductId(applyShop.getOfferNextProductId());
                enhancedPromotionDTO.setLstApplyShop(null);
                enhancedPromotionDTO.setNewPromotionJson(ow.writeValueAsString(enhancedPromotionDTO));
                enhancedPromotionDTO.setLstApplyShop(applyShops);
                ids.add(savePromotion(enhancedPromotionDTO));
            }
            output.put("PromotionIdForAdmin", ids);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return output;
    }

    public Map<String, Object> findAllProductNonBlock(String name, String code, String category, Long outletId, Long countryId, Pageable pageable) {
        Map<String, Object> rs = new HashMap<>();
        outletId = (outletId == null) ? -1 : outletId;
        name = (name == null) ? "" : name;
        code = (code == null) ? "" : code;
        category = (category == null) ? "" : category;
        if (outletId == -1) {
            Page<BlockProductAdminEntity> entities = blockProductAdminRepository.findNonBlockProduct4Admin(category, name, code, countryId, pageable);
            rs.put("totalElement", entities.getTotalElements());
            rs.put("totalPage", entities.getTotalPages());
            rs.put("list", BlockProductAdminUtils.entity2dto(entities.get().collect(Collectors.toList())));
            return rs;
        }
        Page<BlockProductEntity> entities = blockProductRepository.findNonBlockProduct(outletId, category, name, code, countryId, pageable);
        rs.put("totalElement", entities.getTotalElements());
        rs.put("totalPage", entities.getTotalPages());
        rs.put("list", BlockProductUtils.entity2dto(entities.get().collect(Collectors.toList())));
        return rs;
    }

    public Map<String, Object> getProductBlock(Long outletId, String name, String code, String category, Long countryId, Pageable pageable) {
        Map<String, Object> rs = new HashMap<>();
        outletId = (outletId == null) ? -1 : outletId;
        name = (name == null) ? "" : name;
        code = (code == null) ? "" : code;
        category = (category == null) ? "" : category;
        List<BlockProductDTO> dtos = new ArrayList<>();
        if (outletId == -1) {
            Page<BlockProductAdminEntity> entities = blockProductAdminRepository.findBlockProduct4Admin(category, name, code, countryId, pageable);
            rs.put("totalElement", entities.getTotalElements());
            rs.put("totalPage", entities.getTotalPages());
            dtos = BlockProductAdminUtils.entity2dto(entities.get().collect(Collectors.toList()));
        } else {
            Page<BlockProductEntity> entities = blockProductRepository.findBlockProduct(outletId, category, name, code, countryId, pageable);
            rs.put("totalElement", entities.getTotalElements());
            rs.put("totalPage", entities.getTotalPages());
            dtos = BlockProductUtils.entity2dto(entities.get().collect(Collectors.toList()));
        }
//        List<Long> data = productPromotionBlockedRepository.findByOutletIdIsNull().stream().map(ProductPromotionBlockedEntity::getProductOutletSkuId).collect(Collectors.toList());
        for (BlockProductDTO dto : dtos) {
            dto.setCanDelete(true);
        }
        rs.put("list", dtos);
        return rs;
    }

    public void deleteAdminPromotion(Long id) {
        Optional<AdminPromotionEntity> adminPromotionEntity = adminPromotionRepository.findById(id);
        if (adminPromotionEntity.isPresent()) {
            List<OutletPromotionEntity> entities = outletPromotionRepository.findByGroupCode(adminPromotionEntity.get().getGroupCode());
            for (OutletPromotionEntity entity : entities) {
                deleteOutletPromotion(entity);
            }
            adminPromotionRepository.delete(adminPromotionEntity.get());
        } else {
            throw new BadRequestException("Can not find promotion");
        }
    }

    public int deleteOutletBlock(List<Long> ids, Long outletId) {
        List<Long> data = productPromotionBlockedRepository.findByOutletId(outletId).stream().map(ProductPromotionBlockedEntity::getProductOutletSkuId).collect(Collectors.toList());
        int rs = 0;
        for (Long id : ids) {
            if (data.contains(id)) {
                productPromotionBlockedRepository.deleteByProductOutletSkuIdAndOutletId(id, outletId);
                rs++;
            }
        }
        productPromotionBlockedRepository.flush();
        return rs;
    }

    public int deleteAdminBlock(List<Long> ids) {
        List<Long> data = productPromotionBlockedRepository.findByOutletIdIsNull().stream().map(ProductPromotionBlockedEntity::getProductId).collect(Collectors.toList());
        int rs = 0;
        for (Long id : ids) {
            if (data.contains(id)) {
                productPromotionBlockedRepository.deleteByProductId(id);
                rs++;
            }
        }
        productPromotionBlockedRepository.flush();
        return rs;
    }

    public Page getSyncPromotion(Long userId, String token, Pageable pageable) {
        Long outletId = outletEmployeeRepository.findByUserId(userId).getOutletId();
        Timestamp time;
        try {
            time = syncVersionTrackingRepository.findByUserIdAndTypeSyncAndToken(userId, Constants.SYNC_PROMOTION, token).getSyncDate();
        } catch (Exception ex) {
            time = new Timestamp(0);
        }
        return outletPromotionRepository.findByOutletOutletIdAndModifiedDateAfterAndEasyOrderCodeIsNullAndPenetrationType(outletId, time, 0, pageable).map(entity -> {
            try {
                EnhancedPromotionDTO data = getPromotionDetail(entity.getOutletPromotionId());
                data.setOutletPromotionId(entity.getOutletPromotionId());
                data.setThumbnail(CommonUtils.getBase64EncodedImage(env.getProperty("promotion.image.server", "") + data.getThumbnail()));
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> rs = mapper.convertValue(data, Map.class);
                rs.put("rule", entity.getPromotionRule());
                rs.put("modifiedDate", entity.getModifiedDate().getTime());
                if (data.getPromotionRule().getDiscountType() == DiscountType.GIFT) {
                    Set<Long> giftIds = new HashSet<>();
                    data.getPromotionRule().getDiscounts().forEach(dto -> giftIds.addAll(dto.getTargetIds()));
                    List<GiftDTO> dtos = giftRepository.findByGiftIdIn(giftIds).stream().map(giftEntity -> {
                        GiftDTO dto = new GiftDTO();
                        dto.setGiftId(giftEntity.getGiftId());
                        dto.setName(giftEntity.getName());
                        dto.setImage(CommonUtils.getBase64EncodedImage(env.getProperty("url.image.server", "") + giftEntity.getImage()));
                        return dto;
                    }).collect(Collectors.toList());
                    rs.put("gifts", dtos);
                }
                return rs;
            } catch (Exception e) {
                e.printStackTrace();
                throw new BadRequestException("Can not load promotion - id: " + entity.getOutletPromotionId());
            }
        });
    }

    public void makeSyncDone(Long userId, String token, String syncType) {
        Long outletId = outletEmployeeRepository.findByUserId(userId).getOutletId();
        SyncVersionTrackingEntity entity = syncVersionTrackingRepository.findByUserIdAndTypeSyncAndToken(userId, syncType == null ? Constants.SYNC_PROMOTION : Constants.SYNC_PROMOTION_VANSALE, token);
        if (entity == null) {
            entity = new SyncVersionTrackingEntity();
        }
        entity.setOutletId(outletId);
        entity.setSyncDate(new Timestamp(System.currentTimeMillis()));
        entity.setToken(token);
        entity.setUserId(userId);
        entity.setTypeSync(syncType == null ? Constants.SYNC_PROMOTION : Constants.SYNC_PROMOTION_VANSALE);
        syncVersionTrackingRepository.saveAndFlush(entity);
    }

    public ViewItemDTO getProductView(Long productOutletSkuId) {
        ProductOutletSkuEntity productOutletSkuEntity = productOutletSkuRepository.findById(productOutletSkuId).get();
        ProductOutletEntity productOutletEntity = productOutletRepository.findById(productOutletSkuEntity.getProductOutletId()).get();
        ViewItemDTO rs = new ViewItemDTO();
        rs.setImage(productOutletSkuEntity.getImage());
        rs.setId(productOutletSkuEntity.getProductOutletSkuId());
        rs.setName(productOutletEntity.getName() + " | " + productOutletSkuEntity.getTitle());
        return rs;
    }

    public List<UserDTO> getCustomerTarget(List<Long> userIds) {
        return userIds == null ? new ArrayList<>() : userRepository.findByUserIdIn(userIds).stream().map(entity -> {
            UserDTO rs = new UserDTO();
            rs.setUserId(entity.getUserId());
            rs.setFullName(entity.getFullName());
            rs.setEmail(entity.getEmail());
            rs.setPhoneNumber(entity.getPhoneNumber());
            return rs;
        }).collect(Collectors.toList());
    }

    public QuickSellPromotionDTO getPromotionDataForQuickSell(Long warehouseId, Long limit) {
        List<OutletPromotionDTO> promotionsOfOutlet = new ArrayList<>();            //promotion by product in warehouse vansale
        List<ProductOutletSkuDTO> productsWithPromotion = new ArrayList<>();        //products have promotion

        Long outletId = warehouseRepository.findById(warehouseId).get().getOutlet().getOutletId();

        //get list promotion in outlet
        List<OutletPromotionDTO> outletPromotionDTOS = this.getPromotionsInOutlet(outletId);

        //get list product in warehouse vans sale have available
        List<ProductOutletSkuEntity> productAvailable = this.getProductsAvailableVansale(warehouseId, limit);
        for (OutletPromotionDTO dto : outletPromotionDTOS)

            for (ProductOutletSkuEntity entity : productAvailable) {
                ProductEntity productEntity = productRepository.findProductsInWarehouseVanSale(outletId, entity.getProductOutletSkuId());

                //category promotion
                if (dto.getTypePromotion().equals(PromotionType.CATEGORY.getNo())) {
                    if (productEntity.getCatGroup().getCatGroupId().equals(dto.getIdCondition())) {
                        for (Long id : dto.getOutletPromotionIds()) {
                            OutletPromotionEntity outletPromotionEntity = outletPromotionRepository.findById(id).get();
                            int quantityHasApplied = outletPromotionEntity.getMaxPerPromotion() - outletPromotionEntity.getRemainGift();          //quantity has applied
                            promotionsOfOutlet.add(PromotionUtils.entity2DtoForVansales(outletPromotionEntity, quantityHasApplied));
                        }
                        productsWithPromotion.add(ProductOutletSkuBeanUtils.entity2DTO(entity));
                        break;
                    }
                }

                //brand promotion
                if (dto.getTypePromotion().equals(PromotionType.BRAND.getNo())) {
                    if (productEntity.getBrand().getBrandId().equals(dto.getIdCondition())) {
                        for (Long id : dto.getOutletPromotionIds()) {
                            OutletPromotionEntity outletPromotionEntity = outletPromotionRepository.findById(id).get();
                            int quantityHasApplied = outletPromotionEntity.getMaxPerPromotion() - outletPromotionEntity.getRemainGift();          //quantity has applied
                            promotionsOfOutlet.add(PromotionUtils.entity2DtoForVansales(outletPromotionEntity, quantityHasApplied));
                        }
                        productsWithPromotion.add(ProductOutletSkuBeanUtils.entity2DTO(entity));
                        break;
                    }
                }

                //product promotion
                if (dto.getTypePromotion().equals(PromotionType.PRODUCT.getNo())) {
                    if (entity.getProductOutletSkuId().equals(dto.getIdCondition())) {
                        for (Long id : dto.getOutletPromotionIds()) {
                            OutletPromotionEntity outletPromotionEntity = outletPromotionRepository.findById(id).get();
                            int quantityHasApplied = outletPromotionEntity.getMaxPerPromotion() - outletPromotionEntity.getRemainGift();          //quantity has applied
                            promotionsOfOutlet.add(PromotionUtils.entity2DtoForVansales(outletPromotionEntity, quantityHasApplied));
                        }
                        productsWithPromotion.add(ProductOutletSkuBeanUtils.entity2DTO(entity));
                        break;
                    }
                }
            }


        //get list campaign is active of outlet
        List<CampaignEntity> campaignEntities = campaignRepository.findByOutletOutletIdAndStatus(outletId, 1);

        //sort promotion by quantity has applied
        List<OutletPromotionDTO> hotPromotions = promotionsOfOutlet.stream().sorted(Comparator.comparingInt(OutletPromotionDTO::getQuantityApplied).reversed()).limit(limit == null ? Long.MAX_VALUE : limit).collect(Collectors.toList());

        //show all product in warehouse van sales
        List<ProductOutletSkuDTO> recommendProducts = ProductOutletSkuBeanUtils.entity2DTO(productAvailable);

        //limit list product
        productsWithPromotion.stream().limit(limit == null ? Long.MAX_VALUE : limit).collect(Collectors.toList());

        return new QuickSellPromotionDTO(CampaignBeanUtils.entity2DTO(campaignEntities), hotPromotions, recommendProducts, productsWithPromotion);
    }

    public QuickSellCategoryDTO getProductDataForQuickSell(Long warehouseId, Long limit) {
        Set<Long> categoryId = new HashSet<>();
        Set<Long> subcatId = new HashSet<>();
        List<ViewItemDTO> categories = new ArrayList<>();
        List<ViewItemDTO> subcat = new ArrayList<>();
        //get list product in warehouse vans sale
        List<ProductOutletSkuEntity> productOutletSkuEntities = this.getProductsAvailableVansale(warehouseId, limit == null ? Long.MAX_VALUE : limit);
        for (ProductOutletSkuEntity entity : productOutletSkuEntities) {
            ProductDTO productDTO = catalogRepository.getProductByProductOutletSkuId(entity.getProductOutletSkuId());
            if (!Objects.isNull(productDTO)) {
                CatGroupEntity catGroupEntity = categoryRepository.findByCatGroupId(productDTO.getCatGroupId());

                //subcategory
                if (!Objects.isNull(catGroupEntity.getParent())) {
                    subcatId.add(catGroupEntity.getCatGroupId());
                    categoryId.add(catGroupEntity.getParent().getCatGroupId());
                } else {
                    //category
                    categoryId.add(catGroupEntity.getCatGroupId());
                }
            }
        }

        //show view for GUI
        List<CatGroupEntity> catGroupEntities = categoryRepository.findByCatGroupIdIn(categoryId);
        categories = catGroupEntities.stream().map(x -> {
            ViewItemDTO viewItemDTO = new ViewItemDTO();
            viewItemDTO.setId(x.getCatGroupId());
            viewItemDTO.setImage(x.getImage());
            viewItemDTO.setName(x.getName());
            return viewItemDTO;
        }).limit(limit == null ? Long.MAX_VALUE : limit).collect(Collectors.toList());

        //show view for GUI
        List<CatGroupEntity> subcatGroupEntities = categoryRepository.findByCatGroupIdIn(subcatId);
        subcat = subcatGroupEntities.stream().map(x -> {
            ViewItemDTO viewItemDTO = new ViewItemDTO();
            viewItemDTO.setId(x.getCatGroupId());
            viewItemDTO.setImage(x.getImage());
            viewItemDTO.setName(x.getName());
            return viewItemDTO;
        }).limit(limit == null ? Long.MAX_VALUE : limit).collect(Collectors.toList());

        return new QuickSellCategoryDTO(categories, subcat);
    }

    public int countProductNonExpire(ProductOutletStorageEntity entity) {
        int rs = 0;
        for (ProductOutletStorageDetailEntity productOutletStorageDetail : entity.getProductOutletStorageDetails()) {
            rs += productOutletStorageDetail.getExpireDate().getTime() > System.currentTimeMillis() ? productOutletStorageDetail.getQuantity() : 0;
        }
        return rs;
    }

    public List<ProductOutletSkuEntity> getProductsAvailableVansale(Long warehouseId, Long limit) {
        return productOutletStorageRepository.findByWareHouseWareHouseId(warehouseId).stream().map(storageEntity -> {
            ProductOutletSkuEntity productOutletSkuEntity = new ProductOutletSkuEntity();
            int rs = this.countProductNonExpire(storageEntity);
            if (rs > 0) {
                productOutletSkuEntity.setProductOutletSkuId(storageEntity.getProductOutletSku().getProductOutletSkuId());
                productOutletSkuEntity.setBarcode(storageEntity.getProductOutletSku().getBarcode());
                productOutletSkuEntity.setImage(storageEntity.getProductOutletSku().getImage());
                productOutletSkuEntity.setOriginalPrice(storageEntity.getProductOutletSku().getOriginalPrice());
                productOutletSkuEntity.setSalePrice(storageEntity.getProductOutletSku().getSalePrice());
                productOutletSkuEntity.setStatus(storageEntity.getProductOutletSku().getStatus());
                productOutletSkuEntity.setTitle(storageEntity.getProductOutletSku().getTitle());
                productOutletSkuEntity.setUnit(storageEntity.getProductOutletSku().getUnit());
                productOutletSkuEntity.setProductOutletId(storageEntity.getProductOutletSku().getProductOutletId());
                return productOutletSkuEntity;
            } else {
                return null;
            }
        }).filter(x -> x != null).limit(limit == null ? Long.MAX_VALUE : limit).collect(Collectors.toList());
    }

    /*Get List Promotion With Type (0: Category Promotion, 1: Brand Promotion, 2: Product Promotion, 3: Order, Shipping Promotion, 4: Discount for the next purchase) */
    public List<OutletPromotionDTO> filterTypePromotion(List<OutletPromotionEntity> outletPromotionEntities, String fromSrc) {
        List<OutletPromotionDTO> outletPromotionPreList = new ArrayList<>();
        for (OutletPromotionEntity outletPromotionEntity : outletPromotionEntities) {
            if (outletPromotionEntity.getNewPromotionJson() != null) {
                JSONObject jsonObject = null;
                JSONArray ids = new JSONArray();
                try {
                    jsonObject = new JSONObject(outletPromotionEntity.getNewPromotionJson());
                    int typePromotion = jsonObject.getInt("type");
                    if (typePromotion > 2 && (fromSrc != null && !Constants.SOURCE_SMART_VANSALE.equals(fromSrc)))
                        continue; //  0: Category Promotion, 1: Brand Promotion, 2: Product Promotion
                    ids = jsonObject.getJSONObject("promotionRule").getJSONArray("ids");
                    if (ids != null && ids.length() > 0) {
                        for (int i = 0; i < ids.length(); i++) {
                            outletPromotionPreList.add(new OutletPromotionDTO(ids.getLong(i), outletPromotionEntity.getOutletPromotionId(), typePromotion));
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }

        outletPromotionPreList.sort((a, b) -> {
            return a.getIdCondition() > b.getIdCondition() ? 1 : -1;
        });

        List<OutletPromotionDTO> outletPromotionDTOS = new ArrayList<>();
        List<Long> outletPromotionIds = new ArrayList<Long>();
        if (outletPromotionPreList.size() == 0) return outletPromotionPreList;
        Long idGr = outletPromotionPreList.get(0).getIdCondition();
        int typePromotion = outletPromotionPreList.get(0).getTypePromotion();
        for (int i = 0; i < outletPromotionPreList.size(); i++) {
            OutletPromotionDTO outletPromotionDTO = outletPromotionPreList.get(i);
            if (!idGr.equals(outletPromotionDTO.getIdCondition()) || typePromotion != outletPromotionDTO.getTypePromotion()) {
                outletPromotionDTOS.add(new OutletPromotionDTO(idGr, typePromotion, outletPromotionIds));
                outletPromotionIds = new ArrayList<>();
                idGr = outletPromotionDTO.getIdCondition();
                typePromotion = outletPromotionDTO.getTypePromotion();
            }
            outletPromotionIds.add(outletPromotionDTO.getOutletPromotionId());
        }
        outletPromotionDTOS.add(new OutletPromotionDTO(idGr, typePromotion, outletPromotionIds));

        return outletPromotionDTOS;
    }

    public boolean[] checkPromotion(Long userId, long outletId, List<Long> ids, PromotionType type) {
//        System.out.println("Begin get promotion - time: " + System.currentTimeMillis());
        boolean[] rs = new boolean[ids.size()];
        CustomerEnity customer = null;
        String customerGroupName = null;
        Long customerGroupId = null;
        if (userId != null) {
            customer = customerRepository.findByUserId(userId);
            if (customer != null) {
                customerGroupId = loyaltyMemberRepository.findByCustomerIdAndOutletId(customer.getCustomerId(), outletId).getCustomerGroupId();
            }
            if (customerGroupId != null) {
                customerGroupName = customerGroupRepository.getOne(customerGroupId).getGroupName();
            }
        }

        for (OutletPromotionEntity entity : outletPromotionRepository.getAllOutLetPromotionByOutletId(outletId, new Timestamp(System.currentTimeMillis()))) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                EnhancedPromotionDTO promotion = mapper.readValue(entity.getNewPromotionJson(), EnhancedPromotionDTO.class);
                if (promotion.getType() != PromotionType.CATEGORY.getNo() && promotion.getType() != PromotionType.PRODUCT.getNo() && promotion.getType() != PromotionType.BRAND.getNo()) {
                    continue;
                }
                if (customer != null) {
                    if (!canApply(customer, customerGroupName, outletId, promotion)) {
                        continue;
                    }
                } else {
                    if (!promotion.getPromotionRule().getIsAll()) {
                        continue;
                    }
                }
                if (promotion.getType() != type.getNo() && type != PromotionType.PRODUCT) {
                    continue;
                }
                if (type == PromotionType.PRODUCT) {
                    if (promotion.getType() == PromotionType.CATEGORY.getNo()) {
                        Set<Long> skuIds = catalogRepository.findAllSkuIdByCatGroupIds(outletId, promotion.getPromotionRule().getIds()).getBody();
                        if (skuIds != null) {
                            for (int i = 0; i < ids.size(); i++) {
                                if (!promotion.getPromotionRule().getExcludeIds().contains(ids.get(i))) {
                                    if (skuIds.contains(Long.parseLong(ids.get(i).toString()))) {
                                        rs[i] = true;
                                    }
                                }
                            }
                        }
                    }
                    if (promotion.getType() == PromotionType.PRODUCT.getNo()) {
                        promotion.getPromotionRule().getIds().forEach(id -> {
                            for (int i = 0; i < ids.size(); i++) {
                                if (id.equals(Long.parseLong(ids.get(i).toString()))) {
                                    if (!promotion.getPromotionRule().getExcludeIds().contains(ids.get(i))) {
                                        rs[i] = true;
                                    }
                                }
                            }
                        });
                    }
                    if (promotion.getType() == PromotionType.BRAND.getNo()) {
                        Set<Long> skuIds = catalogRepository.findAllSkuIdByBrandIds(outletId, promotion.getPromotionRule().getIds()).getBody();
                        if (skuIds != null) {
                            for (int i = 0; i < ids.size(); i++) {
                                if (!promotion.getPromotionRule().getExcludeIds().contains(ids.get(i))) {
                                    if (skuIds.contains(Long.parseLong(ids.get(i).toString()))) {
                                        rs[i] = true;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    promotion.getPromotionRule().getIds().forEach(id -> {
                        for (int i = 0; i < ids.size(); i++) {
                            if (id.equals(Long.parseLong(ids.get(i).toString()))) {
                                rs[i] = true;
                            }
                        }
                    });
                }
            } catch (JsonProcessingException e) {
            }
        }
//        System.out.println("End get promotion - time: " + System.currentTimeMillis());
        return rs;
    }

    public List<OutletPromotionDTO>[] getPromotionDetail(List<Long> ids, Long userId, long outletId) {
        List<OutletPromotionDTO>[] rs = new ArrayList[ids.size()];
        for (int i = 0; i < rs.length; i++) {
            rs[i] = new ArrayList<>();
        }
        CustomerEnity customer = null;
        String customerGroupName = null;
        Long customerGroupId = null;
        if (userId != null) {
            customer = customerRepository.findByUserId(userId);
            customerGroupId = loyaltyMemberRepository.findByCustomerIdAndOutletId(customer.getCustomerId(), outletId).getCustomerGroupId();
            if (customerGroupId != null) {
                customerGroupName = customerGroupRepository.getOne(customerGroupId).getGroupName();
            }
        }
        for (OutletPromotionEntity entity : outletPromotionRepository.getAllOutLetPromotionByOutletId(outletId, new Timestamp(System.currentTimeMillis()))) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                EnhancedPromotionDTO promotion = mapper.readValue(entity.getNewPromotionJson(), EnhancedPromotionDTO.class);
                if (promotion.getType() != PromotionType.CATEGORY.getNo() && promotion.getType() != PromotionType.PRODUCT.getNo() && promotion.getType() != PromotionType.BRAND.getNo()) {
                    continue;
                }
                if (customer != null) {
                    if (!canApply(customer, customerGroupName, outletId, promotion)) {
                        continue;
                    }
                } else {
                    if (!promotion.getPromotionRule().getIsAll()) {
                        continue;
                    }
                }
                if (promotion.getType() == PromotionType.CATEGORY.getNo()) {
                    Set<Long> skuIds = catalogRepository.findAllSkuIdByCatGroupIds(outletId, promotion.getPromotionRule().getIds()).getBody();
                    if (skuIds != null) {
                        for (int i = 0; i < ids.size(); i++) {
                            if (!promotion.getPromotionRule().getExcludeIds().contains(ids.get(i))) {
                                if (skuIds.contains(Long.parseLong(ids.get(i).toString()))) {
                                    rs[i].add(new OutletPromotionDTO(entity.getOutletPromotionId(), promotion.getTitle(), null));
                                }
                            }
                        }
                    }
                }
                if (promotion.getType() == PromotionType.PRODUCT.getNo()) {
                    promotion.getPromotionRule().getIds().forEach(id -> {
                        for (int i = 0; i < ids.size(); i++) {
                            if (id.equals(Long.parseLong(ids.get(i).toString()))) {
                                if (!promotion.getPromotionRule().getExcludeIds().contains(ids.get(i))) {
                                    rs[i].add(new OutletPromotionDTO(entity.getOutletPromotionId(), promotion.getTitle(), null));
                                }
                            }
                        }
                    });
                }
                if (promotion.getType() == PromotionType.BRAND.getNo()) {
                    Set<Long> skuIds = catalogRepository.findAllSkuIdByBrandIds(outletId, promotion.getPromotionRule().getIds()).getBody();
                    if (skuIds != null) {
                        for (int i = 0; i < ids.size(); i++) {
                            if (!promotion.getPromotionRule().getExcludeIds().contains(ids.get(i))) {
                                if (skuIds.contains(Long.parseLong(ids.get(i).toString()))) {
                                    rs[i].add(new OutletPromotionDTO(entity.getOutletPromotionId(), promotion.getTitle(), null));
                                }
                            }
                        }
                    }
                }

            } catch (JsonProcessingException e) {
            }
        }
        return rs;
    }

    public boolean canApply(CustomerEnity customer, String customerGroupName, Long outletId, EnhancedPromotionDTO promotion) {
        if (!promotion.getPromotionRule().getIsAll()) {
            //check individual user
            if (promotion.getCustomerTargetIds() != null && promotion.getCustomerTargetIds().size() > 0) {
                boolean flag = false;
                for (Long customerTargetId : promotion.getCustomerTargetIds()) {
                    if (customerTargetId.equals(customer.getUserId())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) return false;
            }
            //check customer group user
            if (promotion.getCustomerGroupIds() != null && promotion.getCustomerGroupIds().length > 0) {
                if (customerGroupName == null) return false;
                boolean flag = false;
                for (String groupName : promotion.getCustomerGroupIds()) {
                    if (groupName.equals(customerGroupName)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) return false;
            }
        }
        // check first order
        if (promotion.getPromotionRule().isFirstOrder()) {
            Integer count = orderRepository.countByOutletOutletIdAndCustomerId(outletId, customer.getCustomerId());
            return count == null || count <= 0;
        }
        return true;
    }

    public Map<String, Object> getPromotionDetailForMobile(Long id) {
        Map<String,Object> rs = new HashMap<>();
        List<OutletPromotionEntity> outletPromotionEntity = new ArrayList<>();
        outletPromotionEntity.add(outletPromotionRepository.findById(id).get());
        Long outletId = outletPromotionEntity.get(0).getOutlet().getOutletId();
        List<OutletPromotionDTO> outletPromotionDTOS = this.filterTypePromotion(outletPromotionEntity, Constants.SOURCE_SMART_VANSALE);

        List<ProductEntity> productEntity;
        List<ProductOutletEntity> productOutletEntities = new ArrayList<>();
        Map<Long, List<ProductOutletSkuEntity>> mapSkus = new HashMap<>();
        List<ProductOutletSkuEntity> productOutletSkuEntities = new ArrayList<>();
        //Classify promotion
        for (OutletPromotionDTO dto : outletPromotionDTOS) {

            //CATEGORY promotion , DISCOUNT FOR NEXT PURCHASE
            if (dto.getTypePromotion().equals(PromotionType.CATEGORY.getNo()) || dto.getTypePromotion().equals(PromotionType.NEXTPURCHASE.getNo())) {
                //get product by cate
                productOutletEntities = productOutletRepository.findByCatGroupAndBrand(outletId,dto.getIdCondition(),-1L);
                rs.put("targetId",dto.getIdCondition());
                rs.put("targetType",PromotionType.CATEGORY.getValue());
            }

            //BRAND promotion
            if (dto.getTypePromotion().equals(PromotionType.BRAND.getNo())) {
                //get product by brand
                productOutletEntities = productOutletRepository.findByCatGroupAndBrand(outletId,-1L,dto.getIdCondition());
                rs.put("targetId",dto.getIdCondition());
                rs.put("targetType",PromotionType.BRAND.getValue());
            }

            //PRODUCT promotion
            if (dto.getTypePromotion().equals(PromotionType.PRODUCT.getNo())) {
                //get sku
                ProductOutletSkuEntity productOutletSkuEntity = productOutletSkuRepository.findById(dto.getIdCondition()).get();
                ProductOutletEntity productOutletEntity = productOutletRepository.findById(productOutletSkuEntity.getProductOutletId()).get();

                //check same productoutlet
                if (mapSkus.containsKey(productOutletEntity.getProductOutletId())){
                    mapSkus.get(productOutletEntity.getProductOutletId()).add(productOutletSkuEntity);
                }else{
                    productOutletEntities.add(productOutletEntity);
                    productOutletSkuEntities.add(productOutletSkuEntity);
                    mapSkus.put(productOutletEntity.getProductOutletId(),productOutletSkuEntities);
                }
            }
        }


        if (productOutletSkuEntities == null || productOutletSkuEntities.size() <= 0) {
            //get skus for productoutlet (for Brand/Cat promotion)
            for (ProductOutletEntity productOutletEntity : productOutletEntities) {
                productOutletSkuEntities = productOutletSkuRepository.findByProductOutletId(productOutletEntity.getProductOutletId());
                mapSkus.put(productOutletEntity.getProductOutletId(), productOutletSkuEntities);
            }
        }

        List<ProductOutletMobileDTO> productOutletMobileDTOS = ProductOutletBeanUtils.entities2DtoMobile(productOutletEntities, mapSkus);
        rs.put("promotion",PromotionUtils.entity2DtoForVansales(outletPromotionEntity.get(0),0));
        rs.put("product",productOutletMobileDTOS);
        return rs;
    }
}
