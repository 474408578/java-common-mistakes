package com.xschen.commonmistakes._16_datetime.calc;

import java.time.*;
import java.time.temporal.*;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author xschen
 */
public class CommonMistakesApplication {

    public static void main(String[] args) {
        wrong1();
        wrong1fix();
        right();
        better();

        test();
    }

    private static void wrong1() {
        System.out.println("wrong1");
        Date today = new Date();
        // int 发生溢出
        Date nextMonth = new Date(today.getTime() + 30 * 1000 * 60 * 60 * 24);
        System.out.println(today);
        System.out.println(nextMonth);
    }

    private static void wrong1fix() {
        System.out.println(30 * 1000 * 60 * 60 * 24 + " " + ((30 * 1000 * 60 * 60 * 24) > Integer.MAX_VALUE));
        System.out.println("wrongfix1");
        Date today = new Date();
        Date nextMonth = new Date(today.getTime() + 30L * 1000 * 60 * 60 * 24);
        System.out.println(today);
        System.out.println(nextMonth);
    }

    public static void right() {
        System.out.println("right");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 30);
        System.out.println(c.getTime());
    }

    private static void better() {
        System.out.println("better");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.plus(30, ChronoUnit.DAYS));
    }

    public static void test() {
        System.out.println("----------测试操作日期----------");
        System.out.println(LocalDate.now()
                .minus(Period.ofDays(1))
                .plus(1, ChronoUnit.DAYS)
                .minusMonths(1)
                .plus(Period.ofMonths(1)));

        System.out.println("----------计算日期差----------");
        LocalDate today = LocalDate.of(2021, 3, 12);
        LocalDate specifyDate = LocalDate.of(2021, 2, 20);
        System.out.println(Period.between(specifyDate, today).getDays());
        System.out.println(Period.between(specifyDate, today));
        System.out.println(ChronoUnit.DAYS.between(specifyDate, today));

        System.out.println("----------本月第一天----------");
        System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));

        System.out.println("----------今年的程序员节----------");
        System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).plusDays(255));

        System.out.println("----------今天之前的一个周六----------");
        System.out.println(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SATURDAY)));

        System.out.println("----------本月最后一个工作日----------");
        System.out.println(LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY)));

        System.out.println("----------自定义逻辑----------");
        System.out.println(LocalDate.now().with(
                temporal -> temporal.plus(ThreadLocalRandom.current().nextInt(100), ChronoUnit.DAYS)));

        System.out.println("----------查询是否今天要举办生日----------");
        System.out.println(LocalDate.now().query(CommonMistakesApplication::isFamilyBirthday));
    }

    public static Boolean isFamilyBirthday(TemporalAccessor date) {
        int month = date.get(ChronoField.MONTH_OF_YEAR);
        int day = date.get(ChronoField.DAY_OF_MONTH);
        if (month == Month.JULY.getValue() && day == 27)
            return Boolean.TRUE;
        if (month == Month.SEPTEMBER.getValue() && day == 21)
            return Boolean.TRUE;
        if (month == Month.MAY.getValue() && day == 22)
            return Boolean.TRUE;

        return Boolean.FALSE;

    }


}
