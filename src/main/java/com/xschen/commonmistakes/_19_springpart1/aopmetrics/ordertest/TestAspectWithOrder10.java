package com.xschen.commonmistakes._19_springpart1.aopmetrics.ordertest;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author xschen
 */

//@Aspect
//@Component
//@Order(10)
@Slf4j
public class TestAspectWithOrder10 {
    @Before("execution(* com.xschen.commonmistakes._19_springpart1.aopmetrics.ordertest.TestController.*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("TestAspectWithOrder10 @Before");
    }

    @After("execution(* com.xschen.commonmistakes._19_springpart1.aopmetrics.ordertest.TestController.*(..))")
    public void after(JoinPoint joinPoint) {
        log.info("TestAspectWithOrder10 @After");
    }

    @Around("execution(* com.xschen.commonmistakes._19_springpart1.aopmetrics.ordertest.TestController.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("TestAspectWithOrder10 @Around before");
        Object o = pjp.proceed();
        log.info("TestAspectWithOrder10 @Around after");
        return o;
    }
}
