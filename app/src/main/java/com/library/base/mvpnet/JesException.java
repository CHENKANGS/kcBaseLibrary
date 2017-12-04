package com.library.base.mvpnet;

/**
 * Created by ChenKang on 2017/11/21.
 */

public class JesException extends RuntimeException {

    private int code;

    public JesException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
