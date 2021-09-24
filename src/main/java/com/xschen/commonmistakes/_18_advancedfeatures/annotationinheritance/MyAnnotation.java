package com.xschen.commonmistakes._18_advancedfeatures.annotationinheritance;

import java.lang.annotation.*;

/**
 * @author xschen
 * @date 2021/9/22 10:21
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited // 只能实现类上的注解继承
public @interface MyAnnotation {
    String value();
}

