package com.xschen.commonmistakes.common.result;

/**
 * @author xschen
 *
 * @see <a href="https://www.cnblogs.com/apocelipes/p/13601335.html">https://www.cnblogs.com/apocelipes/p/13601335.html</a>
 */

public enum ResultEnum {
    SUCCESS(200, true, "success"),
    BAD_REQUEST(400, false, "bad Request"),
    SERVER_ERROR(500, false, "server error"),
    UNKNOWN_ERROR(-1, false, "unknown error"),
    ;

    private final Integer code;
    private final Boolean success;
    private final String msg;

    ResultEnum(Integer code, Boolean success, String msg) {
        this.code = code;
        this.success = success;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }
}
