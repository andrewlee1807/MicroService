package com.banvien.fc.promotion.utils.promotion;

import com.banvien.fc.promotion.dto.rules.PromotionRuleDTO;
import com.banvien.fc.promotion.entity.CatGroupEntity;
import com.banvien.fc.promotion.entity.ProductEntity;
import com.banvien.fc.promotion.entity.ProductOutletEntity;
import com.banvien.fc.promotion.entity.ProductOutletSkuEntity;
import com.banvien.fc.promotion.repository.BrandRepository;
import com.banvien.fc.promotion.repository.CategoryRepository;
import com.banvien.fc.promotion.repository.ProductOutletRepository;
import com.banvien.fc.promotion.repository.ProductOutletSkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @noinspection Duplicates
 */
@Component
public class PromotionOverlapChecker {

    private static BrandRepository brandRepository;
    private static CategoryRepository categoryRepository;
    private static ProductOutletRepository productOutletRepository;
    private static ProductOutletSkuRepository productOutletSkuRepository;

    @Autowired
    private BrandRepository _brandRepository;

    @Autowired
    private CategoryRepository _categoryRepository;

    @Autowired
    private ProductOutletRepository _productOutletRepository;

    @Autowired
    private ProductOutletSkuRepository _productOutletSkuRepository;

    @PostConstruct
    private void initRepository() {
        brandRepository = this._brandRepository;
        categoryRepository = this._categoryRepository;
        productOutletRepository = this._productOutletRepository;
        productOutletSkuRepository = this._productOutletSkuRepository;
    }

    public static boolean isOverlap(PromotionRuleDTO promotion1, PromotionRuleDTO promotion2, Long outletId) {
        Set<Long> data = new HashSet<>(promotion1.getIds());
        data.addAll(promotion2.getIds());
        if (data.size() != promotion1.getIds().size() + promotion2.getIds().size()) return true;
        int total = 0;
        Set<Long> set = new HashSet<>();
        try {
            total += process(promotion1, set, outletId);
            total += process(promotion2, set, outletId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return set.size() != total;
    }

    private static int process(PromotionRuleDTO promotion, Set<Long> set, Long outletId) {
        int rs = 0;
        switch (promotion.getPromotionType()) {
            case PRODUCT:
                rs += promotion.getIds().size();
                set.addAll(promotion.getIds());
                break;
            case BRAND:
                for (Long id : promotion.getIds()) {
                    List<Long> productIds = brandRepository.findById(id).get().getProducts().stream().map(ProductEntity::getProductId).collect(Collectors.toList());
                    List<Long> productOutletIds = productOutletRepository.findByProductIdInAndOutletId(productIds, outletId).stream().map(ProductOutletEntity::getProductOutletId).collect(Collectors.toList());
                    List<Long> productOutletSkuIds = productOutletSkuRepository.findByProductOutletIdIn(productOutletIds).stream().map(ProductOutletSkuEntity::getProductOutletSkuId).collect(Collectors.toList());
                    rs += countProduct(new HashSet<>(productOutletSkuIds), promotion, set);
                }
                break;
            case CATEGORY:
                for (Long id : promotion.getIds()) {
                    CatGroupEntity entity = categoryRepository.findById(id).get();
                    List<Long> productIds = entity.getProducts().stream().map(ProductEntity::getProductId).collect(Collectors.toList());
                    List<Long> productOutletIds = productOutletRepository.findByProductIdInAndOutletId(productIds, outletId).stream().map(ProductOutletEntity::getProductOutletId).collect(Collectors.toList());
                    List<Long> productOutletSkuIds = productOutletSkuRepository.findByProductOutletIdIn(productOutletIds).stream().map(ProductOutletSkuEntity::getProductOutletSkuId).collect(Collectors.toList());
                    rs += countProduct(new HashSet<>(productOutletSkuIds), promotion, set);

                    for (CatGroupEntity category : entity.getChilds()) {
                        productIds = category.getProducts().stream().map(ProductEntity::getProductId).collect(Collectors.toList());
                        productOutletIds = productOutletRepository.findByProductIdInAndOutletId(productIds, outletId).stream().map(ProductOutletEntity::getProductOutletId).collect(Collectors.toList());
                        productOutletSkuIds = productOutletSkuRepository.findByProductOutletIdIn(productOutletIds).stream().map(ProductOutletSkuEntity::getProductOutletSkuId).collect(Collectors.toList());
                        rs += countProduct(new HashSet<>(productOutletSkuIds), promotion, set);
                    }
                }
                break;
        }
        return rs;
    }

    private static int countProduct(Set<Long> productIds, PromotionRuleDTO promotion, Set<Long> set) {
        int rs = 0;
        for (Long productId : productIds) {
            if (!promotion.getExcludeIds().contains(productId)) {
                set.add(productId);
                rs++;
            }
        }
        return rs;
    }
}
