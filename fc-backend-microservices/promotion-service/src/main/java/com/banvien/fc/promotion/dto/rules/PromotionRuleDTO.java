package com.banvien.fc.promotion.dto.rules;

import com.banvien.fc.promotion.dto.rules.Enum.BaseOn;
import com.banvien.fc.promotion.dto.rules.Enum.DiscountType;
import com.banvien.fc.promotion.dto.rules.Enum.Operation;
import com.banvien.fc.promotion.dto.rules.Enum.PromotionType;

import java.util.ArrayList;
import java.util.List;

public class PromotionRuleDTO {
    private List<Long> ids;
    private PromotionType promotionType;
    private List<Long> excludeIds;
    private BaseOn baseOn;
    private Operation operation;
    private DiscountType discountType;
    private List<DiscountDTO> discounts;
    private Long expiredDate;
    private boolean firstOrder;
    private boolean isAll;
    private String easyOrderCode;

    public PromotionRuleDTO() {
        this.ids = new ArrayList<>();
        this.excludeIds = new ArrayList<>();
        this.discounts = new ArrayList<>();
        this.operation = Operation.OR;
        this.baseOn = BaseOn.QUANTITY;
        this.promotionType = PromotionType.PRODUCT;
        this.firstOrder = false;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
        if (ids == null) {
            this.ids = new ArrayList<>();
        }
    }

    public Long getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Long expiredDate) {
        this.expiredDate = expiredDate;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
    }

    public List<Long> getExcludeIds() {
        return excludeIds;
    }

    public void setExcludeIds(List<Long> excludeIds) {
        this.excludeIds = excludeIds;
        if (excludeIds == null) {
            this.excludeIds = new ArrayList<>();
        }
    }

    public BaseOn getBaseOn() {
        return baseOn;
    }

    public void setBaseOn(BaseOn baseOn) {
        this.baseOn = baseOn;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public List<DiscountDTO> getDiscounts() {
        discounts.sort((o1, o2) -> o1.getMinValue() > o2.getMinValue() ? 1 : -1);

        this.setDiscountsByDiscountType();
        return discounts;
    }

    public void setDiscounts(List<DiscountDTO> discounts) {
        if (discounts == null) {
            this.discounts = new ArrayList<>();
            return;
        }
        discounts.sort((o1, o2) -> o1.getMinValue() > o2.getMinValue() ? 1 : -1);
        for (DiscountDTO dto : discounts) {
            if (dto.getDiscountType() == null) {
                dto.setDiscountType(this.discountType);
            }
        }
        this.discounts = discounts;
    }

    public void setDiscountsByDiscountType() {
        if (discountType != null) {
            for (DiscountDTO dto : this.discounts) {
                dto.setDiscountType(this.discountType);
            }
        }
    }

    public boolean isFirstOrder() {
        return firstOrder;
    }

    public void setFirstOrder(boolean firstOrder) {
        this.firstOrder = firstOrder;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public boolean getIsAll() {
        return isAll;
    }

    public void setIsAll(boolean all) {
        isAll = all;
    }
    public void setAll(boolean all) {
        isAll = all;
    }

    public String getEasyOrderCode() {
        return easyOrderCode;
    }

    public void setEasyOrderCode(String easyOrderCode) {
        this.easyOrderCode = easyOrderCode;
    }
}
