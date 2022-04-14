package com.banvien.fc.sms.vivas.client.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "DESTINATION")
@XmlType(propOrder={"msgId","msisdn","checkSum","msg"})
public class MsgInfo {
    // ID của SMS phía hệ thống đối tác
    private String msgId;
    //Số điện thoại nhận SMS  Format bắt đầu 84
    private String msisdn;
    private String msg;
    //CHECKSUM của SMS
    private String checkSum;

    @XmlElement(name = "MSGID")
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    @XmlElement(name = "MSISDN")
    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    @XmlElement(name = "RESULT")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @XmlElement(name = "CHECKSUM")
    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public MsgInfo() {
    }

    public MsgInfo(String msgId, String msisdn, String checkSum) {
        this.msgId = msgId;
        this.msisdn = msisdn;
        this.checkSum = checkSum;
    }
}
