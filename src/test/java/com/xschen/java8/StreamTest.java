package com.xschen.java8;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author xschen
 */


public class StreamTest {

    @Test
    public void IntStreamTest() {
        Arrays.asList("a", "b", "c").stream()
                .mapToInt(String::length)
                .asLongStream()
                .mapToDouble(x -> x / 10.0)
                .boxed()
                .mapToLong(x -> 1L)
                .mapToObj(x -> "")
                .forEach(System.out::println);
    }
}
