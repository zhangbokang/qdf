package com.mycharx.qdf.exception;

/**
 * 未授权异常
 *
 * @author 张卜亢
 * @date 2019.03.14 13:35:09
 */
public class QdfUnauthorizedException extends RuntimeException {
    public QdfUnauthorizedException(String msg) {
        super(msg);
    }

    public QdfUnauthorizedException() {
        super();
    }
}
