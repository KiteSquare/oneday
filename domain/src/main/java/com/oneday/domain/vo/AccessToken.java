package com.oneday.domain.vo;

import java.io.Serializable;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/13 17:09
 */
public class AccessToken implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
