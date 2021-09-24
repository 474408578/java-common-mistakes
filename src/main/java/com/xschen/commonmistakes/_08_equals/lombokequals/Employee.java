package com.xschen.commonmistakes._08_equals.lombokequals;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * @author xschen
 * @date 2021/9/23 15:58
 */

@EqualsAndHashCode(callSuper = true)
public class Employee extends Person {

    private String company;

    public Employee(String name, String identity, String company) {
        super(name, identity);
        this.company = company;
    }
}
