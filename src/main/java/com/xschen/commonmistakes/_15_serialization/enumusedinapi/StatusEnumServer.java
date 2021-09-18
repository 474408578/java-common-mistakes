package com.xschen.commonmistakes._15_serialization.enumusedinapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author xschen
 * @date 2021/9/8 14:42
 */

@Getter
public enum StatusEnumServer {
    CREATED(1, "已创建"),
    PAID(2, "已支付"),
    DELIVERED(3, "已送到"),
    FINISHED(4, "已完成"),
    CANCELED(5, "已取消"),
    @JsonEnumDefaultValue
    UNKNOWN(-1, "未知");

    @JsonValue
    private final int status;
    // 让枚举的序列化和反序列化走 desc 字段
//    @JsonValue
    private final String desc;

    StatusEnumServer(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    /**
     * 设置JsonCreator 来强制反序列化时使用自定义的工厂方法，
     * 实现使用枚举的status字段来取值
     * @param o
     * @return
     */
    @JsonCreator
    public static StatusEnumServer parse(Object o) {
        return Arrays.stream(StatusEnumServer.values()).filter(value -> o.equals(value.status)).findFirst().orElse(null);
    }
}
