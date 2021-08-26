package com.xschen.commonmistakes._19_springpart1.aopmetrics.ordertest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xschen
 */

@RestController
@Slf4j
@RequestMapping("test")
public class TestController {
    @GetMapping("test")
    public String test() {
        return "success";
    }
}
