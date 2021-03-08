package com.xschen.commonmistakes._16_datetime.timezone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author xschen
 */

public class CommonMistakesApplication {

    public static void main(String[] args) throws ParseException {
        test();
        wrong1();
        wrong2();
        right();
    }

    /**
     * Date 并无时区问题，世界上任何一台计算机使用 new Date() 初始化得到的时间都一样。
     * 因为，Date 中保存的是 UTC 时间，UTC 是以原子钟为基础的统一时间，不以太阳参照计时，并无时区划分
     *
     * Date 中保存的是一个时间戳，代表的是从 1970 年 1 月 1 日 0 点（Epoch 时间）到现在的毫秒数。
     */
    private static void test() {
        System.out.println("test");
        System.out.println(new Date(0));
        // 当前时区相比 UTC 时差
        //System.out.println(TimeZone.getDefault().getID() + ":" + TimeZone.getDefault().getRawOffset() / 3600000);
        //ZoneId.getAvailableZoneIds().forEach(id ->
        //        System.out.println(String.format("%s: %s", id, ZonedDateTime.now(ZoneId.of(id)))));
    }

    /**
     * 不同时区转化得到的Date会得到不同的时间
     * @throws ParseException
     */
    private static void wrong1() throws ParseException {
        System.out.println("wrong1");
        String stringDate = "2020-01-02 22:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 默认时区解析表示
        Date date1 = inputFormat.parse(stringDate);
        System.out.println(date1);
        // 美国过纽约时区
        inputFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date2 = inputFormat.parse(stringDate);
        System.out.println(date2);
    }

    /**
     * 同一个Date，在不同时区格式化得到不同时间表示
     * @throws ParseException
     */
    public static void wrong2() throws ParseException {
        System.out.println("wrong2");
        String stringDate = "2020-01-02 22:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = inputFormat.parse(stringDate);
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss Z]").format(date1));
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss Z]").format(date1));
    }

    public static void right() {
        System.out.println("right");
        String stringDate = "2020-01-02 22:00:00";
        // 初始化3个时区
        ZoneId timeZoneShanghai = ZoneId.of("Asia/Shanghai");
        ZoneId timeZoneNewYork = ZoneId.of("America/New_York");
        // 东京
        ZoneId timeZoneJst = ZoneOffset.ofHours(9);

        // 格式化器
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime date = ZonedDateTime.of(LocalDateTime.parse(stringDate, dateTimeFormatter), timeZoneJst);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        System.out.println(timeZoneShanghai.getId() + " " + outputFormatter.withZone(timeZoneShanghai).format(date));
        System.out.println(timeZoneNewYork.getId() + " " + outputFormatter.withZone(timeZoneNewYork).format(date));
        System.out.println(timeZoneJst.getId() + " " + outputFormatter.withZone(timeZoneJst).format(date));
    }
}
