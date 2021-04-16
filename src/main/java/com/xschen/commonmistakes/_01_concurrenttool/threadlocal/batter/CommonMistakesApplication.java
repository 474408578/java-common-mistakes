package com.xschen.commonmistakes._01_concurrenttool.threadlocal.batter;

import com.xschen.commonmistakes.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xschen
 */

@SpringBootApplication
@ComponentScan(value = "com.xschen.commonmistakes._01_concurrenttool.threadlocal.batter")
public class CommonMistakesApplication {

    public static void main(String[] args) {
        Utils.loadPropertySource(CommonMistakesApplication.class, "tomcat.properties");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
