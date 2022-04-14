package com.banvien.fc.catalog.service;

import com.banvien.fc.catalog.dto.*;
import com.banvien.fc.catalog.entity.*;
import com.banvien.fc.catalog.repository.*;
import com.banvien.fc.common.rest.errors.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    @Autowired
    private ProductOutletRepository productOutletRepository;

    @Autowired
    private ProductOutletSkuRepository productOutletSkuRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private GiftRepository giftRepository;

    @Autowired
    private OutletRepository outletRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<ViewItemDTO> getParentCategory(Long outletId, Long countryId) {
        try {
            List<CatGroupEntity> entities;
            if (outletId == null) entities = categoryRepository.getByAdmin(countryId);
            else entities = categoryRepository.getByOutlet(outletId, countryId);
            return entities.stream().map(entity -> {
                ViewItemDTO rs = new ViewItemDTO();
                rs.setId(entity.getCatgroupId());
                rs.setName(entity.getName());
                rs.setImage(entity.getImage());
                rs.setParentId(entity.getParentId());
                return rs;
            }).sorted(Comparator.comparing(ViewItemDTO::getName)).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new BadRequestException("Can not load catalog");
        }
    }

    public List<ViewItemDTO> getSubCatByCategory(Long parentId, Long outletId, Long countryId) {
        try {
            List<CatGroupEntity> entities;
            if (outletId == null) entities = categoryRepository.getByParentIdAdmin(parentId, countryId);
            else entities = categoryRepository.getByParentId(outletId, parentId, countryId);
            return entities.stream().map(entity -> {
                ViewItemDTO rs = new ViewItemDTO();
                rs.setId(entity.getCatgroupId());
                rs.setName(entity.getName());
                rs.setImage(entity.getImage());
                rs.setParentId(entity.getParentId());
                return rs;
            }).sorted(Comparator.comparing(ViewItemDTO::getName)).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new BadRequestException("Can not load catalog - parentId: " + parentId);
        }
    }

    public List<ViewItemDTO> getAllBrand(Long outletId, String name, Pageable pageable) {
        try {
            List<BrandEntity> entities;
            if (outletId == null) entities = brandRepository.getByBrandNameAdmin(name, pageable);
            else entities = brandRepository.getByBrandNameInOutlet(name, outletId, pageable);
            return entities.stream().map(entity -> {
                ViewItemDTO rs = new ViewItemDTO();
                rs.setId(entity.getBrandId());
                rs.setName(entity.getName());
                rs.setImage(entity.getImage());
                return rs;
            }).sorted(Comparator.comparing(ViewItemDTO::getName)).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new BadRequestException("Can not load brand - name: " + name);
        }
    }

    public List<ViewItemDTO> getAllGift(Long outletId) {
        try {
            List<GiftEntity> entities = giftRepository.getAllGiftForCreatePromotion(outletId, new Timestamp(System.currentTimeMillis()));
            return entities.stream().map(entity -> {
                ViewItemDTO rs = new ViewItemDTO();
                rs.setId(entity.getGiftId());
                rs.setName(entity.getName());
                rs.setImage(entity.getImage());
                return rs;
            }).sorted(Comparator.comparing(ViewItemDTO::getName)).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new BadRequestException("Can not load gift - outletId: " + outletId);
        }
    }

    public ProductDTO getProductByProductOutletSkuId(long productOutletSkuId) {
        Optional<ProductOutletSkuEntity> productOutletSkuEntity = productOutletSkuRepository.findById(productOutletSkuId);
        if (!productOutletSkuEntity.isPresent()) return null;
        Optional<ProductOutletEntity> productOutletEntity = productOutletRepository.findById(productOutletSkuEntity.get().getProductOutletId());
        if (!productOutletEntity.isPresent()) return null;
        Optional<ProductEntity> productEntity = productRepository.findById(productOutletEntity.get().getProductId());
        return productEntity.map(productEntity1 -> new ProductDTO(productEntity1.getProductId(), productEntity1.getCatGroupId(), productEntity1.getBrandId(), productEntity1.getName())).orElse(null);
    }

    public void changeStatus(Long giftId){
        Optional<GiftEntity> giftEntity = giftRepository.findById(giftId);
        if (giftEntity.isPresent()) {
            giftEntity.get().setStatus(0);
        }
    }

    public Long getAvailableGiftForPromotionApplied(Long giftId) {
        GiftEntity giftEntity = giftRepository.getGiftForPromotionApplied(giftId, new Timestamp(System.currentTimeMillis()));
        if (giftEntity != null) {
            if (giftEntity.getTotalReceivedPromotion() != null) return giftEntity.getQuantityPromotion() - giftEntity.getTotalReceivedPromotion();
            else return Long.valueOf(giftEntity.getQuantityPromotion());
        } else {
            changeStatus(giftId); // expired date
        }
        return null;
    }

    public Long getAvailableProductSkuForPromotionApplied(Long productOutletSkuId) {
        // Checking Out Of Stock
        try {
            Integer quantityAvailable = orderRepository.getProductSkuAvailability(productOutletSkuId);
            return Long.valueOf(quantityAvailable);
        }
        catch (Exception ex){
            System.out.println("Can not call service OrderService to get quantityAvailable");
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<ViewItemDTO> getAllCatalogForPenetration(Long outletId, Long countryId) {
        try {
            List<CatGroupEntity> entities;
            if (outletId == null) entities = categoryRepository.getByAdmin(countryId);
            else entities = categoryRepository.getByOutlet(outletId, countryId);
//            List<CatGroupEntity> entities = categoryRepository.getByOutlet(outletId);
            return entities.stream().map(entity -> {
                ViewItemDTO rs = new ViewItemDTO();
                rs.setId(entity.getCatgroupId());
                rs.setName(entity.getName());
                return rs;
            }).sorted(Comparator.comparing(ViewItemDTO::getName)).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new BadRequestException("Can not found catalog");
        }
    }

    public List<OutletDTO> getOutletByName(String name, Long countryId) {
        try {
            List<OutletEntity> entities = outletRepository.findByNameContainsIgnoreCaseAndCountryId(name, countryId);
            return entities.stream().map(entity -> {
                OutletDTO rs = new OutletDTO();
                rs.setOutletId(entity.getOutletId());
                rs.setName(entity.getName());
                rs.setImage(entity.getImage());
                rs.setAddress(entity.getAddress());
                return rs;
            }).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new BadRequestException("Can not found outlet name: " + name);
        }
    }

    private List<ViewItemDTO> products2View(List<ProductPromotionDTO> products) {
        System.out.println("Found totally : " + products.size());
        return products.stream().map(product -> {
            if (getAvailableProductSkuForPromotionApplied(product.getId()) >= 1) {
                ViewItemDTO rs = new ViewItemDTO();
                rs.setId(product.getId());
                rs.setName(product.getName() + " | " + product.getTitle());
                rs.setImage(product.getImage());
                return rs;
            }
            System.out.println("This Product " + product.getId() + " is not available!");
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<ViewItemDTO> getProductByName(String key, Long outletId, Pageable pageable) {
        List<ViewItemDTO> rs = new ArrayList<>();
        try {
//            int i = 0;
//            while (true) {
//                Pageable page = PageRequest.of(i, pageable.getPageSize());
//                List<ProductPromotionDTO> products = productOutletSkuRepository.findAllProductByName(outletId, key, page);
//                if (products == null || products.size() == 0) break;
//                rs.addAll(products2View(products));
//                if (rs.size() >= pageable.getPageSize() * (pageable.getPageNumber() + 1)) break;
//                i++;
//            }
//            return rs.subList(pageable.getPageSize() * pageable.getPageNumber(), rs.size());
            return products2View(productOutletSkuRepository.findAllProductByName(outletId, key, pageable));
        } catch (Exception ex) {
            System.out.println("Can not found product - outletId: " + outletId + " - key: " + key);
        }
        return rs;
    }

    public List<ViewItemDTO> getProductAdminByName(String key, Long userId, Long outletId, Pageable pageable) {
        try {
            List<ViewItemDTO> rs = new ArrayList<>();
            int i = 0;
            while (true) {
                Pageable page = PageRequest.of(i, pageable.getPageSize());
                List<ProductPromotionDTO> products = productOutletSkuRepository.findAllProductAdminByName(userId, outletId, key, page);
                if (products == null || products.size() == 0) break;
                rs.addAll(products2View(products));
                if (rs.size() >= pageable.getPageSize() * (pageable.getPageNumber() + 1)) break;
                i++;
            }
            return rs.subList(pageable.getPageSize() * pageable.getPageNumber(), rs.size());
//            }).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new BadRequestException("Can not found product - userId: " + userId + " - key: " + key);
        }
    }


    public List<ViewItemDTO> getAllProductOutletSKU(CatalogPromotionDTO catalogPromotionDTO, Long outletId, Long userId) {
        List<ProductPromotionDTO> products = new ArrayList<>();
        try {
            switch (catalogPromotionDTO.getType()) {
                case CATEGORY:
                    if (outletId == null)
                        products = productOutletSkuRepository.findProductAdminByCatGroup(userId, catalogPromotionDTO.getIds(), catalogPromotionDTO.getOutletId());
                    else
                        products = productOutletSkuRepository.findProductByCatGroup(catalogPromotionDTO.getIds(), outletId);
                    break;
                case BRAND:
                    if (outletId == null)
                        products = productOutletSkuRepository.findProductAdminByBrand(userId, catalogPromotionDTO.getIds(), catalogPromotionDTO.getOutletId());
                    else
                        products = productOutletSkuRepository.findProductByBrand(catalogPromotionDTO.getIds(), outletId);
                    break;
                case PRODUCT:
                    if (outletId == null)
                        products = productOutletSkuRepository.findAllProductAdmin(userId, catalogPromotionDTO.getOutletId());
                    else products = productOutletSkuRepository.findAllProduct(outletId);
                    break;
            }
            if (products != null && products.size() > 0) {
                return products.stream().map(entity -> {
                    ViewItemDTO rs = new ViewItemDTO();
                    rs.setId(entity.getId());
                    rs.setName(entity.getName() + " - " + entity.getTitle());
                    rs.setImage(entity.getImage());
                    return rs;
                }).collect(Collectors.toList());
            } else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            throw new BadRequestException("Can not found product - outletId: " + outletId);
        }
    }

    public Set<Long> findAllSkuIdByCatGroupIds(long outletId, List<Long> catIds) {
        return productOutletSkuRepository.findAllSkuIdByCatGroupIds(outletId, catIds);
    }

    public Set<Long> findAllSkuIdByBrandIds(long outletId, List<Long> catIds) {
        return productOutletSkuRepository.findAllSkuIdByBrandIds(outletId, catIds);
    }
}
