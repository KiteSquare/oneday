package com.oneday.vo;

import com.oneday.domain.po.User;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/10/12 16:16
 */
public class UserVo extends User {
    protected String code;

    protected String birthStr;

    protected Integer step;

    public String getBirthStr() {
        return birthStr;
    }

    public void setBirthStr(String birthStr) {
        this.birthStr = birthStr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
