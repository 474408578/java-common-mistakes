package com.xschen.commonmistakes._11_nullvalue.pojonull.entity;

import lombok.Data;

import java.util.Optional;

/**
 * json 反序列化到 dto:
 *      客户端不传某属性，或者传null，这个属性在Dto中都是null 借助 Optional 解决.
 * @author xschen
 * @date 2021/9/28 17:08
 */

@Data
public class UserDTO {
    public Long id;
    private Optional<String> name;
    private Optional<Integer> age;
}
