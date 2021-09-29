package com.xschen.commonmistakes._12_exception.handleexception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xschen
 * @date 2021/9/29 15:45
 */

@Slf4j
@RestController
@RequestMapping("handleexception")
public class HandleExceptionController {

    @GetMapping("exception")
    public void exception(@RequestParam("business") boolean bool) {
        if (bool) {
            throw new BusinessException("订单不存在", 2001);
        }
        throw new RuntimeException("系统错误");
    }



}
