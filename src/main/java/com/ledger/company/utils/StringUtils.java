package com.ledger.company.utils;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class StringUtils {

    public static boolean isFloat(String value) {
        try {
            parseFloat(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isInteger(String value) {
        try {
            parseInt(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
