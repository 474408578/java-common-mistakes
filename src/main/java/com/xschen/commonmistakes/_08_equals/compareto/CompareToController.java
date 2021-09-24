package com.xschen.commonmistakes._08_equals.compareto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@link Comparable#compareTo(Object)} 和 equals 的逻辑一致性
 * @author xschen
 * @date 2021/9/23 12:35
 */

@RestController
@RequestMapping("compareto")
@Slf4j
public class CompareToController {



    @GetMapping("wrong")
    public void wrong() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "zhang"));
        list.add(new Student(2, "wang"));

        Student student = new Student(2, "li");
        log.info("ArrayList.indexOf");
        int index1 = list.indexOf(student); // -1 找不到
        Collections.sort(list);
        log.info("Collections.binarySearch");
        // 底层调用的是Comparable#compareTo(Object)来比较
        int index2 = Collections.binarySearch(list, student); // 1 找到

        log.info("index1 = " + index1);
        log.info("index2 = " + index2);
    }

    @GetMapping("right")
    public void right() {
        List<StudentRight> list = new ArrayList<>();
        list.add(new StudentRight(1, "zhang"));
        list.add(new StudentRight(2, "wang"));
        StudentRight student = new StudentRight(2, "li");

        log.info("ArrayList.indexOf");
        int index1 = list.indexOf(student);
        Collections.sort(list);
        log.info("Collections.binarySearch");
        int index2 = Collections.binarySearch(list, student);
        log.info("index1 = " + index1); // -1
        log.info("index2 = " + index2); // -1
    }
}
