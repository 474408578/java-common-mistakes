package com.xschen.commonmistakes._11_nullvalue.pojonull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.xschen.commonmistakes._11_nullvalue.pojonull.entity.User;
import com.xschen.commonmistakes._11_nullvalue.pojonull.entity.UserDTO;
import com.xschen.commonmistakes._11_nullvalue.pojonull.entity.UserEntity;
import com.xschen.commonmistakes._11_nullvalue.pojonull.repository.UserEntityRepository;
import com.xschen.commonmistakes._11_nullvalue.pojonull.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xschen
 * @date 2021/9/28 15:19
 */

@Slf4j
@RestController
@RequestMapping("pojonull")
public class PojoNullController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserEntityRepository userEntityRepository;

    /**
     * Optional<String> name; 在json 反序列化中：
     *      DTO 未传值 name 时， 反序列化得到 name = null
     *      DTO 传值 name = null 时， 反序列化得到 name = Optional.empty
     */
    @GetMapping("test")
    public void test() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module()); // 注册 JDK8 module
        UserDTO result = objectMapper.readValue("{\"id\":\"1\", \"age\":30, \"name\":null}", UserDTO.class);
        log.info("field name with null value dto: {}, name: {}", result, result.getName().orElse("N/A"));
        // field name with null value dto: UserDto(id=1, name=Optional.empty, age=Optional[30]), name: N/A
        log.info("miss field name dto: {}", objectMapper.readValue("{\"id\": \"1\", \"age\": 30}", UserDTO.class));
        // miss field name dto: UserDto(id=1, name=null, age=Optional[30])
    }

    @PostMapping("wrong")
    public User wrong(@RequestBody User user) {
        user.setNickName(String.format("guest%s", user.getName()));
        return userRepository.save(user);
    }

    @GetMapping("right")
    public UserEntity right(@RequestBody UserDTO userDTO) {
        if (userDTO == null || userDTO.getId() == null) { // 传递无id值的json串认证.
            throw new IllegalArgumentException("用户Id不能为空");
        }

        UserEntity userEntity = userEntityRepository.findById(userDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("用户名不存在"));
        if (userDTO.getName() != null) { // name 字段显式传递了的
            userEntity.setName(userDTO.getName().orElse(""));
        }
        userEntity.setNickName("guest" + userEntity.getName());
        if (userDTO.getAge() != null) { // age 字段时显式传了的
            userEntity.setAge(userDTO.getAge().orElseThrow(() -> new IllegalArgumentException("年龄不能为空")));
        }
        return userEntityRepository.save(userEntity);
    }
}
