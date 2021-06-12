package com.ledger.company.utils;

public class StringUtils {

    public static boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
