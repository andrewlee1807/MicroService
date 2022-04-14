package com.banvien.fc.common.dto;

/**
 * Created by son.nguyen on 7/8/2020.
 */
public class ErrorDTO {
    private Integer errorNo;
    private String errorCode;

    public Integer getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(Integer errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
