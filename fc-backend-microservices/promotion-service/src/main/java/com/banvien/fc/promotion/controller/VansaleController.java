package com.banvien.fc.promotion.controller;

import com.banvien.fc.common.util.Constants;
import com.banvien.fc.common.util.RestUtil;
import com.banvien.fc.promotion.service.PromotionService;
import com.banvien.fc.promotion.service.VansaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/promotion/vansale")
public class VansaleController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private VansaleService vansaleService;

    @GetMapping("/quicksell")
    public ResponseEntity getPromotionDataForQuicksell(@RequestParam Long warehouseId) {
        System.out.println("----------------------- Get promotion data for vansale");
        return ResponseEntity.ok(promotionService.getPromotionDataForQuickSell(warehouseId, 8L));
    }

    @GetMapping("/quicksell/category")
    public ResponseEntity getCategoryForQuickSell(@RequestParam Long warehouseId) {
        System.out.println("----------------------- Get product data for vansale");
        return ResponseEntity.ok(promotionService.getProductDataForQuickSell(warehouseId, 8L));
    }

    @GetMapping("/quicksell/allProducts")
    public ResponseEntity getAllProucts(@RequestParam Long warehouseId) {
        return ResponseEntity.ok(vansaleService.getAllProducts4QuickSell(warehouseId));
    }

    @GetMapping("/quicksell/allProductsPromotion")
    public ResponseEntity getAllProductHavePromotion(@RequestParam Long warehouseId) {
        return ResponseEntity.ok(vansaleService.getAllProductHavePromotion4QuickSell(warehouseId));
    }

    @GetMapping("/quicksell/allHotPromotion")
    public ResponseEntity getAllHotPromotion4VansaleApp(@RequestParam Long warehouseId) {
        return ResponseEntity.ok(vansaleService.getAllPromotion4Vansale(warehouseId));
    }

    @GetMapping("/quicksell/allCat")
    public ResponseEntity getAllCat(@RequestParam Long warehouseId) {
        return ResponseEntity.ok(vansaleService.getAllCatAndSubcat(warehouseId));
    }

    @GetMapping("/products")
    public ResponseEntity getProductForVansaleApp(@RequestParam Long warehouseId) {
        return ResponseEntity.ok(vansaleService.getProductsByCategory(warehouseId, 8L));
    }

    @GetMapping("/sync")
    public ResponseEntity sync(@RequestParam("token") String token, @RequestParam("warehouseId") Long warehouseId, HttpServletRequest request, Pageable pageable) {
        Long userId = RestUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(vansaleService.syncPromotionInWareHouse(userId, token, pageable, warehouseId));
    }

    @GetMapping("/list")
    public ResponseEntity getPromotions4VansaleApp(@RequestParam("warehouseId") Long warehouseId,
                                                   @RequestParam(value = "userId", required = false) Long userId,
                                                   Pageable pageable) {
        return ResponseEntity.ok(vansaleService.getPromotionsOfWarehouse(warehouseId, userId,  pageable));
    }

    @GetMapping("/makeSyncDone")
    public ResponseEntity syncDone(@RequestParam("token") String token, HttpServletRequest request) {
        Long userId = RestUtil.getUserIdFromToken(request);
        promotionService.makeSyncDone(userId, token, Constants.SYNC_PROMOTION_VANSALE);
        return ResponseEntity.ok("{ \"status\":true}");
    }
}
