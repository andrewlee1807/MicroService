package com.banvien.fc.order.dto.rules.Enum;

public enum  BaseOn {
    QUANTITY("quantity"),
    AMOUNT("amount");

    private final String value;

    BaseOn(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getValueUper(){
        return value.replaceFirst(value.substring(0,1),value.substring(0,1).toUpperCase());
    }
}
