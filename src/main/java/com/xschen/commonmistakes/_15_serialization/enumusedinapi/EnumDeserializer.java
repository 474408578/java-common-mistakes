package com.xschen.commonmistakes._15_serialization.enumusedinapi;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author xschen
 * @date 2021/9/8 17:54
 */
public class EnumDeserializer extends JsonDeserializer<Enum> implements ContextualDeserializer {

    /**
     * 枚举类
     */
    private Class<Enum> targetClass;

    public EnumDeserializer() {
    }

    public EnumDeserializer(Class<Enum> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public Enum deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        // 遍历枚举类中有 @JsonValue注解的字段，作为反序列化的基准字段
        Optional<Field> valueFieldOpt = Arrays.asList(targetClass.getDeclaredFields()).stream()
                .filter(field -> field.isAnnotationPresent(JsonValue.class))
                .findFirst();
        if (valueFieldOpt.isPresent()) {
            Field valueField = valueFieldOpt.get();
            if (!valueField.isAccessible()) {
                valueField.setAccessible(true);
            }
            // 遍历枚举项，查找字段的值等于反序列化的字符串的那个枚举项
            return Arrays.stream(targetClass.getEnumConstants()).filter(e -> {
                try {
                    return valueField.get(e).toString().equalsIgnoreCase(parser.getValueAsString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }).findFirst().orElseGet(()->Arrays.stream(targetClass.getEnumConstants()).filter(e -> {
                // 如果找不到，就需要查找默认枚举值来替代，同样遍历所有枚举项，查找@JsonEnumDefault 注解标识的枚举项
                try {
                    return targetClass.getField(e.name()).isAnnotationPresent(JsonEnumDefaultValue.class);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }).findFirst().orElse(null));
        }
        return null;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        targetClass = (Class<Enum>) ctxt.getContextualType().getRawClass();
        return new EnumDeserializer(targetClass);
    }
}
