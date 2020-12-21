package com.xschen.commonmistakes._03_threadpool.threadpooloom;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xschen.commonmistakes.common.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author xschen
 */

@RestController
@RequestMapping("threadpooloom")
@Slf4j
public class ThreadPoolOOMController {
    // 一亿
    private static final int HUNDRED_MILLION = 100000000;

    @GetMapping("oom1")
    public void oom1() throws InterruptedException {
        ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        Utils.printStats(fixedThreadPool);
        for (int i = 0; i < HUNDRED_MILLION; i++) {
            fixedThreadPool.execute(() -> {
                String payload = IntStream.rangeClosed(1, 1000000)
                        .mapToObj(__ -> "a")
                        .collect(Collectors.joining("" )) + UUID.randomUUID().toString();
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(payload);
            });
        }

        fixedThreadPool.shutdown();
        fixedThreadPool.awaitTermination(1, TimeUnit.HOURS);
    }


    /**
     * 是否可以设置jvm参数快速复现，此处机制还需要了解一下
     * @throws InterruptedException
     */
    @GetMapping("oom2")
    public void oom2() throws InterruptedException {
        ThreadPoolExecutor cachedThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Utils.printStats(cachedThreadPool);
        for (int i = 0; i < HUNDRED_MILLION; i++) {
            cachedThreadPool.execute(() -> {
                String payload = UUID.randomUUID().toString();
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(payload);
            });
        }
        cachedThreadPool.shutdown();
        cachedThreadPool.awaitTermination(1, TimeUnit.HOURS);
    }
    
    @GetMapping("right")
    public int right() throws InterruptedException {
        // 计数器跟踪完成的任务数
        AtomicInteger atomicInteger = new AtomicInteger();
        // 创建一个具有2个核心线程、5个最大线程，
        // 使用容量为10的ArrayBlockingQueue阻塞队列作为工作队列的线程池，使用默认的AbortPolicy拒绝策略
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 5, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                // 使用guava类库构建线程工厂
                new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());

        Utils.printStats(threadPoolExecutor);

        /**
         * 每次间隔1s向线程池提交任务，每个任务需要10s执行完成，循环20次
         */
        IntStream.rangeClosed(1, 20).forEach(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int id = atomicInteger.incrementAndGet();
            try {
                threadPoolExecutor.submit(() -> {
                    log.info("{} started", id);
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("{} finished", id);
                });
            } catch (Exception e) {
               log.error("error submitting task {}", id, e);
               atomicInteger.decrementAndGet();
            }
        });

        TimeUnit.SECONDS.sleep(60);
        return atomicInteger.intValue();
    }


    @GetMapping("better")
    public int better() {
        // 这里开始激进线程池的实现
        LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<Runnable>(10) {
            // 先返回false, 造成队列满的假象，让线程池优先扩容
            @Override
            public boolean offer(Runnable runnable) {
                return false;
            }
        };

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5,
                5, TimeUnit.SECONDS, queue,
                new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").build(),
                (r, executor) -> {
                    try {
                        // 等出现拒绝后再加入队列
                        // 如果希望队列满了阻塞线程，而不是抛出异常，可以修改如下三行代码为executor.getQueue().put(r);
                        if (!executor.getQueue().offer(r, 0, TimeUnit.SECONDS)) {
                            throw new RejectedExecutionException("ThreadPool Queue full, fail to offer " + r.toString());
                        }
                    } catch (InterruptedException e) {
//                      // ？
                        Thread.currentThread().interrupt();
                    }
                });
        // 激进线程实现结束


        Utils.printStats(threadPool);

        AtomicInteger atomicInteger = new AtomicInteger();
        
    }


}
