package com.banvien.fc.order.dto.rules.Enum;

public enum ApiStatusCode {
    SUCCESS("Success", 200),
    BAD_REQUEST("Bad request", 400),
    UNAUTHORIZED("Unauthorized", 401),
    FORBIDDEN("Forbidden", 403),
    NOT_FOUND("Not Found", 404),
    NOT_ACCEPTABLE("Not Acceptable", 406),
    PRECONDITION_FAILED("Precondition Failed", 412),
    INTERNAL_SERVER_ERROR("Internal Server Error", 500),
    NOT_IMPLEMENTED("Not Implemented", 501),
    DUPLICATED("Duplicated constrain", 502),
    NOT_DELETED("Removed object", 202);

    private final String key;
    private final Integer value;

    ApiStatusCode(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public Integer getValue() {
        return value;
    }
}
