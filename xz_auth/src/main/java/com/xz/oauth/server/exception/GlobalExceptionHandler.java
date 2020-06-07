package com.xz.oauth.server.exception;

import com.xz.oauth.server.domain.BaseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xz
 * @Description 全局异常处理
 * @date 2020/6/5 10:49
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public BaseResult exceptionHandler(Exception e) {
        return BaseResult.error(e.getMessage(), 500);
    }
}
