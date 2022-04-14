package com.banvien.fc.common.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {
    public enum NUMBER {
        ZERO(0.00, "0"),
        ONE(0.01, "1"),
        TWO(0.02, "2"),
        THREE(0.03, "3"),
        FOUR(0.04, "4"),
        FIVE(0.05, "5"),
        SIX(0.06, "6"),
        SEVEN(0.07, "7"),
        EIGHT(0.08, "8"),
        NINE(0.09, "9"),
        TEN(0.10, "10");
        private final double value;
        private final String code;

        NUMBER(double value, String code) {
            this.value = value;
            this.code = code;
        }

        public double getValue() {
            return this.value;
        }

        public String getCode() {
            return this.code;
        }
    }

    public static void main(String[] args) {
        double doubleNumber = 2.32;
        System.out.println("number: " + calculate(doubleNumber));

        System.out.println("string: " + toString(calculate(doubleNumber)));
    }

    public static <T extends Number> Double calculate(T price) {
        double p;
        if (price instanceof Double) {
            BigDecimal input = new BigDecimal((Double) price).setScale(2, RoundingMode.HALF_UP);
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(input));
            int intValue = bigDecimal.intValue();
            String decimal = bigDecimal.subtract(new BigDecimal(intValue)).toPlainString();
            String s = String.valueOf(decimal.charAt(3));
            double d = !s.equalsIgnoreCase(NUMBER.ZERO.code) ? Double.valueOf(new StringBuffer(decimal).replace(3,4, "").toString()) : 0D;
            if (s.contains(NUMBER.ONE.code) || s.contains(NUMBER.TWO.code)) {
                p = twoDecimal(d + intValue + NUMBER.ZERO.value);
            } else if (s.contains(NUMBER.THREE.code) || s.contains(NUMBER.FOUR.code)
                    || s.contains(NUMBER.SIX.code) || s.contains(NUMBER.SEVEN.code)) {
                p = twoDecimal(d + intValue + NUMBER.FIVE.value);
            } else if (s.contains(NUMBER.EIGHT.code) || s.contains(NUMBER.NINE.code)) {
                p = twoDecimal(d + intValue + NUMBER.TEN.value);
            }else{
                p = input.doubleValue();
            }
        }else{
//            p = price.doubleValue();
            return null;
        }
        return p;
    }

    private static double twoDecimal(double number){
        return new BigDecimal(number).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static String toString(Double number){
        return number.toString().length() == 3 ? number.toString() + "0" : number.toString();
    }
}