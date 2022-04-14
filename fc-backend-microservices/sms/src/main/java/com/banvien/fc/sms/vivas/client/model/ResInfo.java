package com.banvien.fc.sms.vivas.client.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class ResInfo {
    private Integer errorCode;
    private String message;
    private String phoneNumber;
    private String transactionId;
    private String requestId;
    private Integer amount;
    private String requestTime;
    private String product;
    private List<String> cardList;
    private String partnerName;
    private Integer total;
    private List<TransList> transLists;
    @XmlElement(name="error_code")
    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
    @XmlElement(name="message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @XmlElement(name="phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @XmlElement(name="transaction_id")
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    @XmlElement(name="request_id")
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    @XmlElement(name="amount")
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    @XmlElement(name="request_time")
    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }
    @XmlElement(name="product")
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
    @XmlElement(name="cardList")
    public List<String> getCardList() {
        return cardList;
    }

    public void setCardList(List<String> cardList) {
        this.cardList = cardList;
    }
    @XmlElement(name="partner_name")
    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
    @XmlElement(name="total")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    @XmlElement(name="trans_list")
    public List<TransList> getTransLists() {
        return transLists;
    }

    public void setTransLists(List<TransList> transLists) {
        this.transLists = transLists;
    }
}
