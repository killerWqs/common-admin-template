package com.killer.demo.utils;

import java.text.DecimalFormat;

/**
 *@description
 *@author killerWqs
 *@date 2019/02/20 - 21:14
 */
public class NumberUtils {
    public static Double stringToDouble(String numberS, int precison) {
        Double aDouble = Double.valueOf(numberS);

        StringBuilder format = new StringBuilder("#.");
        for(int i = 0; i < precison; i++) {
            format.append("0");
        }

        DecimalFormat decimalFormat = new DecimalFormat(format.toString());
        String result = decimalFormat.format(aDouble);
        return Double.valueOf(result);
    }

    public static Float stringToFloat(String numberS, int precison) {
        Float aFloat = Float.valueOf(numberS);

        StringBuilder format = new StringBuilder("#.");
        for(int i = 0; i < precison; i++) {
            format.append("0");
        }

        //
        DecimalFormat decimalFormat = new DecimalFormat(format.toString());
        String result = decimalFormat.format(aFloat);
        return Float.valueOf(result);
    }

    public static void main(String[] args) {
        String a = "12.334365345";

        System.out.println(stringToDouble(a, 4));
    }
}
