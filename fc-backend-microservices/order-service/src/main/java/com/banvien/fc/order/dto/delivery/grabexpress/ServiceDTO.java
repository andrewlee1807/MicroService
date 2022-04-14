package com.banvien.fc.order.dto.delivery.grabexpress;

import java.io.Serializable;

public class ServiceDTO implements Serializable {
    private Integer id;
    private String type;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
