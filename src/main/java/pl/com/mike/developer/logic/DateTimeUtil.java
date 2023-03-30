package pl.com.mike.developer.logic;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    private static DateTimeFormatter timeFormatterLong = DateTimeFormatter.ofPattern("H:mm:ss");
    private static DateTimeFormatter timeFormatterShort = DateTimeFormatter.ofPattern("H:mm");
    private static DateTimeFormatter timeFormatterHour = DateTimeFormatter.ofPattern("H");

    public static LocalTime parseHour(String hour) {
        if (hour == null || hour.isEmpty() || "null".equalsIgnoreCase(hour)) {
            return null;
        }
        try {
            return LocalTime.parse(hour, timeFormatterLong);
        } catch (DateTimeParseException e) {
            try {
                return LocalTime.parse(hour, timeFormatterShort);
            } catch (DateTimeParseException ex) {
                return LocalTime.parse(hour, timeFormatterHour);
            }
        }
    }

    static boolean isWeekend(LocalDateTime dateTime) {
        return isWeekend(dateTime.toLocalDate());
    }

    static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return isWeekend(dayOfWeek);
    }

    static boolean isSunday(LocalDate date) {
        return DayOfWeek.SUNDAY.equals(date.getDayOfWeek());
    }


    static boolean isWeekend(DayOfWeek dayOfWeek) {
        return DayOfWeek.SUNDAY.equals(dayOfWeek) || DayOfWeek.SATURDAY.equals(dayOfWeek);
    }

    static Integer dayOfWeek(LocalDateTime dateTime) {
        return dayOfWeek(dateTime.toLocalDate());
    }

    static Integer dayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getValue();
    }

}
