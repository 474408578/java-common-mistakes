package com.xschen.commonmistakes._15_serialization.jsonignoreproperties;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author xschen
 * @date 2021/9/7 17:12
 * @see DeserializationFeature#FAIL_ON_UNKNOWN_PROPERTIES 出现未知字段时是否抛出异常
 * @see MappingJackson2HttpMessageConverter#MappingJackson2HttpMessageConverter() ->
 * @see Jackson2ObjectMapperBuilder#json() ->
 * @see Jackson2ObjectMapperBuilder#build() ->
 * @see Jackson2ObjectMapperBuilder#configure(ObjectMapper) ->
 * @see Jackson2ObjectMapperBuilder#customizeDefaultFeatures(ObjectMapper)
 */

@Configuration
public class ObjectMapperConfig {

    /**
     * 重新定义 objectMapper, 开启 WRITE_ENUMS_USING_INDEX 功能特性,
     * 这样会将 FAIL_ON_UNKNOWN_PROPERTIES设置为true
     * @return
     */
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
//        // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        return objectMapper;
//    }

    /**
     * 直接定义 Jackson2ObjectMapperBuilderCustomizer 来启用 WRITE_ENUMS_USING_INDEX 特性。
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
    }

}
