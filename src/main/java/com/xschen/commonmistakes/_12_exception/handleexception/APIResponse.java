package com.xschen.commonmistakes._12_exception.handleexception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * API 通用响应体
 * @author xschen
 * @date 2021/9/29 16:15
 */

@Data
@AllArgsConstructor
public class APIResponse<T> {
    private boolean success;
    private T data;
    private int code;
    private String message;
}
