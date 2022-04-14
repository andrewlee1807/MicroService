package com.banvien.fc.common.enums;

public enum MarkForDeleteStatus {
    DELETED(1),
    ACTIVE(0),;
    private final int value;

    private MarkForDeleteStatus(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
