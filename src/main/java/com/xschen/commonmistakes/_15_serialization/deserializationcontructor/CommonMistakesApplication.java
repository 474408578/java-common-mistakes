package com.xschen.commonmistakes._15_serialization.deserializationcontructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 反序列化时要小心类的构造方法
 * @author xschen
 * @date 2021/9/7 18:32
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
