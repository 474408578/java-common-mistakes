package com.xschen.commonmistakes._15_serialization.redistemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * 序列化方式
 * @author xschen

 */

@RestController
@RequestMapping("redistemplate")
@Slf4j
public class RedisTemplateController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, User> userRedisTemplate;
    @Autowired
    private RedisTemplate<String, Long> countRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() throws JsonProcessingException {
        redisTemplate.opsForValue().set("redisTemplate", new User("song", 23));
        stringRedisTemplate.opsForValue().set("stringRedisTemplate", objectMapper.writeValueAsString(new User("song", 23)));
    }

    /**
     * 分别使用 redisTemplate 和 stringRedisTemplate 存取的数据是无法完全通用的。
     */
    @GetMapping("wrong")
    public void wrong() {
        log.info("redisTemplate get {}", redisTemplate.opsForValue().get("stringRedisTemplate"));
        log.info("stringRedisTemplate get {}", stringRedisTemplate.opsForValue().get("redisTemplate"));
//        redisTemplate get null
//        stringRedisTemplate get null
    }

    /**
     * 分别使用redisTemplate 和 stringRedisTemplate 来存取自己保存的数据
     */
    @GetMapping("right")
    public void right() throws JsonProcessingException {
        User userFromRedisTemplate = (User) redisTemplate.opsForValue().get("redisTemplate");
        log.info("redisTemplate get {}", userFromRedisTemplate);
        User userFromStringRedisTemplate = objectMapper.readValue(stringRedisTemplate.opsForValue().get("stringRedisTemplate"), User.class);
        log.info("stringRedisTemplate get {}", userFromStringRedisTemplate);
//        redisTemplate get User(name=song, age=23)
//        stringRedisTemplate get User(name=song, age=23)
    }

    @GetMapping("right2")
    public void right2() {
        User user = new User("song", 24);
        userRedisTemplate.opsForValue().set(user.getName(), user);
        User userFromRedis = userRedisTemplate.opsForValue().get(user.getName());
        log.info("userRedisTemplate get {} {}", userFromRedis, userFromRedis.getClass());
        log.info("stringRedisTemplate get {}", stringRedisTemplate.opsForValue().get(user.getName()));
//        userRedisTemplate get User(name=song, age=24) class com.xschen.commonmistakes._15_serialization.redistemplate.User
//        stringRedisTemplate get [com.xschen.commonmistakes._15_serialization.redistemplate.User, {name=song, age=24}]
    }

}
