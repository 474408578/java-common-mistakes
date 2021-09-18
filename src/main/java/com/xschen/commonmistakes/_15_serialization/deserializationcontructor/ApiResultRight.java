package com.xschen.commonmistakes._15_serialization.deserializationcontructor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 默认情况下，在反序列化的时候，Jackson 框架只会调用无参构造方法创建对象,
 * 如果走自定义的构造方法创建对象，需要通过 @JsonCreator 来指定构造方法，
 * 并通过 @JsonProperty 设置构造方法中参数对应的 JSON 属性名
 *
 * @author xschen
 * @date 2021/9/7 18:37
 */

@Data
public class ApiResultRight {

    private boolean success;
    private int code;

    public ApiResultRight() {
    }

    @JsonCreator
    public ApiResultRight(@JsonProperty("code") int code) {
        this.code = code;
        if (code == 2000)
            success = true;
        else
            success = false;
    }
}
