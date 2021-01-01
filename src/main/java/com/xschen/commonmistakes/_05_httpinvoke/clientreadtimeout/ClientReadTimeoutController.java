package com.xschen.commonmistakes._05_httpinvoke.clientreadtimeout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author xschen
 */


@Slf4j
@RestController
@RequestMapping("clientreadtimeout")
public class ClientReadTimeoutController {

    @GetMapping("client")
    public String client() throws IOException {
        log.info("client1 called");
        return getResponse("/server?timeout=5000", 1000, 2000);
    }

    private String getResponse(String url, int connectTimeout, int readTimeout) throws IOException {
        return Request.Get("http://127.0.0.1:8080/clientreadtimeout/" + url)
                .connectTimeout(connectTimeout)
                .socketTimeout(readTimeout)
                .execute()
                .returnContent()
                .asString();
    }

    @GetMapping("server")
    public void server(@RequestParam("timeout") int timeout) throws InterruptedException {
        log.info("server called");
        TimeUnit.MILLISECONDS.sleep(timeout);
        log.info("Done");
    }
}
