package com.banvien.fc.order.dto.rules.Enum;

public enum  DiscountType {
    PERCENT_OFF("percentOff"),
    AMOUNT_OFF("amountOff"),
    PRODUCT("product"),
    GIFT("gift"),
    FIX_PRICE("fixPrice"),
    DELIVERY_FIXED_PRICE("deliveryFixedPrice"),
    SHIPPING_DISCOUNT("shippingDiscount"),
    SHIPPING_FIX_PRICE("shippingFixPrice"),
    DELIVERY_DISCOUNT("deliveryDiscount");

    private final String value;

    DiscountType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
