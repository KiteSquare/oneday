package com.oneday.common.domain;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/13 17:25
 */
public class Result {
    private String code;
    private String message;
    private Object data;
    public Result() {
        this("0", "成功!");
    }
    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result create(String code, String result) {
        return new Result(code, result);
    }

    public static Result success() {
        return success("操作成功!");
    }

    public static Result success(String msg) {
        return create("success", msg);
    }

    public static Result failure() {
        return failure("操作失败!");
    }

    public static Result failure(String msg) {
        return create("failure", msg);
    }

    public static Result failure(Exception ex) {
        return failure("系统异常:" + ex.getMessage(), ex);
    }

    public static Result failure(String message, Exception ex) {
        if(ex == null) {
            return failure();
        } else {
            Result msg = failure(message);

            try {
                StringWriter e = new StringWriter();
                ex.printStackTrace(new PrintWriter(e));
                msg.setData(e.toString());
            } catch (Exception var4) {
                ;
            }

            return msg;
        }
    }
}
