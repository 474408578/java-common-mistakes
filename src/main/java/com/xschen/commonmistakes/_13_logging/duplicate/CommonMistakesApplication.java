package com.xschen.commonmistakes._13_logging.duplicate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xschen
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        System.setProperty("logging.config", "classpath:logging/multiple-levels-filter.xml");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
