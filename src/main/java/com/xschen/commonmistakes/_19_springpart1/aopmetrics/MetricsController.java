package com.xschen.commonmistakes._19_springpart1.aopmetrics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xschen
 */

@Slf4j
@RestController
@RequestMapping("metricstest")
public class MetricsController {

    @Autowired
    private UserService userService;
    @GetMapping("transaction")
    public int transaction(@RequestParam("name") String name) {
        try {
            userService.createUser(new UserEntity(name));
        } catch (Exception e) {
            log.error("create user failed because {}", e.getMessage());
        }
        return userService.getUserCount(name);
    }
}
