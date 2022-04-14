package com.banvien.fc.order.dto.delivery.grabexpress;

import java.io.Serializable;


public class OriginDTO implements Serializable {
    private String address;
    private String keywords;
    private CoordinatesDTO coordinates;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public CoordinatesDTO getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesDTO coordinates) {
        this.coordinates = coordinates;
    }
}
