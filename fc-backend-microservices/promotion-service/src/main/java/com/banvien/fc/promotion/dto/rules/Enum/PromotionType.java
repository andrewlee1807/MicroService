package com.banvien.fc.promotion.dto.rules.Enum;

public enum PromotionType {
    PRODUCT("id", 2),
    BRAND("brandId", 1),
    CATEGORY("categoryId", 0),
    SHIPPING("shipping", 3),
    ORDER("order", 3),
    NEXTPURCHASE("nextPurchase", 4),
    EASYORDER("easyOrder", 5);

    private final String value;

    private final int no;

    PromotionType(String value, int no) {
        this.value = value;
        this.no = no;
    }

    public String getValue() {
        return value;
    }

    public int getNo() {
        return no;
    }

    public static PromotionType getEnum(String value){
        switch (value){
            case "id":
                return PRODUCT;
            case "brandId":
                return BRAND;
            case "categoryId":
                return CATEGORY;
            case "order":
                return ORDER;
            case "shipping":
                return SHIPPING;
            case "nextPurchase":
                return NEXTPURCHASE;
            case "easyOrder":
                return EASYORDER;
        }
        return null;
    }

    public static PromotionType getEnum(int no){
        switch (no){
            case 0:
                return CATEGORY;
            case 1:
                return BRAND;
            case 2:
                return PRODUCT;
            case 3:
                return ORDER;
            case 4:
                return NEXTPURCHASE;
            case 5:
                return EASYORDER;
        }
        return null;
    }
}
