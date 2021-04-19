package com.xschen.commonmistakes._13_logging.duplicate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xschen
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        // 1. logger 配置继承关系导致日志重复记录
        // wrong
        //String wrong = "classpath:logging/logger-wrong.xml";
        //System.setProperty("logging.config", wrong);

        /**
         * fix-a。 logger配置继承关系，导致日志重复记录 logger 继承了 root，日志会被记录两次，注释掉appender-ref即可
         */
        //String right1 = "classpath:logging/logger-right-1.xml";
        //System.setProperty("logging.config", right1);

        /**
         * fix-b. additivity="false"，日志不向上传递，只在自己的本 logger 中输出
         */
        //String right2 = "classpath:logging/logger-right-2.xml";
        //System.setProperty("logging.config", right2);

        // 2. 错误配置 LevelFilter 造成日志重复记录
        String filterWrong = "classpath:logging/filter-wrong.xml";
        System.setProperty("logging.config", filterWrong);



        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
