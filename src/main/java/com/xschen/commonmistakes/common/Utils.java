package com.xschen.commonmistakes.common;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * @author xschen
 */

@Slf4j
public class Utils {

    public static void loadPropertySource(Class clazz, String fileName) {
        try {
            Properties properties = new Properties();
            properties.load(clazz.getClassLoader().getResourceAsStream(fileName));
            properties.forEach((k, v) -> {
                log.info("{}={}", k, v);
                System.setProperty(k.toString(), v.toString());
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
