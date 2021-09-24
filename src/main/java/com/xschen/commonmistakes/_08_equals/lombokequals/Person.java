package com.xschen.commonmistakes._08_equals.lombokequals;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xschen
 * @date 2021/9/23 15:51
 */

@Data
public class Person {
    @EqualsAndHashCode.Exclude // 从 equals 和 hashcode 的实现中排除 name 字段
    private String name;
    private String identity;

    public Person(String name, String identity) {
        this.name = name;
        this.identity = identity;
    }
}
