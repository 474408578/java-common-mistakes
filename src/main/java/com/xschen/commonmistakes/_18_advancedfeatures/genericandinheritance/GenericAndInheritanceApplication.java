package com.xschen.commonmistakes._18_advancedfeatures.genericandinheritance;

import java.util.Arrays;

/**
 * 1. getMethods 和 getDeclaredMethods 是有区别的，前者可以查询到父类方法，后者只能查询到当前类。
 * 2. 反射进行方法调用要注意过滤桥接方法。
 * extra: Java标准反射不保证方法列表返回的顺序一致.
 * @author xschen
 * @date 2021/9/22 9:27
 */

public class GenericAndInheritanceApplication {

    public static void main(String[] args) {
        System.out.println("------wrong1--------");
        wrong1();
        System.out.println("------wrong2--------");
        wrong2();
        System.out.println("------wrong3--------");
        wrong3();
        System.out.println("------right--------");
        right();
    }

    /**
     * getMethods() 会获取当前类和父类的所有public方法
     * getDeclaredMethods() 只能获取当前类的所有public,protected,package,private方法
     */
    public static void wrong1() {
        Child1 child1 = new Child1();
        Arrays.stream(child1.getClass().getMethods())
                .filter(method -> method.getName().equals("setValue"))
                .forEach(method -> {
                    try {
                        method.invoke(child1, "test");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(child1.toString());
    }

    /**
     * 输出结果虽然正确，但是重写父类的 setValue()是失败的，其他人使用 Child1 时还是会发现有两个 setValue 方法，非常容易让人困惑。
     */
    public static void wrong2() {
        Child1 child1 = new Child1();
        Arrays.stream(child1.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals("setValue"))
                .forEach(method -> {
                    try {
                        method.invoke(child1, "test");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(child1.toString());
    }

    /**
     * 虽然重写成功，但是日志记录还是重复
     */
    public static void wrong3() {
        Child2 child2 = new Child2();
        // 调试发现有两个方法, 其中有一个是桥接方法
        // public void com.xschen.commonmistakes._18_advancedfeatures.genericandinheritance.Child2.setValue(java.lang.String)
        // public void com.xschen.commonmistakes._18_advancedfeatures.genericandinheritance.Child2.setValue(java.lang.Object)
        Arrays.stream(child2.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals("setValue"))
                .forEach(method -> {
                    try {
                        method.invoke(child2, "test");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(child2.toString());
    }

    public static void right() {
        Child2 child2 = new Child2();
        Arrays.stream(child2.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals("setValue"))
                .filter(method -> !method.isBridge())
                .forEach(method -> {
                    try {
                        method.invoke(child2, "test");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(child2.toString());
    }
}
