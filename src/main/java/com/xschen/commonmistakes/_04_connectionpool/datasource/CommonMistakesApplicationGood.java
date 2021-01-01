package com.xschen.commonmistakes._04_connectionpool.datasource;

import com.xschen.commonmistakes.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xschen
 */

@SpringBootApplication
public class CommonMistakesApplicationGood {

    public static void main(String[] args) {
        Utils.loadPropertySource(CommonMistakesApplicationGood.class, "other/good.properties");
        SpringApplication.run(CommonMistakesApplicationGood.class, args);
    }
}
