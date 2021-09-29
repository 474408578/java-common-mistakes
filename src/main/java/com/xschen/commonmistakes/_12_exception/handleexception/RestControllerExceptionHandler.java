package com.xschen.commonmistakes._12_exception.handleexception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xschen
 * @date 2021/9/29 16:28
 */

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {

    private static int GENERIC_SERVER_ERROR_CODE = 200;
    private static String GENERIC_SERVER_ERROR_MESSAGE = "服务器忙，请稍后重试";

    @ExceptionHandler
    public APIResponse handler(HttpServletRequest request, HandlerMethod method, Exception ex) {
        if (ex instanceof BusinessException) {
            BusinessException exception = (BusinessException) ex;
            log.warn(String.format("访问 %s -> %s 出现业务异常！", request.getRequestURI(), method.toString()), ex);
            return new APIResponse(false, null, exception.getCode(), exception.getMessage());
        } else {
            log.error(String.format("访问 %s -> %s 出现系统异常！", request.getRequestURI(), method.toString()), ex);
            return new APIResponse(false, null, GENERIC_SERVER_ERROR_CODE, GENERIC_SERVER_ERROR_MESSAGE);
        }
    }
}
