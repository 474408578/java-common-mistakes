package com.xschen.commonmistakes._08_equals.equalitymethod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 实现 equals 方法
 * @author xschen
 * @date 2021/9/23 11:17
 * @see Object#equals(Object)
 * @see Integer#equals(Object)
 * @see String#equals(Object)
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
