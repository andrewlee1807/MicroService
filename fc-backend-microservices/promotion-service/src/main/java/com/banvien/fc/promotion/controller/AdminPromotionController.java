package com.banvien.fc.promotion.controller;

import com.banvien.fc.common.util.RestUtil;
import com.banvien.fc.promotion.dto.AdminPromotionDTO;
import com.banvien.fc.promotion.dto.EnhancedPromotionDTO;
import com.banvien.fc.promotion.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Map;

@RestController
@RequestMapping("/promotion")
public class AdminPromotionController {
    @Autowired
    private PromotionService promotionService;

    @RequestMapping("/admin/list")
    public Page<AdminPromotionDTO> getAllPromotion(@RequestParam(value = "programName", required = false) String programName,
                                                   @RequestParam(value = "status", required = false) Integer status,
                                                   @RequestParam(value = "startDate", required = false) Long startDate,
                                                   @RequestParam(value = "endDate", required = false) Long endDate,
                                                   @RequestParam(value = "type", required = false) Integer type,
                                                   @PageableDefault Pageable pageable, HttpServletRequest request) {
        Long countryId = null;
        countryId = RestUtil.getCountryIdFromToken(request);
        if (countryId == null) return null;
        Timestamp start = startDate == null || startDate < 0 ? null : new Timestamp(startDate);
        Timestamp end = endDate == null || endDate < 0 ? null : new Timestamp(endDate);
        return promotionService.getAdminPromotion(programName, status, start, end, type, countryId, pageable);
    }

    @PostMapping({"/admin/add", "/admin/edit"})
    public ResponseEntity addNewPromotionAdmin(@RequestBody EnhancedPromotionDTO enhancedPromotionDTO, HttpServletRequest request) {
        Long countryId = null;
        countryId = RestUtil.getCountryIdFromToken(request);
        if (countryId == null) return null;
        Map<String, Object> output = promotionService.createOrUpdate4Admin(enhancedPromotionDTO, countryId);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/admin/promotionDetail")
    public ResponseEntity getPromotionDetailForAdmin(@RequestParam("id") Long id) throws Exception {
        return ResponseEntity.ok(promotionService.getPromotionDetailForAdmin(id));
    }

    @PostMapping("/admin/delete")
    public ResponseEntity deletePromotionByAdmin(@RequestParam(value = "id") Long outletPromotionId, HttpServletRequest request) {
        promotionService.deleteAdminPromotion(outletPromotionId);
        return ResponseEntity.ok("{\"status\": true }");
    }

    @PostMapping("/admin/checkOverlap")
    public ResponseEntity checkOverlap(@RequestBody EnhancedPromotionDTO promotionDTOS, HttpServletRequest request) {
        return ResponseEntity.ok(promotionService.checkOverlap4Admin(promotionDTOS));
    }
}
