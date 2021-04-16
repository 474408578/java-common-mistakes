package com.xschen.commonmistakes._01_concurrenttool.threadlocal.batter.controller;

import com.xschen.commonmistakes._01_concurrenttool.threadlocal.batter.utils.LoginUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xschen
 */

@RestController
@RequestMapping("/batter/threadlocal")
public class ThreadLocalBetterController {

    @GetMapping("/right")
    public Map right(@RequestParam("userId") Integer userId) {
        String before = Thread.currentThread().getName() + ":" + LoginUtil.get();
        LoginUtil.set(userId);
        String after = Thread.currentThread().getName() + ":" + LoginUtil.get();
        Map result = new HashMap();
        result.put("before", before);
        result.put("after", after);
        return result;
    }
}
