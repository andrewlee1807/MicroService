package com.banvien.fc.sms.utils;

import java.security.MessageDigest;

/**
 * Created by son.nguyen on 7/26/2020.
 */
public class MD5Utils {
    public MD5Utils() {
    }

    public static String md5(String plainText) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            byte[] input = plainText.getBytes("UTF-8");
            messageDigest.update(input);
            byte[] md5hash = messageDigest.digest();
            return convertToHex(md5hash);
        } catch (Exception var4) {
            return "";
        }
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();

        for(int i = 0; i < data.length; ++i) {
            int halfbyte = data[i] >>> 4 & 15;
            int var4 = 0;

            do {
                if (0 <= halfbyte && halfbyte <= 9) {
                    buf.append((char)(48 + halfbyte));
                } else {
                    buf.append((char)(97 + (halfbyte - 10)));
                }

                halfbyte = data[i] & 15;
            } while(var4++ < 1);
        }

        return buf.toString();
    }
}
