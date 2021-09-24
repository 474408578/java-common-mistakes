package com.xschen.commonmistakes._08_equals.compareto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

/**
 * @author xschen
 * @date 2021/9/23 14:05
 * @see Comparator 外部比较器
 * @see Comparable 内部比较器
 */

@Slf4j
@Data
@AllArgsConstructor
public class StudentRight implements Comparable<StudentRight> {

    private int id;
    private String name;

    /**
     * @see Comparator#compare(Object, Object) 
     */
    @Override
    public int compareTo(StudentRight other) {
        return Comparator.comparing(StudentRight::getName)
                .thenComparing(StudentRight::getId)
                .compare(this, other);
    }
}
