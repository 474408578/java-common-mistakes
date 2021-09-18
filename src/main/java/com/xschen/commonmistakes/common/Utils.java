package com.xschen.commonmistakes.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    /**
     * 打印线程池信息
     * @param threadPoolExecutor
     */
    public static void printStats(ThreadPoolExecutor threadPoolExecutor) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("===========================================");
            log.info("Pool Size: {}", threadPoolExecutor.getPoolSize());
            log.info("Active Threads: {}", threadPoolExecutor.getActiveCount());
            log.info("Number of Tasks completed: {}", threadPoolExecutor.getCompletedTaskCount());
            log.info("Number of Tasks in Queue： {}", threadPoolExecutor.getQueue().size());
            log.info("===========================================");
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * sleep 等待，单位为 毫秒，已捕捉异常
     * @param durationMillis
     */
    public static void sleepMillis(long durationMillis) {
        try {
            TimeUnit.MILLISECONDS.sleep(durationMillis);
        } catch (InterruptedException e) {
            /**
             *  将线程中断标志位置为true
             */
            Thread.currentThread().interrupt();
        }
    }

    /**
     * sleep 等待，单位自己设置，已捕捉异常
     * @param duration
     * @param unit
     */
    public static void sleep(long duration, TimeUnit unit) {
        long millis = unit.toMillis(duration);
        sleepMillis(millis);
    }

}
