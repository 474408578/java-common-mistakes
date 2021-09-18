package com.xschen.commonmistakes._15_serialization.jsonignoreproperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xschen
 * @date 2021/9/7 8:59
 */

@RestController
@RequestMapping("jsonignoreproperties")
@Slf4j
public class JsonIgnorePropertiesController {

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("test")
    public void test() throws JsonProcessingException {
        log.info("color: {}", objectMapper.writeValueAsString(Color.BLUE));
        // color: "BLUE" ; color: 1
    }

    /**
     * 请求参数为，报错
     * {
     *     "name": "xschen",
     *     "version": "1"
     * }
     * @param userWrong
     * @return
     */
    @PostMapping("wrong")
    public UserWrong wrong(@RequestBody UserWrong userWrong) {
        return userWrong;
    }

    @PostMapping("right")
    public UserRight right(@RequestBody UserRight userRight) {
        return userRight;
    }

    enum Color {
        RED, BLUE
    }
}
