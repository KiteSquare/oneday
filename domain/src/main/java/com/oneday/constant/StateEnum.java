package com.oneday.constant;

/**
 * 追求者状态
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 14:52
 */
public enum StateEnum {

    REJECT(8,"拒绝"),
    SEND(4,"发送"),
    ACCEPT(2,"接受"),
    ADMIT(1,"承认");

    private Integer status;
    private String desc;

    private StateEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
