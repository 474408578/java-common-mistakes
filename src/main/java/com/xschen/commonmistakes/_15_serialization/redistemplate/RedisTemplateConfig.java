package com.xschen.commonmistakes._15_serialization.redistemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisTemplate 配置类
 * @author xschen
 * @see RedisTemplate#afterPropertiesSet()
 * @see StringRedisTemplate#StringRedisTemplate()
 * @see StringRedisSerializer
 */

@Configuration
public class RedisTemplateConfig {

    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
        // 在序列化数据时，写入对象类型
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // key 的序列化使用 RedisSerializer.string() 实现
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // value 的序列化使用 Jackson2JsonRedisSerializer
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 使用RedisSerializer.json() 快捷方法来获取序列化器
     */
//    @Bean
//    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        // key 的序列化使用 RedisSerializer.string() 实现
//        redisTemplate.setKeySerializer(RedisSerializer.string());
//        // value 的序列化使用 Jackson2JsonRedisSerializer
//        redisTemplate.setValueSerializer(RedisSerializer.json());
//        redisTemplate.setHashKeySerializer(RedisSerializer.string());
//        redisTemplate.setHashValueSerializer(RedisSerializer.json());
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
}
