package com.xschen.commonmistakes._04_connectionpool.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xschen
 */

@RestController
@Slf4j
@RequestMapping("improperdatasourcepoolsize")
public class ImproperDatasourcePoolSizeController {

    @Autowired
    private UserService userService;

    @GetMapping("test")
    public Object test() {
        return userService.register();
    }
}
