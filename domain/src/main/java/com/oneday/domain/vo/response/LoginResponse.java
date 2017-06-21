package com.oneday.domain.vo.response;


import java.io.Serializable;


/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/2/27 17:06
 */
public class LoginResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;
    private String sdktoken;
    private String accessToken;
    /**
     * 登录类型
     * 1 为phone+password
     * 2 为phone+code
     */
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSdktoken() {
        return sdktoken;
    }

    public void setSdktoken(String sdktoken) {
        this.sdktoken = sdktoken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
