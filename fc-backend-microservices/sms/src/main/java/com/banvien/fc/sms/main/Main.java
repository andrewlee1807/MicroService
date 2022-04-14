package com.banvien.fc.sms.main;

/**
 * Created by son.nguyen on 7/26/2020.
 */
public class Main {
    public static void main(String[] arg) {
        SmsUtils.getInstance().sendSms("+84938789900", "test");
    }
}
