package com.oneday.constant;

/**
 * 追求者状态
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 14:52
 */
public enum HunterEnum {
    NOTHING(0,"无状态"),
    SINGLE(8,"单身"),
    WAITING(4,"等待"),
    HOLD(2,"暂定"),
    SUCCESS(1,"确定");

    private Integer status;
    private String desc;

    private HunterEnum(Integer status, String desc) {
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
