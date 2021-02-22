package com.xschen.commonmistakes.java8;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;

/**
 * Lambda 表达式 示例
 * @see Supplier
 * @see Predicate
 * @see Consumer
 * @see Function
 * @author xschen
 */

public class LambdaTest {

    /**
     * Lambda 与 匿名类比较
     */
    @Test
    public void lambdaVsAnonymousClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello, anonymous class");
            }
        }).start();

        new Thread(() -> System.out.println("hello, lambda")).start();
    }

    /**
     * 函数式接口
     * 可以看一下 java.util.function包
     */
    @Test
    public void functionalInterfaces() {

        // Predicate 示例, 输入一个参数，返回一个 bool 值
        Predicate<Integer> positiveNumber = i -> i > 0; // 是否大于0
        Predicate<Integer> eventNumber = i -> i % 2 == 0; // 是否为偶数
        Assert.assertTrue(positiveNumber.and(eventNumber).test(8));

        // consumer 示例， 消费数据
        Consumer<String> println = System.out::println;
        println.andThen(println).accept("abcde"); // 连续两次输出"abcde"

        // Function 示例， 输入一个数据，计算后输出一个数据
        Function<String, String> upperCase = String::toUpperCase;
        Function<String, String> duplicate = s -> s.concat(s);
        Assert.assertThat(upperCase.andThen(duplicate).apply("test"), Matchers.is("TESTTEST"));

        // Supplier 示例，提供一个数据
        // 返回空字符串
        Supplier<String> supplier = String::new;
        // 返回 OK 字符串
        Supplier<String> stringSupplier = () -> "ok";
        Supplier<Integer> random = () -> ThreadLocalRandom.current().nextInt();
        System.out.println(random.get());

        // BinaryOperator 示例
        BinaryOperator<Integer> add = Integer::sum;
        BinaryOperator<Integer> subtraction = (a, b) -> a - b;
        Assert.assertThat(subtraction.apply(add.apply(1, 2), 3), Matchers.is(0));
    }
}
