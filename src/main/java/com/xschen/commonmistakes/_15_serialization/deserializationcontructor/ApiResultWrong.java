package com.xschen.commonmistakes._15_serialization.deserializationcontructor;

import lombok.Data;

/**
 * @author xschen
 * @date 2021/9/7 18:35
 */

@Data
public class ApiResultWrong {
    private boolean success;
    private int code;

    public ApiResultWrong() {
    }

    public ApiResultWrong(int code) {
        this.code = code;
        if (code == 2000)
            success = true;
        else
            success = false;
    }
}
