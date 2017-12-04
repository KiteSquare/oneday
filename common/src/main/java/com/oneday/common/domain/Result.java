package com.oneday.common.domain;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/13 17:25
 */
public class Result {
    public static final String STATUS_OK = "0";
    public static final String CODE_OK = "0";

    private String status;
    private String code;
    private String message;
    private Object data;

    public Result() {
        this.status = STATUS_OK;
        this.code = CODE_OK;
    }

    public Result(String code) {
        this.status = STATUS_OK;
        this.code = code;
        this.message="操作成功";
    }

    public Result(String code, String message) {
        this.status = STATUS_OK;
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

    /**
     * 构造带提示消息的响应结果
     * 适用于前段需要显示后端提供的message的场景
     * @param data
     * @param message
     * @return
     */
    public static Result success(Object data,String message) {
        Result result = new Result(CODE_OK,message);
        result.setData(data);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result(CODE_OK);
        result.setData(data);
        return result;
    }

    /**
     * 业务层失败
     *
     * @param code
     * @param message
     * @return
     */
    public static Result bizFailure(String code, String message) {
        return new Result(code, message);
    }

    /**
     * 系统层失败
     *
     * @param status
     * @param message
     * @return
     */
    public static Result systemFailure(String status, String message) {
        Result result=new Result();
        result.setStatus(status);
        result.setMessage(message);
        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
