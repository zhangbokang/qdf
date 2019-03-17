package com.mycharx.qdf.controller.common;

import com.mycharx.qdf.common.JsonConvertUtil;
import com.mycharx.qdf.exception.QdfCustomException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 统一返回
 *
 * @author 张卜亢
 * @date 2019.03.14 13:19:47
 */
@Data
@Slf4j
public class ResponseBean<T> {

    /**
     * http 状态码
     */
    private int code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T data;

    public ResponseBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 将非法请求跳转到 /401
     */
    public static void response401(ServletRequest req, ServletResponse resp, String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = httpServletResponse.getWriter()) {
            String data = JsonConvertUtil.objectToJson(new ResponseBean(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + msg, null));
            out.append(data);
        } catch (IOException e) {
            log.error("直接返回Response信息出现IOException异常:" + e.getMessage());
            throw new QdfCustomException("直接返回Response信息出现IOException异常:" + e.getMessage());
        }
    }
}
