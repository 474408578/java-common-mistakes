package com.xschen.commonmistakes._01_concurrenttool.threadlocal.batter.aspect;

import com.xschen.commonmistakes._01_concurrenttool.threadlocal.batter.utils.LoginUtil;
import com.xschen.commonmistakes.common.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author xschen
 */

@Aspect
@Component
@Slf4j
public class HttpAspect {

    @Pointcut("execution(public * com.xschen.commonmistakes._01_concurrenttool.threadlocal.batter.controller.*.*(..))")
    public void log() {
        log.info("log");
    }

    @Before("log()")
    public void doBefore() {
        log.info("HttpAspect.doBefore");
    }

    @After("log()")
    public void doAfter() {
        LoginUtil.clear();
        log.info("HttpAspect.doAfter");
    }

    @AfterReturning(pointcut = "log()", returning = "obj")
    public void doAfterReturning(Object obj) {
        log.info("HttpAspect.doAfterReturning");
    }

    @AfterThrowing(pointcut = "log()", throwing = "e")
    public void doAfterThrowing(Exception e) {
        ResultUtil.error(e.getMessage());
    }

    // fixme 待改造
    @Around("log()")
    public Object doAround(ProceedingJoinPoint joinPoint) {

        Object obj = null;
        // 业务
        Object[] args = joinPoint.getArgs();
        try {
            obj = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        LoginUtil.clear();
        return obj;
    }
}
