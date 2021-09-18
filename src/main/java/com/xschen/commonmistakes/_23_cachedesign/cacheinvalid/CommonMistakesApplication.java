package com.xschen.commonmistakes._23_cachedesign.cacheinvalid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 缓存雪崩问题：短时间内大量缓存失效，导致瞬间有大量的数据需要回源到数据库，极限情况下甚至导致后端数据库直接崩溃。
 * @author xschen
 * @date 2021/9/8 18:20
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
