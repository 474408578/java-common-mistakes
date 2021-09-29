package com.xschen.commonmistakes._11_nullvalue.dbnull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author xschen
 * @date 2021/9/29 15:11
 */

@Slf4j
@RestController
@RequestMapping("dbnull")
public class DbNullController {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        // id = 1, score = null
        userRepository.save(new User());
    }

    @GetMapping("wrong")
    public void wrong() throws NoSuchMethodException {

        log.info("Query:{}, Result: {}", UserRepository.class.getDeclaredMethod("wrong1").getAnnotation(Query.class).value(),
                userRepository.wrong1());
        log.info("Query:{}, Result: {}", UserRepository.class.getDeclaredMethod("wrong2").getAnnotation(Query.class).value(),
                userRepository.wrong2());
        log.info("Query:{}, Result: {}", UserRepository.class.getDeclaredMethod("wrong3").getAnnotation(Query.class).value(),
                userRepository.wrong3());
    }

    @GetMapping("right")
    public void right() throws NoSuchMethodException {
        log.info("Query:{}, Result: {}", UserRepository.class.getDeclaredMethod("right1").getAnnotation(Query.class).value(),
                userRepository.right1());
        log.info("Query:{}, Result: {}", UserRepository.class.getDeclaredMethod("right2").getAnnotation(Query.class).value(),
                userRepository.right2());
        log.info("Query:{}, Result: {}", UserRepository.class.getDeclaredMethod("right3").getAnnotation(Query.class).value(),
                userRepository.right3());
    }
}
