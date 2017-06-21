package com.oneday.domain.vo.request;

import com.oneday.domain.po.User;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/10/12 16:16
 */
public class UserUpdateRequest extends User {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
