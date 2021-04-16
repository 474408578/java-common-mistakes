package com.xschen.commonmistakes.common.result;

import lombok.Data;

import java.util.List;

/**
 * 前端返回 List 格式
 * @author xschen
 */

@Data
public class ListResult<T> {
    /** 错误码 **/
    private Integer code;
    /** 成功或失败 **/
    private Boolean success;
    /** 提示信息 **/
    private String msg;
    /** 具体内容 **/
    private List<T> data;
}
