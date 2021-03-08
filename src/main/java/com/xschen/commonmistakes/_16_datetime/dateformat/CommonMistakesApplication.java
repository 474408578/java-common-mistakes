package com.xschen.commonmistakes._16_datetime.dateformat;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xschen
 */

public class CommonMistakesApplication {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        test();

    }

    public static void test() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        System.out.println(simpleDateFormat.format(calendar.getTime()));
        //System.out.println(simpleDateFormat.format(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())));
        System.out.println(simpleDateFormat.format(LocalDateTime.now()));
    }
}
