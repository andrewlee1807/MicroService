package com.banvien.fc.order.dto.rules.Enum;

public enum  PromotionType {
    PRODUCT("id", 2),
    BRAND("brandId", 1),
    CATEGORY("categoryId", 0),
    SHIPPING("shipping", 3),
    ORDER("order", 3),
    NEXTPURCHASE("nextPurchase", 4),
    EASYORDER("easyOrder", 5);

    private final String value;

    private final int no;

    PromotionType(String value, int no){
        this.value = value;
        this.no = no;
    }

    public String getValue() {
        return value;
    }

    public int getNo() {
        return no;
    }
}
