package com.banvien.fc.order.status;

public class StatusResponse {
    private String code;    // ERROR CODE
    private String value;
    private Long id;
    private String authenUrl;
    private String orderCode;

    public StatusResponse() {
    }

    public StatusResponse(String code) {
        this.code = code;
    }

    public StatusResponse(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAuthenUrl() {
        return authenUrl;
    }

    public void setAuthenUrl(String authenUrl) {
        this.authenUrl = authenUrl;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
