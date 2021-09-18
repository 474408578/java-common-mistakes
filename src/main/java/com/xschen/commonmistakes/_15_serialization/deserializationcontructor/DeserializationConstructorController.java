package com.xschen.commonmistakes._15_serialization.deserializationcontructor;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 反序列化时要小心类的构造方法
 * @author xschen
 * @date 2021/9/7 18:38
 */

@RestController
@RequestMapping("deserializationconstructor")
@Slf4j
public class DeserializationConstructorController {

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("wrong")
    public void wrong() throws JsonProcessingException {
        log.info("result: {}", objectMapper.readValue("{\"code\": 1234}", ApiResultWrong.class));
        log.info("result: {}", objectMapper.readValue("{\"code\": 2000}", ApiResultWrong.class));
        // result: ApiResultWrong(success=false, code=1234)
        // result: ApiResultWrong(success=false, code=2000)
    }

    @GetMapping("right")
    public void right() throws JsonProcessingException {
        log.info("result: {}", objectMapper.readValue("{\"code\": 1234}", ApiResultRight.class));
        log.info("result: {}", objectMapper.readValue("{\"code\": 2000}", ApiResultRight.class));
        // result: ApiResultRight(success=false, code=1234)
        // result: ApiResultRight(success=true, code=2000)
    }

}

