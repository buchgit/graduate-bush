package ru.graduate.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtils {

    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 1, 1, 0, 0);

    public static LocalDateTime toBeginOfDay(LocalDate ld){
        return ld==null?MIN_DATE:LocalDateTime.of(ld, LocalTime.MIN);
    }
    public static LocalDateTime toEndOfDay(LocalDate ld){
        return ld==null?MAX_DATE:LocalDateTime.of(ld, LocalTime.MAX);
    }
}
