package com.xschen.commonmistakes._18_advancedfeatures.genericandinheritance;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xschen
 * @date 2021/9/22 9:30
 */
public class Parent<T> {

    AtomicInteger updateCount = new AtomicInteger();
    private T value;

    public void setValue(T value) {
        System.out.println("Parent.setValue called.");
        this.value = value;
        updateCount.incrementAndGet();
    }

    @Override
    public String toString() {
        return String.format("value: %s, updateCount: %d", value, updateCount.get());
    }
}
