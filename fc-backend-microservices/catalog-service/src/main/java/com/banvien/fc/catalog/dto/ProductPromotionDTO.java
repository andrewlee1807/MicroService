package com.banvien.fc.catalog.dto;

import org.springframework.beans.factory.annotation.Value;

public interface ProductPromotionDTO {
    @Value("#{target.pimage}")
    String getImage();

    @Value("#{target.pproductoutletskuid}")
    Long getId();

    @Value("#{target.pname}")
    String getName();

    @Value("#{target.ptitle}")
    String getTitle();
}
