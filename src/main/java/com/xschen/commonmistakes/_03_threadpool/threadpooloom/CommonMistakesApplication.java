package com.xschen.commonmistakes._03_threadpool.threadpooloom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xschen
 * 可设置jvm参数，尽快看出效果：-Xms32m -Xmx32m
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
