package com.banvien.fc.order.dto.payment;

import java.io.Serializable;

public class CertificatesDTO implements Serializable {
    private String state;

    public CertificatesDTO() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
