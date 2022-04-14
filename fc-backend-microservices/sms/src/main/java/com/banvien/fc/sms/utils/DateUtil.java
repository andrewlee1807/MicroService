package com.banvien.fc.sms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by son.nguyen on 7/26/2020.
 */
public class DateUtil {
    public static String date2String(Date input, String format) throws IllegalArgumentException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(input);
    }
}
