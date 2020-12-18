package com.xschen.commonmistakes._01_concurrenttool.copyonwritelistmisuse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author xschen
 */

@RestController
@Slf4j
@RequestMapping("copyonwritelistmisuse")
public class CopyOnWriteListMisuseController {

    private static final int LOOP_COUNT = 100000;


    @GetMapping("/testWrite")
    public Map<String, Integer> testWrite() {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Write:copyOnWriteArrayList");
        IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(__ -> copyOnWriteArrayList.add(ThreadLocalRandom.current().nextInt(LOOP_COUNT)));
        stopWatch.stop();

        stopWatch.start("Write:synchronizedList");
        IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(__ -> synchronizedList.add(ThreadLocalRandom.current().nextInt()));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        Map<String, Integer> result = new HashMap<>();
        result.put("copyOnWriteArrayList", copyOnWriteArrayList.size());
        result.put("synchronizedList", synchronizedList.size());
        return result;
    }



    private void addAll(List<Integer> list) {
        list.addAll(IntStream.rangeClosed(1, LOOP_COUNT).boxed().collect(Collectors.toList()));
    }

    @GetMapping("/testRead")
    public Map<String, Integer> testRead() {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());

        addAll(copyOnWriteArrayList);
        addAll(synchronizedList);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Read:copyOnWriteArrayList");
        IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(__ -> copyOnWriteArrayList.get(ThreadLocalRandom.current().nextInt(LOOP_COUNT)));
        stopWatch.stop();

        stopWatch.start("Read:synchronizedList");
        IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(__ -> synchronizedList.get(ThreadLocalRandom.current().nextInt(LOOP_COUNT)));
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());
        Map<String, Integer> result = new HashMap<>();
        result.put("copyOnWriteArrayList", copyOnWriteArrayList.size());
        result.put("synchronizedList", synchronizedList.size());
        return result;
    }


}
