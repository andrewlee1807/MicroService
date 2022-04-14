package com.banvien.fc.promotion.dto.rules.Enum;

public enum PenetrationType {
    CATEGORY(1),
    BRAND(2),
    LAPSED_USER(3);

    private final int value;

    PenetrationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PenetrationType getEnum(int value) {
        switch (value) {
            case 1:
                return CATEGORY;
            case 2:
                return BRAND;
            case 3:
                return LAPSED_USER;
            default:
                return null;
        }
    }
}
