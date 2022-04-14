package com.banvien.fc.common.rest.errors;

import java.util.Date;

public class ErrorMessage {
    private Date timestamp;
    private String message;
    private String detail;
    private Integer error;
    private String uri;

    public ErrorMessage(){}

    public ErrorMessage(Integer error, Date timestamp, String message, String detail) {
        this.timestamp = timestamp;
        this.message = message;
        this.detail = detail;
        this.error = error;
    }

    public ErrorMessage(Integer error, Date timestamp, String message, String detail, String uri) {
        this.timestamp = timestamp;
        this.message = message;
        this.detail = detail;
        this.error = error;
        this.uri = uri;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
