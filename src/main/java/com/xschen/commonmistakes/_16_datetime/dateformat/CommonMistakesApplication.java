package com.xschen.commonmistakes._16_datetime.dateformat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    public static void main(String[] args) throws ParseException, InterruptedException {
        //test();

        //wrong1();
        //wrong1fix();
        //better();

        //wrong2();
        wrong2fix();

    }

    public static void test() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        System.out.println(simpleDateFormat.format(calendar.getTime()));
        System.out.println(dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())));
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
    }

    private static void wrong1() throws ParseException {
        // 三个问题，YYYY, 线程不安全， 不合法格式
        //Locale.setDefault(Locale.FRANCE); // 区域改为法国
        System.out.println("defaultLocale: " + Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 29, 0, 0, 0);
        SimpleDateFormat YYYY = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println("格式化：" + YYYY.format(calendar.getTime()));
        System.out.println("weekYear: "+ calendar.getWeekYear());

        System.out.println("firstDayOfWeek: " + calendar.getFirstDayOfWeek());
        System.out.println("minimalDayInFirstWeek: " + calendar.getMinimalDaysInFirstWeek());

        String dateString = "20190601";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        System.out.println("result: " + dateFormat.parse(dateString));
    }

    private static void wrong1fix() throws ParseException {
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 29, 0, 0, 0);
        System.out.println("格式化：" + yyyy.format(calendar.getTime()));

        String dateString = "20190601";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        System.out.println("result: " + dateFormat.parse(dateString));
    }

    private static void better() {
        LocalDateTime localDateTime = LocalDateTime.parse("2020-01-02 12:58:56", dateTimeFormatter);
        System.out.println(localDateTime.format(dateTimeFormatter));

        String dt = "20160701";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM");
        // 抛出异常
        System.out.println("result: " + dateTimeFormatter.parse(dt));
    }

    private static void wrong2() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(simpleDateFormat.parse("2020-01-01 11:12:13"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    private static void wrong2fix() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                for (int j = 0; j < 20; j++) {
                    try {
                        System.out.println(threadSafeSimpleDateFormat.get().parse("2020-01-01 11:12:13"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }
}
