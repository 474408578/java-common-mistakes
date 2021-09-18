package com.xschen.commonmistakes._15_serialization.enumusedinapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author xschen
 * @date 2021/9/8 14:59
 */

@RestController
@RequestMapping("enumusedinapi")
@Slf4j
public class EnumUsedInApiController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("getOrderStatusClient")
    public void getOrderStatusClient() {
        StatusEnumClient result = restTemplate.getForObject("http://localhost:8080/enumusedinapi/getOrderStatus", StatusEnumClient.class);
        log.info("result {}", result);
    }

    @GetMapping("getOrderStatus")
    public StatusEnumServer getOrderStatus() {
        return StatusEnumServer.CANCELED;
    }

    /**
     * 枚举序列化，反序列化实现自定义的字段 bug
     * @param enumServers
     * @return
     */
    @PostMapping("queryOrdersByStatusList")
    public List<StatusEnumServer> queryOrdersByStatus(@RequestBody List<StatusEnumServer> enumServers) {
        enumServers.add(StatusEnumServer.CANCELED);
        return enumServers;
    }

    /**
     * 序列化走了 status 的值，但是反序列化没有根据 status 来，还是用了枚举的ordinal() 索引值
     * 只能在 StatusEnumClient， StatusEnumServer 字段 status 中使用 JsonValue
     */
    @GetMapping("queryOrdersByStatusListClient")
    public void queryOrdersByStatusListClient() {
        List<StatusEnumClient> request = Arrays.asList(StatusEnumClient.CREATED, StatusEnumClient.PAID);
        HttpEntity<List<StatusEnumClient>> entity = new HttpEntity<>(request, new HttpHeaders());
        List<StatusEnumClient> response = restTemplate.exchange("http://localhost:8080/enumusedinapi/queryOrdersByStatusList",
                HttpMethod.POST, entity, new ParameterizedTypeReference<List<StatusEnumClient>>() {
                }).getBody();
        log.info("result {}", response);
        // result [DELIVERED, FINISHED, UNKNOWN]
    }
}
