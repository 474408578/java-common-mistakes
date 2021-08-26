package com.xschen.commonmistakes._19_springpart1.beansingletonandorder.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xschen
 */

@Slf4j
@Service
public class SayBye extends SayService{
    @Override
    public void say() {
        super.say();
        log.info("bye");
    }
}
