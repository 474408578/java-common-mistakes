package com.xschen.commonmistakes._23_cachedesign.cachepenetration;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author xschen
 * @date 2021/9/17 17:53
 * @see BloomFilter
 */

@Slf4j
@RestController
@RequestMapping("cachepenetration")
public class CachePenetrationController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private AtomicInteger atomicInteger = new AtomicInteger();
    @Autowired
    private BloomFilter<Integer> bloomFilter;

    @PostConstruct
    public void init() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS: {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);

        // 创建布隆过滤器， 元素数量 10000， 期望误判率1%
        bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 10000, 0.01);
        // 填充布隆过滤器
        IntStream.rangeClosed(1, 10000).forEach(bloomFilter::put);
    }

    @GetMapping("wrong")
    public String wrong(@RequestParam("id") int id) {
        String key = "user" + id;
        String data = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(data)) {
            data = getCityFromDb(id);
            stringRedisTemplate.opsForValue().set(key, data, 30, TimeUnit.SECONDS);
        }
        return data;
    }

    /**
     * 代码内做判断，对于不存在的数据，设置一个缓存，下次可以直接在缓存中获取
     * @param id
     * @return
     */
    @GetMapping("right1")
    public String right1(@RequestParam("id") int id) {
        String key = "user" + id;
        String data = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(data)) {
            data = getCityFromDb(id);
            // 校验数据库返回的数据是否有效
            if (!StringUtils.isEmpty(data)) {
                stringRedisTemplate.opsForValue().set(key, data, 30, TimeUnit.SECONDS);
            } else {
                // 如果无效，直接在缓存中设置一个 NODATA, 这样下次查询时，即使是无效用户，还是可以命中缓存
                stringRedisTemplate.opsForValue().set(key, "NODATA", 30, TimeUnit.SECONDS);
            }
        }
        return data;
    }

    /**
     * 使用 布隆过滤器
     * @return
     */
    @GetMapping("right2")
    public String right2(@RequestParam("id") int id) {
        String data = "";
        if (bloomFilter.mightContain(id)) {
            String key = "user" + id;
            data = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isEmpty(data)) {
                data = getCityFromDb(id);
                stringRedisTemplate.opsForValue().set(key, data, 30, TimeUnit.SECONDS);
            }
        }
        return data;
    }


    private String getCityFromDb(int id) {
        atomicInteger.getAndIncrement();
        // 注意，只有ID介于0（不含）和10000（包含）之间的用户才是有效用户，可以查询到用户信息
        if (id > 0 && id <= 10000) {
            return "userData";
        }

        // 否则返回空字符串
        return "";
    }

}
