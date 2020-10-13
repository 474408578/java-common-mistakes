package com.xschen.commonmistakes._01_concurrenttool.threadlocal;

import com.xschen.commonmistakes.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xschen
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        Utils.loadPropertySource(CommonMistakesApplication.class, "tomcat.properties");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
