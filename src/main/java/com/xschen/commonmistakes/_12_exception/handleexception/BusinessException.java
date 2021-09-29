package com.xschen.commonmistakes._12_exception.handleexception;

/**
 * @author xschen
 * @date 2021/9/29 16:31
 */
public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }


    public int getCode() {
        return code;
    }
}
