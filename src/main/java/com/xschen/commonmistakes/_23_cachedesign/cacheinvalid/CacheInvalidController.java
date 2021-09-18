package com.xschen.commonmistakes._23_cachedesign.cacheinvalid;

import com.xschen.commonmistakes.common.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 缓存雪崩 Controller
 * @author xschen
 * @date 2021/9/14 17:21
 * 应用层面大量的key在同一时间过期，导致大量数据回源
 */
@Slf4j
@RestController
@RequestMapping("cacheinvalid")
public class CacheInvalidController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private AtomicInteger atomicInteger = new AtomicInteger();

//    @PostConstruct
    public void wrongInit() {
        Long startTime = System.currentTimeMillis();
        // 初始化1000个城市数据到Redis，所有缓存数据有效期为30秒
        IntStream.rangeClosed(1, 1000)
                .parallel().forEach(i -> stringRedisTemplate.opsForValue().set("city" + i,
                        getCityFromDb(i),
                        30,
                        TimeUnit.SECONDS));
        Long endTime = System.currentTimeMillis();
        log.info("wrongInit costTime: {} ms.", endTime - startTime);
        log.info("wrongInit Cache init finished.");
        // 每秒1次，输出数据库访问的qps
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS: {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * 差异化过期时间，不要让大量的key在同一时间过期
     */
//    @PostConstruct
    public void rightInit1() {
        Long startTime = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1000)
                .parallel().forEach(i -> stringRedisTemplate.opsForValue().set("city" + i,
                        getCityFromDb(i),
                        30 + ThreadLocalRandom.current().nextInt(30),
                        TimeUnit.SECONDS));
        Long endTime = System.currentTimeMillis();
        log.info("rightInit1 costTime: {} ms.", endTime - startTime);
        log.info("rightInit1 Cache init finished.");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS: {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * 让缓存不主动过期，后台有一个线程每30s一次定时将所有数据更新到缓存中。
     * @throws InterruptedException
     */
    @PostConstruct
    public void rightInit2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            IntStream.rangeClosed(1, 1000).forEach(i -> {
                String data = getCityFromDb(i);
                // 模拟更新缓存需要一定时间
                Utils.sleep(20, TimeUnit.MILLISECONDS);
                if (!StringUtils.isEmpty(data)) {
                    // 缓存永不过期，被动更新
                    stringRedisTemplate.opsForValue().set("city" + i, data);
                }
            });
            latch.countDown();
        }, 0, 30, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS: {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);
        latch.await();
        log.info("rightInit2 Cache init finished.");
    }

    @GetMapping("city")
    public String city() {
        int id = ThreadLocalRandom.current().nextInt(1000) + 1;
        String key = "city" + id;
        String data = stringRedisTemplate.opsForValue().get(key);
        if (data == null) {
            data = getCityFromDb(id);
            if (!StringUtils.isEmpty(data)) {
                stringRedisTemplate.opsForValue().set(key, data, 30, TimeUnit.SECONDS);
            }
        }
        return data;
    }

    private String getCityFromDb(int i) {
        atomicInteger.incrementAndGet();
        return "citydata" + System.currentTimeMillis();
    }
}
