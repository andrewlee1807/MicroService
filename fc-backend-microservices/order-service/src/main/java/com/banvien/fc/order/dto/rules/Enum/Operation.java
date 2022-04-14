package com.banvien.fc.order.dto.rules.Enum;

public enum  Operation {
    AND("and"),
    OR("or");

    private final String value;

    Operation(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
