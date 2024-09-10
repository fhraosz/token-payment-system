package com.bulewalnut.tokenpaymentsystem.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomKeyUtil {

    private static final String REFID_PREFIX = "CUST";
    private static final String TRANSCATION_PREFIX = "TRANSC";
    private static final Integer IDENTIFIER_BASE = 10000;
    public static final String PATTERN = "yyyyMMddHHmmssSSS";
    public static final String TRANSACTION_ID_PATTERN = "yyyyMMddHHmmss";
    private static final Integer TRANSACTION_ID_BASE = 100;

    public static String createRefId() {
        String datePart = new SimpleDateFormat(PATTERN).format(new Date());
        String uniqueNumber = String.format("%04d", new Random().nextInt(IDENTIFIER_BASE));
        return String.format("%s-%s-%s", REFID_PREFIX, datePart, uniqueNumber);
    }

    public static String createTransactionId() {
        String datePart = new SimpleDateFormat(TRANSACTION_ID_PATTERN).format(new Date());
        String uniqueNumber = String.format("%02d", new Random().nextInt(TRANSACTION_ID_BASE));
        return String.format("%s%s%s", TRANSCATION_PREFIX, datePart, uniqueNumber);
    }
}
