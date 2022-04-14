package com.banvien.fc.sms.main;


import com.banvien.fc.sms.vivas.client.VivasSmsUtils;

public abstract class SmsUtils {
    private static SmsUtils instance;
    private static final String VIVAS = "VIVAS";

    public static SmsUtils getInstance() {
        if (instance == null) {
            String type = VIVAS;
            instance = new VivasSmsUtils();
        }
        return instance;
    }
    public abstract Object[] sendSms(String phoneNumber, String message);
}
