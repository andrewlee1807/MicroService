package com.banvien.fc.order.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceBeanUtils {

    public static String formatPrice(Double aDouble) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ROOT);
        if (aDouble < 0d) {
            aDouble = aDouble * (-1);
            String currency = format.format(aDouble);
            String last = currency.substring(currency.lastIndexOf(CoreConstants.dotCharacter) + 1);
            if (last.equals(CoreConstants.doubleZero))
                return CoreConstants.minusOperator+currency.substring(2, currency.lastIndexOf(CoreConstants.dotCharacter));
            return CoreConstants.minusOperator+currency.substring(2);
        } else if (aDouble > 0d) {
            String currency = format.format(aDouble);
            String last = currency.substring(currency.lastIndexOf(CoreConstants.dotCharacter) + 1);
            if (last.equals(CoreConstants.doubleZero))
                return currency.substring(2, currency.lastIndexOf(CoreConstants.dotCharacter));
            return currency.substring(2);
        } else {
            return "0";
        }
    }

    // return true data , remove 0.0000000001, value type of double
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
