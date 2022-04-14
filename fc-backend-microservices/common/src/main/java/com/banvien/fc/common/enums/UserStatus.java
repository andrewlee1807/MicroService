package com.banvien.fc.common.enums;

/**
 * Created by son.nguyen on 7/26/2020.
 */
public enum UserStatus {
    DELETED(-1),
    PENDING(0),
    ACTIVE(1),;
    private final int value;

    private UserStatus(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
