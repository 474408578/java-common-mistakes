package com.xschen.commonmistakes._19_springpart1.beansingletonandorder.service;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author xschen
 */

@Slf4j
public abstract class SayService {
    /**
     * sayService 是有状态的，如果SayService是单例的话，必然会导致oom
     */
    List<String> data = new ArrayList<>();

    public void say() {
        data.add(IntStream.rangeClosed(1, 10000)
                .mapToObj(__ -> "a")
                .collect(Collectors.joining("")) + UUID.randomUUID().toString());
        log.info("I'm {} size :{}", this, data.size());
    }
}
