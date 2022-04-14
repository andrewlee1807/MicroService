package com.banvien.fc.promotion.dto.rules.Enum;

public enum  DiscountType {
    PERCENT_OFF("percentOff", 0),
    AMOUNT_OFF("amountOff", 1),
    PRODUCT("product", 2),
    GIFT("gift",3),
    FIX_PRICE("fixPrice",4),
    DELIVERY_FIXED_PRICE("deliveryFixedPrice",5),
    SHIPPING_DISCOUNT("shippingDiscount",6),
    SHIPPING_FIX_PRICE("shippingFixPrice",7),
    DELIVERY_DISCOUNT("deliveryDiscount",8);

    private final String value;

    private final int no;

//    DiscountType(String value){
//        this.value = value;
//    }

    DiscountType(String value, int no){
        this.value = value;
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public String getValue() {
        return value;
    }
}
