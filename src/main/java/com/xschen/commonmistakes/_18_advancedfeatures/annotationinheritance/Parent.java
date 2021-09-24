package com.xschen.commonmistakes._18_advancedfeatures.annotationinheritance;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xschen
 * @date 2021/9/22 10:38
 */

@Slf4j
@MyAnnotation(value = "Class")
public class Parent {

    @MyAnnotation(value = "Method")
    public void foo(){
    }
}
