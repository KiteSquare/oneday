package com.oneday.constant;


/**
 * 响应status枚举
 * Created by chender on 2017/12/04.
 */
public enum StatusEnum {
    OK("0"),
    NEEDLOGIN("-1");


    private String code;

    StatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
