package com.xschen.commonmistakes._05_httpinvoke.feignandribbontimeout;

import com.xschen.commonmistakes.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xschen
 */

@SpringBootApplication
public class CommonMistakesApplicationDefault {

    public static void main(String[] args) {
        Utils.loadPropertySource(CommonMistakesApplicationDefault.class, "_05_httpinvoke/default.properties");
        SpringApplication.run(CommonMistakesApplicationDefault.class, args);
    }
}
