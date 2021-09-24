package com.xschen.commonmistakes._08_equals.equalitymethod;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * @author xschen
 * @date 2021/9/23 11:18
 */

@RestController
@Slf4j
@RequestMapping("equalitymethod")
public class EqualityMethodController {


    // 未重写 equals() 调用的是Object的equals(), 比较的是引用。
    @GetMapping("wrong")
    public void wrong() {
        Point p1 = new Point(1, 2, "a");
        Point p2 = new Point(1, 2, "b");
        Point p3 = new Point(1, 2, "a");
        log.info("p1.equals(p2) ? {}", p1.equals(p2));
        log.info("p1.equals(p3) ? {}", p1.equals(p3));
    }

    @GetMapping("wrong2")
    public void wrong2() {
        PointWrong p1 = new PointWrong(1, 2, "a");
        // 空指针异常
        try {
            log.info("p1.equals(null) ? {}", p1.equals(null));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        // 类型转换异常
        Object obj = new Object();
        try {
            log.info("p1.equals(obj) ? {}", p1.equals(obj));
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        // 正确输出
        PointWrong p2 = new PointWrong(1, 2, "b");
        log.info("p1.equals(p2) ? {}", p1.equals(p2)); // true

        // 按照改进后的equals方法，p1.equals(p2), set中包含p1, 就应该包含p2,结果为false，因为散列表需要使用hashcode来定位元素放到哪个桶.
        HashSet<PointWrong> points = new HashSet<>();
        points.add(p1);
        log.info("HashSet: points.contains(p2) ? {}", points.contains(p2)); // false
    }

    @GetMapping("right")
    public void right() {
        PointRight p1 = new PointRight(1, 2, "a");
        log.info("p1.equals(null) ? {}", p1.equals(null));

        Object obj = new Object();
        log.info("p1.equals(obj) ? {}", p1.equals(obj));

        PointRight p2 = new PointRight(1, 2, "b");
        log.info("p1.equals(p2) ? {}", p1.equals(p2));
        HashSet<PointRight> points = new HashSet<>();
        points.add(p1);
        log.info("points.contains(p2) ? {}", points.contains(p2));
    }
}
