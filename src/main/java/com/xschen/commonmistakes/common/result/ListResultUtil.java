package com.xschen.commonmistakes.common.result;

import java.util.List;

/**
 * @author xschen
 */

public class ListResultUtil {

    public static <T> ListResult<T> success(List<T> data) {
        ListResult<T> result = new ListResult();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setSuccess(ResultEnum.SUCCESS.getSuccess());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static ListResult success() {
        return success(null);
    }

    public static ListResult error(ResultEnum resultEnum) {
        ListResult result = new ListResult();
        result.setCode(resultEnum.getCode());
        result.setSuccess(resultEnum.getSuccess());
        result.setMsg(resultEnum.getMsg());
        return result;
    }

    public static <T> ListResult<T> error(List<T> data) {
        ListResult<T> result = new ListResult();
        result.setCode(ResultEnum.UNKNOWN_ERROR.getCode());
        result.setSuccess(ResultEnum.UNKNOWN_ERROR.getSuccess());
        result.setMsg(ResultEnum.UNKNOWN_ERROR.getMsg());
        result.setData(data);
        return result;
    }
}
