package com.xschen.commonmistakes._02_lock.lockscope;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

/**
 * @author xschen
 */


@RestController
@RequestMapping("/lockscope")
@Slf4j
public class LockScopeController {


    @GetMapping("wrong")
    public int wrong(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        Data.reset();
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().wrong());
        return Data.getCounter();
    }

    @GetMapping("rightUseOneInstance")
    public int rightUseOneInstance(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        Data.reset();
        Data data = new Data();
        IntStream.rangeClosed(1, count).parallel().forEach(i -> data.wrong());
        return Data.getCounter();
    }

    @GetMapping("right")
    public int right(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        Data.reset();
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().right());
        return Data.getCounter();
    }
}
