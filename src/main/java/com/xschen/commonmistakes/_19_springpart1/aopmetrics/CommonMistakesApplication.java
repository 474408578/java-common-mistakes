package com.xschen.commonmistakes._19_springpart1.aopmetrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xschen
 * Spring对不同切面增强的执行顺序是由Bean优先级决定的，具体规则是：
 *      入操作（Around(连接点执行前)，Before）,切面优先级越高。越先执行。
 *          一个切面的入操作执行完成，才会轮到下一个切面，所有切面如操作执行完成。才开始执行连接点（方法）。
 *
 *      出操作(Around(连接点执行后)，After、AfterReturning, AfterThrowing)，切面优先级越低，越先执行，
 *          一个切面的出操作执行完成，才轮到下一个切面，知道返回到调用点。
 *
 *      同一切面的执行顺序：
 *          1. 对于入操作，Around在Before之前执行。
 *          2. 对于出操作，Around在After之后执行。
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}