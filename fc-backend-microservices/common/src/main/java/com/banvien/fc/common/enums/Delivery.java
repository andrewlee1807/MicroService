package com.banvien.fc.common.enums;

public enum Delivery {
    PICK_N_GO("PICK_N_GO"),
    PAY_N_GET("PAY_N_GET"),
    SHIP_TO_HOME("SHIP_TO_HOME"),
    SHIP_TO_HOME_EXPRESS("SHIP_TO_HOME_EXPRESS");

    private final String value;

    Delivery(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
