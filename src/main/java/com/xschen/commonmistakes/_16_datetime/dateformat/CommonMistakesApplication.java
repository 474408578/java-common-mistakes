package com.xschen.commonmistakes._16_datetime.dateformat;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xschen
 */

public class CommonMistakesApplication {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final ThreadLocal<SimpleDateFormat> threadSafeSimpleDateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR)
            .appendLiteral("-")
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendLiteral("-")
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .appendLiteral(" ")
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(":")
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .appendLiteral(":")
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .toFormatter();

    public static void main(String[] args) {
        test();

    }

    public static void test() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        System.out.println(simpleDateFormat.format(calendar.getTime()));
        System.out.println(dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())));
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
    }
}
