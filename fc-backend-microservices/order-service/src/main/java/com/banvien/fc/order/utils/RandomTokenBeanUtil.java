package com.banvien.fc.order.utils;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomTokenBeanUtil {
    static final String num = "0123456789";
    static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static SecureRandom rnd = new SecureRandom();

    public RandomTokenBeanUtil() {
    }

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);

        for(int i = 0; i < len; ++i) {
            if (i % 2 == 0) {
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(rnd.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
            } else {
                sb.append("0123456789".charAt(rnd.nextInt("0123456789".length())));
            }
        }

        return sb.toString();
    }

    public static String generateOrderCode() {
        DateFormat df = new SimpleDateFormat("ddMMyyyy");
        StringBuilder sb = new StringBuilder(6);

        for(int i = 0; i < 6; ++i) {
            sb.append(num.charAt(rnd.nextInt(num.length())));
        }

        return df.format(new Date()) + sb.toString();
    }
}
