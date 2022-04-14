package com.banvien.fc.promotion.controller;

import com.banvien.fc.common.util.CommonUtils;
import com.banvien.fc.common.util.RestUtil;
import com.banvien.fc.promotion.dto.*;
import com.banvien.fc.promotion.dto.rules.Enum.ActionType;
import com.banvien.fc.promotion.dto.rules.Enum.PromotionType;
import com.banvien.fc.promotion.service.PromotionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @RequestMapping("/list")
    public Page getOutletPromotion(@RequestParam(value = "programName", required = false) String programName,
                                   @RequestParam(value = "status", required = false) Integer status,
                                   @RequestParam(value = "startDate", required = false) Long startDate,
                                   @RequestParam(value = "endDate", required = false) Long endDate,
                                   @RequestParam(value = "type", required = false) Integer type,
                                   @PageableDefault Pageable pageable, HttpServletRequest request) {
        Long outletId = RestUtil.getOutletIdFromToken(request);
        if (outletId == null) {
            return null;
        }
        Timestamp start = startDate == null || startDate < 0 ? null : new Timestamp(startDate);
        Timestamp end = endDate == null || endDate < 0 ? null : new Timestamp(endDate);
        return promotionService.getOutletPromotion(programName, status, start, end, outletId, type, pageable);
    }

    @RequestMapping("/listOnEasyOrder")
    public Page<PromotionsDTO> getOutletPromotions4EasyOrder(
            @RequestParam(value = "outletId", required = true) Long outletId,
            @PageableDefault Pageable pageable) {
        return promotionService.getOutletPromotions4EasyOrder(outletId, pageable);
    }

    @GetMapping({"public/list/{outletId}"})
    public Page<OutletPromotionDTO> getAllOutletPromotion(@PathVariable("outletId") Long outletId,
                                                          @RequestParam(value = "userId", required = false) Long userId,
                                                          @PageableDefault Pageable pageable) {
        return promotionService.getAllOutLetPromotionByOutletId(outletId, userId, pageable);
    }

    @GetMapping({"/public/list/wholesaler"})
    public Page<OutletPromotionDTO> getAllOutletPromotion(@RequestParam(value = "programName", required = false) String programName, @RequestParam(value = "outletId") long outletId,
                                                          @PageableDefault Pageable pageable) {
        return promotionService.getAllOutLetPromotion4WholeSaler(programName, outletId, pageable);
    }


    @PostMapping("/apply")
    public PromotionPerOrderDTO getPromotionDiscountPriceByEasyOrder(@RequestBody EasyOrderSubmitDTO easyOrderSubmitDTO) {
        ActionType actionType;
        actionType = ActionType.GET_TO_CART;
        if (easyOrderSubmitDTO.getAction() != null && easyOrderSubmitDTO.getAction().equals(1)) {
            actionType = ActionType.GET_TO_ORDER;
        }
        return promotionService.getPromotionDiscountByOrder(easyOrderSubmitDTO, actionType);
    }

    @PostMapping("/order/unapply")
    public ResponseEntity restoreRemain2OutletPromotion(@RequestParam("orderOutletId") Long orderOutletId, @RequestParam("customerId") Long customerId) {
        promotionService.restoreRemain2OutletPromotion(orderOutletId, customerId);
        return ResponseEntity.ok("{ \"status\":true}");
    }


    @GetMapping("/list/outlet/{id}")
    public List<OutletPromotionDTO> getPromotionsInOutlet(@PathVariable("id") Long id) {
        return promotionService.getPromotionsInOutlet(id);  // result for view mobile idCondition(product+promotion)
    }

