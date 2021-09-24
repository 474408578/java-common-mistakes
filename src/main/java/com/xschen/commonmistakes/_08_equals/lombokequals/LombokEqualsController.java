package com.xschen.commonmistakes._08_equals.lombokequals;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xschen
 * @date 2021/9/23 15:50
 */

@RestController
@RequestMapping("lombokequals")
@Slf4j
public class LombokEqualsController {

    @GetMapping("test1")
    public void test1() {
        Person p1 = new Person("wang", "103");
        Person p2 = new Person("li", "103");
        log.info("p1.equals(p2) ? {}", p1.equals(p2)); // false
    }

    @GetMapping("test2")
    public void test2() {
        Employee e2 = new Employee("wang", "102", "foo");
        Employee e1 = new Employee("li", "103", "foo");
        log.info("e1.equals(e2) ? {}", e1.equals(e2));
    }
}
