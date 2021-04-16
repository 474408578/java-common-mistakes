package com.xschen.commonmistakes.common.result;

/**
 * @author xschen
 */

public class ResultUtil {

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setSuccess(ResultEnum.SUCCESS.getSuccess());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setSuccess(resultEnum.getSuccess());
        result.setMsg(resultEnum.getMsg());
        return result;
    }

    public static <T> Result<T> error(T data) {
        Result<T> result = new Result();
        result.setCode(ResultEnum.UNKNOWN_ERROR.getCode());
        result.setSuccess(ResultEnum.UNKNOWN_ERROR.getSuccess());
        result.setMsg(ResultEnum.UNKNOWN_ERROR.getMsg());
        result.setData(data);
        return result;
    }
}
