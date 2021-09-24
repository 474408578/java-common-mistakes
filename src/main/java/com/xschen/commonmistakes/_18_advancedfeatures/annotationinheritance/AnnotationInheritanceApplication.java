package com.xschen.commonmistakes._18_advancedfeatures.annotationinheritance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * @author xschen
 * @date 2021/9/22 10:32
 * @see AnnotatedElementUtils#findMergedAnnotation(AnnotatedElement, Class)  处理注解的继承问题
 */

@Slf4j
public class AnnotationInheritanceApplication {

    public static void main(String[] args) throws NoSuchMethodException {
        wrong();
        right();
    }

    public static void wrong() throws NoSuchMethodException {
        Parent parent = new Parent();
        log.info("ParentClass:{}", getAnnotationValue(parent.getClass().getAnnotation(MyAnnotation.class))); // Class
        log.info("ParentMethod:{}", getAnnotationValue(parent.getClass().getDeclaredMethod("foo").getAnnotation(MyAnnotation.class))); // Method

        Child child = new Child();
        log.info("ChildClass:{}", getAnnotationValue(child.getClass().getAnnotation(MyAnnotation.class)));
        log.info("ChildMethod:{}", getAnnotationValue(child.getClass().getDeclaredMethod("foo").getAnnotation(MyAnnotation.class)));
    }

    public static void right() throws NoSuchMethodException {
        Parent parent = new Parent();
        log.info("ParentClass:{}", getAnnotationValue(parent.getClass().getAnnotation(MyAnnotation.class))); // Class
        log.info("ParentMethod:{}", getAnnotationValue(parent.getClass().getDeclaredMethod("foo").getAnnotation(MyAnnotation.class))); // Method
        Child child = new Child();
        log.info("ChildClass:{}", getAnnotationValue(AnnotatedElementUtils.findMergedAnnotation(child.getClass(), MyAnnotation.class)));
        log.info("ChildMethod:{}", getAnnotationValue(AnnotatedElementUtils.findMergedAnnotation(child.getClass().getDeclaredMethod("foo"), MyAnnotation.class)));
    }



    private static String getAnnotationValue(MyAnnotation annotation) {
        if (annotation == null)
            return "";
        return annotation.value();
    }
}
