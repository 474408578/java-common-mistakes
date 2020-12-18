package com.xschen.commonmistakes._02_lock.lockscope;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xschen
 */

@Slf4j
public class Interesting {
    volatile int a = 1;
    volatile int b = 1;

    public synchronized void add() {
        log.info("add start");
        for (int i = 0; i < 10000; i++) {
            a++;
            b++;
        }
        log.info("add done");
    }

    public void compare() {
        log.info("compare start");
        for (int i = 0; i < 10000; i++) {
            if (a < b) {
                log.info("a: {}, b: {}, {}", a, b, a > b);
            }
        }

        log.info("compare done");
    }

    public synchronized void rightCompare() {
        log.info("compare start");
        for (int i = 0; i < 10000; i++) {
            if (a < b) {
                log.info("a: {}, b: {}, {}", a, b, a > b);
            }
        }

        log.info("compare done");
    }


    public static void main(String[] args) {
        Interesting interesting = new Interesting();
        new Thread(() -> interesting.add()).start();
        new Thread(() -> interesting.compare()).start();
//        new Thread(() -> interesting.rightCompare()).start();

    }
}
