package com.xschen.commonmistakes._23_cachedesign.cachepenetration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 缓存穿透问题：要查的数据压根不存在，每次都会回源数据库。布隆过滤器可以解决这个问题。
 * @author xschen
 * @date 2021/9/17 17:41
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
