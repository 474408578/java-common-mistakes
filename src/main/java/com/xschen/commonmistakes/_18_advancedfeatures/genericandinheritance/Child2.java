package com.xschen.commonmistakes._18_advancedfeatures.genericandinheritance;

/**
 * @author xschen
 * @date 2021/9/22 9:33
 */
public class Child2 extends Parent<String> {

    @Override
    public void setValue(String value) {
        System.out.println("Child2.setValue called.");
        super.setValue(value);
    }
}
