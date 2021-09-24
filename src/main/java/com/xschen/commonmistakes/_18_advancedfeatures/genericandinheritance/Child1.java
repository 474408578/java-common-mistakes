package com.xschen.commonmistakes._18_advancedfeatures.genericandinheritance;

/**
 * @author xschen
 * @date 2021/9/22 9:32
 */
public class Child1  extends Parent {

    public void setValue(String value) {
        System.out.println("Child1.setValue called.");
        super.setValue(value);
    }
}
