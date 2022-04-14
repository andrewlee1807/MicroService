package com.banvien.fc.promotion.controller;

import com.banvien.fc.common.util.RestUtil;
import com.banvien.fc.promotion.dto.ProductBlockDTO;
import com.banvien.fc.promotion.repository.OutletRepository;
import com.banvien.fc.promotion.service.PromotionService;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/promotion")
public class BlockController {

    @Autowired
    private PromotionService promotionService;
    @Autowired
    private OutletRepository outletRepository;


    @GetMapping("/product/block")
    public ResponseEntity getBlockList(@RequestParam(value = "code", required = false) String code,
                                       @RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "category", required = false) String category,
                                       @PageableDefault Pageable pageable, HttpServletRequest request) {
        Long countryId = null;
        countryId = RestUtil.getCountryIdFromToken(request);
        if (countryId == null) return null;
        Long outletId = RestUtil.getOutletIdFromToken(request);
        return ResponseEntity.ok(promotionService.getProductBlock(outletId, name, code, category, countryId, pageable));
    }

    @GetMapping("/mobile/product/block")
    public ResponseEntity getBlockList(@RequestParam(value = "outletId") Long outletId, @PageableDefault Pageable pageable) {
        Long countryId = outletRepository.getOne(outletId).getCountryId();
        return ResponseEntity.ok(promotionService.getProductBlock(outletId, null, null, null, countryId, pageable));
    }

    @GetMapping("/product/nonBlock")
    public ResponseEntity getNotBlockList(@RequestParam(value = "productCode", required = false) String code,
                                          @RequestParam(value = "productName", required = false) String name,
                                          @RequestParam(value = "category", required = false) String category,
                                          @PageableDefault Pageable pageable, HttpServletRequest request) {
        Long countryId = null;
        countryId = RestUtil.getCountryIdFromToken(request);
        if (countryId == null) return null;
        Long outletId = RestUtil.getOutletIdFromToken(request);
        return ResponseEntity.ok(promotionService.findAllProductNonBlock(name, code, category, outletId, countryId, pageable));
    }

    @PostMapping("/product/block/add")
    public ResponseEntity addProductBlock(@RequestBody ProductBlockDTO products, HttpServletRequest request) throws JSONException {
        Long outletId = RestUtil.getOutletIdFromToken(request);
        return promotionService.addProductBlock(outletId, products.getProducts());
    }

    @PostMapping("/product/block/delete")
    public ResponseEntity deleteProductBlock(@RequestBody List<Long> ids, HttpServletRequest request) {
        Long outletId = RestUtil.getOutletIdFromToken(request);
        if (outletId == null) {
            return ResponseEntity.ok(promotionService.deleteAdminBlock(ids));
        } else {
            return ResponseEntity.ok(promotionService.deleteOutletBlock(ids, outletId));
        }
    }

    @GetMapping("/product/block/export")
    public ResponseEntity export2Excel(HttpServletRequest request) {
        Long outletId = RestUtil.getOutletIdFromToken(request);
        DateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
        String currentDateTime = dateFormatter.format(new Date());
        String headerValue = "attachment; filename=disallow-block-products_" + currentDateTime + ".xlsx";
        InputStreamResource file = new InputStreamResource(promotionService.exportBlockProducts2Excel(outletId));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
