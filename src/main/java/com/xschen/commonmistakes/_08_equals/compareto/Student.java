package com.xschen.commonmistakes._08_equals.compareto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xschen
 * @date 2021/9/23 13:00
 */

@Slf4j
@Data
@AllArgsConstructor
public class Student implements Comparable<Student> {
    private int id;
    private String name;
    @Override
    public int compareTo(Student other) {
//        int result = Integer.compare(id, other.id); // 从小到大
        int result = Integer.compare(other.id, id); // 从大到小
        if (result == 0) {
            log.info("this {} == other {}", this, other);
        }
        return result;
    }
}
