package com.xschen.commonmistakes._05_httpinvoke.routelimit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xschen
 */

@RequestMapping("routelimit")
@RestController
public class RouteLimitController {

    static CloseableHttpClient httpClient1;
    static CloseableHttpClient httpClient2;


}
