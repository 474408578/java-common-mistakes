package com.xschen.commonmistakes.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * @author xschen
 */

@Slf4j
public class Utils {

    /***
     * 加载配置文件，并设置为系统参数
     * @param clazz
     * @param fileName
     */
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


    // 格式化输出json
    public static void printJson(Object object) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        System.out.println(gson.toJson(object));
    }

}
