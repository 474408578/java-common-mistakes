package com.xschen.commonmistakes._13_logging.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xschen
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        /**
         * 同步方式记录日志
         */
        //String sync = "classpath:logging/performance-sync.xml";
        //System.setProperty("logging.config", sync);


        String async = "classpath:logging/performance-async.xml";
        System.setProperty("logging.config", async);
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
