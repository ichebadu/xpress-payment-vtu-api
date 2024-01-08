package com.iche.xpresspayapi.utils;

public class BillerTransactionIdGenerator {
    public static long generateTransactionId() {
        return System.currentTimeMillis();
    }
}
