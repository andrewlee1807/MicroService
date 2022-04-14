package com.banvien.fc.common.enums;

public enum SyncTracking {
    SYNC_PROMOTION("SYNC_PROMOTION");

    private final String value;

    SyncTracking(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
