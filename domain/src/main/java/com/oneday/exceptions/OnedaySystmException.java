package com.oneday.exceptions;

/**
 * 通用异常
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 16:26
 */
public class OnedaySystmException extends RuntimeException {
    protected String code;
    public OnedaySystmException(String code, String message) {
        super(message);
        this.code = code;
    }

    public OnedaySystmException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
