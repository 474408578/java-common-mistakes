package com.xschen.commonmistakes._19_springpart1.beansingletonandorder.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * 在为类标记上 @Service 注解把类型交由容器管理前，首先评估一下类是否有状态，然后为 Bean 设置合适的 Scope,
 * 让Service以代理方式注入，这样虽然Controller本身是单例的，但是每次都能从代理获取Service，
 * 这样一来，prototype的范围的配置才能真正生效
 * @author xschen
 */

@Slf4j
@Service
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SayHello extends SayService{
    @Override
    public void say() {
        super.say();
        log.info("hello");
    }
}
