package ru.graduate.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtils {
    public static LocalDateTime toBeginOfDay(LocalDate ld){
        if (ld==null){
            ld = LocalDate.now();
        }
        return  LocalDateTime.of(ld, LocalTime.MIN);
    }
    public static LocalDateTime toEndOfDay(LocalDate ld){
        if (ld==null){
            ld = LocalDate.now();
        }
        return  LocalDateTime.of(ld, LocalTime.MAX);
    }
}
