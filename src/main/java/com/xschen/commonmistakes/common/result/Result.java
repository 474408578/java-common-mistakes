package com.xschen.commonmistakes.common.result;

import lombok.Data;

/**
 * 前端返回格式
 * @author xschen
 */

@Data
public class Result<T> {

    /** 错误码 **/
    private Integer code;
    /** 成功或失败 **/
    private Boolean success;
    /** 提示信息 **/
    private String msg;
    /** 具体内容 **/
    private T data;
}
