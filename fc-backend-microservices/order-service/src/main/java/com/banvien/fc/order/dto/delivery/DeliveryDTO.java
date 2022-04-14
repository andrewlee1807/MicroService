package com.banvien.fc.order.dto.delivery;

import java.io.Serializable;
import java.util.List;

public class DeliveryDTO implements Serializable {
    private String title;
    private List<ServiceItemDTO> service;
    private String code;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ServiceItemDTO> getService() {
        return service;
    }

    public void setService(List<ServiceItemDTO> service) {
        this.service = service;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
