package com.ledger.company.utils;

import org.junit.jupiter.api.Test;

import static com.ledger.company.utils.StringUtils.isFloat;
import static com.ledger.company.utils.StringUtils.isInteger;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void isFloatTest() {
        assertTrue(isFloat("30.14"));
        assertTrue(isFloat("-30.14"));
        assertFalse(isFloat("30.14a"));
        assertFalse(isFloat("a30.14"));
        assertFalse(isFloat("30!14"));
        assertFalse(isFloat("30..14"));
    }

    @Test
    void isIntegerTest() {
        assertTrue(isInteger("5"));
        assertTrue(isInteger("-5"));
        assertFalse(isInteger("5.0"));
        assertFalse(isInteger("xt"));
        assertFalse(isInteger("5.2"));
    }
}