package ru.graduate.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtils {

    private static final LocalDateTime MIN_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MAX_DATE_TIME = LocalDateTime.of(3000, 1, 1, 0, 0);

    public static LocalDateTime toBeginOfDay(LocalDate ld) {
        return ld == null ? MIN_DATE_TIME : LocalDateTime.of(ld, LocalTime.MIN);
    }

    public static LocalDateTime toEndOfDay(LocalDate ld) {
        return ld == null ? MAX_DATE_TIME : LocalDateTime.of(ld, LocalTime.MAX);
    }

    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    public static LocalDate toMinDate(LocalDate ld) {
        return ld == null ? MIN_DATE : ld;
    }

    public static LocalDate toMaxDate(LocalDate ld) {
        return ld == null ? MAX_DATE : ld;
    }

}
