package com.oneday.domain.vo;

import com.oneday.domain.po.User;


/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/2/27 17:06
 */
public class LoginResult extends User {
    private static final long serialVersionUID = 1L;
    private String url;
    private Long id;
    private String sdktoken;
    private String head;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
