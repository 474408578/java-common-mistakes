package com.xschen.commonmistakes._04_connectionpool.datasource;

import com.xschen.commonmistakes.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xschen
 */


@SpringBootApplication
public class CommonMistakesApplicationBad {

    public static void main(String[] args) {
        Utils.loadPropertySource(CommonMistakesApplicationBad.class, "other/bad.properties");
        SpringApplication.run(CommonMistakesApplicationBad.class, args);
    }
}
