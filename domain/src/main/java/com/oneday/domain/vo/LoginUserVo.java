package com.oneday.domain.vo;

import java.io.Serializable;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/2/27 17:06
 */
public class LoginUserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String phone;
    private String password;
    private String code;
    private String url;
    /**
     * 登录类型
     * 1 为phone+password
     * 2 为phone+code
     */
    private Integer type;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
}
