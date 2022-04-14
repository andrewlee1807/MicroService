package com.banvien.fc.sms.vivas.client.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "RQST")
@XmlType(propOrder={"reqId","brandName","textMsg","sendTime","type","isUnicode","msgInfo"})
public class SmsReq {
    //ID của request của hệ thống phía bên đối tác Là chuỗi ký tự, độ dài tối đa 255 ký tự, không được trùng nhau cho mỗi request
    private String reqId;
    //Tên Brandname Case sensitive
    private String brandName;
    //Nội dung tin nhắn Không chứa các ký tự đặc biệt charcode lớn hơn 127
    private String textMsg;
    //Thời gian gửi tin Theo format yyyyMMddHHmmss
    private String sendTime;
    //Loại SMS 1: CSKH 2:QC
    private String type;
    //Tin nhắn Unicode 0: nếu là tin nhắn không dấu  8: nếu là tin nhắn unicode
    private String isUnicode;
    //Mỗi số điện thoại nhận tin nhắn được gửi trong một khối DESTINATION
    private MsgInfo msgInfo;

    @XmlElement(name = "REQID")
    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }
    @XmlElement(name = "BRANDNAME")
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    @XmlElement(name = "TEXTMSG")
    public String getTextMsg() {
        return textMsg;
    }

    public void setTextMsg(String textMsg) {
        this.textMsg = textMsg;
    }
    @XmlElement(name = "SENDTIME")
    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
    @XmlElement(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @XmlElement(name = "ISUNICODE")
    public String getIsUnicode() {
        return isUnicode;
    }

    public void setIsUnicode(String isUnicode) {
        this.isUnicode = isUnicode;
    }

    @XmlElement(name = "DESTINATION")
    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public SmsReq() {
    }

    public SmsReq(String reqId, String brandName, String textMsg, String sendTime, String type, String isUnicode) {
        this.reqId = reqId;
        this.brandName = brandName;
        this.textMsg = textMsg;
        this.sendTime = sendTime;
        this.type = type;
        this.isUnicode = isUnicode;
    }
}
