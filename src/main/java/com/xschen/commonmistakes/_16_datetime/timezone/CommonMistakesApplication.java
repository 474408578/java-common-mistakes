package com.xschen.commonmistakes._16_datetime.timezone;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author xschen
 */

public class CommonMistakesApplication {

    public static void main(String[] args) {
        test();
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
        System.out.println(TimeZone.getDefault().getID() + ":" + TimeZone.getDefault().getRawOffset() / 3600000);
        ZoneId.getAvailableZoneIds().forEach(id ->
                System.out.println(String.format("%s: %s", id, ZonedDateTime.now(ZoneId.of(id)))));
    }

    private static void wrong1() {

    }
}
