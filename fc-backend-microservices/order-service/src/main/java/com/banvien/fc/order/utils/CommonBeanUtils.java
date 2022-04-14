package com.banvien.fc.order.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CommonBeanUtils {

    public static  <T> List<List<T>> getSubLists(List<T> sources, int sizeSubList) {

        List<List<T>> rs = new ArrayList<>();
        if (sources == null || sources.isEmpty()) {
            return rs;
        }
        if (sizeSubList <= 0) {
            rs.add(sources);
            return rs;
        }
        for (int i = 0; i < (sources.size() + sizeSubList - 1) / sizeSubList; i++) {

            int from = i * sizeSubList;
            int to = from + sizeSubList;
            if (to > sources.size()) {
                to = sources.size();
            }
            rs.add(sources.subList(from, to));
        }
        return rs;
    }

    public static Timestamp calculatorExpiredFromNowWithMonthInput(Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, month);
        int res = cal.getActualMaximum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, res);
        return new Timestamp(cal.getTimeInMillis());
    }
}
