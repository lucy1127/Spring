package com.example.Spring2.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Time {
    public static String localDate() {
        LocalDate localDate = Instant.now().atZone(ZoneOffset.ofHours(+8)).toLocalDate();

        return localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String localTime() {
        LocalTime localTime = Instant.now().atZone(ZoneOffset.ofHours(+8)).toLocalTime();
        return localTime.format(DateTimeFormatter.ofPattern("HHmmssSSS"));
    }

}
