package com.banvien.fc.common.enums;

public enum PromotionProperty {
    INDEPENDENT(0),
    COMBINE(1);

    private final int value;

    PromotionProperty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
