package com.xschen.commonmistakes._19_springpart1.aopmetrics;

import java.lang.annotation.*;

/**
 * 自定义注解
 * @author xschen
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Metrics {

    /**
     * 在方法成功执行后打点，记录方法的执行时间发送到指标系统，默认开启
     * @return
     */
    boolean recordSuccessMetrics() default true;

    /**
     * 在方法失败后打点，记录方法的执行时间发送到指标系统，默认开启
     * @return
     */
    boolean recordFailMetrics() default true;

    /**
     * 通过日志记录请求参数，默认开启
     * @return
     */
    boolean logParameters() default true;

    /**
     * 通过日志记录方法返回值，默认开启
     * @return
     */
    boolean logReturn() default true;

    /**
     * 出现异常后通过日志记录异常信息，默认开启
     * @return
     */
    boolean logException() default true;

    /**
     * 出现异常后忽略异常返回默认值，默认关闭
     * @return
     */
    boolean ignoreException() default false;

}
