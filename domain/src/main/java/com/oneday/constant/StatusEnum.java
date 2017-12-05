package com.oneday.constant;


/**
 * 响应status枚举
 * Created by chender on 2017/12/04.
 */
public enum StatusEnum {
    OK("0","正常"),
    NEEDLOGIN("-1","未登录或会话已超时");


    private String code;

    private String name;

    StatusEnum(String code,String name) {
        this.code = code;
        this.setName(name);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
