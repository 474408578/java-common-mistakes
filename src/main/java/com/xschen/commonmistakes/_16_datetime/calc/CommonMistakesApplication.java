package com.xschen.commonmistakes._16_datetime.calc;

import java.util.Date;

/**
 * @author xschen
 */
public class CommonMistakesApplication {

    public static void main(String[] args) {
        wrong1();
        wrong1fix();
    }

    private static void wrong1() {
        System.out.println("wrong1");
        Date today = new Date();
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
}
