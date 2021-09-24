package com.xschen.commonmistakes._18_advancedfeatures.reflectionissue;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射调用方法，是以反射获取方法时传入的方法名称和参数类型来确定调用方法的。
 * @author xschen
 * @date 2021/9/17 19:06
 */

@Slf4j
public class ReflectionIssueApplication {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // System.out.println(Integer.TYPE); // int
        // System.out.println(Integer.class); // Integer

        ReflectionIssueApplication application = new ReflectionIssueApplication();
        // 不通过反射，通过传参走重载方法正确
        application.age(23);
        application.age(Integer.valueOf("23"));

        // 通过重载，走传参重载
        Method intAgeMethod = ReflectionIssueApplication.class.getDeclaredMethod("age", Integer.TYPE); // int age = 23
        Method integerAgeMethod = ReflectionIssueApplication.class.getDeclaredMethod("age", Integer.class); // integer age = 23
        intAgeMethod.invoke(application, 36); // int age = 36
        integerAgeMethod.invoke(application, 36);// integer age = 36
        integerAgeMethod.invoke(application, Integer.valueOf("36"));// integer age = 36

    }

    private void age(int age) {
        log.info("int age = {}", age);
    }

    private void age(Integer age) {
        log.info("integer age = {}", age);
    }
}
