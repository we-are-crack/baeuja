package xyz.baeuja.api.global.util.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String toDateTimeString(LocalDateTime dateTime) {
        return dateTime.truncatedTo(ChronoUnit.SECONDS).format(FORMATTER);
    }
}
