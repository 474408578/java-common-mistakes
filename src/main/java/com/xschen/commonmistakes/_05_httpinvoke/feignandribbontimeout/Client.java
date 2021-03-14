package com.xschen.commonmistakes._05_httpinvoke.feignandribbontimeout;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author xschen
 */

@FeignClient(name = "clientsdk")
public interface Client {
    @PostMapping("/feignandribbon/server")
    void server();
}
