package com.bulewalnut.tokenpaymentsystem.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static String formatToDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
        return dateTime.format(formatter);
    }
}
