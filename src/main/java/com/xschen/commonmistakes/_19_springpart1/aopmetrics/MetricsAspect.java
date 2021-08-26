package com.xschen.commonmistakes._19_springpart1.aopmetrics;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xschen
 */

@Aspect
@Component
@Slf4j
public class MetricsAspect {

    /**
     * 8个原始数据类型数组
     */
    public static final Class[] PRIMITIVE_TYPE_ARRAY = {boolean.class, byte.class, char.class, double.class, int.class, long.class, short.class, float.class};
    /**
     * 创建一个长度为1，类型为clazz的数组，并获取其第一个值，
     * 因为Java一旦创建了一个数组，这个数组内的所有元素都有一个默认值，如int 数组的元素的默认值是0, boolean的是false.
     */
    public static final Map<Class<?>, Object> DEFAULT_VALUES = Arrays.stream(PRIMITIVE_TYPE_ARRAY)
            .collect(Collectors.toMap(clazz -> clazz,
                    clazz -> Array.get(Array.newInstance(clazz, 1), 0)));
    @Autowired
    private ObjectMapper objectMapper;

    public static <T> T getDefaultValue(Class<T> clazz) {
        return (T) DEFAULT_VALUES.get(clazz);
    }

    /**
     * 匹配帶有Metrics注解的类
     */
//    @Pointcut("within(@com.xschen.commonmistakes._19_springpart1.aopmetrics.Metrics *)")
//    public void withMetricsAnnotation() {
//    }

    /**
     * 对标记了Metrics注解的方法进行匹配
     */
    @Pointcut("@annotation(com.xschen.commonmistakes._19_springpart1.aopmetrics.Metrics)")
    public void withMetricsAnnotation() {
    }

    /**
     * 实现了匹配那些类型上标记了@RestController注解的方法
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerBean() {
    }

    @Around("(withMetricsAnnotation() || controllerBean())")
    public Object metrics(ProceedingJoinPoint pjp) throws Throwable {
        // 通过连接点获取方法签名和方法上Metrics注解，并根据方法签名生成日志中要输出的方法定义描述
       MethodSignature signature = (MethodSignature) pjp.getSignature();
       String name = String.format("【%s】", signature.toLongString());
       Metrics metrics = signature.getMethod().getAnnotation(Metrics.class);
       if (metrics == null) {
           @Metrics
           final class c {}
           metrics = c.class.getAnnotation(Metrics.class);
       }
       // 对于Web项目，可以从上下文中获取到额外的一些信息来丰富日志
       RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
       if (requestAttributes != null) {
           HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
           if (request != null) {
               name += String.format("【%s】", request.getRequestURL().toString());
           }
       }

       // 入参的日志输出
       if (metrics.logParameters())
           log.info(String.format("【入参日志】调用 %s 的参数是：%s", name, objectMapper.writeValueAsString(pjp.getArgs())));
       Object returnValue;
       Instant start = Instant.now();
       try {
           // 连接点方法的执行
           returnValue = pjp.proceed();
           if (metrics.recordSuccessMetrics())
               log.info(String.format("【成功打点】调用 %s 成功，耗时：%d ms", name, Duration.between(start, Instant.now()).toMillis()));
       } catch (Exception ex) {
           if (metrics.recordFailMetrics())
               log.info(String.format("【失败打点】调用 %s 失败，耗时：%d ms", name, Duration.between(start, Instant.now()).toMillis()));

           if (metrics.logException())
               log.error(String.format("【异常日志】调用 %s 出现异常!", name), ex);

           if (metrics.ignoreException())
               returnValue = getDefaultValue(signature.getReturnType());

           else
               throw ex;
       }
       // 实现了返回值的日志输出
       if (metrics.logReturn()) {
           log.info(String.format("【出参日志】调用 %s 的返回值是：【%s】", name, returnValue));
       }
       return returnValue;
    }
}
