package com.banvien.fc.promotion.repository;

import com.banvien.fc.promotion.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Repository
@FeignClient(name = "catalog-service")
@RequestMapping("/catalog")
public interface CatalogRepository {
    @RequestMapping("/product/{productOutletSkuId}")
    ProductDTO getProductByProductOutletSkuId(@PathVariable("productOutletSkuId") long productOutletSkuId);

    @GetMapping("/gift/checkOutOfStock/{giftId}")
    Long checkGiftForPromotionApplied(@PathVariable("giftId") long giftId);

    @GetMapping("/gift/changeStatus/{giftId}")
    Long changeStatus(@PathVariable("giftId") long giftId);

    @GetMapping("/product/checkOutOfStock/{productSkuId}")
    Long getProductForPromotionApplied(@PathVariable("productSkuId") long productSkuId);

    @GetMapping("/findAllSkuIdByCatGroupIds")
    ResponseEntity<Set<Long>> findAllSkuIdByCatGroupIds(@RequestParam("outletId") long outletId,
                                                        @RequestParam("catGroupIds") List<Long> catIds);

    @GetMapping("/findAllSkuIdByBrandIds")
    ResponseEntity<Set<Long>> findAllSkuIdByBrandIds(@RequestParam("outletId") long outletId,
                                                     @RequestParam("brandIds") List<Long> brandIds);
}
