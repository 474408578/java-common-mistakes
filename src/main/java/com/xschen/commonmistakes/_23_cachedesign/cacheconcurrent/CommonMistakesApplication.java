package com.xschen.commonmistakes._23_cachedesign.cacheconcurrent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 缓存击穿问题：高并发场景下，热点key过期，会导致大量的并发请求同时回源，打到数据库中。
 * @author xschen
 * @date 2021/9/8 18:20
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
