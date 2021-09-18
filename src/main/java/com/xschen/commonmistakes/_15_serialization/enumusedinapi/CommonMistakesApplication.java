package com.xschen.commonmistakes._15_serialization.enumusedinapi;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.xschen.commonmistakes.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * 枚举作为 API 接口参数或返回值的两大坑
 * @author xschen
 * @date 2021/9/8 13:58
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        Utils.loadPropertySource(CommonMistakesApplication.class, "_15_serizlization/jackson-enum.properties");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    /**
     * 配置 RestTemplate 反序列化特性，使用 SpringBoot的 MappingJackson2HttpMessageConverter
     * @param converter
     * @return
     */
    @Bean
    public RestTemplate restTemplate(MappingJackson2HttpMessageConverter converter) {
        return new RestTemplateBuilder()
                .additionalMessageConverters(converter)
                .build();
    }

    @Bean
    public Module enumModule() {
        SimpleModule module = new SimpleModule();
        // 将自定义的反序列化器注册到Jackson中
        module.addDeserializer(Enum.class, new EnumDeserializer());
        return module;
    }
}
