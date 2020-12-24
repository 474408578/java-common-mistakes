package com.xschen.commonmistakes._03_threadpool.threadpoolmixuse;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xschen.commonmistakes.common.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

/**
 * @author xschen
 */

@RestController
@RequestMapping("threadpoolmixuse")
@Slf4j
public class ThreadPoolMixuseController {

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            2, 2,
            1, TimeUnit.HOURS,
            new ArrayBlockingQueue<>(100),
            new ThreadFactoryBuilder().setNameFormat("batchfileprocess-threadpool-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());


    // 默认的任务拒绝策略AbortPolicy
    private static ThreadPoolExecutor asyncCalcThreadPool = new ThreadPoolExecutor(
            200, 200,
            1, TimeUnit.HOURS,
            new ArrayBlockingQueue<>(1000),
            new ThreadFactoryBuilder().setNameFormat("asynccalc-threadpool-%d").build());

    private Callable<Integer> calcTask() {
        return () -> {
            TimeUnit.MILLISECONDS.sleep(10);
            return 1;
        };
    }

    @GetMapping("wrong")
    public int wrong() throws ExecutionException, InterruptedException {
        return threadPool.submit(calcTask()).get();
    }

    @GetMapping("right")
    public int right() throws ExecutionException, InterruptedException {
        return asyncCalcThreadPool.submit(calcTask()).get();
    }

    @PostConstruct
    public void init() {
        Utils.printStats(threadPool);

        new Thread(() -> {
            String payload = IntStream.rangeClosed(1, 1_000_000)
                    .mapToObj(__ -> "a")
                    .collect(Collectors.joining(""));
            while (true) {
                threadPool.execute(() -> {
                    try {
                        Files.write(Paths.get("demo.txt"),
                                Collections.singletonList(LocalTime.now().toString() + ":" + payload),
                                UTF_8,
                                CREATE,
                                TRUNCATE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    log.info("batch file processing done");
                });
            }
        }).start();
    }


}
