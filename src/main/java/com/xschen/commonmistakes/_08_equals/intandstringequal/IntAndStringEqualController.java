package com.xschen.commonmistakes._08_equals.intandstringequal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 1. 对基本类型，比如 int、long，进行判等，只能使用 ==，比较的是直接值。因为基本类型的值就是其数值。
 * 2. 对引用类型，比如 Integer、Long 和 String，进行判等，需要使用 equals 进行内容判等。因为引用类型的直接值是指针，
 *    使用 == 的话，比较的是指针，也就是两个对象在内存中的地址，即比较它们是不是同一个对象，而不是比较对象的内容。
 * @author xschen
 * @date 2021/9/23 9:17
 * @see java.lang.Integer -> private static class IntegerCache
 */

@RestController
@Slf4j
@RequestMapping("intandstringequal")
public class IntAndStringEqualController {


    /**
     * 比较 Integer的值请使用equals, 而不是 ==
     */
    @GetMapping("intcompare")
    public void intCompare() {
        Integer a = 127; // Integer.valueOf(127) 自动装箱
        Integer b = 127; // Integer.valueOf(127)
        log.info("\nInteger a = 127;\n" + "Integer b = 127;\n" + "a == b ? {}", a == b); // true

        Integer c = 128; // Integer.valueOf(128)
        Integer d = 128; // Integer.valueOf(128)
        log.info("\nInteger c = 128;\n" + "Integer d = 128;\n" + "c == d ? {}", c == d); // false
        // 设置 -XX:AutoBoxCacheMax=1000 再试试 默认情况下会缓存[-128,127]的数值，在此区间的同一数值指向的是同一个对象（Java享元模式）


        Integer e = 127; //Integer.valueOf(127)
        Integer f = new Integer(127); //new instance
        log.info("\nInteger e = 127;\n" + "Integer f = new Integer(127);\n" + "e == f ? {}", e == f);   //false

        Integer g = new Integer(127); //new instance
        Integer h = new Integer(127); //new instance
        log.info("\nInteger g = 127;\n" + "Integer h = 127;\n" + "g == h ? {}", g == h); // false

        Integer i = 128; // unbox -> compare
        int j = 128;
        log.info("\nInteger i = 128;\n" + "int j = 128;\n" + "i == j ? {}", a == j);
    }

    // 线上案例
    @PostMapping("enumcompare")
    public void enumCompare(@RequestBody OrderQuery orderQuery) {
        StatusEnum statusEnum = StatusEnum.DELIVERED;
        log.info("orderQuery:{} statusEnum:{} result:{}", orderQuery, statusEnum, statusEnum.getStatus() == orderQuery.getStatus()); // false
    }

    /**
     * 字符串驻留/池化：当代码中出现双引号形式创建字符串对象时，JVM 会先对这个字符串进行检查，
     *      如果字符串常量池中存在相同内容的字符串对象的引用，则将这个引用返回；
     *      否则，创建新的字符串对象，然后将这个引用放入字符串常量池，并返回该引用。
     */
    @GetMapping("stringcompare")
    public void stringCompare() {
        String a = "1";
        String b = "1";
        log.info("\nString a = \"1\";\n" + "String b = \"1\";\n" + "a == b ? {}", a == b); //true

        String c = new String("2");
        String d = new String("2");
        log.info("\nString c = new String(\"2\");\n" + "String d = new String(\"2\");\n" + "a == b ? {}", c == d); // false

        String e = new String("3").intern();
        String f = new String("3").intern();
        log.info("\nString e = new String(\"3\").intern();\n" + "String f = new String(\"3\").intern();\n" + "e == f ? {}", e == f); // true

        String g = new String("4");
        String h = new String("4");
        log.info("\nString g = new String(\"4\");\n" + "String h = new String(\"4\");\n" + "g.equals(h) ? {}", g.equals(h)); // true
    }

    /**
     * 滥用 intern(), 会产生性能问题
     * @param size
     * @return
     */
    @GetMapping("internperformance")
    public int internPerformance(@RequestParam(value = "size", defaultValue = "10000000") int size) {
        // -XX:+PrintStringTableStatistics
        // 未设置-XX:StringTableSize=10000000, size: 10000000 took:34335 ms
        // 设置-XX:StringTableSize=10000000，size: 10000000 took:6469 ms
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List list = IntStream.rangeClosed(1, size)
                .mapToObj(i -> String.valueOf(i).intern())
                .collect(Collectors.toList());
        stopWatch.stop();
        log.info("size: {} took:{} ms", size, stopWatch.getTotalTimeMillis());
        return list.size(); //
    }

}
