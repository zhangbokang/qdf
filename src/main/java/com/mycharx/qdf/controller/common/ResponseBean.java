package com.mycharx.qdf.controller.common;

import lombok.Data;

/**
 * 统一返回
 *
 * @author 张卜亢
 * @date 2019.03.14 13:19:47
 */
@Data
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

}