//    @PostMapping("/promotionCode")
//    public DiscountDTO appliedPromotionCode(@RequestParam("promotionCode") String promotionCode,
//                                            @RequestParam("customerId") long customerId) {
//        return promotionService.appliedPromotionCode(promotionCode, customerId, ActionType.GET_TO_CART);
//    }

    @PostMapping("/easyOrder/presuppose")
    public List<OutletPromotionDTO> getPromotionByEasyOrder(@RequestBody EasyOrderSubmitDTO easyOrderSubmitDTO) {
        return promotionService.getPromotionDiscountByEasyOrder(easyOrderSubmitDTO);
    }

    @GetMapping("/easyOrder/listCode")
    public List<String> getAllEasyOrderCode(HttpServletRequest request) {
        Long outletId = RestUtil.getOutletIdFromToken(request);
        if (outletId == null) {
            return null;
        }
        return promotionService.getAllEasyOrderCode(outletId);
    }

    @GetMapping("/easyOrder/checkCode")
    public ResponseEntity checkEasyOrderCode(@RequestParam String easyOrderCode, HttpServletRequest request) {
        Long outletId = RestUtil.getOutletIdFromToken(request);
        if (outletId == null) {
            return null;
        }
        return ResponseEntity.ok(promotionService.checkEasyOrderCode(outletId, easyOrderCode));
    }

    @GetMapping("/easyOrder/generateCode")
    public ResponseEntity generateEasyOrderCode() {
        return ResponseEntity.ok(promotionService.generateEasyOrderCode());
    }

    @GetMapping("/easyOrder/getByEasyOrderCode")
    public ResponseEntity getPromotionByEasyOrderCode(@RequestParam("easyOrderCode") String easyOrderCode) {
        return ResponseEntity.ok(promotionService.getPromotionByEasyOrderCode(easyOrderCode));
    }

    @GetMapping({"/promotionDetail", "/public/promotionDetail"})
    public ResponseEntity<Map<String, Object>> getPromotionDetail(@RequestParam("id") Long id) throws Exception {
        Map<String, Object> rs = new HashMap<>();
        EnhancedPromotionDTO dto = promotionService.getPromotionDetail(id);
        List itemConditions = new ArrayList();
        List itemExcludes = new ArrayList();
        rs.put("outletId", dto.getOutletId());
        rs.put("title", dto.getTitle());
        rs.put("startDate", dto.getStartDate());
        rs.put("endDate", dto.getEndDate());
        rs.put("status", dto.getStatus());
        rs.put("description", dto.getDescription());
        rs.put("thumbnail", dto.getThumbnail());
        rs.put("promotionProperty", dto.getPromotionProperty());
        rs.put("maxPerPromotion", dto.getMaxPerPromotion());
        rs.put("maxPerCustomer", dto.getMaxPerCustomer());
        rs.put("maxPerOrder", dto.getMaxPerOrder());
        rs.put("totalGift", dto.getTotalGift());
        rs.put("remains", dto.getRemains());
        rs.put("priority", dto.getPriority());
        rs.put("notifyContent", dto.getNotifyContent());
        rs.put("notifySendDate", dto.getNotifySendDate());
        rs.put("promotionRule", dto.getPromotionRule());
        rs.put("allCustomerGroups", dto.getAllCustomerGroups());
        if (dto.getPenetration().getPenetrationType() != 0) {
            rs.put("penetration", dto.getPenetration());
        }

        //get item when buy have apply promotion
        if (dto.getPromotionRule().getIds() != null && dto.getPromotionRule().getIds().size() > 0) {
            itemConditions = promotionService.getItemForPromotionDetail(dto.getPromotionRule().getIds(), dto.getPromotionRule().getPromotionType());
        }
        rs.put("itemConditions", itemConditions);

        //get item when buy not have apply promotion
        if (!(dto.getPromotionRule().getExcludeIds() == null)) {
            itemExcludes = promotionService.getItemForPromotionDetail(dto.getPromotionRule().getExcludeIds(), PromotionType.PRODUCT);
        }

        rs.put("itemExcludes", itemExcludes);

        if (dto.getPromotionRule().getPromotionType() != PromotionType.NEXTPURCHASE) {
            rs.put("itemDiscounts", dto.getPromotionRule().getDiscounts().stream().map(promotionService::findDiscountItemDetail).filter(Objects::nonNull).collect(Collectors.toList()));
        } else {
            List<ViewItemDTO> data = new ArrayList<>();
            data.add(promotionService.getProductView(dto.getOfferNextProductId()));
            rs.put("itemDiscounts", data);
        }

        rs.put("customerTargets", promotionService.getCustomerTarget(dto.getCustomerTargetIds()));

        //get item in case penetration
        List<Long> lstPenetrationValue = new ArrayList<>();
        if (dto.getPenetration() != null && dto.getPenetration().getPenetrationType() != 3 && dto.getPenetration().getPenetrationType() != 0) {
//            lstPenetrationValue.add((long)dto.getPenetration().getPenetrationType());
            lstPenetrationValue.add(Long.parseLong(dto.getPenetration().getPenetrationValue()));
            if (dto.getPenetration().getPenetrationType() == 1) {
                rs.put("itemPenetration", promotionService.getItemForPromotionDetail(lstPenetrationValue, PromotionType.CATEGORY));
            } else {
                rs.put("itemPenetration", promotionService.getItemForPromotionDetail(lstPenetrationValue, PromotionType.BRAND));
            }
        }
        if (dto.getCustomerGroupIds() != null) {
            rs.put("customerGroupIds", dto.getCustomerGroupIds());
        }
        return ResponseEntity.ok(rs);
    }

    @PostMapping({"/add", "/edit", "/copy"})
    public ResponseEntity addNewPromotion(@RequestBody EnhancedPromotionDTO promotionDTO, HttpServletRequest request) {
//        if (promotionDTO.getOutletPromotionId() != null)
        Long outletId = RestUtil.getOutletIdFromToken(request);
        promotionDTO.setOutletId(outletId);
        Map<String, Object> output = promotionService.createOrUpdateOutletPromotion(promotionDTO);
        return ResponseEntity.ok(output);
    }

    @PostMapping("/checkOverlap")
    public ResponseEntity checkOverlap(@RequestBody EnhancedPromotionDTO promotionDTO, HttpServletRequest request) {
        Long outletId = RestUtil.getOutletIdFromToken(request);
        return ResponseEntity.ok(promotionService.checkOverlap(promotionDTO, outletId));
    }

    @PostMapping("/delete")
    public ResponseEntity deletePromotion(@RequestParam(value = "id") Long outletPromotionId) {
        promotionService.deleteOutletPromotion(outletPromotionId);
        return ResponseEntity.ok("{\"status\": true}");
    }

    @PostMapping("/updateState")
    public ResponseEntity updatePromotionStateByUser(@RequestParam(value = "ids") List<Long> ids,
                                                     @RequestParam(value = "state") String state,
                                                     @RequestParam(value = "notificationContent", required = false) String notificationContent,
                                                     HttpServletRequest request) {
        Long outletId = RestUtil.getOutletIdFromToken(request);
        if (outletId == null) {
            return ResponseEntity.ok(promotionService.updateAdminPromotionState(ids, state, notificationContent));
        } else {
            return ResponseEntity.ok(promotionService.updatePromotionState(ids, state, notificationContent, false));
        }
    }

    @GetMapping("/checkNotified")
    public ResponseEntity checkPromotionsWereNotified(@RequestParam(value = "ids") List<Long> ids, HttpServletRequest request) {
        Long outletId = RestUtil.getOutletIdFromToken(request);
        return ResponseEntity.ok(promotionService.checkPromotionsWereNotified(ids, outletId));
    }

    @GetMapping("/sync")
    public ResponseEntity sync(@RequestParam("token") String token, HttpServletRequest request, Pageable pageable) {
        Long userId = RestUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(promotionService.getSyncPromotion(userId, token, pageable));
    }

    @GetMapping("/makeSyncDone")
    public ResponseEntity syncDone(@RequestParam("token") String token, HttpServletRequest request) {
        Long userId = RestUtil.getUserIdFromToken(request);
        promotionService.makeSyncDone(userId, token, null);
        return ResponseEntity.ok("{ \"status\":true}");
    }

    @RequestMapping("/checkProductPromotion")
    public ResponseEntity getPromotionProduct(@RequestBody String body) throws Exception {
        JSONObject obj = new JSONObject(body);
        ObjectMapper mapper = new ObjectMapper();
        Long userId = null;
        try {
            userId = obj.getLong("userId");
        } catch (Exception ex) {
        }
        long outletId = obj.getLong("outletId");
        List<Long> skuIds = mapper.readValue(obj.getJSONArray("skuIds").toString(), new TypeReference<List<Long>>() {});
        return ResponseEntity.ok(promotionService.checkPromotion(userId, outletId, skuIds, PromotionType.PRODUCT));
    }

    @RequestMapping("/checkCategoryPromotion")
    public ResponseEntity getPromotionCategory(@RequestBody String body) throws Exception {
        JSONObject obj = new JSONObject(body);
        ObjectMapper mapper = new ObjectMapper();
        Long userId = null;
        try {
            userId = obj.getLong("userId");
        } catch (Exception ex) {
        }
        long outletId = obj.getLong("outletId");
        List<Long> categoryIds = mapper.readValue(obj.getJSONArray("categoryIds").toString(), new TypeReference<List<Long>>() {});
        return ResponseEntity.ok(promotionService.checkPromotion(userId, outletId, categoryIds, PromotionType.CATEGORY));
    }

    @RequestMapping("/checkBrandPromotion")
    public ResponseEntity getPromotionBrand(@RequestBody String body) throws Exception {
        JSONObject obj = new JSONObject(body);
        ObjectMapper mapper = new ObjectMapper();
        Long userId = null;
        try {
            userId = obj.getLong("userId");
        } catch (Exception ex) {
        }
        long outletId = obj.getLong("outletId");
        List<Long> brandIds = mapper.readValue(obj.getJSONArray("brandIds").toString(), new TypeReference<List<Long>>() {});
        return ResponseEntity.ok(promotionService.checkPromotion(userId, outletId, brandIds, PromotionType.BRAND));
    }

    @RequestMapping("/checkAllPromotion")
    public ResponseEntity getPromotion(@RequestBody String body) throws Exception {
        Map<String, Object> rs = new HashMap<>();
        JSONObject object = new JSONObject(body);
        CommonUtils.ignoringExc(() -> {
            JSONObject brand = object.getJSONObject("brand");
            rs.put("brand", getPromotionBrand(brand.toString()).getBody());
        });
        CommonUtils.ignoringExc(() -> {
            JSONObject product = object.getJSONObject("product");
            rs.put("product", getPromotionProduct(product.toString()).getBody());
        });
        CommonUtils.ignoringExc(() -> {
            JSONObject category = object.getJSONObject("category");
            rs.put("category", getPromotionCategory(category.toString()).getBody());
        });
        return ResponseEntity.ok(rs);
    }

    @RequestMapping("/getPromotionDetailForMobile")
    public ResponseEntity getPromotionDetailForMobile(@RequestBody String body) throws Exception {
        JSONObject obj = new JSONObject(body);
        ObjectMapper mapper = new ObjectMapper();
        Long userId = null;
        try {
            userId = obj.getLong("userId");
        } catch (Exception ex) {
        }
        long outletId = obj.getLong("outletId");
        List<Long> skuIds = mapper.readValue(obj.getJSONArray("skuIds").toString(), new TypeReference<List<Long>>() {});
        return ResponseEntity.ok(promotionService.getPromotionDetail(skuIds, userId, outletId));
    }


    @RequestMapping("/mobile/detail")
    public ResponseEntity getPrmotionDetailForMobile(@RequestParam("id") Long id) throws Exception {
        return ResponseEntity.ok(promotionService.getPromotionDetailForMobile(id));
    }
}
