package com.xschen.commonmistakes._23_cachedesign.cacheconcurrent;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xschen
 * @date 2021/9/17 16:33
 */

@Slf4j
@RestController
@RequestMapping("cacheconcurrent")
public class CacheConcurrentController {

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    // 单机只允许2个线程回源
    private Semaphore semaphore = new Semaphore(2);

    @PostConstruct
    public void init() {
        stringRedisTemplate.opsForValue().set("hotspot", getExpensiveData(), 5, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS : {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);
    }

    @GetMapping("wrong")
    public String wrong() {
        String data = stringRedisTemplate.opsForValue().get("hotspot");
        if (StringUtils.isEmpty(data)) {
            data = getExpensiveData();
            stringRedisTemplate.opsForValue().set("hotspot", data, 5, TimeUnit.SECONDS);
        }
        return data;
    }

    /**
     * 方案1：利用双重检查分布式锁进行全局的并发控制
     * @return
     */
    @GetMapping("right1")
    public String right1() {
        String data = stringRedisTemplate.opsForValue().get("hotspot");
        if (StringUtils.isEmpty(data)) {
            RLock locker = redissonClient.getLock("locker");
            // 获取分布式锁
            if (locker.tryLock()) {
                try {
                    data = stringRedisTemplate.opsForValue().get("hotspot");
                    if (StringUtils.isEmpty(data)) {
                        data = getExpensiveData();
                        stringRedisTemplate.opsForValue().set("hotspot", data, 5, TimeUnit.SECONDS);
                    }
                } finally {
                    locker.unlock();
                }
            }
        }
        return data;
    }

    /**
     * 方案2：使用semaphore限制并发数，每个节点可以有2个线程回源
     * @return
     * @throws InterruptedException
     */
    @GetMapping("right2")
    public String right2() throws InterruptedException {
        String data = stringRedisTemplate.opsForValue().get("hotspot");
        if (StringUtils.isEmpty(data)) {
            try {
                semaphore.acquire();
                data = stringRedisTemplate.opsForValue().get("hotspot");
                if (StringUtils.isEmpty(data)) {
                    data = getExpensiveData();
                    stringRedisTemplate.opsForValue().set("hotspot", data, 5, TimeUnit.SECONDS);
                }
            } finally {
                semaphore.release();
            }
        }
        return data;
    }

    /**
     * 方案3：进程内锁限制，每个节点只有一个线程可以回源
     */
    @GetMapping("right3")
    public String right3() {
        String data = stringRedisTemplate.opsForValue().get("hotspot");
        if (StringUtils.isEmpty(data)) {
            synchronized (this) {
                data = stringRedisTemplate.opsForValue().get("hotspot");
                if (StringUtils.isEmpty(data)) {
                    data = getExpensiveData();
                    stringRedisTemplate.opsForValue().set("hotspot", data, 5, TimeUnit.SECONDS);
                }
            }
        }
        return data;
    }

    /**
     * 昂贵的回源操作
     * @return
     */
    private String getExpensiveData() {
        atomicInteger.getAndIncrement();
        return "important data";
    }
}
