package com.oneday.constant;

/**
 * 各种key值定义
 * Created by chender on 2017/12/04.
 */
public enum HttpKeyEnum {
    HTTPHEADTOKEN("accessToken"),
    REQUESTATTIBUTERUSER("oneday_user");

    private String key;

    private HttpKeyEnum(String key){
        this.key=key;
    }

    public String getKey() {
        return key;
    }
}
