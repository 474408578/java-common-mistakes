package com.xschen.commonmistakes._05_httpinvoke.feignandribbontimeout;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author xschen
 */

@Configuration
@EnableFeignClients("com.xschen.commonmistakes._05_httpinvoke.feignandribbontimeout")
public class AutoConfig {

}
