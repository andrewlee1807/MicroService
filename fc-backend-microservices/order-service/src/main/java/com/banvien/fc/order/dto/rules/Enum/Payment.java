package com.banvien.fc.order.dto.rules.Enum;

public enum Payment {
    PAYMENT_METHOD_COD("COD"),
    PAYMENT_METHOD_POINT("POINT"),
    PAYMENT_METHOD_CREDIT("CREDIT"),
    PAYMENT_METHOD_GRABPAY("GRABPAY"),
    PAYMENT_METHOD_GATEWAY("GATEWAY"),
    PAYMENT_METHOD_BANK_TRANSFER("BANK_TRANSFER");

    private final String value;

    Payment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
