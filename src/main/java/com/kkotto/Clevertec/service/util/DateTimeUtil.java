package com.kkotto.Clevertec.service.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {
    private static DateTimeUtil instance;

    private DateTimeUtil() {
    }

    public static DateTimeUtil getInstance() {
        if (instance == null) {
            instance = new DateTimeUtil();
        }
        return instance;
    }

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public String formatDateTime(LocalDateTime creationTime) {
        return creationTime.format(Constants.DATE_TIME_FORMATTER);
    }

    public String formatDate(LocalDate creationDate) {
        return creationDate.format(Constants.DATE_FORMATTER);
    }

    public String formatTime(LocalTime creationTime) {
        return creationTime.format(Constants.TIME_FORMATTER);
    }

    public String getFormattedCurrentDateTime() {
        return LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER);
    }
}
