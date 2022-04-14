package com.banvien.fc.catalog.controller;

import com.banvien.fc.catalog.dto.CatalogPromotionDTO;
import com.banvien.fc.catalog.dto.ProductDTO;
import com.banvien.fc.catalog.service.CatalogService;
import com.banvien.fc.common.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/getAllCat")
    public ResponseEntity getAllCategory(HttpServletRequest request) {
        Long outletId = null;
        Long countryId = null;
        outletId = RestUtil.getOutletIdFromToken(request);
        countryId = RestUtil.getCountryIdFromToken(request);
        if (countryId == null) return null;
        return ResponseEntity.ok(catalogService.getParentCategory(outletId, countryId));
    }

    @GetMapping("/getSubCat")
    public ResponseEntity getSubCatByCategory(@RequestParam("id") Long parentId, HttpServletRequest request) {
        Long outletId = null;
        Long countryId = null;
        countryId = RestUtil.getCountryIdFromToken(request);
        if (countryId == null) return null;
        outletId = RestUtil.getOutletIdFromToken(request);
        return ResponseEntity.ok(catalogService.getSubCatByCategory(parentId, outletId, countryId));
    }

    @GetMapping("/getAllGift")
    public ResponseEntity getAllGift(@RequestParam(value = "id", required = false) Long outletId, HttpServletRequest request) {
        if (outletId == null || outletId < 0) {
            outletId = RestUtil.getOutletIdFromToken(request);
        }
        return ResponseEntity.ok(catalogService.getAllGift(outletId));
    }

    @GetMapping("/penetration/getAllCat")
    public ResponseEntity getAllCat(HttpServletRequest request) {
        Long outletId = null;
        Long countryId = null;
        countryId = RestUtil.getCountryIdFromToken(request);
        if (countryId == null) return null;
        outletId = RestUtil.getOutletIdFromToken(request);
        return ResponseEntity.ok(catalogService.getAllCatalogForPenetration(outletId, countryId));
    }

    @GetMapping("/brand/getAllBrand")
    public ResponseEntity getAllBrand(@RequestParam("key") String name, Pageable pageable, HttpServletRequest request) {
        Long outletId = null;
//        Long countryId = null;
//        countryId = RestUtil.getCountryIdFromToken(request);
//        if (countryId == null) return null;
        outletId = RestUtil.getOutletIdFromToken(request);
        return ResponseEntity.ok(catalogService.getAllBrand(outletId, name, pageable));
    }

    @PostMapping("/getProduct")
    public ResponseEntity getAllProduct(@RequestBody CatalogPromotionDTO dto, HttpServletRequest request) {
        Long outletId = dto.getOutletId();
        if (outletId == null) {
            outletId = RestUtil.getOutletIdFromToken(request);
        }
        Long userId = null;
        userId = RestUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(catalogService.getAllProductOutletSKU(dto, outletId, userId));
    }

    @GetMapping("/product/{productOutletSkuId}")
    public ProductDTO getProductByProductOutletSkuId(@PathVariable("productOutletSkuId") long productOutletSkuId) {
        return catalogService.getProductByProductOutletSkuId(productOutletSkuId);
    }

    @GetMapping("/gift/changeStatus/{giftId}")
    public void changeStatus(@PathVariable("giftId") long giftId){
        catalogService.changeStatus(giftId);
    }

    @GetMapping("/gift/checkOutOfStock/{giftId}")
    public Long checkGiftForPromotionApplied(@PathVariable("giftId") long giftId) {
        return catalogService.getAvailableGiftForPromotionApplied(giftId);
    }

    @GetMapping("/product/checkOutOfStock/{productSkuId}")
    public Long getGiftForPromotionApplied(@PathVariable("productSkuId") long productSkuId) {
        return catalogService.getAvailableProductSkuForPromotionApplied(productSkuId);
    }

    @GetMapping("/getOutlet")
    public ResponseEntity getOutletByName(@RequestParam("key") String name, HttpServletRequest request) {
        Long outletId = null;
        Long countryId = null;
        outletId = RestUtil.getOutletIdFromToken(request);
        countryId = RestUtil.getCountryIdFromToken(request);
        if (outletId == null && countryId != null)   // only Admin can do it
            return ResponseEntity.ok(catalogService.getOutletByName(name, countryId));
        else
            return null;
    }

    @PostMapping("/getAdminProduct")
    public ResponseEntity getAllProduct4Admin(@RequestBody CatalogPromotionDTO dto, HttpServletRequest request) {
        Long userId = null;
        userId = RestUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(catalogService.getAllProductOutletSKU(dto, null, userId));
    }

    @GetMapping("/getProductByKey")
    public ResponseEntity getProductByKey(@RequestParam("key") String key,
                                          @RequestParam(value = "outletId", required = false) Long outletId,
                                          Pageable pageable, HttpServletRequest request) {
        boolean isAdmin = RestUtil.getOutletIdFromToken(request) == null; //
        Long userId = null;
        userId = RestUtil.getUserIdFromToken(request);
        if (userId == null) return null;
        if (isAdmin)
            if (outletId != null)
                return ResponseEntity.ok(catalogService.getProductAdminByName(key, userId, outletId, pageable));
            else
                return null;
        else {
            return ResponseEntity.ok(catalogService.getProductByName(key, RestUtil.getOutletIdFromToken(request), pageable));
        }
    }

    @GetMapping("/findAllSkuIdByCatGroupIds")
    public ResponseEntity findAllSkuIdByCatGroupIds(@RequestParam("outletId") long outletId,
                                                    @RequestParam("catGroupIds") List<Long> catIds) {
        return ResponseEntity.ok(catalogService.findAllSkuIdByCatGroupIds(outletId, catIds));
    }

    @GetMapping("/findAllSkuIdByBrandIds")
    public ResponseEntity findAllSkuIdByBrandIds(@RequestParam("outletId") long outletId,
                                                 @RequestParam("brandIds") List<Long> brandIds) {
        return ResponseEntity.ok(catalogService.findAllSkuIdByBrandIds(outletId, brandIds));
    }
}
