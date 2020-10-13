package com.xschen.commonmistakes._01_concurrenttool.threadlocal;


import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xschen
 */

@RestController
@RequestMapping("/threadlocal")
public class ThreadLocalMisuseController {

    private static final ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);

    /**
     * 将tomcat线程池最大线程数设置为1，server.tomcat.max-threads=1
     * @param userId
     * @return
     */
    @RequestMapping(value = "/wrong", method = RequestMethod.GET)
    public Map wrong(@RequestParam("userId") Integer userId) {
        String before = Thread.currentThread().getName() + ":" + currentUser.get();
        currentUser.set(userId);
        String after = Thread.currentThread().getName() + ":" + currentUser.get();
        Map result = new HashMap();
        result.put("before", before);
        result.put("after", after);
        return result;
    }

    @GetMapping("/right")
    public Map right(@RequestParam("userId") Integer userId) {
        try {
            String before = Thread.currentThread().getName() + ":" + currentUser.get();
            currentUser.set(userId);
            String after = Thread.currentThread().getName() + ":" + currentUser.get();
            Map result = new HashMap();
            result.put("before", before);
            result.put("after", after);
            return result;
        } finally {
            // 在finally代码块中删除ThreadLocal中的数据，确保数据不串
            currentUser.remove();
        }
    }

}
