package com.xschen.commonmistakes.java8;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author xschen
 */

public class ParallelStreamTest {

    @Test
    public void parallel() {
        IntStream.rangeClosed(1, 100).parallel().forEach(i -> {
            System.out.println(LocalDateTime.now() + ": " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        });
    }

    // 1. 使用线程，将任务按照线程数均匀分割给线程去执行
    private int thread(int taskCount, int threadCount) throws InterruptedException {
        // 总操作次数计数器
        AtomicInteger atomicInteger = new AtomicInteger();
        // 使用 countDownLatch 来等待所有线程执行完成
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        IntStream.rangeClosed(1, threadCount)
                .mapToObj(i -> new Thread(() -> {
                    IntStream.rangeClosed(1, taskCount / threadCount).forEach(j -> increment(atomicInteger));
                    countDownLatch.countDown();
                })).forEach(Thread::start);

        countDownLatch.await();
        return atomicInteger.get();
    }

    // 2. 使用线程池
    private int threadpool(int taskCount, int threadCount) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        IntStream.rangeClosed(1, taskCount).forEach(i -> executorService.submit(() -> increment(atomicInteger)));
        // 提交关闭线程池申请，等待之前所有任务执行完成
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        return atomicInteger.get();
    }





    /**
     * 方法的执行需要10毫秒
     * @param atomicInteger
     */
    private void increment(AtomicInteger atomicInteger) {
        atomicInteger.incrementAndGet();
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {

        }
    }
}
