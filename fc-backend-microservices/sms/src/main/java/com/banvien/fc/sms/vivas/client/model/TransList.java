package com.banvien.fc.sms.vivas.client.model;

import javax.xml.bind.annotation.XmlElement;

public class TransList {
    private Integer serviceType;
    private Integer status;
    private String transactionId;
    private String createdAt;
    @XmlElement(name="service_type")
    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }
    @XmlElement(name="status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    @XmlElement(name="transaction_id")
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    @XmlElement(name="created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
