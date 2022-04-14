package com.banvien.fc.order.dto.rules;

import com.banvien.fc.order.dto.rules.Enum.BaseOn;
import com.banvien.fc.order.dto.rules.Enum.DiscountType;
import com.banvien.fc.order.dto.rules.Enum.Operation;
import com.banvien.fc.order.dto.rules.Enum.PromotionType;

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
    private boolean firstOrder;

    public PromotionRuleDTO() {
        this.ids = new ArrayList<>();
        this.excludeIds = new ArrayList<>();
        this.discounts = new ArrayList<>();
        this.operation = Operation.OR;
        this.baseOn = BaseOn.QUANTITY;
        this.promotionType = PromotionType.PRODUCT;
        this.firstOrder = false;
    }

    public PromotionRuleDTO(List<Long> ids, PromotionType promotionType, List<Long> excludeIds, BaseOn baseOn, Operation operation, List<DiscountDTO> discounts, boolean isFirstOrder) {
        this.ids = ids;
        this.promotionType = promotionType;
        this.excludeIds = excludeIds;
        this.baseOn = baseOn;
        this.operation = operation;
        discounts.sort((o1, o2) -> o1.getMinValue() > o2.getMinValue() ? 1 : 0);
        this.discounts = discounts;
        this.firstOrder = isFirstOrder;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
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
        for (DiscountDTO dto : discounts){
            if (dto.getDiscountType() == null){
                dto.setDiscountType(this.discountType);
            }
        }
        return discounts;
    }

    public void setDiscounts(List<DiscountDTO> discounts) {
        discounts.sort((o1, o2) -> o1.getMinValue() > o2.getMinValue() ? 1 : -1);
        for (DiscountDTO dto : discounts){
            if (dto.getDiscountType() == null){
                dto.setDiscountType(this.discountType);
            }
        }
        this.discounts = discounts;
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
}
