package com.oneday.vo;

import com.oneday.domain.po.User;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/10/12 16:16
 */
public class UserVo extends User {
    protected String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
