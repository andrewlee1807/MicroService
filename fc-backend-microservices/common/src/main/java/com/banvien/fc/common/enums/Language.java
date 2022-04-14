package com.banvien.fc.common.enums;

/**
 * Created by Andrew 11/11/2020
 */
public enum Language {
    VN("vi"),
    MY("ma"),
    ID("id"),
    CN("cn");


    private final String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
