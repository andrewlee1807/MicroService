package com.banvien.fc.promotion.dto.rules.Enum;

public enum  Operation {
    AND("and", 1),
    OR("or", 0);

    private final String value;

    private final int no;

    Operation(String value, int no){
        this.value = value;
        this.no = no;
    }

    public String getValue() {
        return value;
    }

    public int getNo() {
        return no;
    }
}
