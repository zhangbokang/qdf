package com.mycharx.qdf.controller.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 *
 * @author 张卜亢
 * @date 2019.03.14 16:21:53
 */
@Slf4j
@RestControllerAdvice
public class QdfExceptionController {

    /**
     * 捕捉shiro的异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseBean handle401(ShiroException e) {
        log.error(e.getMessage(),e);
        return new ResponseBean(401, e.getMessage(), null);
    }

    /**
     * 捕捉UnauthorizedException自定义未授权异常
     *
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseBean handle401(UnauthorizedException e) {
        log.error(e.getMessage(),e);
        return new ResponseBean(401, "Unauthorized", null);
    }

    /**
     * 捕捉其他所有异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBean globalException(HttpServletRequest request, Throwable ex) {
        log.error(ex.getMessage(),ex);
        return new ResponseBean(getStatus(request).value(), ex.getMessage(), null);
    }

    /**
     * 获取HttpStatus状态码
     *
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

