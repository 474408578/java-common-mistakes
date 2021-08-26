package com.xschen.commonmistakes._19_springpart1.beansingletonandorder;

import com.xschen.commonmistakes._19_springpart1.beansingletonandorder.service.SayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xschen
 */

@Slf4j
@RestController
@RequestMapping("beansingletonandorder")
public class BeanSingletonAndOrderController {

    /**
     * Bean 默认是单例的，所以单例的Controller注入的service也是一次性创建的。
     * 即使Service本身标识prototype的范围也没有用。解决方法：
     *  1. 让sayService以代理方式注入
     *  2. 每次都从ApplicationContext中获取Bean
     */
    @Autowired
    private List<SayService> sayServiceList;
    @GetMapping("test")
    public void test() {
        log.info("=======================");
        sayServiceList.forEach(SayService::say);
    }


    @Autowired
    private ApplicationContext applicationContext;
    @GetMapping("test2")
    public void test2() {
        applicationContext.getBeansOfType(SayService.class).values().forEach(SayService::say);
    }
}
