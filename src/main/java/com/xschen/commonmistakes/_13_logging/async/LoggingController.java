package com.xschen.commonmistakes._13_logging.async;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author xschen
 */

@Slf4j
@RestController
@RequestMapping("logging")
public class LoggingController {

    @GetMapping("log")
    public void log() {
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }

    @GetMapping("performance")
    public void performance(@RequestParam(name = "count", defaultValue = "1000") int count) {
        long begin = System.currentTimeMillis();

        // 模拟 每条日志大概 1MB 大小的数据
        String payload = IntStream.rangeClosed(1, 1000000)
                .mapToObj(__ -> "a")
                .collect(Collectors.joining("")) + UUID.randomUUID().toString();

        IntStream.rangeClosed(1, count).forEach(i -> log.info("{} {}", i, payload));

        Marker timeMarker = MarkerFactory.getMarker("time");
        log.info(timeMarker, "took {} ms", System.currentTimeMillis() - begin);
    }
}
