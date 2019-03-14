package com.mycharx.qdf.exception;

/**
 * 自定义异常
 *
 * @author 张卜亢
 * @date 2019.03.14 16:52:00
 */
public class QdfCustomException extends RuntimeException {
    public QdfCustomException(String msg) {
        super(msg);
    }

    public QdfCustomException() {
        super();
    }
}
