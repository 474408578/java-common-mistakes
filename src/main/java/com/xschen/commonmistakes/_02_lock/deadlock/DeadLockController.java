package com.xschen.commonmistakes._02_lock.deadlock;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author xschen
 */

@RestController
@RequestMapping("/deadlock")
@Slf4j
public class DeadLockController {
    // 现有的商品
    private ConcurrentHashMap<String, Item> items = new ConcurrentHashMap<>();

    public DeadLockController() {
        IntStream.rangeClosed(0, 10).forEach(i -> items.put("item"+i, new Item("item" + i)));
    }

    @GetMapping("wrong")
    public long wrong() {
        long begin = System.currentTimeMillis();
        // 并发执行100次下单操作，统计下载次数
        long success = IntStream.rangeClosed(1, 100)
                .parallel() // 并行流
                .mapToObj(i -> {
                    List<Item> cart = createCart();
                    return createOrder(cart);
                })
                .filter(result -> result)
                .count();

        log.info("success:{}, Total Remaining: {}, tooks:{}ms, items: {}",
                success,
                items.entrySet().stream().map(item -> item.getValue().remaining).reduce(0, Integer::sum),
                System.currentTimeMillis() - begin,
                items);

        return success;
    }

    @GetMapping("right")
    public long right() {
        long begin = System.currentTimeMillis();
        // 并发执行100次下单操作，统计下载次数
        long success = IntStream.rangeClosed(1, 100)
                .parallel()
                .mapToObj(i -> {
                    List<Item> cart = createCart().stream()
                            .sorted(Comparator.comparing(Item::getName))
                            .collect(Collectors.toList());
                    return createOrder(cart);
                })
                .filter(result -> result)
                .count();

        log.info("success:{}, Total Remaining: {}, tooks:{}ms, items: {}",
                success,
                items.entrySet().stream().map(item -> item.getValue().remaining).reduce(0, Integer::sum),
                System.currentTimeMillis() - begin,
                items);

        return success;
    }




    /**
     * 模拟在购物车进行商品选购，每次从商品清单（items 字段）中随机选购三个商品
     * @return
     */
    private List<Item> createCart() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> "item" + ThreadLocalRandom.current().nextInt(items.size()))
                .map(name -> items.get(name))
                .collect(Collectors.toList());
    }

    private boolean createOrder(List<Item> order) {
        // 存放订单中所有的锁
        List<ReentrantLock> locks = new ArrayList<>();
        // 依次获取商品的锁，10s未获得锁则释放之前的锁，并且下单失败
        for (Item item : order) {
            try {
                if (item.getLock().tryLock(10, TimeUnit.SECONDS)) {
                    locks.add(item.getLock());
                } else {
                    locks.forEach(ReentrantLock::unlock);
                    return false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 锁拿到后，依次执行扣减库存逻辑
        try {
            order.forEach(item -> item.remaining--);
        } finally {
            locks.forEach(ReentrantLock::lock);
        }

        return true;
    }

    @Data
    @RequiredArgsConstructor //将每一个final字段或者non-null字段生成一个构造方法
    static class Item {
        final String name; // 商品名
        int remaining = 1000; // 库存
        @ToString.Exclude
        ReentrantLock lock = new ReentrantLock(); // 库存锁
    }

}
