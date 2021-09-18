package com.xschen.commonmistakes._15_serialization.jsonignoreproperties;

import com.xschen.commonmistakes.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 注意Jackson JSON 反序列化对额外字段的处理
 * @author xschen
 * @date 2021/9/7 8:55
 */

@SpringBootApplication
public class CommonMistakesApplication {
    public static void main(String[] args) {
        // 通过更改配置文件来启用新特性 WRITE_ENUMS_USING_INDEX
//        Utils.loadPropertySource(CommonMistakesApplication.class, "_15_serizlization/jackson.properties");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
