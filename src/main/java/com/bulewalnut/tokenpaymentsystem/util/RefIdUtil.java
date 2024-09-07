package com.bulewalnut.tokenpaymentsystem.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RefIdUtil {

    private static final String PREFIX = "CUST";
    private static final Integer IDENTIFIER_BASE = 10000;

    public static String createRefId() {
        String datePart = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String uniqueNumber = String.format("%04d", new Random().nextInt(IDENTIFIER_BASE));
        return String.format("%s-%s-%s", PREFIX, datePart, uniqueNumber);
    }
}
